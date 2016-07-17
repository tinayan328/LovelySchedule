package com.example.tinayan.lovelyschedule.data_models;

import com.android.volley.Response;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ICourseInfo;
import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ISubjectInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by tinayan on 18/06/16.
 */
public interface IPublicDataHolder {
    void getAllSubject(IPublicCallBack<List<ISubjectInfo>> callBack,
                       Response.ErrorListener errorListener);
    void getAllCourses(IPublicCallBack<List<ICourseInfo>> callBack,
                       Response.ErrorListener errorListener);
    void getAllCourses(String Subject, IPublicCallBack<List<ICourseInfo>> callBack,
                       Response.ErrorListener errorListener);
}
