package com.example.tinayan.lovelyschedule.data_models.uw.API_connection;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.example.tinayan.lovelyschedule.data_models.uw.API_connection.UWAPIConstants.*;

/**
 * Created by tinayan on 18/06/16.
 */
public class UWConnection {

    private static Context mContext;

    private static class instanceHolder {
        public static UWConnection instance = new UWConnection();
    }

    public static UWConnection getInstance(Context context) {
        mContext = context.getApplicationContext();
        return instanceHolder.instance;
    }

    private RequestQueue queue = null;

    private UWConnection() {
        queue = Volley.newRequestQueue(mContext);
    }

    public void cancellAll(String tag) {
        queue.cancelAll(tag);
    }

    public void getAllCourseInfo(String tag, Response.Listener<String> successAction,
                                  Response.ErrorListener errorAction) {
        String requestUrl = UWapi + Terms + Courses + APIKey;
        if(successAction == null) {
            successAction = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            };
        }

        if(errorAction == null) {
            errorAction = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("request error: " + error.getMessage());
                }
            };
        }

        StringRequest request = new StringRequest(Request.Method.GET, requestUrl,
                successAction, errorAction);

        request.setTag(tag);
        queue.add(request);
    }

    public void getAllSubjectInfo(String tag, Response.Listener<String> successAction,
                                   Response.ErrorListener errorAction) {
        String requestUrl = UWapi + Subject + APIKey;
        StringRequest request = new StringRequest(Request.Method.GET, requestUrl,
                successAction, errorAction);
        request.setTag(tag);
        queue.add(request);
    }
}