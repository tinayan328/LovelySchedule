package com.example.tinayan.lovelyschedule.data_models;

import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ICourseInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tinayan on 18/06/16.
 */
public class PersonalDataHolder {

    final private static int defaultCourseLoad = 5;

    private Map<String, ICourseInfo> CourseList;

    private static class instanceHolder {
        static PersonalDataHolder instance = new PersonalDataHolder();
    }

    public static PersonalDataHolder getInstance() {
        return instanceHolder.instance;
    }

    private PersonalDataHolder() {
        CourseList = new HashMap<>(defaultCourseLoad);
    }

    public void addCourse(String CourseName, ICourseInfo Course) {
        CourseList.put(CourseName, Course);
    }

    public void dropCourse(String CourseName) {
        CourseList.remove(CourseName);
    }

    public ICourseInfo getCourse(String CourseName) {
        return CourseList.get(CourseName);
    }

    public List<ICourseInfo> getCourseList() {
        return new ArrayList<>(CourseList.values());
    }
}
