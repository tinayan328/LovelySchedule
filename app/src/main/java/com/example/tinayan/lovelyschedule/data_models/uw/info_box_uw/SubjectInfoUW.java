package com.example.tinayan.lovelyschedule.data_models.uw.info_box_uw;

import com.example.tinayan.lovelyschedule.data_models.info_box_interface.ISubjectInfo;

/**
 * Created by tinayan on 18/06/16.
 */
public class SubjectInfoUW implements ISubjectInfo {
    private String subAbb;
    public String getSubAbb() {return this.subAbb;}

    private String subFull;
    public String getSubFull() {return this.subFull;}

    public SubjectInfoUW(String abb, String fullName) {
        this.subAbb = abb;
        this.subFull = fullName;
    }
}
