package com.acme.acmevendor.activity.dashboard;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.acme.acmevendor.R;
import com.acme.acmevendor.adapters.CampaignListAdapter;
import com.acme.acmevendor.adapters.ClientListAdapter;
import com.acme.acmevendor.databinding.ActivityMainBinding;
import com.acme.acmevendor.utility.RoundRectCornerImageView;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.acme.acmevendor.viewmodel.MainViewModel;
import com.acme.acmevendor.viewmodel.SiteDetail;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.StringTokenizer;

public class AdminDashboardActivity extends AppCompatActivity implements ApiInterface {
    ActivityMainBinding binding;
    boolean showMenus = false;
    private final Context ctxt= this;
    int vendorclientorcampaign=0; //campaign is 0, client is 1, vendor is 2
    //TODO- populate this token
    String logintoken="";
    JSONArray jsonArray1;
    JSONArray jsonArray2;//client array
    JSONArray jsonArray3;//vendor array

    ProgressBar progressBar;
    Animation rotateAnimation;


    //todo access token save to memory add to api call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rvCampaignList.setLayoutManager(layoutManager);
        Log.d("whichclass", "AdminDashboardActivity");
        Log.d("tg5","2");
        logintoken = FileHelper.readLoginToken(this);
        Log.d("tg4", logintoken);

        //animation code
        progressBar= findViewById(R.id.progressBar);
        rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_animation);
        //animation code

        Log.d("tg5","3");
        //TODO remove after adding to ui
        jsonArray1= new JSONArray();
        jsonArray2=new JSONArray();
        jsonArray3= new JSONArray();
        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray1, false);
        binding.rvCampaignList.setAdapter(adapter);
        Log.d("tg5","4");

        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(rotateAnimation);

        campaignList();
    }

    //onresponsereceived from api
    public void onResponseReceived(String response){
        Log.d("addbatest", "response is "+ response);
        Log.d("tag21","5");
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
            if(jsonResponse.getBoolean("success")) {
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                if(dataArray != null && dataArray.length() > 0) {
                    if(vendorclientorcampaign==0){//cam

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
                        String imageUrl = dataObject.optString("image"); // or "logo"
                        //imageUrl = "https://acme.warburttons.com/" + imageUrl;
                        jsonObject.putOpt("image", imageUrl); // Store the full image URL
                        Log.d("tag1234", imageUrl);

                        //siteDetail.setName(dataObject.optString("name"));

                        jsonArray1.put(jsonObject);
//TODO here
                        }
                    }
                    }else if(vendorclientorcampaign==1){//client
                        for(int i=0; i< dataArray.length();i++){
                            Log.d("tg12", Integer.toString(i));
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            Log.d("tg12", dataArray.getJSONObject(i).toString());

                            if(dataObject != null) {
                                jsonObject1 = new JSONObject();
                                jsonObject= new JSONObject();
                                Log.d("DataObjectContent", "Data Object: " + dataObject.toString());
                                //AdminCrudDataClass siteDetail = new AdminCrudDataClass();
                                jsonObject.putOpt("id", dataObject.optInt("id"));
                                jsonObject.putOpt("company_name", dataObject.optString("company_name"));
                                jsonObject.putOpt("image", dataObject.optString("logo"));
                                jsonObject.putOpt("name", dataObject.optString("name"));
                                Log.d("tg12", dataArray.getJSONObject(i).toString());
                                String imageUrl = dataObject.optString("image"); // or "logo"
                                //imageUrl = "https://acme.warburttons.com/" + imageUrl;
                                //jsonObject.putOpt("image", imageUrl); // Store the full image URL



                                //to pass to client class
                                jsonObject1.putOpt("id", dataObject.optInt("id"));
                                jsonObject1.putOpt("name", dataObject.optString("name"));
                                jsonObject1.putOpt("email", dataObject.optString("email"));
                                jsonObject1.putOpt("phone_number", dataObject.optString("phone_number"));
                                jsonObject1.putOpt("company_name", dataObject.optString("company_name"));
                                jsonObject1.putOpt("company_address", dataObject.optString("company_address"));
                                jsonObject1.putOpt("gst_no", dataObject.optString("gst_no"));
                                jsonObject1.putOpt("logo", dataObject.optString("logo"));
                                jsonObject1.putOpt("created_at", dataObject.optString("created_at"));
                                jsonObject1.putOpt("updated_at", dataObject.optString("updated_at"));

                                //siteDetail.setName(dataObject.optString("name"));


                                jsonArray1.put(jsonObject);
                                jsonArray2.put(jsonObject1);
                            //TODO here
                            }
                        }

                    }else if(vendorclientorcampaign==2){//ven

                        for(int i=0; i< dataArray.length();i++){
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            if(dataObject != null) {
                                jsonObject2 = new JSONObject();
                                jsonObject= new JSONObject();

                                Log.d("DataObjectContent", "Data Object: " + dataObject.toString());
                                //AdminCrudDataClass siteDetail = new AdminCrudDataClass();
                                jsonObject.putOpt("id", dataObject.optInt("id"));
                                jsonObject.putOpt("company_name", dataObject.optString("company_name"));
                                jsonObject.putOpt("image", dataObject.optString("logo"));
                                jsonObject.putOpt("name", dataObject.optString("name"));
                                String imageUrl = dataObject.optString("image"); // or "logo"
                                //imageUrl = "https://acme.warburttons.com/" + imageUrl;
                                //jsonObject.putOpt("image", imageUrl); // Store the full image URL


                                //to pass to client class
                                jsonObject2.putOpt("id", dataObject.optInt("id"));
                                jsonObject2.putOpt("name", dataObject.optString("name"));
                                jsonObject2.putOpt("email", dataObject.optString("email"));
                                jsonObject2.putOpt("phone_number", dataObject.optString("phone_number"));
                                jsonObject2.putOpt("company_name", dataObject.optString("company_name"));
                                jsonObject2.putOpt("company_address", dataObject.optString("company_address"));
                                jsonObject2.putOpt("gst_no", dataObject.optString("gst_no"));
                                jsonObject2.putOpt("logo", dataObject.optString("logo"));
                                jsonObject2.putOpt("created_at", dataObject.optString("created_at"));
                                jsonObject2.putOpt("updated_at", dataObject.optString("updated_at"));
                                //siteDetail.setName(dataObject.optString("name"));


                                jsonArray1.put(jsonObject);
                                jsonArray3.put(jsonObject2);
//TODO here
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


                                              progressBar.clearAnimation();
                                              progressBar.setVisibility(View.GONE);
                                              //view.setVisibility(View.GONE);


                                  GridLayoutManager layoutManager = new GridLayoutManager(ctxt, 2);
                                  binding.rvCampaignList.setLayoutManager(layoutManager);
                                    CampaignListAdapter adapter = new CampaignListAdapter(ctxt, jsonArray1, true);
                                    binding.rvCampaignList.setAdapter(adapter);
                              }});
        }catch (Exception e){
            Log.d("tag40",e.toString());
        }
    }

    private void campaignList() {
        vendorclientorcampaign=0;
        //TODO pass correct logintoken here
        //logintoken="211|fcsu2C90hfOUduHNXDSZRxu7394NaQhOpiG3zMeM";
        Log.d("tg5","fin");


        APIreferenceclass api= new APIreferenceclass(vendorclientorcampaign, logintoken, this);
    }

    private void clientList() {
        vendorclientorcampaign=1;
        //TODO pass correct logintoken here
        //logintoken="Bearer 211|fcsu2C90hfOUduHNXDSZRxu7394NaQhOpiG3zMeM";
        //TODO- here

        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(rotateAnimation);
        APIreferenceclass api= new APIreferenceclass(vendorclientorcampaign, logintoken, this);
    }

    private void venderList() {
        vendorclientorcampaign=2;
        //TODO pass correct logintoken here
        //logintoken="Bearer 211|fcsu2C90hfOUduHNXDSZRxu7394NaQhOpiG3zMeM";

        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(rotateAnimation);
        APIreferenceclass api= new APIreferenceclass(vendorclientorcampaign, logintoken, this);
    }

    @SuppressLint("ResourceAsColor")
    public void btnCompaignClick(View view) {
        clearUi();

        binding.tvVender.setBackgroundResource(0);
        binding.tvVender.setTextColor(R.color.colorPrimaryDark);

        binding.tvClient.setBackgroundResource(0);
        binding.tvClient.setTextColor(R.color.colorPrimaryDark);

        binding.tvCompaign.setBackgroundResource(R.drawable.primaryround);
        binding.tvCompaign.setTextColor(Color.WHITE);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(rotateAnimation);

        campaignList();
        // ... your logic ...
    }

    @SuppressLint("ResourceAsColor")
    public void btnVenderClick(View view) {
        Log.d("tag20", "btnvenderclick");
        clearUi();

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
        clearUi();

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

    public void onEditClick(int position){
        //add code to dropdown a list which says delete
        //delete code


    }

    public void onItemClick(int position) {
        try {
            Log.d("tag51", Integer.toString(position));
            // Retrieve JSONObject from your jsonArray at position
            JSONObject jsonObject = jsonArray1.getJSONObject(position);
            Log.d("tag51", jsonArray1.getJSONObject(position).toString());

            //TODO add correct login token here
            //logintoken="Bearer 322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";

            // Get site id or site no from the JSONObject
            String id = jsonObject.getString("id"); // Or get an id if you have that
            Log.d("tag51", jsonObject.getString("id"));
            Log.d("tag60", jsonObject.toString());

            // String siteId = jsonObject.getString("siteId"); // If you have a site id.

            if(vendorclientorcampaign==0){//campaign

                // Start new activity and pass the retrieved data


                startActivity(new Intent(this, ViewCampaignSites.class)
                        .putExtra("campaignType", "old")
                        .putExtra("id", id)
                        .putExtra("logintoken", logintoken)
                        .putExtra("vendorclientorcampaign", vendorclientorcampaign)
                        .putExtra("apiresponse", jsonObject.toString()));

            }else if(vendorclientorcampaign==1){//client

                JSONObject jsonObject1 = jsonArray2.getJSONObject(position);
                Log.d("tag91", jsonArray2.getJSONObject(position).toString());
                Log.d("tag91",jsonArray2.toString());
                Log.d("tag91",jsonObject1.toString());

                Log.d("tag91",Integer.toString(position));

                Log.d("tg11", id);

                startActivity(new Intent(this, AdminViewClientDetails.class)
                        .putExtra("id", id)
                        .putExtra("logintoken", logintoken)
                        .putExtra("vendorclientorcampaign", vendorclientorcampaign)
                        .putExtra("jsonArray", jsonObject1.toString()));
                //jsonObject1= new JSONObject();
                //jsonArray2= new JSONArray();

            }else if(vendorclientorcampaign==2){//vendor

                JSONObject jsonObject1 = jsonArray3.getJSONObject(position);
                //Log.d("tag91", jsonArray2.getJSONObject(position).toString());
                //Log.d("tag91",jsonArray2.toString());
                //Log.d("tag91",jsonObject1.toString());
                //Log.d("tag91",Integer.toString(position));

                startActivity(new Intent(this, AdminViewVendorDetails.class)
                        .putExtra("id", id)
                        .putExtra("logintoken", logintoken)
                        .putExtra("vendorclientorcampaign", vendorclientorcampaign)
                        .putExtra("jsonArray", jsonObject1.toString()));
                //jsonObject1= new JSONObject();
                //jsonArray2= new JSONArray();
            }

            // .putExtra("siteId", siteId)); // If you are passing site id
        } catch (JSONException e) {
            Log.d("tag123", e.toString());
            // Handle exception (e.g. show a Toast to the user indicating an error)
        }
    }

    private void clearUi() {
        // Clear the RecyclerView
        if (binding.rvCampaignList.getAdapter() != null) {
            CampaignListAdapter adapter = (CampaignListAdapter) binding.rvCampaignList.getAdapter();
            adapter.clearData(); // You'll need to implement a method 'clearData()' in your adapter class
            if (vendorclientorcampaign == 0) {
                jsonArray1 = new JSONArray();
            } else if (vendorclientorcampaign == 1) {
                jsonArray2 = new JSONArray();
            } else if(vendorclientorcampaign == 2){
                jsonArray3= new JSONArray();
            }
        }
        // Reset any other UI elements here as needed
    }

    public void onPlusClick(View view) {

        Log.d("tag20", "onplusclick");
    if(vendorclientorcampaign==1) {
        Intent intent = new Intent(AdminDashboardActivity.this, AddClientActivity.class);
        intent.putExtra("logintoken", logintoken);
        startActivity(intent);
    }else if(vendorclientorcampaign==2){//vendor
        Intent intent = new Intent(AdminDashboardActivity.this, AddVenderActivity.class);
        intent.putExtra("logintoken", logintoken);
        startActivity(intent);
    }else{
        Intent intent = new Intent(AdminDashboardActivity.this, AddCampaignDetails.class);
        intent.putExtra("logintoken", logintoken);
        startActivity(intent);
    }

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