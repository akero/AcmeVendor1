package com.acme.acmevendor.activity.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityViewSiteDetailBinding;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.acme.acmevendor.viewmodel.SiteDetail;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;

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

    private void implementUI(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            if(jsonResponse.getBoolean("success")) {
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                JSONObject dataObject = dataArray.getJSONObject(0);

                SiteDetail siteDetail = new SiteDetail();
                siteDetail.setSiteId(String.valueOf(dataObject.getInt("id")));
                siteDetail.setSiteName(dataObject.optString("vendor_id"));
                siteDetail.setLocation(dataObject.getString("location"));

                // Assuming these properties are in SiteDetail
                siteDetail.setLastInspection(dataObject.getString("created_at"));
                siteDetail.setNextInspection(dataObject.getString("end_date"));
                // ... add further properties as per your actual SiteDetail class ...
                siteDetail.setInspectorName("N/A");

//TODO here
                // Update UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tvSiteId = findViewById(R.id.tvSiteno);
                        tvSiteId.setText(siteDetail.getSiteId());

                        TextView tvLocation = findViewById(R.id.tvLocation);
                        tvLocation.setText(siteDetail.getLocation());

                        TextView tvSiteName = findViewById(R.id.tvAddSiteDetail);
                        tvSiteName.setText(siteDetail.getSiteName());



                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ViewSiteDetailActivity.this, "Data retrieval failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            // Handle JSON parsing error
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ViewSiteDetailActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                }
            });

            // Assigning values and listeners to Buttons
            Button btnNext = findViewById(R.id.btnNext);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle next button click
                }
            });

            Button btnDownload = findViewById(R.id.btnDownload);
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle download button click
                }
            });

            Button btnClose = findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle close button click
                    finish();
                }
            });
        }}




    void apicall(String logintoken, String siteNumber){

        Log.d("tag41", "6");
        Context context= this;
        APIreferenceclass api= new APIreferenceclass(logintoken, siteNumber, context);
        Log.d("tag41", "7");

    }
    public static String[] extractDataStrings(String apiResponse) {
        Gson gson = new Gson();
        JsonObject jsonResponse = gson.fromJson(apiResponse, JsonObject.class);
        JsonArray dataArray = jsonResponse.getAsJsonArray("data");

        String[] dataStrings = new String[dataArray.size()];
        for (int i = 0; i < dataArray.size(); i++) {
            dataStrings[i] = dataArray.get(i).toString();
        }

        return dataStrings;
    }

    @Override
    public void onResponseReceived(String response){

        implementUI(response);
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
