package com.acme.acmevendor.activity.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.acme.acmevendor.R;
import com.acme.acmevendor.adapters.CampaignListAdapter;
import com.acme.acmevendor.databinding.ActivityClientDashBoardBinding;
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

public class ClientDashBoardActivity extends AppCompatActivity implements ApiInterface {

    private ActivityClientDashBoardBinding binding;
    private boolean oldcampaign = true;

    String loginToken="";

    //TODO handle clicks on old and live campaign, make api call, parse data, populate. pass site details api data to viewsitedetailactivity
    //TODO access token save to memory add to api call

    Context ctxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_dash_board);

        ctxt= this;

        loginToken= FileHelper.readLoginToken(this);
        Log.d("tg4", loginToken);


        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray1);
        binding.rvCampaignList.setAdapter(adapter);
        //TODO remove after adding to ui
       // jsonArray1= new JSONArray();
       // jsonArray2=new JSONArray();
       // jsonArray3= new JSONArray();


      /*  //TODO remove
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
*/
        campaignList();
    }

    public void onNotificationClick(View v){
        //TODO ask lodu what this does
    }
    //TODO pass correct logintoken here
    String logintoken="534|ehyJudmoAsTjmkbTLBcIjzUOCFIui40OSBL01JJJ";
    private void campaignList() {
        int vendorclientorcampaign= 1;



        Log.d("tg5","fin");
        APIreferenceclass api= new APIreferenceclass(vendorclientorcampaign, logintoken, this);
    }

/*
    private void campaignList() {
        int vendorclientorcampaign= 1;
        String logintoken= loginToken;

        //TODO here
        //TODO remove after hardcode is removed
        //logintoken= "534|ehyJudmoAsTjmkbTLBcIjzUOCFIui40OSBL01JJJ";
        APIreferenceclass apiref= new APIreferenceclass(vendorclientorcampaign, logintoken, this);

        //TODO now add response to ui

        //TODO remove
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
*/

    public void onPlusClick(View view) {

        Log.d("tag20", "here");

            Intent intent = new Intent(ClientDashBoardActivity.this, AddCampaignDetails.class);
            intent.putExtra("logintoken", logintoken);
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
                if(jsonResponse.getBoolean("success")) {
                    JSONArray dataArray = jsonResponse.getJSONArray("data");
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

                                            //TODO
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
                       // }
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

                //TODO add correct login token here
                //logintoken="Bearer 322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";

                // Get site id or site no from the JSONObject
                String id = jsonObject.getString("id"); // Or get an id if you have that
                Log.d("tag51", jsonObject.getString("id"));
                Log.d("tag60", jsonObject.toString());

                // String siteId = jsonObject.getString("siteId"); // If you have a site id.
                int vendorclientorcampaign= 1;


                    // Start new activity and pass the retrieved data
                    startActivity(new Intent(this, ViewCampaignSites.class)
                            .putExtra("campaignType", "old")
                            .putExtra("id", id)
                            .putExtra("vendorclientorcampaign", vendorclientorcampaign)
                            .putExtra("logintoken", logintoken)//TODO
                            .putExtra("apiresponse", jsonObject.toString()));
                // .putExtra("siteId", siteId)); // If you are passing site id
            } catch (JSONException e) {
                Log.d("tag123", e.toString());
                // Handle exception (e.g. show a Toast to the user indicating an error)
            }
        }

    }
