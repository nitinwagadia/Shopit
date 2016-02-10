package com.cloudcomputing.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.cloudcomputing.apicallmodels.DealHolder;

import java.util.List;

/**
 * Created by Nitin on 12/24/2015.
 */
@JsonObject
public class Hits {

    @JsonField
    List<DealHolder> hits;

    public List<DealHolder> getHitsList() {
        return hits;
    }
}
