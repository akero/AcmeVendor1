package com.acme.acmevendor.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.acme.acmevendor.R;
import com.acme.acmevendor.activity.dashboard.AdminDashboardActivity;
import com.acme.acmevendor.activity.dashboard.CampaignListActivity;
import com.acme.acmevendor.activity.dashboard.ClientDashBoardActivity;
import com.acme.acmevendor.activity.dashboard.ViewCampaignSites;
import com.acme.acmevendor.activity.dashboard.ViewVendorSites;
import com.acme.acmevendor.activity.vender.VenderDashBoardActivity;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class CampaignListAdapter extends RecyclerView.Adapter<CampaignListAdapter.ViewHolder> {
    private Context context;
    public JSONArray jsonArray;
    private boolean showEdit;
    public void clearData() {
        // Clear the existing data
        jsonArray = new JSONArray(); // Or clear the existing array in another appropriate way
        notifyDataSetChanged();
    }

    public CampaignListAdapter(Context context, JSONArray jsonArray, boolean showEdit) {
        this.context = context;
        this.jsonArray = jsonArray;
        this.showEdit = showEdit;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCampaign;
        TextView tvSiteNo;
        TextView tvUnitNo;
        ImageView tvImage;
        ImageView edit;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCampaign = itemView.findViewById(R.id.ivCampaign);
            tvSiteNo = itemView.findViewById(R.id.tvSiteNo);
            tvUnitNo = itemView.findViewById(R.id.tvUnitNo);
            tvImage= itemView.findViewById(R.id.ivText);
            edit= itemView.findViewById(R.id.edit);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_campaign_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.edit.setVisibility(showEdit ? View.VISIBLE : View.GONE);
            holder.edit.setOnClickListener(v -> {
                if (context instanceof AdminDashboardActivity) {
                    ((AdminDashboardActivity) context).onEditClick(position, v);
                }if(context instanceof ViewCampaignSites){
                    ((ViewCampaignSites) context).onEditClick(position, v);

                }
            });


            JSONObject jsonObject = jsonArray.getJSONObject(position);
            holder.tvSiteNo.setText(jsonObject.getString("name"));
            if(jsonObject.has("uid")) {
                holder.tvUnitNo.setText("Unit Id:- " + jsonObject.getString("uid"));
            } else {
                holder.tvUnitNo.setText("Company:- " + jsonObject.getString("company_name"));
            }

            String imageUrl = jsonObject.optString("image");
            if(imageUrl != null && !imageUrl.isEmpty() && !imageUrl.equals("null")) {
                imageUrl = "https://acme.warburttons.com/" + imageUrl;
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.temp_campaign) // Placeholder image while loading
                        .into(holder.ivCampaign);
            } else {
                // Load placeholder image if there's no valid image URL
                Glide.with(context)
                        .load(R.drawable.temp_campaign) // Placeholder image when there's no image URL
                        .into(holder.ivCampaign);
            }

            // Click listener for each item
            holder.itemView.setOnClickListener(v -> {
                if (context instanceof CampaignListActivity) {
                    ((CampaignListActivity) context).onItemClick(position);
                } else if (context instanceof ClientDashBoardActivity) {
                    ((ClientDashBoardActivity) context).onItemClick(position);
                } else if (context instanceof VenderDashBoardActivity) {
                    ((VenderDashBoardActivity) context).onItemClick(position);
                } else if (context instanceof AdminDashboardActivity) {
                    ((AdminDashboardActivity) context).onItemClick(position);
                } else if (context instanceof ViewVendorSites) {
                    ((ViewVendorSites) context).onItemClick(position);
                } else if (context instanceof ViewCampaignSites) {
                    ((ViewCampaignSites) context).onItemClick(position);
                } else if (context instanceof ClientDashBoardActivity) {
                    ((ClientDashBoardActivity) context).onItemClick(position);
                }
            });
        } catch (Exception e) {
            Log.e("tag41", "Error in onBindViewHolder: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}