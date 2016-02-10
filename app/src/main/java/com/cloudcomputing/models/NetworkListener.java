package com.cloudcomputing.models;

import java.util.List;

/**
 * Created by Nitin on 12/16/2015.
 */
public interface NetworkListener {

    <T> void onLoadResults (T result);

    <T> void onRefreshResults (T result);

    <T> void onPagingResults (T result);


}
