package com.cloudcomputing.apicallmodels;

import com.android.volley.VolleyError;

/**
 * Created by Nitin on 12/17/2015.
 */
public class GenericClass {

    private boolean success;

    private VolleyError error;

    public VolleyError getError() {
        return error;
    }

    public void setError(VolleyError error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
