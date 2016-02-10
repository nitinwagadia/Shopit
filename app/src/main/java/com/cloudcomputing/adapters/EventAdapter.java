package com.cloudcomputing.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudcomputing.fragments.EventFragment;
import com.cloudcomputing.shopit.R;

import java.util.ArrayList;

/**
 * Created by Nitin on 12/20/2015.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    Context mContext;
    ArrayList<String> mData;
    LayoutInflater inflater;
    EventFragment mEventFragment;

    public EventAdapter(Context context, ArrayList<String> mCategoryList, EventFragment eventFragment) {

        mContext = context;
        mData = mCategoryList;
        inflater = LayoutInflater.from(context);
        mEventFragment = eventFragment;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(inflater.inflate(R.layout.category_card, parent, false));
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {

        holder.categoryTextView.setText(mData.get(position).trim());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        CardView categoryCardView;
        TextView categoryTextView;

        public EventViewHolder(View itemView) {
            super(itemView);
            categoryTextView = (TextView) itemView.findViewById(R.id.categoryTextView);
            categoryCardView = (CardView) itemView.findViewById(R.id.categoryCard);

            categoryCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEventFragment.startBackgroundService(mData.get(getAdapterPosition()));
                }
            });
        }
    }
}
