package com.cloudcomputing.shopit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cloudcomputing.adapters.NotificationDealAdapter;

public class NotificationDealActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    NotificationDealAdapter adapater;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_deal);
        mRecyclerView = (RecyclerView) findViewById(R.id.deals);
        adapater = new NotificationDealAdapter(getApplicationContext(), Constants.mNotificationDeals);
        mRecyclerView.setAdapter(adapater);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

    }

}
