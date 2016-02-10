package com.cloudcomputing.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.cloudcomputing.adapters.DealsAdapter;
import com.cloudcomputing.apicallmodels.DealHolder;
import com.cloudcomputing.apicallmodels.Test;
import com.cloudcomputing.models.CallType;
import com.cloudcomputing.models.NetworkListener;
import com.cloudcomputing.models.onDrawerItemSelected;
import com.cloudcomputing.networkcalls.Network;
import com.cloudcomputing.shopit.Constants;
import com.cloudcomputing.shopit.R;

import java.util.List;


public class MapDealFragment extends Fragment implements NetworkListener {

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
    int pageNumber = 2;
    String mFormattedQuery = "";
    String latitude = "", longitude = "", keyword = "";
    public onDrawerItemSelected mDrawerItemSelected;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDrawerItemSelected = (onDrawerItemSelected) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuery = Constants.getDealsQuery(Constants.GETDEAL_QUERY);
        if (Constants.mLocation != null) {
            latitude = String.valueOf(Constants.mLocation.getLatitude());
            longitude = String.valueOf(Constants.mLocation.getLongitude());
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            keyword = bundle.getString("keyword");
        }
        mFormattedQuery = String.format(mQuery, latitude, longitude,  pageNumber + "", Constants.keyword);
        Log.d("MQuery is : ", mFormattedQuery);
        Network.getInstance().getDealsKeyword(mFormattedQuery, this, CallType.LOAD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deals, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_deals);
        noResulyTextview = (TextView) view.findViewById(R.id.noresultsTextView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipetoRefreshDealsFragment);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                              @Override
                                              public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                                                  if (dy > 0) //check for scroll down
                                                  {
                                                      visibleItemCount = mLayoutManager.getChildCount();
                                                      totalItemCount = mLayoutManager.getItemCount();
                                                      pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();


                                                      if (loading) {
                                                          if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                                              pageNumber += 1;
                                                              mFormattedQuery = String.format(mQuery, latitude, longitude,  (pageNumber) + "", Constants.keyword);
                                                              Network.getInstance().getDealsKeyword(mFormattedQuery, MapDealFragment.this, CallType.PAGING);


                                                          }
                                                      }
                                                  }
                                              }


                                              @Override
                                              public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                                  super.onScrollStateChanged(recyclerView, newState);
                                              }
                                          }

        );

    }


    @Override
    public <T> void onLoadResults(T result) {


        mProgressBar.setVisibility(View.GONE);
        mQueryData = (Test) result;

        if (mQueryData.isSuccess() && mQueryData.getHits().getHitsList() != null) {

            if (mQueryData.getHits().getHitsList().size() > 0) {
                mRecyclerView.setVisibility(View.VISIBLE);
                noResulyTextview.setVisibility(View.GONE);
                mDealHolder = mQueryData.getHits().getHitsList();
                mDealsAdapter = new DealsAdapter(getActivity(), mDealHolder, mDrawerItemSelected);
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
            if (mQueryData.getError() != null) {
                Toast.makeText(getActivity(), mQueryData.getError().toString(), Toast.LENGTH_LONG).show();
                Log.e("Error", mQueryData.getError().toString());
            } else {
                Log.e("NO results", "NO data received");
            }

        }

    }

    @Override
    public <T> void onRefreshResults(T result) {

    }

    @Override
    public <T> void onPagingResults(T result) {

        mQueryData = (Test) result;
        if (mQueryData.isSuccess() && mQueryData.getHits().getHitsList() != null) {
            int itemCount = mRecyclerView.getAdapter().getItemCount();
            mDealHolder.addAll(mQueryData.getHits().getHitsList());
            mRecyclerView.getAdapter().notifyItemRangeInserted(itemCount, 10);
        } else {
            Toast.makeText(getActivity(), "Loading Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
