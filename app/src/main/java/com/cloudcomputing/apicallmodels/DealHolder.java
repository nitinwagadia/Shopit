package com.cloudcomputing.apicallmodels;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.cloudcomputing.models.Deals;

/**
 * Created by Nitin on 12/16/2015.
 */

@JsonObject
public class DealHolder {

   /* @JsonField
    Deals deal;
*/

    @JsonField
    String _type;

    @JsonField
    Deals _source;

    public Deals getDeal() {
        return _source;
    }

    public String get_type() {
        return _type;
    }

    /*public Deals getDeal() {
        return deal;
    }*/
}
