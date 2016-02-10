package com.cloudcomputing.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcomputing.adapters.CategoryAdapter;
import com.cloudcomputing.models.onDrawerItemSelected;
import com.cloudcomputing.shopit.Constants;
import com.cloudcomputing.shopit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Category extends Fragment {

    RecyclerView mRecyclerView;
    CategoryAdapter mCategoryAdapter;
    LinearLayoutManager mLinearLayoutManager;
    onDrawerItemSelected mDrawerItemSelection;

    public Category() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mDrawerItemSelection = (onDrawerItemSelected) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.categoryRecyclerview);
        mCategoryAdapter = new CategoryAdapter(getActivity(), Constants.mCategoryList, this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

    }

    public void callFragmentChanger(String categoryName) {
        mDrawerItemSelection.fragmentFetcher(Constants.FRAGMENT_CATEGORY_DEALS, Constants.GETDEAL_CATEGORY_QUERY, categoryName);

    }
}
