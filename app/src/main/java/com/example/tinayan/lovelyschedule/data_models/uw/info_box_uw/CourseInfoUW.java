package com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw;

import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ICourseInfo;

/**
 * Created by tinayan on 18/06/16.
 */
public class CourseInfoUW implements ICourseInfo {

    private String subject;
    private String courseNumber;
    private String name;

    public CourseInfoUW(String subject, String courseNumber, String name) {
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.name = name;
    }

    public String getAbbName() {
        return String.format("%1$s %2$s", subject, courseNumber);
    }

    public String getName() {
        return this.name;
    }
}
