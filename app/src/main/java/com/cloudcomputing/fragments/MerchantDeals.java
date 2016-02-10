package com.cloudcomputing.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloudcomputing.adapters.DealsAdapter;
import com.cloudcomputing.adapters.MerchantDealsAdapter;
import com.cloudcomputing.apicallmodels.DealHolder;
import com.cloudcomputing.apicallmodels.Test;
import com.cloudcomputing.models.MerchantDealsModel;
import com.cloudcomputing.models.onDrawerItemSelected;
import com.cloudcomputing.shopit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantDeals extends Fragment {

    RecyclerView mRecyclerView;
    private boolean loading = true;
    ProgressBar mProgressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    DealsAdapter mDealsAdapter;
    Test mQueryData;
    List<DealHolder> mDealHolder;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    TextView noResulyTextview;
    int mQueryType = 0;
    String mCategory;
    String mQuery;
    String mFormattedQuery = "";
    public onDrawerItemSelected mDrawerItemSelected;
    String latitude = "", longitude = "";
    int pageNumber = 0;

    public MerchantDeals() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer_orders, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_deals);
        noResulyTextview = (TextView) view.findViewById(R.id.noresultsTextView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipetoRefreshConsumerOrderFragment);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        MerchantDealsAdapter adapter = new MerchantDealsAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(adapter);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


    }

    private ArrayList<MerchantDealsModel> getData() {
        ArrayList<MerchantDealsModel> mList = new ArrayList<MerchantDealsModel>();
        MerchantDealsModel mModel = new MerchantDealsModel("50% off on Shoes");
        mList.add(mModel);
        mModel = new MerchantDealsModel("40% off on clothes");
        mList.add(mModel);
        mModel = new MerchantDealsModel("50% off on Shoes");
        mList.add(mModel);
        mModel = new MerchantDealsModel("50% off on food");
        mList.add(mModel);
        mModel = new MerchantDealsModel("50% off on Shoes");
        mList.add(mModel);

        return mList;

    }
}
