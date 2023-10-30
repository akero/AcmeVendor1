package com.acme.acmevendor.activity.vender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.acme.acmevendor.R;
import com.acme.acmevendor.activity.dashboard.AdminViewClientDetails;
import com.acme.acmevendor.activity.dashboard.AdminViewVendorDetails;
import com.acme.acmevendor.activity.dashboard.ViewCampaignSites;
import com.acme.acmevendor.activity.dashboard.ViewSiteDetailActivity;
import com.acme.acmevendor.activity.dashboard.ViewVendorSites;
import com.acme.acmevendor.activity.login.LoginActivity;
import com.acme.acmevendor.adapters.CampaignListAdapter;
import com.acme.acmevendor.api.MyUrlRequestCallback;
import com.acme.acmevendor.databinding.ActivityVenderDashBoardBinding;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.chromium.net.CronetEngine;
import org.chromium.net.UploadDataProvider;
import org.chromium.net.UploadDataSink;
import org.chromium.net.UrlRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VenderDashBoardActivity extends AppCompatActivity implements ApiInterface{

    private ActivityVenderDashBoardBinding binding;
    Executor cronetExecutor= Executors.newSingleThreadExecutor();

    //TODO add api call and populate
    //>todo access token save to memory add to api call

    String loginToken="";

    JSONArray jsonArray1;

    //intent contents
    JSONArray jsonArray2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vender_dash_board);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rvCampaignList.setLayoutManager(layoutManager);

        //TODO implement this
        //loginToken= readFromFile(this);

        jsonArray1= new JSONArray();
        jsonArray2= new JSONArray();
        //Log.d("vdbatest", "logintoken "+loginToken);
        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray1);
        binding.rvCampaignList.setAdapter(adapter);
        campaignList();
    }

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
            //jsonArray3= new JSONArray();
            String ids[];
            JSONObject jsonResponse = new JSONObject(response);
            if(jsonResponse.getBoolean("success")) {
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                if(dataArray != null && dataArray.length() > 0) {

                        for (int i = 0; i < dataArray.length(); i++) {

                            JSONObject dataObject = dataArray.getJSONObject(i);
                            if (dataObject != null) {
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


        }catch (Exception e){}


    }

    private void campaignList() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        binding.rvCampaignList.setLayoutManager(layoutManager);

        int vendorclientorcampaign= 2;
        //TODO pass correct token
        String logintoken= "322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";
int a=0;
        APIreferenceclass apiref= new APIreferenceclass(logintoken, this, a);
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

    public void onItemClick(int position) {
        try {
            Log.d("tag51", Integer.toString(position));
            // Retrieve JSONObject from your jsonArray at position
            JSONObject jsonObject = jsonArray1.getJSONObject(position);
            Log.d("tag51", jsonArray1.getJSONObject(position).toString());


            //TODO add correct login token here
            String logintoken="Bearer 322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";

            // Get site id or site no from the JSONObject
            String id = jsonObject.getString("id"); // Or get an id if you have that
            Log.d("tag51", jsonObject.getString("id"));
            Log.d("tag60", jsonObject.toString());

            // String siteId = jsonObject.getString("siteId"); // If you have a site id.
            int vendorclientorcampaign= 2;



                // Start new activity and pass the retrieved data
                startActivity(new Intent(this, ViewVendorSites.class)
                        .putExtra("campaignType", "old")
                        .putExtra("id", id)
                        .putExtra("logintoken", logintoken)
                        .putExtra("vendorclientorcampaign", vendorclientorcampaign)
                        .putExtra("apiresponse", jsonObject.toString()));




            // .putExtra("siteId", siteId)); // If you are passing site id
        } catch (JSONException e) {
            Log.d("tag123", e.toString());
            // Handle exception (e.g. show a Toast to the user indicating an error)
        }
    }





    Context ctxt= this;
/*
    private void implementUi(String ids[]) {

        Log.d("tag40", "1");

        //TODO unit id is not clear yet
        int unitid = 1;
        //ids[] output is 22,23,24,25. In strings.
        //TODO implement ids and unit id to ui

        jsonArray= new JSONArray();


        //TODO add site number, change pics
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Your UI update code goes here

                GridLayoutManager layoutManager = new GridLayoutManager(ctxt, 1);
                binding.rvCampaignList.setLayoutManager(layoutManager);

                Log.d("tag31", "here");

                // Loop through the extracted campaign ids and create JSONObjects for each id
                for (int i = 0; i < ids.length; i++) {
                    try {
                        // Create a new JSONObject for each campaign id
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("sitenumber",  (i + 1));
                        jsonObject.put("unitnumber", ids[i]);  // Assuming the campaign id is the unit number
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        Log.d("tag21", e.toString());
                    }
                }
//TODO here. ui is updating. Implement properly for all values and for vendor and client
                //TODO trace back the part where you are querying for api data and implement from there

                CampaignListAdapter adapter = new CampaignListAdapter(ctxt, jsonArray);
                binding.rvCampaignList.setAdapter(adapter);
            }
        });
    }

    private String[] extractIds(String[] dataStrings) {
        // Create an array to store extracted ids.
        String[] ids = new String[dataStrings.length];

        // Loop through each string in the input array.
        for (int i = 0; i < dataStrings.length; i++) {
            try {
                // Parse the string into a JSONObject.
                JSONObject jsonObject = new JSONObject(dataStrings[i]);

                // Extract the "id" field and store it in the ids array.
                ids[i] = String.valueOf(jsonObject.getInt("id"));
            } catch (JSONException e) {
                // Handle JSON parsing error. Here setting the id to a default error value ("error").
                ids[i] = "error";
                e.printStackTrace();
            }
        }

        // Return the extracted ids.
        return ids;
    }

    //read token
    String readFromFile(Context context) {
        String fileName = "logintoken";
        StringBuilder content = new StringBuilder();

        try (FileInputStream fis = context.openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            Log.d("tag26", "File not found: " + e.toString());
            // Handle file not found, perhaps return a default value or notify the user
        } catch (IOException e) {
            Log.d("tag26", "Read file error: " + e.toString());
            // Handle other I/O error
        }

        String fileContent = content.toString();
        if (fileContent.isEmpty()) {
            Log.d("tag26", "File is empty");
            // Handle empty file, perhaps return a default value or notify the user
        }

        Log.d("vdbatest", "filecontent for logintoken "+fileContent);
        return fileContent;
    }


*/
}