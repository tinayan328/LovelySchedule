package com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw.gson_deserializer;

import static com.example.tinayan.lovelyschedule.data_models.uw.API_connection.UWAPIConstants.*;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ISubjectInfo;
import com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw.SubjectInfoUW;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tinayan on 19/06/16.
 */
/*
{
  "meta":{
    "requests":104,
    "timestamp":1444838214,
    "status":200,
    "message":"Request successful",
    "method_id":1237,
    "method":{

    }
  },
  "data":[
    {
      "subject":"AADMS",
      "description":"Arts Administration Specialization Seminar",
      "unit":"ARTSDEAN",
      "group":"ART"
    },
    {
      "subject":"AB",
      "description":"Arabic (WLU)",
      "unit":"VPA",
      "group":"VPA"
    },
    {
      "subject":"ACC",
      "description":"Accounting",
      "unit":"ACC",
      "group":"ART"
    },
    {
      "subject":"ACINTY",
      "description":"Academic Integrity",
      "unit":"VPA",
      "group":"VPA"
    }]
  }
}
 */
public class subjectInfoListDeserializer implements JsonDeserializer<Map<String, ISubjectInfo>> {
    @Override
    public Map<String, ISubjectInfo> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
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
