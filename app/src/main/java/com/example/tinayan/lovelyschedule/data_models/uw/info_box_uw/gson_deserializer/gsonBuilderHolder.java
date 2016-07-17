package com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw.gson_deserializer;

import com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw.CourseInfoUW;
import com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw.SubjectInfoUW;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by tinayan on 18/06/16.
 */

//TODO: distinguish different between specific map... (courseInfo or subjectInfo)
public class gsonBuilderHolder {

    private static GsonBuilder instance = null;

    public static Gson getParser() {
        if(instance == null) {
            instance = new GsonBuilder();
            instance.registerTypeAdapter(CourseInfoUW.class,
                    new courseInfoListDeserializer());
            instance.registerTypeAdapter(SubjectInfoUW.class,
                    new subjectInfoListDeserializer());
        }
        return instance.create();
    }
}
