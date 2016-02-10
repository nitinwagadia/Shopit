package com.cloudcomputing.apicallmodels;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Nitin on 12/16/2015.
 */
@JsonObject
public class DealQuery {

    @JsonField
    String page;

    @JsonField
    String query;

    @JsonField
    String location;

    @JsonField
    String radius;

    @JsonField
    String categoty_slugs;

    @JsonField
    String provider_slugs;

    @JsonField
    String updated_after;
}
