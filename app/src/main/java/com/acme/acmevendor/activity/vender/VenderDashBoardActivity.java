package com.acme.acmevendor.activity.vender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.acme.acmevendor.R;
import com.acme.acmevendor.activity.dashboard.ViewSiteDetailActivity;
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

    JSONArray jsonArray;

    //intent contents


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vender_dash_board);
        loginToken= readFromFile(this);

        jsonArray= new JSONArray();
        Log.d("vdbatest", "logintoken "+loginToken);

        campaignList();
    }

    private void campaignList() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        binding.rvCampaignList.setLayoutManager(layoutManager);

        int vendorclientorcampaign= 2;
        String logintoken= loginToken;

        APIreferenceclass apiref= new APIreferenceclass(vendorclientorcampaign, logintoken, this);


      /*  //TODO parse api response and enter into recyclerview, remove this
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObjectairbnb = new JSONObject();
        JSONObject jsonObjecthyundai = new JSONObject();
        JSONObject jsonObjectford = new JSONObject();
        JSONObject jsonObjectpatanjli = new JSONObject();

        try {
            jsonObjectairbnb.put("sitenumber", "001");
            jsonObjecthyundai.put("sitenumber", "002");
            jsonObjectford.put("sitenumber", "003");
            jsonObjectpatanjli.put("sitenumber", "004");

            jsonObjectairbnb.put("unitnumber", "#887001");
            jsonObjecthyundai.put("unitnumber", "#878002");
            jsonObjectford.put("unitnumber", "#765003");
            jsonObjectpatanjli.put("unitnumber", "#432004");

            jsonArray.put(jsonObjectairbnb);
            jsonArray.put(jsonObjecthyundai);
            jsonArray.put(jsonObjectford);
            jsonArray.put(jsonObjectpatanjli);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray);
        binding.rvCampaignList.setAdapter(adapter);


       */
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
            // Retrieve JSONObject from your jsonArray at position
            JSONObject jsonObject = jsonArray.getJSONObject(position);

            // Get site id or site no from the JSONObject
            String siteNumber = jsonObject.getString("unitnumber"); // Or get an id if you have that
            // String siteId = jsonObject.getString("siteId"); // If you have a site id.

            // Start new activity and pass the retrieved data
            startActivity(new Intent(this, ViewSiteDetailActivity.class)
                    .putExtra("position", position)
                    .putExtra("campaignType", "old")
                    .putExtra("siteNumber", siteNumber));

            Log.d("jkl", siteNumber);
            // .putExtra("siteId", siteId)); // If you are passing site id
        } catch (JSONException e) {
            Log.d("tag123", e.toString());
            // Handle exception (e.g. show a Toast to the user indicating an error)
        }
    }




    //TODO add token to future activity
    @Override
    public void onResponseReceived(String response){
        Log.d("vdbatest","response is "+ response);
        //TODO response is wrong from api end follow up with the dev

        //TODO replace. this is for response for the current page's data.
        //TODO implement response into UI
        String campaignType="";
        int position= 0;

        String[] dataStrings = extractDataStrings(response);


        // Usage example
        for(String dataStr : dataStrings) {
            Log.d("tag2222",dataStr);
        }

        //id array. send to ui
        String[] idArray= extractIds(dataStrings);
        //TODO extract the unit id and pass that too

        implementUi(idArray);

        /*Intent intent= new Intent(VenderDashBoardActivity.this, UpdateSiteDetailActivity.class);
        intent.putExtra("campaigntype", campaignType);
        intent.putExtra("position", position);
        intent.putExtra("logintoken", loginToken);
        startActivity( intent);
    */
    }

    Context ctxt= this;

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



}