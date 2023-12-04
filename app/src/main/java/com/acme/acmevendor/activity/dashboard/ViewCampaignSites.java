package com.acme.acmevendor.activity.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
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

import com.acme.acmevendor.R;
import com.acme.acmevendor.adapters.CampaignListAdapter;
import com.acme.acmevendor.databinding.ActivityMainBinding;
import com.acme.acmevendor.databinding.ActivityViewCampaignSitesBinding;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.acme.acmevendor.viewmodel.MainViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;

public class ViewCampaignSites extends AppCompatActivity implements ApiInterface {

    int id=0;
    ActivityViewCampaignSitesBinding binding;
    //JSONArray jsonArray;
    boolean showMenus = false;
    private final Context ctxt= this;
    int vendorclientorcampaign=0; //campaign is 0, client is 1, vendor is 2
    //TODO- populate this token
    String logintoken="";
    String idofcampaign;

    //todo access token save to memory add to api call

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_campaign_sites);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rvCampaignList.setLayoutManager(layoutManager);
        Log.d("whichclass", "ViewCampaignSites");

        //animation code
        progressBar= findViewById(R.id.progressBar);
        rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_animation);
        //animation code

        FileHelper fh= new FileHelper();
        logintoken= fh.readLoginToken(this);

        idofcampaign=getIntent().getStringExtra("id");
        Log.d("tag58", idofcampaign);

        //TODO remove after adding to ui
        jsonArray1= new JSONArray();
        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray1);
        binding.rvCampaignList.setAdapter(adapter);
        campaignList(idofcampaign);
    }

    //onresponsereceived from api
    public void onResponseReceived(String response){

        Log.d("addbatest", "response is "+ response);
        Log.d("tag58","got response");


        implementUi(response);

        /*
        //TODO: handle population
        String[] dataStrings = extractDataStrings(response);
        // Usage example
        for(String dataStr : dataStrings) {
            Log.d("tag2222",dataStr);
        }
        //id array. send to ui
        String[] idArray= extractIds(dataStrings);

        //TODO extract the unit id and pass that too
        implementUi(response);
        Log.d("MyApp", "Extracted IDs: " + Arrays.toString(idArray));

 */

    }
    JSONArray jsonArray1;
    private void implementUi(String response){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonArray1= new JSONArray();

            String ids[];
            JSONObject jsonResponse = new JSONObject(response);
            if(jsonResponse.getBoolean("success")) {
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                if(dataArray != null && dataArray.length() > 0) {
                    if(vendorclientorcampaign==0){

                        //TODO here. have to filter based on whether this class is called from admin dash or client dash or vendor dash. Right now only 0 being called

                        Log.d("tag998", "1");
                        for(int i=0; i< dataArray.length();i++){

                            JSONObject dataObject = dataArray.getJSONObject(i);
                            if(dataObject != null) {
                                jsonObject = new JSONObject();
                                Log.d("DataObjectContent", "Data Object: " + dataObject.toString());
                                //AdminCrudDataClass siteDetail = new AdminCrudDataClass();
                                jsonObject.putOpt("id", dataObject.optInt("id"));
                                jsonObject.putOpt("uid", dataObject.optString("uid"));
                                jsonObject.putOpt("image", dataObject.optString("image"));
                                jsonObject.putOpt("name", dataObject.optString("name"));

                                //siteDetail.setName(dataObject.optString("name"));

                                try {
                                    String imageUrl = dataObject.optString("image");
                                    imageUrl = "https://acme.warburttons.com/" + imageUrl;
                                    Log.d("tag41", "imageurl is " + imageUrl);
                                    if (imageUrl != "null" && !imageUrl.isEmpty()) {
                                        URL url = new URL(imageUrl);
                                        Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        //siteDetail.setImage(bitmap);
                                    }
                                } catch (Exception e) {
                                    Log.d("tag41", "error in implementui" + e.toString());
                                    Log.e("tag41", "sdfdg", e);
                                    // Handle error
                                }
                                jsonArray1.put(jsonObject);
//TODO here
                            }
                        }
                    }else if(vendorclientorcampaign==1){

                        Log.d("tag998", "2");
                        for(int i=0; i< dataArray.length();i++){
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            if(dataObject != null) {
                                jsonObject = new JSONObject();
                                Log.d("DataObjectContent", "Data Object: " + dataObject.toString());
                                //AdminCrudDataClass siteDetail = new AdminCrudDataClass();
                                jsonObject.putOpt("id", dataObject.optInt("id"));
                                jsonObject.putOpt("company_name", dataObject.optString("company_name"));
                                jsonObject.putOpt("image", dataObject.optString("logo"));
                                jsonObject.putOpt("name", dataObject.optString("name"));

                                //siteDetail.setName(dataObject.optString("name"));

                                try {
                                    String imageUrl = dataObject.optString("logo");
                                    imageUrl = "https://acme.warburttons.com/" + imageUrl;
                                    Log.d("tag41", "imageurl is " + imageUrl);
                                    if (imageUrl != "null" && !imageUrl.isEmpty()) {
                                        URL url = new URL(imageUrl);
                                        Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        //siteDetail.setImage(bitmap);
                                    }
                                } catch (Exception e) {
                                    Log.d("tag41", "error in implementui" + e.toString());
                                    Log.e("tag41", "sdfdg", e);
                                    // Handle error
                                }
                                jsonArray1.put(jsonObject);
//TODO here
                            }
                        }

                    }else if(vendorclientorcampaign==2){

                        Log.d("tag998", "3");
                        for(int i=0; i< dataArray.length();i++){
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            if(dataObject != null) {
                                jsonObject = new JSONObject();
                                Log.d("DataObjectContent", "Data Object: " + dataObject.toString());
                                //AdminCrudDataClass siteDetail = new AdminCrudDataClass();
                                jsonObject.putOpt("id", dataObject.optInt("id"));
                                jsonObject.putOpt("company_name", dataObject.optString("company_name"));
                                jsonObject.putOpt("image", dataObject.optString("logo"));
                                jsonObject.putOpt("name", dataObject.optString("name"));

                                //siteDetail.setName(dataObject.optString("name"));

                                try {
                                    String imageUrl = dataObject.optString("logo");
                                    imageUrl = "https://acme.warburttons.com/" + imageUrl;
                                    Log.d("tag41", "imageurl is " + imageUrl);
                                    if (imageUrl != "null" && !imageUrl.isEmpty()) {
                                        URL url = new URL(imageUrl);
                                        Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        //siteDetail.setImage(bitmap);
                                    }
                                } catch (Exception e) {
                                    Log.d("tag41", "error in implementui" + e.toString());
                                    Log.e("tag41", "sdfdg", e);
                                    // Handle error
                                }

//TODO here
                            jsonArray1.put(jsonObject);
                        }
                        }
                    }
                    Log.d("JSONArrayContent", "JSONArray1: " + jsonArray1.toString());
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Your UI update code goes here

                    GridLayoutManager layoutManager = new GridLayoutManager(ctxt, 2);
                    binding.rvCampaignList.setLayoutManager(layoutManager);
                    CampaignListAdapter adapter = new CampaignListAdapter(ctxt, jsonArray1);

                    //animation code
                            progressBar.clearAnimation();
                            progressBar.setVisibility(View.GONE);
                    //animation code

                    binding.rvCampaignList.setAdapter(adapter);
                }});
        }catch (Exception e){}
    }

    ProgressBar progressBar;
    Animation rotateAnimation;

    private void campaignList(String id) {
        vendorclientorcampaign=0;
        //TODO pass correct logintoken here

        //animation code
        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(rotateAnimation);
        //view.setVisibility(View.VISIBLE);
        //animation code
        APIreferenceclass api= new APIreferenceclass(logintoken, this, id);
    }

    public void onItemClick(int position) {
        try {
            Log.d("tag51", Integer.toString(position));
            // Retrieve JSONObject from your jsonArray at position
            JSONObject jsonObject = jsonArray1.getJSONObject(position);
            Log.d("tag51", jsonArray1.getJSONObject(position).toString());

            //TODO add correct login token here
            //="Bearer 322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";

            // Get site id or site no from the JSONObject
            String id = jsonObject.getString("id"); // Or get an id if you have that
            Log.d("tag51", jsonObject.getString("id"));

            // String siteId = jsonObject.getString("siteId"); // If you have a site id.

            // Start new activity and pass the retrieved data
            startActivity(new Intent(this, ViewSiteDetailActivity.class)
                    .putExtra("campaignType", "old")
                    .putExtra("campaignId", idofcampaign)
                    .putExtra("siteNumber", id)
                    .putExtra("logintoken", logintoken)
                    .putExtra("vendorclientorcampaign", vendorclientorcampaign));

            // .putExtra("siteId", siteId)); // If you are passing site id
        } catch (JSONException e) {
            Log.d("tag123", e.toString());
            // Handle exception (e.g. show a Toast to the user indicating an error)
        }
    }

    public void onPlusClick(View view) {

        Log.d("tag20", "onplugtreehththtsclick");
        Intent intent= new Intent(ViewCampaignSites.this, AddSiteDetailActivity.class);
        startActivity(intent);
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
}