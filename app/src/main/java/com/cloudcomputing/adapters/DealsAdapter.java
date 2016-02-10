package com.cloudcomputing.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.cloudcomputing.animation.Animation;
import com.cloudcomputing.apicallmodels.DealHolder;
import com.cloudcomputing.models.onDrawerItemSelected;
import com.cloudcomputing.shopit.ApplicationInstance;
import com.cloudcomputing.shopit.R;

import java.util.List;

/**
 * Created by Nitin on 12/12/2015.
 */
public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealsViewHolder> {

    Context mContext;
    LayoutInflater inflater;
    List<DealHolder> mData;
    ImageLoader mImageLoader;
    int previousPosition = 0;
    onDrawerItemSelected mDrawerItemSelected;

    public DealsAdapter(Context context, List<DealHolder> data, onDrawerItemSelected drawerItemSelected) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mData = data;
        mImageLoader = ApplicationInstance.getInstance().getImageLoader();
        mDrawerItemSelected = drawerItemSelected;

    }


    @Override
    public DealsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DealsViewHolder(inflater.inflate(R.layout.deals_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(DealsViewHolder holder, int position) {


        if (mData.get(position).getDeal().isOnline())
            holder.categoryTextView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.online_deal_drawable));
        else
            holder.categoryTextView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.non_online_deals_drawable));

        holder.dealsImageView.setImageUrl(mData.get(position).getDeal().getImage_url(), mImageLoader);
        holder.dealsImageView.setDefaultImageResId(R.drawable.background);
        holder.dealsImageView.setDefaultImageResId(R.drawable.com_facebook_button_background);


        holder.decriptionTextView.setText(mData.get(position).getDeal().getTitle());
        holder.categoryTextView.setText(mData.get(position).getDeal().getCategory_name());
        /*SpannableString spannableString = new SpannableString(String.valueOf(mData.get(position).getValue()));
        spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.oldPriceTextView.setText(spannableString);
        */
        holder.oldPriceTextView.setText(String.valueOf(mData.get(position).getDeal().getValue()));
        holder.oldPriceTextView.setPaintFlags(holder.oldPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.newPriceTextView.setText(String.valueOf(mData.get(position).getDeal().getPrice()));

        if (position > previousPosition) {
            Animation.AnimateListItem(holder.cardView, true);
        } else {
            Animation.AnimateListItem(holder.cardView, false);
        }

        previousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class DealsViewHolder extends RecyclerView.ViewHolder {
        TextView expiryTextView, categoryTextView, decriptionTextView, oldPriceTextView, newPriceTextView;
        NetworkImageView dealsImageView;
        CardView cardView;

        public DealsViewHolder(View itemView) {
            super(itemView);
            decriptionTextView = (TextView) itemView.findViewById(R.id.deal_description);
            categoryTextView = (TextView) itemView.findViewById(R.id.category_name);
            oldPriceTextView = (TextView) itemView.findViewById(R.id.previous_amount);
            newPriceTextView = (TextView) itemView.findViewById(R.id.current_amount);
            expiryTextView = (TextView) itemView.findViewById(R.id.expiration);
            dealsImageView = (NetworkImageView) itemView.findViewById(R.id.deal_image);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerItemSelected.fragmentFetcher(6, 0, mData.get(getAdapterPosition()).getDeal().getUrl());
                }
            });
        }
    }
}
