package com.acme.acmevendor.activity.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.acme.acmevendor.R;
import com.acme.acmevendor.adapters.CampaignListAdapter;
import com.acme.acmevendor.adapters.ClientListAdapter;
import com.acme.acmevendor.databinding.ActivityMainBinding;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.acme.acmevendor.viewmodel.MainViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringTokenizer;

public class AdminDashboardActivity extends AppCompatActivity implements ApiInterface {
    MainViewModel mainViewModel;
    ActivityMainBinding binding;
    JSONArray jsonArray;
    boolean showMenus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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
            Log.d("tag21", e.toString());
        }

        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray);
        binding.rvCampaignList.setAdapter(adapter);

        campaignList();
    }



    int id=0;
    String image="";
    String vendor_id="";
    int campaign_id=0;
    String start_date=null;
    String end_date=null;
    String location="";
    String longitude="";
    String latitude="";
    String width="";
    String height="";
    String total_area="";
    String media_type="";
    String illumination="";
    String created_at="";
    String updated_at="";

    public String parseString(String response){


        String a=response;
        int i= a.indexOf("[");
        int j= a.lastIndexOf("]");

        String b= a.substring(i+1,j);
        Log.d("tag26", b);

        return b;
    }


    int vendorclientorcampaign=0; //campaign is 0, client is 1, vendor is 2

    //TODO- populate this token
    String logintoken="";


//onresponsereceived from api
    public void onResponseReceived(String response){

        Log.d("tag25", "response is "+ response);
        Log.d("tag21","5");
        //TODO: handle population


        //removing stuff except data
        String a= parseString(response);

        //put data for every item in a separate string
        char d;
        int numberofitems= 0;
        for(int k=0; k< a.length(); k++){

            d= a.charAt(k);
            if (d =='{'){
                numberofitems+=1;
            }
        }

        String data[]= new String[numberofitems];
        StringTokenizer str= new StringTokenizer(a, "{");

        for(int v=0; v<numberofitems; v++){

            data[v]= str.nextToken();
            if (data[v].endsWith(",")){
                data[v]= data[v].substring(0, data[v].length()-1);
            }

            if (data[v].endsWith("}")){
                data[v]= data[v].substring(0, data[v].length()-1);
            }
            implementUi(data);




        }
    }

    //TODO sort this data and populate page. then implement for vendor and client

    private final Context ctxt= this;

    //array for campaign id
    String campaignidarray[][];

    private void implementUi(String a[]) {

        Log.d("tag40", "1");

        //TODO unit id is not clear yet
        int unitid = 1;

        campaignidarray = new String[a.length][1];
        Log.d("tag40", "2");

        // Extracting campaign ids
        String[][] extractedcampaignids = extractCampaignIds(a);
        Log.d("tag40", "3");

        //TODO here
        Log.d("tag40", "4");

        // Print the populated array
        for (int i = 0; i < extractedcampaignids.length; i++) {
            for (int j = 0; j < extractedcampaignids[i].length; j++) {
                Log.d("tag33", extractedcampaignids[i][j] + " ");
            }
        }
        Log.d("tag40", "5");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Your UI update code goes here

                GridLayoutManager layoutManager = new GridLayoutManager(ctxt, 2);
                binding.rvCampaignList.setLayoutManager(layoutManager);
                JSONArray jsonArray = new JSONArray();

                Log.d("tag31", "here");

                // Loop through the extracted campaign ids and create JSONObjects for each id
                for (int i = 0; i < extractedcampaignids.length; i++) {
                    try {
                        // Create a new JSONObject for each campaign id
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("sitenumber", "Site " + (i + 1));
                        jsonObject.put("unitnumber", extractedcampaignids[i][0]);  // Assuming the campaign id is the unit number
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        Log.d("tag21", e.toString());
                    }
                }
//TODO here. ui is updating. Implement properly for all values and for vendor and client

                CampaignListAdapter adapter = new CampaignListAdapter(ctxt, jsonArray);
                binding.rvCampaignList.setAdapter(adapter);
            }
        });
    }

    public String[][] extractCampaignIds(String[] a) {
        // The result array will have a length of a.length, but only the first entry will be populated
        String[][] extractedCampaignIds = new String[a.length][1];

        // Check if the first entry of the array is not null
        if (a[0] != null) {
            // Get the first entry of the array
            String b = a[0];

            // Find the index of "campaign_id":
            int campaignIdIndex = b.indexOf("\"campaign_id\":");
            if (campaignIdIndex != -1) {  // Check if "campaign_id": was found
                // Extract the campaign_id value
                int startIndex = campaignIdIndex + 13;  // 13 is the length of "campaign_id": including the colon
                int endIndex = b.indexOf(",", startIndex);  // Find the next comma after "campaign_id":
                if (endIndex == -1) {  // If there's no comma, find the next closing curly brace
                    endIndex = b.indexOf("}", startIndex);
                }
                if (endIndex != -1) {  // Check if endIndex was found
                    // Extract the campaign_id substring
                    String campaignId = b.substring(startIndex, endIndex);
                    // Remove any unwanted characters (like quotes) from campaignId
                    campaignId = campaignId.replace("\"", "").trim();
                    // Store the campaign_id in the result array
                    extractedCampaignIds[0][0] = campaignId;
                } else {
                    System.err.println("Failed to extract campaign_id: endIndex not found");
                }
            } else {
                System.err.println("Failed to extract campaign_id: campaign_id index not found");
            }
        } else {
            System.err.println("The first entry of the array is null");
        }

        return extractedCampaignIds;
    }


    private void campaignList() {
        vendorclientorcampaign=0;
        logintoken="322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";
        APIreferenceclass api= new APIreferenceclass(vendorclientorcampaign, logintoken, this);
    }

    private void clientList() {
        vendorclientorcampaign=1;
        logintoken="Bearer 322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";

        //TODO- here

        APIreferenceclass api= new APIreferenceclass(vendorclientorcampaign, logintoken, this);





    }

    private void venderList() {
        vendorclientorcampaign=2;
        logintoken="Bearer 322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";

        APIreferenceclass api= new APIreferenceclass(vendorclientorcampaign, logintoken, this);


    }

    public void onPlusClick(View view) {

        Log.d("tag20", "onplusclick");
        //TODO ask lodu what this does
    }

    public void onNotificationClick(View view){
        //TODO ask lodu what this does
    }

    public void onAddClientClick(View view) {
        startActivity(new Intent(this, AddClientActivity.class));
    }

    public void onAddVenderClick(View view) {
        startActivity(new Intent(this, AddVenderActivity.class));
    }

    public void onDeleteClientClick(View view) {
        // ... your logic ...
    }

    @SuppressLint("ResourceAsColor")
    public void btnCompaignClick(View view) {

        binding.tvVender.setBackgroundResource(0);
        binding.tvVender.setTextColor(R.color.colorPrimaryDark);

        binding.tvClient.setBackgroundResource(0);
        binding.tvClient.setTextColor(R.color.colorPrimaryDark);

        binding.tvCompaign.setBackgroundResource(R.drawable.primaryround);
        binding.tvCompaign.setTextColor(Color.WHITE);

        campaignList();
        // ... your logic ...
    }

    @SuppressLint("ResourceAsColor")
    public void btnVenderClick(View view) {
        Log.d("tag20", "btnvenderclick");

        binding.tvVender.setBackgroundResource(R.drawable.primaryround);
        binding.tvVender.setTextColor(Color.WHITE);

        binding.tvClient.setBackgroundResource(0);
        binding.tvClient.setTextColor(R.color.colorPrimaryDark);

        binding.tvCompaign.setBackgroundResource(0);
        binding.tvCompaign.setTextColor(R.color.colorPrimaryDark);


        venderList();
        // ... your logic ...
    }

    @SuppressLint("ResourceAsColor")
    public void btnClientClick(View view) {
        Log.d("tag20", "btnclientclick");

        binding.tvVender.setBackgroundResource(0);
        binding.tvVender.setTextColor(R.color.colorPrimaryDark);

        binding.tvClient.setBackgroundResource(R.drawable.primaryround);
        Log.d("tag20", "1");
        binding.tvClient.setTextColor(Color.WHITE);
        Log.d("tag20", "2");

        binding.tvCompaign.setBackgroundResource(0);
        binding.tvCompaign.setTextColor(R.color.colorPrimaryDark);

        clientList();
        // ... your logic ...
    }

    public void onItemClick(int position) {
        startActivity(new Intent(this, ViewSiteDetailActivity.class)
                .putExtra("position", position)
                .putExtra("campaignType", "old"));
    }
}