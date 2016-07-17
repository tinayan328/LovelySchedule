package com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw;

import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ICourseInfo;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ISubjectInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.tinayan.lovelyschedule.data_models.uw.API_connection.UWAPIConstants.CatalogNum;
import static com.example.tinayan.lovelyschedule.data_models.uw.API_connection.UWAPIConstants.CourseFullName;
import static com.example.tinayan.lovelyschedule.data_models.uw.API_connection.UWAPIConstants.DataEntryName;
import static com.example.tinayan.lovelyschedule.data_models.uw.API_connection.UWAPIConstants.SubjectFullName;
import static com.example.tinayan.lovelyschedule.data_models.uw.API_connection.UWAPIConstants.SubjectName;

/**
 * Created by tinayan on 19/06/16.
 */
public class JsonDeserializer {

    public static Map<String, List<ICourseInfo>> deserializeCourseInfoList (final JsonObject result) throws JsonParseException {
        final JsonArray dataList = result.get(DataEntryName).getAsJsonArray();
        final Map<String, List<ICourseInfo>> returnResult = new HashMap<>();
        for(Iterator it = dataList.iterator(); it.hasNext();) {
            final JsonObject course = ((JsonElement) it.next()).getAsJsonObject();
            final String subjectName = course.get(SubjectName).getAsString();
            ICourseInfo newCourse = new CourseInfoUW(subjectName,
                    course.get(CatalogNum).getAsString(),
                    course.get(CourseFullName).getAsString());
            if(!returnResult.containsKey(subjectName)) {
                returnResult.put(subjectName, new ArrayList<ICourseInfo>());
            }
            returnResult.get(subjectName).add(newCourse);
        }
        return returnResult;
    }

    public Map<String, ISubjectInfo> deserializeSubjectInfo(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject result = json.getAsJsonObject();
        final JsonArray dataList = result.get(DataEntryName).getAsJsonArray();
        final Map<String, ISubjectInfo> returnResult = new HashMap<>();
        for(Iterator it = dataList.iterator(); it.hasNext(); ){
            final JsonObject subject = ((JsonElement) it.next()).getAsJsonObject();
            final String subjectName = subject.get(SubjectName).getAsString();
            final ISubjectInfo subjectInfo = new SubjectInfoUW(subjectName,
                    subject.get(SubjectFullName).getAsString());
            returnResult.put(subjectName, subjectInfo);
        }
        return returnResult;
    }
}
