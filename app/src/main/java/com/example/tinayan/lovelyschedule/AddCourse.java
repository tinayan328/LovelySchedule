package com.example.tinayan.lovelyschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tinayan.lovelyschedule.adapter.CourseSelectorAdapter;
import com.example.tinayan.lovelyschedule.data_models.PersonalDataHolder;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ICourseInfo;

import java.util.Calendar;
import java.util.List;

public class AddCourse extends AppCompatActivity {

    private ListView courseList;

    private String getCurrentTerm() {
        Calendar currendDate = Calendar.getInstance();
        String season;
        if(currendDate.get(Calendar.MONTH) <= 4) {
            season = " Winter";
        } else if (currendDate.get(Calendar.MONTH) <= 8) {
            season = " Spring";
        } else {
            season = " Fall";
        }
        return currendDate.get(Calendar.YEAR) + season + "Term";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView termName = (TextView) findViewById(R.id.TermName);
        termName.setText(getCurrentTerm());

        courseList = (ListView)findViewById(R.id.CourseList);

        PersonalDataHolder CourseData = PersonalDataHolder.getInstance();

        List<ICourseInfo> courseInfoList = CourseData.getCourseList();

        ListAdapter currentCourses = new CourseSelectorAdapter(this,
                R.layout.course_selector_fragment,
                courseInfoList.toArray(new ICourseInfo[courseInfoList.size()]));
        courseList.setAdapter(currentCourses);
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

    public void onAddCourseClick(View view) {
        Intent addCourseIntent = new Intent(this, SelectList.class);
        finish();
        startActivity(addCourseIntent);
    }
}
