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
import com.acme.acmevendor.activity.vender.VenderDashBoardActivity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class CampaignListAdapter extends RecyclerView.Adapter<CampaignListAdapter.ViewHolder> {
    private Context context;
    private JSONArray jsonArray;

    public void clearData() {
        // Clear the existing data
        jsonArray = new JSONArray(); // Or clear the existing array in another appropriate way
        notifyDataSetChanged();
    }

    public CampaignListAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCampaign;
        TextView tvSiteNo;
        TextView tvUnitNo;
        ImageView tvImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCampaign = itemView.findViewById(R.id.ivCampaign);
            tvSiteNo = itemView.findViewById(R.id.tvSiteNo);
            tvUnitNo = itemView.findViewById(R.id.tvUnitNo);
            tvImage= itemView.findViewById(R.id.ivText);
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
            holder.tvSiteNo.setText(jsonObject.getString("name"));
            holder.tvUnitNo.setText("Unit Id:- " + jsonObject.getString("uid"));

            try {
                String imageUrl = jsonObject.optString("image");
                //TODO here get urls working then check is the recyclerview is populated with images
                //TODO network on main thread exception
                imageUrl= "https://acme.warburttons.com/"+ imageUrl;
                Log.d("tag41", "imageurl is "+ imageUrl);
                if(imageUrl != "null" && !imageUrl.isEmpty()) {
                    URL url = new URL(imageUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    holder.tvImage.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                Log.d("tag41", "error in implementui" +e.toString());
                Log.e("tag41", "sdfdg", e);
                // Handle error
            }

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
