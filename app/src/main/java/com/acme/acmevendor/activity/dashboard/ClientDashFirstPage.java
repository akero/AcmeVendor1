package com.acme.acmevendor.activity.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.acme.acmevendor.R;
import com.acme.acmevendor.adapters.CampaignListAdapter;
import com.acme.acmevendor.databinding.ActivityClientDashFirstPageBinding;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClientDashFirstPage extends AppCompatActivity implements ApiInterface {

    private ActivityClientDashFirstPageBinding binding;
    private boolean oldcampaign = true;

    String loginToken="";
    int liveold; //0 live

    //TODO handle clicks on old and live campaign, make api call, parse data, populate. pass site details api data to viewsitedetailactivity
    //TODO access token save to memory add to api call

    Context ctxt;

    ProgressBar progressBar;
    Animation rotateAnimation;
    int clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_dash_first_page);

        Log.d("tag199", "1");
        Log.d("whichclass", "ClientDashFirstPage");
        clientId= 0;
        liveold= 0;

        //animation code
        progressBar= findViewById(R.id.progressBar);
        rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_animation);
        //animation code

        String jsonArray= "";
        try {
            jsonArray = getIntent().getStringExtra("jsonArray");
            Log.d("tag234", jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            JSONObject jsonobj = new JSONObject(jsonArray);
            clientId= jsonobj.getInt("id");
            Log.d("clientid", Integer.toString(clientId));
        }catch(Exception e){
            e.printStackTrace();
        }

        ctxt= this;

        loginToken= FileHelper.readLoginToken(this);
        Log.d("tg4", loginToken);


        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray1, false);
        binding.rvCampaignList.setAdapter(adapter);
        //TODO remove after adding to ui
        // jsonArray1= new JSONArray();
        // jsonArray2=new JSONArray();
        // jsonArray3= new JSONArray();

        campaignList();
    }

    public void onNotificationClick(View v){
        //TODO ask lodu what this does
    }
    //String logintoken="534|ehyJudmoAsTjmkbTLBcIjzUOCFIui40OSBL01JJJ";
    private void campaignList() {
        int vendorclientorcampaign= 1;

        Log.d("tg5","fin");

        //animation code
        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(rotateAnimation);
        //view.setVisibility(View.VISIBLE);
        //animation code

        APIreferenceclass api= new APIreferenceclass(clientId, vendorclientorcampaign, loginToken, this);
    }

    public void onPlusClick(View view) {

        Log.d("tag20", "here");

        Intent intent = new Intent(ClientDashFirstPage.this, AddCampaignDetails.class);
        intent.putExtra("logintoken", loginToken);
        startActivity(intent);
    }

    //TODO add token to future activity
    @Override
    public void onResponseReceived(String response){
        Log.d("cldbatest","response is "+ response);


        implementUi(response);
    }

    JSONArray jsonArray1;
    JSONArray jsonArray2;//client array
    JSONArray jsonArray3;//vendor array

    private void implementUi(String response){
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObject1= new JSONObject();
            JSONObject jsonObject2= new JSONObject();
            jsonArray1= new JSONArray();
            jsonArray2= new JSONArray();
            jsonArray3= new JSONArray();
            String ids[];
            JSONObject jsonResponse = new JSONObject(response);
            if(jsonResponse.getString("status").equals("success")) {

                if(liveold== 0){
                JSONArray dataArray = jsonResponse.getJSONArray("live_campaigns");
                if(dataArray != null && dataArray.length() > 0) {
                    // if(vendorclientorcampaign==0){

                    for(int i=0; i< dataArray.length();i++){

                        JSONObject dataObject = dataArray.getJSONObject(i);
                        if(dataObject != null) {
                            jsonObject = new JSONObject();
                            Log.d("DataObjectContent", "Data Object: " + dataObject.toString());
                            //AdminCrudDataClass siteDetail = new AdminCrudDataClass();
                            jsonObject.putOpt("id", dataObject.optInt("id"));
                            jsonObject.putOpt("uid", dataObject.optString("uid"));
                            jsonObject.putOpt("image", dataObject.optString("logo"));
                            jsonObject.putOpt("name", dataObject.optString("name"));

                            //siteDetail.setName(dataObject.optString("name"));

                            // Inside the for-loop where you process each `dataObject`
                            //String imageUrl = dataObject.optString("image");
                            //jsonObject.putOpt("image", imageUrl);

                            jsonArray1.put(jsonObject);
//TODO here
                        }
                    }
                    // }
                    Log.d("JSONArrayContent", "JSONArray1: " + jsonArray1.toString());
                }
                }else{

                    JSONArray dataArray = jsonResponse.getJSONArray("old_campaigns");
                    if(dataArray != null && dataArray.length() > 0) {
                        // if(vendorclientorcampaign==0){

                        for(int i=0; i< dataArray.length();i++){

                            JSONObject dataObject = dataArray.getJSONObject(i);
                            if(dataObject != null) {
                                jsonObject = new JSONObject();
                                Log.d("DataObjectContent", "Data Object: " + dataObject.toString());
                                //AdminCrudDataClass siteDetail = new AdminCrudDataClass();
                                jsonObject.putOpt("id", dataObject.optInt("id"));
                                jsonObject.putOpt("uid", dataObject.optString("uid"));
                                jsonObject.putOpt("image", dataObject.optString("logo"));
                                jsonObject.putOpt("name", dataObject.optString("name"));

                                //siteDetail.setName(dataObject.optString("name"));

                                // Inside the for-loop where you process each `dataObject`
                                //String imageUrl = dataObject.optString("image");
                                //jsonObject.putOpt("image", imageUrl);

                                jsonArray1.put(jsonObject);
//TODO here
                            }
                        }
                        // }
                        Log.d("JSONArrayContent", "JSONArray1: " + jsonArray1.toString());
                    }

                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Your UI update code goes here

                    GridLayoutManager layoutManager = new GridLayoutManager(ctxt, 2);
                    binding.rvCampaignList.setLayoutManager(layoutManager);
                    CampaignListAdapter adapter = new CampaignListAdapter(ctxt, jsonArray1, false);

                    //animation code
                    progressBar.clearAnimation();
                    progressBar.setVisibility(View.GONE);
                    //animation code

                    binding.rvCampaignList.setAdapter(adapter);
                }});
        }catch (Exception e){
            Log.d("tag40",e.toString());
        }
    }


    //TODO replace. this is for response for the current page's data.
    //TODO implement response into UI
    String campaignType="";
    int position= 0;

    /*Intent intent= new Intent(VenderDashBoardActivity.this, UpdateSiteDetailActivity.class);
    intent.putExtra("campaigntype", campaignType);
    intent.putExtra("position", position);
    intent.putExtra("logintoken", loginToken);
    startActivity( intent);
*/
    public void onItemClick(int position) {
        try {
            Log.d("tag51", Integer.toString(position));
            // Retrieve JSONObject from your jsonArray at position
            JSONObject jsonObject = jsonArray1.getJSONObject(position);
            Log.d("tag51", jsonArray1.getJSONObject(position).toString());


            //logintoken="Bearer 322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";

            // Get site id or site no from the JSONObject
            String id = jsonObject.getString("id"); // Or get an id if you have that
            Log.d("tag51", jsonObject.getString("id"));
            Log.d("tag60", jsonObject.toString());

            // String siteId = jsonObject.getString("siteId"); // If you have a site id.
            int vendorclientorcampaign= 1;

            Log.d("tag122", id);

            // Start new activity and pass the retrieved data
            startActivity(new Intent(this, ViewCampaignSitesClientDash.class)
                    .putExtra("campaignType", "old")

                    .putExtra("id", id)
                    .putExtra("vendorclientorcampaign", vendorclientorcampaign)
                    .putExtra("logintoken", loginToken)
                    .putExtra("apiresponse", jsonObject.toString()));

            // .putExtra("siteId", siteId)); // If you are passing site id
        } catch (JSONException e) {
            Log.d("tag123", e.toString());
            // Handle exception (e.g. show a Toast to the user indicating an error)
        }
    }

    void oldCampaign(){

        int vendorclientorcampaign= 1;
        //anim code
        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(rotateAnimation);
        //anim code

        APIreferenceclass api= new APIreferenceclass(clientId, vendorclientorcampaign, loginToken, this, 1);

    }

    public void liveCampaignClick(View v){
        clearUi();
        liveold=0;
        campaignList();
    }

    public void oldCampaignClick(View v){
        clearUi();
        liveold=1;
        campaignList();
    }

    private void clearUi() {
        // Clear the RecyclerView
        if (binding.rvCampaignList.getAdapter() != null) {
            CampaignListAdapter adapter = (CampaignListAdapter) binding.rvCampaignList.getAdapter();
            adapter.clearData(); // You'll need to implement a method 'clearData()' in your adapter class



            /*if (vendorclientorcampaign == 0) {
                jsonArray1 = new JSONArray();
            } else if (vendorclientorcampaign == 1) {
                jsonArray2 = new JSONArray();
            } else if(vendorclientorcampaign == 2){
                jsonArray3= new JSONArray();
            }*/
        }
        // Reset any other UI elements here as needed
    }
}
