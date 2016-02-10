package com.cloudcomputing.networkcalls;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bluelinelabs.logansquare.LoganSquare;
import com.cloudcomputing.apicallmodels.CategoryDealHolder;
import com.cloudcomputing.apicallmodels.Test;
import com.cloudcomputing.fragments.CategoryDeals;
import com.cloudcomputing.fragments.DealsFragment;
import com.cloudcomputing.fragments.MapDealFragment;
import com.cloudcomputing.models.CallType;
import com.cloudcomputing.models.NetworkListener;
import com.cloudcomputing.models.ResultListener;
import com.cloudcomputing.shopit.ApplicationInstance;
import com.cloudcomputing.shopit.BackgroundLocationService;
import com.cloudcomputing.shopit.MainActivity;
import com.cloudcomputing.shopit.RegistrationActivity;

import java.util.List;

public class Network {
    private static Network mInstance;


    public static Network getInstance() {
        if (mInstance == null) {
            mInstance = new Network();
        }

        return mInstance;
    }


    public Test getDeals(String query, final DealsFragment dealsFragment, final CallType callType) {

        StringRequest jsonOStringRequests = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("output is : ", response);
                Test mDeals = null;// = new Test();

                try {
                    mDeals = LoganSquare.parse(response, Test.class);// parseList(response, Test.class);
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }

                if (mDeals != null) {
                    mDeals.setSuccess(true);
                } else {
                    mDeals = new Test();
                    mDeals.setSuccess(true);
                }

                NetworkListener mListener = dealsFragment;

                switch (callType) {
                    case LOAD:
                        mListener.onLoadResults(mDeals);
                        break;
                    case PAGING:
                        mListener.onPagingResults(mDeals);
                        break;
                    case REFRESH:
                        mListener.onRefreshResults(mDeals);
                        break;
                }


            }
        }, new Response.ErrorListener() {

            Test mDeals = new Test();

            @Override
            public void onErrorResponse(VolleyError error) {

                mDeals.setSuccess(false);
                mDeals.setError(error);
                NetworkListener mListener = dealsFragment;

                switch (callType) {
                    case LOAD:
                        mListener.onLoadResults(mDeals);
                        break;
                    case PAGING:
                        mListener.onPagingResults(mDeals);
                        break;
                    case REFRESH:
                        mListener.onRefreshResults(mDeals);
                        break;

                }

            }
        });

        ApplicationInstance.getInstance().getRequestQueue().add(jsonOStringRequests);


        return null;
    }


    public void getDealsByCategory(String query, final CategoryDeals categoryDeals, final CallType callType) {

        StringRequest jsonOStringRequests = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("output is : ", response);

                Test mDeals = null;// = new Test();

                try {
                    mDeals = LoganSquare.parse(response, Test.class);// parseList(response, Test.class);
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }

                if (mDeals != null) {
                    mDeals.setSuccess(true);
                } else {
                    mDeals = new Test();
                    mDeals.setSuccess(true);
                }



                /*List<CategoryDealHolder> mDealHolder = null;
                try {

                    mDealHolder = LoganSquare.parseList(response, CategoryDealHolder.class);
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }

*/
                NetworkListener mListener = categoryDeals;

                switch (callType) {
                    case LOAD:
                        mListener.onLoadResults(mDeals);
                        break;
                    case PAGING:
                        mListener.onPagingResults(mDeals);
                        break;
                    case REFRESH:
                        mListener.onRefreshResults(mDeals);
                        break;
                }


            }
        }, new Response.ErrorListener() {

            //CategoryDealHolder mDealHolder = new CategoryDealHolder();
            Test mDeals;

            @Override
            public void onErrorResponse(VolleyError error) {

                mDeals.setSuccess(false);
                mDeals.setError(error);/*
                mDealHolder.setSuccess(false);
                mDealHolder.setError(error);*/
                NetworkListener mListener = categoryDeals;

                switch (callType) {
                    case LOAD:
                        mListener.onLoadResults(mDeals);
                        break;
                    case PAGING:
                        mListener.onPagingResults(mDeals);
                        break;
                    case REFRESH:
                        mListener.onRefreshResults(mDeals);
                        break;

                }

            }
        });

        ApplicationInstance.getInstance().getRequestQueue().add(jsonOStringRequests);

    }


    public void SignUp(String query, final RegistrationActivity registrationActivity) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResultListener mResultListener = registrationActivity;
                mResultListener.onResult(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ResultListener mResultListener = registrationActivity;
                mResultListener.onResult(true);

            }
        });

        ApplicationInstance.getInstance().getRequestQueue().add(stringRequest);

    }

    public void Login(String query, final MainActivity mainActivity) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ResultListener mResultListener = mainActivity;
                mResultListener.onResult(response.equals("true"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ResultListener mResultListener = mainActivity;
                mResultListener.onResult(false);
            }
        });

        ApplicationInstance.getInstance().getRequestQueue().add(stringRequest);

    }

    public void getDealsKeyword(String query, final MapDealFragment mapDealFragment, final CallType callType) {

        StringRequest jsonOStringRequests = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("output is : ", response);
                Test mDeals = null;// = new Test();

                try {
                    mDeals = LoganSquare.parse(response, Test.class);// parseList(response, Test.class);
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }

                if (mDeals != null) {
                    mDeals.setSuccess(true);
                } else {
                    mDeals = new Test();
                    mDeals.setSuccess(true);
                }

                NetworkListener mListener = mapDealFragment;

                switch (callType) {
                    case LOAD:
                        mListener.onLoadResults(mDeals);
                        break;
                    case PAGING:
                        mListener.onPagingResults(mDeals);
                        break;
                    case REFRESH:
                        mListener.onRefreshResults(mDeals);
                        break;
                }


            }
        }, new Response.ErrorListener() {

            Test mDeals = new Test();

            @Override
            public void onErrorResponse(VolleyError error) {

                mDeals.setSuccess(false);
                mDeals.setError(error);
                NetworkListener mListener = mapDealFragment;

                switch (callType) {
                    case LOAD:
                        mListener.onLoadResults(mDeals);
                        break;
                    case PAGING:
                        mListener.onPagingResults(mDeals);
                        break;
                    case REFRESH:
                        mListener.onRefreshResults(mDeals);
                        break;

                }

            }
        });

        ApplicationInstance.getInstance().getRequestQueue().add(jsonOStringRequests);

    }


    public void getDealsNotification(String query, final BackgroundLocationService backgroundLocationService) {

        StringRequest jsonOStringRequests = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("output is : ", response);
                //Test mDeals = null;// = new Test();
               /* List<CategoryDealHolder> mDealHolder = null;

                try {
                    //mDeals = LoganSquare.parse(response, Test.class);// parseList(response, Test.class);
                    mDealHolder = LoganSquare.parseList(response, CategoryDealHolder.class);
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }*/


                Test mDeals = null;// = new Test();

                try {
                    mDeals = LoganSquare.parse(response, Test.class);// parseList(response, Test.class);
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }

                if (mDeals != null) {
                    mDeals.setSuccess(true);
                } else {
                    mDeals = new Test();
                    mDeals.setSuccess(true);
                }



                NetworkListener mListener = backgroundLocationService;

                mListener.onLoadResults(mDeals);


            }
        }, new Response.ErrorListener() {

            //List<CategoryDealHolder> mDealHolder = null;
            Test mDeals;
            @Override
            public void onErrorResponse(VolleyError error) {

                mDeals.setSuccess(false);
                mDeals.setError(error);/*
                mDealHolder.setSuccess(false);
                mDealHolder.setError(error);*/
               // NetworkListener mListener = backgroundLocationService;
                NetworkListener mListener = backgroundLocationService;
                mListener.onLoadResults(mDeals);

            }
        });

        ApplicationInstance.getInstance().getRequestQueue().add(jsonOStringRequests);

    }
}
