package com.example.tinayan.lovelyschedule.data_models.uw;

import android.content.Context;

import com.android.volley.Response;
import com.example.tinayan.lovelyschedule.data_models.uw.API_connection.UWConnection;
import com.example.tinayan.lovelyschedule.data_models.IPublicCallBack;
import com.example.tinayan.lovelyschedule.data_models.IPublicDataHolder;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ICourseInfo;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ISubjectInfo;
import com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw.gson_deserializer.courseInfoListDeserializer;
import com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw.gson_deserializer.subjectInfoListDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tinayan on 18/06/16.
 */
public class PublicDataHolderUW implements IPublicDataHolder {

    private static Context mContext;

    private static class instanceHolder {
        public static PublicDataHolderUW instance = new PublicDataHolderUW();
    }

    public static PublicDataHolderUW getInstance(Context context) {
        mContext = context.getApplicationContext();
        return instanceHolder.instance;
    }

    private UWConnection uwConnection;
    final private Map<String, ISubjectInfo> SubjectList = new HashMap<>();
    final private Map<String, List<ICourseInfo>> CourseList = new HashMap<>();

    private PublicDataHolderUW() {
        uwConnection = UWConnection.getInstance(mContext);
    }

    private void retrieveSubjectData(IPublicCallBack<Map<String, ISubjectInfo>> subjectCallBack) {
        if(SubjectList.isEmpty() || CourseList.isEmpty()) {
            return;
        }
        List<String> tempStorage = new ArrayList<>();
        for(String key : SubjectList.keySet()) {
            if(!CourseList.containsKey(key)) {
                tempStorage.add(key);
            }
        }
        for(String key : tempStorage) {
            SubjectList.remove(key);
        }
        if(subjectCallBack != null) {
            subjectCallBack.callBackMethod(SubjectList);
        }
    }

    public void loadData(final IPublicCallBack<Map<String, List<ICourseInfo>>> courseCallBack,
                         final Response.ErrorListener courseErrorListener,
                         final IPublicCallBack<Map<String, ISubjectInfo>> subjectCallBack,
                         final Response.ErrorListener subjectErrorListener) {
        if (!CourseList.isEmpty() && !SubjectList.isEmpty()) {
            return;
        }
        DateFormat tagFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String tag = tagFormat.format(new Date()) + "PublicDataHolderUWLoadingData";

        try {
            uwConnection.getAllCourseInfo(tag,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            GsonBuilder builder = new GsonBuilder();
                            builder.registerTypeAdapter(CourseList.getClass(), new courseInfoListDeserializer());
                            Gson gson = builder.create();
                            synchronized (CourseList) {
                                if(CourseList.isEmpty()) {
                                    CourseList.putAll(gson.fromJson(response, CourseList.getClass()));
                                }
                            }
                            System.out.println("request success: " + response);
                            if(courseCallBack != null) {
                                courseCallBack.callBackMethod(CourseList);
                            }
                            retrieveSubjectData(subjectCallBack);
                        }
                    }, courseErrorListener);

            uwConnection.getAllSubjectInfo(tag,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            GsonBuilder builder = new GsonBuilder();
                            builder.registerTypeAdapter(SubjectList.getClass(), new subjectInfoListDeserializer());
                            Gson gson = builder.create();
                            synchronized (SubjectList) {
                                if(SubjectList.isEmpty()) {
                                    SubjectList.putAll(gson.fromJson(response, SubjectList.getClass()));
                                }
                            }
                            System.out.println("request success: " + response);
                            retrieveSubjectData(subjectCallBack);
                        }
                    }, subjectErrorListener);

        } catch (Exception e) {
            uwConnection.cancellAll(tag);
        }
    }

    public void getAllSubject(final IPublicCallBack<List<ISubjectInfo>> callBack,
                              final Response.ErrorListener errorListener) {
        if(SubjectList.isEmpty()) {
            loadData(null,null, new IPublicCallBack<Map<String, ISubjectInfo>>() {
                @Override
                public void callBackMethod(Map<String, ISubjectInfo> input) {
                    callBack.callBackMethod(new ArrayList<>(SubjectList.values()));
                }
            },errorListener);
        } else {
            callBack.callBackMethod(new ArrayList<>(SubjectList.values()));
        }
    }

    public void getAllCourses(final IPublicCallBack<List<ICourseInfo>> callBack,
                              final Response.ErrorListener errorListener) {
        if(CourseList.isEmpty()) {
            loadData(new IPublicCallBack<Map<String, List<ICourseInfo>>>() {
                @Override
                public void callBackMethod(Map<String, List<ICourseInfo>> input) {
                    List<ICourseInfo> result = new ArrayList<>();
                    for(List<ICourseInfo> Courses : CourseList.values()) {
                        result.addAll(Courses);
                    }
                    callBack.callBackMethod(result);
                }
            }, errorListener, null, null);
        } else {
            List<ICourseInfo> result = new ArrayList<>();
            for(List<ICourseInfo> Courses : CourseList.values()) {
                result.addAll(Courses);
            }
            callBack.callBackMethod(result);
        }
    }

    public void getAllCourses(final String Subject, final IPublicCallBack<List<ICourseInfo>> callBack,
                              final Response.ErrorListener errorListener) {
        if(CourseList.isEmpty()) {
            loadData(new IPublicCallBack<Map<String, List<ICourseInfo>>>() {
                @Override
                public void callBackMethod(Map<String, List<ICourseInfo>> input) {
                    callBack.callBackMethod(CourseList.get(Subject));
                }
            }, errorListener, null,  null);
        } else {
            callBack.callBackMethod(CourseList.get(Subject));
        }

    }
}
