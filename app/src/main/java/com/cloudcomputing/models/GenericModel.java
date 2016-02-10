package com.cloudcomputing.models;

/**
 * Created by Nitin on 12/11/2015.
 */
public class GenericModel {

    private String mName;
    private Integer mId;

    public GenericModel(String mName, Integer mId) {
        this.mName = mName;
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public Integer getmId() {
        return mId;
    }
}
