package com.cloudcomputing.apicallmodels;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.cloudcomputing.models.Hits;

/**
 * Created by Nitin on 12/14/2015.
 */
@JsonObject
public class Test extends GenericClass {


   /* @JsonField
    public DealQuery query;*/

    /*  @JsonField
      public List<DealHolder> deals;
  */
    @JsonField
    public Hits hits;

    public Hits getHits() {
        return hits;
    }

    /* public DealQuery getQuery() {
        return query;
    }

    public List<DealHolder> getDeals() {
        return deals;
    }*/
}
