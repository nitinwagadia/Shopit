package com.cloudcomputing.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cloudcomputing.models.MerchantDealsModel;
import com.cloudcomputing.shopit.ApplicationInstance;
import com.cloudcomputing.shopit.R;

import java.util.ArrayList;

/**
 * Created by Nitin on 12/18/2015.
 */
public class MerchantDealsAdapter extends RecyclerView.Adapter<MerchantDealsAdapter.MerchantDealsViewHolder> {

    ArrayList<MerchantDealsModel> mData;
    Context mContext;
    LayoutInflater inflater;

    public MerchantDealsAdapter(Context context, ArrayList<MerchantDealsModel> data) {
        mData = data;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MerchantDealsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MerchantDealsViewHolder(inflater.inflate(R.layout.merchant_deal_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MerchantDealsViewHolder holder, int position) {

        holder.mDescription.setText(mData.get(position).getDescription());
        Log.e("I am in", "Biund View");

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MerchantDealsViewHolder extends RecyclerView.ViewHolder {

        Button buy;
        TextView mDescription;

        public MerchantDealsViewHolder(View itemView) {
            super(itemView);
            buy = (Button) itemView.findViewById(R.id.buyButton);
            mDescription = (TextView) itemView.findViewById(R.id.description);

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = "http://172.16.22.73:8080/com.example.rest/mywebservice/notifymerchant";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    ApplicationInstance.getInstance().getRequestQueue().add(stringRequest);
                }
            });
        }
    }
}
