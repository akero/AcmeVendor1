package com.acme.acmevendor.activity.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.acme.acmevendor.R;
import com.acme.acmevendor.adapters.CampaignListAdapter;
import com.acme.acmevendor.databinding.ActivityClientDashBoardBinding;
import org.json.JSONArray;
import org.json.JSONObject;

public class ClientDashBoardActivity extends AppCompatActivity {

    private ActivityClientDashBoardBinding binding;
    private boolean oldcampaign = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_dash_board);
        campaignList();
    }

    public void oldCampaignClick(View view) {
        oldcampaign = true;
        binding.tvOldCampaign.setBackgroundResource(R.drawable.primaryround);
        binding.tvLiveCampaign.setBackgroundResource(R.color.coloryellow);
        binding.tvOldCampaign.setTextColor(Color.parseColor("#FFFFFF"));
        binding.tvLiveCampaign.setTextColor(Color.parseColor("#0089BE"));
    }

    public void liveCampaignClick(View view) {
        oldcampaign = false;
        binding.tvLiveCampaign.setBackgroundResource(R.drawable.primaryround);
        binding.tvOldCampaign.setBackgroundResource(R.color.coloryellow);
        binding.tvLiveCampaign.setTextColor(Color.parseColor("#FFFFFF"));
        binding.tvOldCampaign.setTextColor(Color.parseColor("#0089BE"));
    }

    private void campaignList() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rvCampaignList.setLayoutManager(layoutManager);

        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObjectairbnb = new JSONObject();
            jsonObjectairbnb.put("sitenumber", "001");
            jsonObjectairbnb.put("unitnumber", "#887001");
            jsonArray.put(jsonObjectairbnb);

            JSONObject jsonObjecthyundai = new JSONObject();
            jsonObjecthyundai.put("sitenumber", "002");
            jsonObjecthyundai.put("unitnumber", "#878002");
            jsonArray.put(jsonObjecthyundai);

            JSONObject jsonObjectford = new JSONObject();
            jsonObjectford.put("sitenumber", "003");
            jsonObjectford.put("unitnumber", "#765003");
            jsonArray.put(jsonObjectford);

            JSONObject jsonObjectpatanjli = new JSONObject();
            jsonObjectpatanjli.put("sitenumber", "004");
            jsonObjectpatanjli.put("unitnumber", "#432004");
            jsonArray.put(jsonObjectpatanjli);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray);
        binding.rvCampaignList.setAdapter(adapter);
    }

    public void onItemClick(int position) {
        startActivity(new Intent(this, ViewSiteDetailActivity.class)
                .putExtra("position", position)
                .putExtra("campaignType", "old"));
    }
}
