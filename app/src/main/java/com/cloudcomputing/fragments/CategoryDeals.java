package com.cloudcomputing.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcomputing.adapters.CategoryDealsAdapter;
import com.cloudcomputing.apicallmodels.CategoryDealHolder;
import com.cloudcomputing.apicallmodels.DealHolder;
import com.cloudcomputing.apicallmodels.Test;
import com.cloudcomputing.models.CallType;
import com.cloudcomputing.models.NetworkListener;
import com.cloudcomputing.models.onDrawerItemSelected;
import com.cloudcomputing.networkcalls.Network;
import com.cloudcomputing.shopit.Constants;
import com.cloudcomputing.shopit.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDeals extends Fragment implements NetworkListener {

    RecyclerView mRecyclerView;
    private boolean loading = true;
    ProgressBar mProgressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CategoryDealsAdapter mDealsAdapter;
    List<DealHolder> mQueryData;
    List<DealHolder> mDealHolder;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int mQueryType = 0;
    String mCategory;
    String mQuery;
    String mFormattedQuery = "";
    TextView noResulyTextview;
    public onDrawerItemSelected mDrawerItemSelected;
    String latitude, longitude;

    public CategoryDeals() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDrawerItemSelected = (onDrawerItemSelected) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mCategory = bundle.getString(Constants.QUERY_PARAMETER_CATEGORY);
        }
        if (Constants.mLocation == null) {
            latitude = "40.6431880";
            longitude = "-74.0084290";

        } else {
            latitude = String.valueOf(Constants.mLocation.getLatitude());
            longitude = String.valueOf(Constants.mLocation.getLongitude());
        }


        mQuery = Constants.getDealsQuery(Constants.GETDEAL_CATEGORY_QUERY);
        mFormattedQuery = String.format(mQuery,!mCategory.equals("") ? mCategory.toLowerCase() : "", latitude,longitude,1);
        Log.d("MQuery is : ", mFormattedQuery);
        Network.getInstance().getDealsByCategory(mFormattedQuery, this, CallType.LOAD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deals, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_deals);
        noResulyTextview = (TextView) view.findViewById(R.id.noresultsTextView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipetoRefreshDealsFragment);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


    }

    @Override
    public <T> void onLoadResults(T result) {

        mProgressBar.setVisibility(View.GONE);
        //mQueryData = (List<CategoryDealHolder>) result;
        Test mTemp = (Test) result;
        mQueryData = mTemp.getHits().getHitsList();
        if (mQueryData != null) {
            if (mQueryData.size() > 0) {
                mRecyclerView.setVisibility(View.VISIBLE);
                noResulyTextview.setVisibility(View.GONE);
                mDealsAdapter = new CategoryDealsAdapter(getActivity(), mQueryData, mDrawerItemSelected);
                mRecyclerView.setAdapter(mDealsAdapter);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                Log.e("Yipeee", "Its workingggg");

            } else {
                mRecyclerView.setVisibility(View.GONE);
                noResulyTextview.setVisibility(View.VISIBLE);

            }

        } else {

            mRecyclerView.setVisibility(View.GONE);
            noResulyTextview.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "No results!", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public <T> void onRefreshResults(T result) {

        Log.i("Yippe", "WOrking on Refresh");

    }

    @Override
    public <T> void onPagingResults(T result) {

        Log.i("Yippe", "WOrking on Paging");

    }
}
