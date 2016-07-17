package com.example.tinayan.lovelyschedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tinayan.lovelyschedule.R;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ISubjectInfo;

/**
 * Created by tinayan on 19/06/16.
 */
public class SubjectSelectorAdapter extends ArrayAdapter<ISubjectInfo> {

    private View.OnClickListener onClickListener = null;

    public SubjectSelectorAdapter(Context context, int layoutResourceId, ISubjectInfo[] subjectList) {
        super(context, layoutResourceId, subjectList);
    }

    public SubjectSelectorAdapter(Context context, int layoutResourceId, ISubjectInfo[] subjectList,
                                 View.OnClickListener listener) {
        super(context, layoutResourceId, subjectList);
        onClickListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View courseSelectorView = convertView;

        if(courseSelectorView == null) {
            LayoutInflater courseSelectorInflater = LayoutInflater.from(getContext());
            courseSelectorView = courseSelectorInflater.inflate(R.layout.course_selector_fragment,
                    parent, false);
        }

        ISubjectInfo subject = getItem(position);

        if(subject != null) {
            LinearLayout selector = (LinearLayout) courseSelectorView.findViewById(R.id.Selector);

            if(onClickListener != null) {
                selector.setOnClickListener(onClickListener);
            }

            TextView Subject = (TextView) courseSelectorView.findViewById(R.id.courseSelectorSubject);

            Subject.setText(subject.getSubAbb());
            selector.setTag(subject.getSubAbb());

            TextView fullName = (TextView) courseSelectorView.findViewById(R.id.courseSelectorName);
            if(fullName != null) {
                fullName.setText(subject.getSubFull());
            }
        }

        return courseSelectorView;
    }
}
