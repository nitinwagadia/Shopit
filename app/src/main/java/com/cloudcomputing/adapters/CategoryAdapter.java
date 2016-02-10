package com.cloudcomputing.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudcomputing.fragments.Category;
import com.cloudcomputing.shopit.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context mContext;
    ArrayList<String> mCategoryList;
    LayoutInflater inflater;
    Category mCategory;

    public CategoryAdapter(Context context, ArrayList<String> data, Category category) {
        mContext = context;
        mCategoryList = data;
        mCategory = category;
        inflater = LayoutInflater.from(context);

    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(inflater.inflate(R.layout.category_card, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

        holder.categoryTextView.setText(mCategoryList.get(position).trim());

    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView categoryCardView;
        TextView categoryTextView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryTextView = (TextView) itemView.findViewById(R.id.categoryTextView);
            categoryCardView = (CardView) itemView.findViewById(R.id.categoryCard);

            categoryCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCategory.callFragmentChanger(mCategoryList.get(getAdapterPosition()).trim());
                }
            });
        }
    }
}
