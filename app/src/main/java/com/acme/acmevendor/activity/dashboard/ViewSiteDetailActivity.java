package com.acme.acmevendor.activity.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityViewSiteDetailBinding;

public class ViewSiteDetailActivity extends AppCompatActivity {

    private String campaignType = "";
    private int position = 0;
    private ActivityViewSiteDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_site_detail);

        if (getIntent().getExtras() != null) {
            campaignType = getIntent().getExtras().getString("campaignType", "");
            position = getIntent().getExtras().getInt("position", 0);
        }

        if ("old".equalsIgnoreCase(campaignType)){
            binding.tvOldCampaign.performClick();
        } else {
            binding.tvLiveCampaign.performClick();
        }
    }

    public void btnCloseClick(View view) {
        finish();
    }

    public void btnNextClick(View view) {
        // finish();
    }

    public void onDownloadClick(View view) {
        // finish();
    }

    public void oldCampaignClick(View view) {
        binding.tvOldCampaign.setBackgroundResource(R.drawable.primaryround);
        binding.tvLiveCampaign.setBackgroundResource(R.color.coloryellow);
    }

    public void liveCampaignClick(View view) {
        binding.tvLiveCampaign.setBackgroundResource(R.drawable.primaryround);
        binding.tvOldCampaign.setBackgroundResource(R.color.coloryellow);
    }
}
