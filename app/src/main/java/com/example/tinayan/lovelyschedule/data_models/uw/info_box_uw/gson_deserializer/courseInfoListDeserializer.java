package com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw.gson_deserializer;

import static com.example.tinayan.lovelyschedule.data_models.uw.API_connection.UWAPIConstants.*;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ICourseInfo;
import com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw.CourseInfoUW;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by tinayan on 18/06/16.
 */
/*
{
  "meta":{
    "requests":796933,
    "timestamp":1452284471,
    "status":200,
    "message":"Request successful",
    "method_id":1511,
    "method":{

    }
  },
  "data":[
    {
      "subject":"ACC",
      "catalog_number":"607",
      "units":0.5,
      "title":"Tax Issues Integration"
    },
    {
      "subject":"ACC",
      "catalog_number":"611",
      "units":0.5,
      "title":"External Reporting"
    }
  ]
}
 */
public class courseInfoListDeserializer implements JsonDeserializer<Map<String, List<ICourseInfo>>> {
    @Override
    public Map<String, List<ICourseInfo>> deserialize(final JsonElement json, final Type typeOfT,
                                         final JsonDeserializationContext context) throws JsonParseException {
        final JsonObject result = json.getAsJsonObject();
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
}
