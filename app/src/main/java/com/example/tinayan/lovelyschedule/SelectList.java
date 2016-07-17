package com.example.tinayan.lovelyschedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.tinayan.lovelyschedule.adapter.CourseSelectorAdapter;
import com.example.tinayan.lovelyschedule.adapter.SubjectSelectorAdapter;
import com.example.tinayan.lovelyschedule.data_models.IPublicCallBack;
import com.example.tinayan.lovelyschedule.data_models.IPublicDataHolder;
import com.example.tinayan.lovelyschedule.data_models.PersonalDataHolder;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ICourseInfo;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ISubjectInfo;
import com.example.tinayan.lovelyschedule.data_models.uw.PublicDataHolderUW;

import java.util.List;

public class SelectList extends AppCompatActivity {

    private String Subject;
    private Context self = this;

    private LinearLayout Loading;
    private LinearLayout ResponseFail;

    private void setErrorPage(String errorMessage) {
        System.out.println(errorMessage);
        Loading.setVisibility(View.INVISIBLE);
        ResponseFail.setVisibility(View.VISIBLE);
    }

    private void getCourseList(String SubjectName) {

        IPublicDataHolder dataHolder = PublicDataHolderUW.getInstance(this);

        try {
            dataHolder.getAllCourses(SubjectName, new IPublicCallBack<List<ICourseInfo>>() {
                @Override
                public void callBackMethod(List<ICourseInfo> input) {
                    ListView courseList = (ListView) findViewById(R.id.CourseList);

                    ListAdapter courseListAdapter = new CourseSelectorAdapter(self,
                            R.layout.course_selector_fragment,
                            input.toArray(new ICourseInfo[input.size()]),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onAddCourseClick(v);
                                }
                            });

                    courseList.setAdapter(courseListAdapter);
                    Loading.setVisibility(View.INVISIBLE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setErrorPage(error.getMessage());
                }
            });
        } catch (Exception e) {
            setErrorPage("Exception when getting data list: " + e.getMessage());
        }

    }

    private void getSubjectList() {

        IPublicDataHolder dataHolder = PublicDataHolderUW.getInstance(this);

        try {
            dataHolder.getAllSubject(new IPublicCallBack<List<ISubjectInfo>>() {
                @Override
                public void callBackMethod(List<ISubjectInfo> input) {
                    ListView subjectList = (ListView)findViewById(R.id.CourseList);

                    ListAdapter subjectListAdapter = new SubjectSelectorAdapter(self,
                            R.layout.course_selector_fragment,
                            input.toArray(new ISubjectInfo[input.size()]),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onSubjectClick(v);
                                }
                            });

                    subjectList.setAdapter(subjectListAdapter);
                    Loading.setVisibility(View.INVISIBLE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setErrorPage(error.getMessage());
                }
            });
        } catch (Exception e) {
            setErrorPage("Exception when getting data list: " + e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Loading = (LinearLayout) findViewById(R.id.LoadingSpinner);
        ResponseFail = (LinearLayout) findViewById(R.id.getResponseFail);

        getSubjectList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSubjectClick (View view) {

        TextView SubjectTitle = (TextView)findViewById(R.id.TermName);
        try {
            SubjectTitle.setText(view.getTag().toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ListView SubjectList = (ListView)findViewById(R.id.CourseList);
        try {
            SubjectList.setAdapter(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Loading.setVisibility(View.VISIBLE);

        getCourseList(view.getTag().toString());
    }

    public void onAddCourseClick(View view) {
        PersonalDataHolder dataHolder = PersonalDataHolder.getInstance();
        ICourseInfo correspondingCourse = (ICourseInfo)view.getTag();
        dataHolder.addCourse(correspondingCourse.getAbbName(),
                correspondingCourse);

        Intent addCourseIntent = new Intent(this, AddCourse.class);
        finish();
        startActivity(addCourseIntent);
    }
}
