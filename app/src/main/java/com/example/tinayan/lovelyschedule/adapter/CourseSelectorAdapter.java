package com.example.tinayan.lovelyschedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tinayan.lovelyschedule.R;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ICourseInfo;

/**
 * Created by tinayan on 19/06/16.
 */
public class CourseSelectorAdapter extends ArrayAdapter<ICourseInfo> {

    private View.OnClickListener onClickListener = null;

    public CourseSelectorAdapter(Context context, int layoutResourceId, ICourseInfo[] courseList) {
        super(context, layoutResourceId, courseList);
    }

    public CourseSelectorAdapter(Context context, int layoutResourceId, ICourseInfo[] courseList,
                                 View.OnClickListener listener) {
        super(context, layoutResourceId, courseList);
        onClickListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View courseSelectorView = convertView;

        if(courseSelectorView == null) {
            LayoutInflater courseSelectorInflator = LayoutInflater.from(getContext());
            courseSelectorView = courseSelectorInflator.inflate(R.layout.course_selector_fragment,
                    parent, false);
        }

        ICourseInfo course = getItem(position);

        if(course != null) {
            TextView subject = (TextView) courseSelectorView.findViewById(R.id.courseSelectorSubject);

            if(subject != null) {
                subject.setText(course.getAbbName());
            }

            TextView fullName = (TextView) courseSelectorView.findViewById(R.id.courseSelectorName);
            if(fullName != null) {
                fullName.setText(course.getName());
            }

            if(onClickListener != null) {
                LinearLayout selector = (LinearLayout) courseSelectorView.findViewById(R.id.Selector);
                selector.setOnClickListener(onClickListener);
                selector.setTag(course);
            }
        }

        return courseSelectorView;
    }

}
