package com.acme.acmevendor.activity.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityViewSiteDetailBinding;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;

public class ViewSiteDetailActivity extends AppCompatActivity implements ApiInterface {

    private String campaignType = "";
    private int position = 0;

    String siteNumber= "";
    String logintoken="";
    private ActivityViewSiteDetailBinding binding;

    //TODO populate all fields. pass api call data from prev activity
    //have to pass logintoken and siteid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_site_detail);

        Log.d("tag41", "1");


        if (getIntent().getExtras() != null) {
            Log.d("tag41", "2");
            campaignType = getIntent().getExtras().getString("campaignType", "");
            siteNumber= getIntent().getExtras().getString("siteNumber", "");
            logintoken= getIntent().getExtras().getString("logintoken","");
            Log.d("tag41", "3");
        }

        if ("old".equalsIgnoreCase(campaignType)){
            binding.tvOldCampaign.performClick();
        } else {
            binding.tvLiveCampaign.performClick();
        }
        Log.d("tag41", "4");
        apicall(logintoken, siteNumber);
        Log.d("tag41", "5");
    }

    void apicall(String logintoken, String siteNumber){

        Log.d("tag41", "6");
        Context context= this;
        APIreferenceclass api= new APIreferenceclass(logintoken, siteNumber, context);
        Log.d("tag41", "7");

    }
    @Override
    public void onResponseReceived(String response){

        Log.d("tag41", response);
    }

    public void btnCloseClick(View view) {
        finish();
    }

    public void btnNextClick(View view) {
        //TODO ask what this does
        // finish();
    }

    public void onNotificationClick(View view){
        //TODO ask lodu what this does
    }

    public void onDownloadClick(View view) {

        //TODO put shit in a file and download and finish activity
        finish();
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
