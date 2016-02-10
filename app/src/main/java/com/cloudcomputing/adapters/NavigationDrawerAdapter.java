package com.cloudcomputing.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudcomputing.models.GenericModel;
import com.cloudcomputing.shopit.NavigationDrawerFragment;
import com.cloudcomputing.shopit.R;

import java.util.ArrayList;

/**
 * Created by Nitin on 12/10/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NavigationDrawerViewHolder> {

    LayoutInflater inflater;
    Context mContext;
    ArrayList<GenericModel> mData;
    NavigationDrawerFragment mNavigationDrawerFragment;

    public NavigationDrawerAdapter(Context context, ArrayList<GenericModel> data, NavigationDrawerFragment navigationDrawerFragment) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
        mNavigationDrawerFragment = navigationDrawerFragment;
    }

    @Override
    public NavigationDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NavigationDrawerViewHolder(inflater.inflate(R.layout.navigation_drawer_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NavigationDrawerViewHolder holder, int position) {

        holder.textview.setText(mData.get(position).getmName());
        holder.imageView.setImageResource(mData.get(position).getmId());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class NavigationDrawerViewHolder extends RecyclerView.ViewHolder {
        TextView textview;
        ImageView imageView;
        LinearLayout itemLayout;

        public NavigationDrawerViewHolder(View itemView) {
            super(itemView);
            textview = (TextView) itemView.findViewById(R.id.recyclerview_category_name);
            imageView = (ImageView) itemView.findViewById(R.id.recyclerview_category_image);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mNavigationDrawerFragment.CallFragmentChanger(getAdapterPosition() == mData.size() - 1 ? 7 : getAdapterPosition());

                }
            });
        }
    }
}
