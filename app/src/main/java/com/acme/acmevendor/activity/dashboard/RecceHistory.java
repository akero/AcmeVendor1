package com.acme.acmevendor.activity.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.acme.acmevendor.databinding.ActivityRecceHistoryBinding;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.acme.acmevendor.R;
import com.acme.acmevendor.adapters.CampaignListAdapter;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class RecceHistory extends AppCompatActivity implements ApiInterface {

    int id;
    String logintoken;
    String[] idarray;
    private final Context ctxt= this;
    //TODO- populate this token


    ProgressBar progressBar;
    Animation rotateAnimation;

    String campaignName;
    JSONArray jsonArray1;
    ActivityRecceHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recce_history);
        campaignName="";


        id= 0;
        logintoken= "";

        try{

            id= getIntent().getIntExtra("id", 0);
            logintoken= getIntent().getStringExtra("logintoken");

        }catch(Exception e){
            Log.d("asdsad", e.toString());
        }

        Log.d("id", Integer.toString(id));

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        binding.rvCampaignList.setLayoutManager(layoutManager);

        Log.d("whichclass", "ViewVendorSites");

        binding.ivPlus.setVisibility(View.GONE);




        //animation code
        progressBar= findViewById(R.id.progressBar);
        rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_animation);
        //animation code
        //animation code
        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(rotateAnimation);
        //view.setVisibility(View.VISIBLE);
        //animation code

        Log.d("tag97", "here");



        //TODO remove after adding to ui
        jsonArray1= new JSONArray();
        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray1, false);
        binding.rvCampaignList.setAdapter(adapter);

        APIreferenceclass api= new APIreferenceclass(logintoken, this, id, 1);

    }

    public void onItemClick(int position) {
        try {
            Log.d("tag51", Integer.toString(position));
            // Retrieve JSONObject from your jsonArray at position
            JSONObject jsonObject = jsonArray1.getJSONObject(position);
            Log.d("tag51", jsonArray1.getJSONObject(position).toString());

            //logintoken="Bearer 322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";

            // Get site id or site no from the JSONObject
            String id1 = jsonObject.getString("id"); // Or get an id if you have that
            Log.d("tag51", jsonObject.getString("id"));

            // String siteId = jsonObject.getString("siteId"); // If you have a site id.

            //TODO change this

            // Start new activity and pass the retrieved data
            startActivity(new Intent(this, RecceHistoryDetails.class)
                    .putExtra("id", id1)
                    .putExtra("camefrom", "RecceHistory")
                    .putExtra("userid", id)
                    .putExtra("logintoken", logintoken)
                    .putExtra("idarray", idarray));

            // .putExtra("siteId", siteId)); // If you are passing site id
        } catch (JSONException e) {
            Log.d("tag123", e.toString());
            // Handle exception (e.g. show a Toast to the user indicating an error)
        }
    }

    void implementUI(String response){

        try {
        JSONObject jsonObject = new JSONObject();
        jsonArray1= new JSONArray();

        Log.d("tg111", response);
        String ids[];
        JSONObject jsonResponse = new JSONObject(response);

        //campaignName="";
        //campaignName= jsonResponse.getString("campaign_name");

        if(jsonResponse.getString("message").equals("Data fetched successfully!")) {
            JSONArray dataArray = jsonResponse.getJSONArray("data");
            idarray= new String[dataArray.length()];

            if(dataArray != null && dataArray.length() > 0) {


                    for(int i=0; i< dataArray.length();i++){

                        JSONObject dataObject = dataArray.getJSONObject(i);
                        if(dataObject != null) {
                            jsonObject = new JSONObject();
                            Log.d("DataObjectContent", "Data Object: " + dataObject.toString());
                            //AdminCrudDataClass siteDetail = new AdminCrudDataClass();
                            idarray[i]= Integer.toString(dataObject.optInt("id"));

                            jsonObject.putOpt("id", dataObject.optInt("id"));
                            jsonObject.putOpt("uid", "n/a");
                            jsonObject.putOpt("image", dataObject.optString("image1"));
                            jsonObject.putOpt("name", dataObject.optString("retail_name"));

                            //siteDetail.setName(dataObject.optString("name"));

                            try {
                                String imageUrl = dataObject.optString("image1");
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

                Log.d("JSONArrayContent", "JSONArray1: " + jsonArray1.toString());
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Your UI update code goes here

                GridLayoutManager layoutManager = new GridLayoutManager(ctxt, 1);
                binding.rvCampaignList.setLayoutManager(layoutManager);
                CampaignListAdapter adapter = new CampaignListAdapter(ctxt, jsonArray1, false);


                //binding.tvCampaign.setText(campaignName);
                progressBar.clearAnimation();
                progressBar.setVisibility(View.GONE);
                //view.setVisibility(View.GONE);

                binding.rvCampaignList.setAdapter(adapter);
            }});
    }catch (Exception e){}
    }
    @Override
    public void onResponseReceived(String response) {

        try {
            JSONObject jsonobj = new JSONObject(response);

            if (jsonobj.getString("message").equals("Data fetched successfully!")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        implementUI(response);
                    }
                });
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "An error occured.", Toast.LENGTH_SHORT).show();
                    }
            });}


        }catch (Exception e){
            e.printStackTrace();
        }




    }
}