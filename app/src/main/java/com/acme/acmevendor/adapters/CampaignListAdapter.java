package com.acme.campaignproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.acme.campaignproject.R;
import com.acme.campaignproject.activity.dashboard.AdminDashboardActivity;
import com.acme.campaignproject.activity.dashboard.CampaignListActivity;
import com.acme.campaignproject.activity.dashboard.ClientDashBoardActivity;
import com.acme.campaignproject.activity.vender.VenderDashBoardActivity;
import org.json.JSONArray;
import org.json.JSONObject;

public class CampaignListAdapter extends RecyclerView.Adapter<CampaignListAdapter.ViewHolder> {
    private Context context;
    private JSONArray jsonArray;

    public CampaignListAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCampaign;
        TextView tvSiteNo;
        TextView tvUnitNo;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCampaign = itemView.findViewById(R.id.ivCampaign);
            tvSiteNo = itemView.findViewById(R.id.tvSiteNo);
            tvUnitNo = itemView.findViewById(R.id.tvUnitNo);
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
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            holder.tvSiteNo.setText("Site No. " + jsonObject.getString("sitenumber"));
            holder.tvUnitNo.setText("Unit Id:- " + jsonObject.getString("unitnumber"));
            holder.itemView.setOnClickListener(v -> {
                if (context instanceof CampaignListActivity) {
                    ((CampaignListActivity) context).onItemClick(position);
                } else if (context instanceof ClientDashBoardActivity) {
                    ((ClientDashBoardActivity) context).onItemClick(position);
                } else if (context instanceof VenderDashBoardActivity) {
                    ((VenderDashBoardActivity) context).onItemClick(position);
                } else if (context instanceof AdminDashboardActivity) {
                    ((AdminDashboardActivity) context).onItemClick(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}
