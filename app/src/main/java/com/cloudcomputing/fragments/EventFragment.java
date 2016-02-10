package com.cloudcomputing.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcomputing.adapters.EventAdapter;
import com.cloudcomputing.shopit.BackgroundLocationService;
import com.cloudcomputing.shopit.Constants;
import com.cloudcomputing.shopit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    RecyclerView mRecyclerView;
    EventAdapter adapter;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.event_recyclcerView);
        adapter = new EventAdapter(getActivity(), Constants.mCategoryList, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void startBackgroundService(String category) {

        Intent intent = new Intent(getActivity(), BackgroundLocationService.class);
        Constants.mNotificationCategory = category.toLowerCase();
      //  intent.putExtra("category", category);
        getActivity().startService(intent);

    }
}
