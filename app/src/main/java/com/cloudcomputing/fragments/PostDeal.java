package com.cloudcomputing.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.cloudcomputing.adapters.SpinnerAdapter;
import com.cloudcomputing.shopit.Constants;
import com.cloudcomputing.shopit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostDeal extends Fragment {


    Spinner mSpinner;
    public PostDeal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_deal, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSpinner = (Spinner) view.findViewById(R.id.dealCategory);
        //SpinnerAdapter mAdapter = new SpinnerAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Constants.mCategoryList);
       // mSpinner.setAdapter(new SpinnerAdapter(getActivity(), Constants.mCategoryList));
    }
}
