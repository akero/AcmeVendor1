package com.acme.campaignproject.activity.vender;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.acme.campaignproject.R;
import com.acme.campaignproject.adapters.CampaignListAdapter;
import com.acme.campaignproject.databinding.ActivityVenderDashBoardBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VenderDashBoardActivity extends AppCompatActivity {

    private ActivityVenderDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vender_dash_board);
        campaignList();
    }

    private void campaignList() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        binding.rvCampaignList.setLayoutManager(layoutManager);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObjectairbnb = new JSONObject();
        JSONObject jsonObjecthyundai = new JSONObject();
        JSONObject jsonObjectford = new JSONObject();
        JSONObject jsonObjectpatanjli = new JSONObject();

        try {
            jsonObjectairbnb.put("sitenumber", "001");
            jsonObjecthyundai.put("sitenumber", "002");
            jsonObjectford.put("sitenumber", "003");
            jsonObjectpatanjli.put("sitenumber", "004");

            jsonObjectairbnb.put("unitnumber", "#887001");
            jsonObjecthyundai.put("unitnumber", "#878002");
            jsonObjectford.put("unitnumber", "#765003");
            jsonObjectpatanjli.put("unitnumber", "#432004");

            jsonArray.put(jsonObjectairbnb);
            jsonArray.put(jsonObjecthyundai);
            jsonArray.put(jsonObjectford);
            jsonArray.put(jsonObjectpatanjli);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray);
        binding.rvCampaignList.setAdapter(adapter);
    }

    public void onItemClick(int position) {
        startActivity(new Intent(this, UpdateSiteDetailActivity.class));
    }
}
