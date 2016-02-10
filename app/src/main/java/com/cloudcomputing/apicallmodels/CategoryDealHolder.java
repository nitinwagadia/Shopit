package com.cloudcomputing.apicallmodels;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.cloudcomputing.models.Deals;

import java.util.List;

/**
 * Created by Nitin on 12/18/2015.
 */
@JsonObject
public class CategoryDealHolder extends GenericClass {

    @JsonField
    public Deals deal;

    public Deals getDeal() {
        return deal;
    }

}
