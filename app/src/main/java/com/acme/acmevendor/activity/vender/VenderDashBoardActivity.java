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
import com.acme.acmevendor.viewmodel.ApiInterface;

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

    //intent contents


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vender_dash_board);
        loginToken= readFromFile(this);
        campaignList();
    }

    private void campaignList() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        binding.rvCampaignList.setLayoutManager(layoutManager);





//TODO parse api response and enter into recyclerview
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
    }



    public void onItemClick(int position) {
        //adding api here


        CronetEngine.Builder builder = new CronetEngine.Builder(this);
        CronetEngine cronetEngine = builder.build();

        //TODO
        String token= loginToken;
        // Create a JSON payload with the email and password

        //TODO
        String jsonPayload = "{\"token\":\"" + token + "\"}";

        // Convert the JSON payload to bytes for uploading
        final byte[] postData = jsonPayload.getBytes(StandardCharsets.UTF_8);

        // Create an upload data provider to send the POST data
        UploadDataProvider uploadDataProvider = new UploadDataProvider() {
            @Override
            public long getLength() throws IOException {
                return postData.length;
            }

            @Override
            public void read(UploadDataSink uploadDataSink, ByteBuffer byteBuffer) throws IOException {
                byteBuffer.put(postData);
                uploadDataSink.onReadSucceeded(false);
            }

            @Override
            public void rewind(UploadDataSink uploadDataSink) throws IOException {
                uploadDataSink.onRewindSucceeded();
            }

            @Override
            public void close() throws IOException {
                // No-op
            }
        };

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                        "https://acme.warburttons.com/api/get_vendor_campaigns", new MyUrlRequestCallback( (ApiInterface) this), cronetExecutor)
                .setHttpMethod("GET")  // Set the method to GET
                .addHeader("Content-Type", "application/json")  // Indicate we're sending JSON data
                .setUploadDataProvider(uploadDataProvider, cronetExecutor);  // Attach the payload

        UrlRequest request = requestBuilder.build();
        request.start();
    }




    //TODO add token to future activity
    @Override
    public void onResponseReceived(String response){
        Log.d("tag11", "response is "+ response);

    //TODO
        String campaignType="";
        int position= 0;

        Intent intent= new Intent(VenderDashBoardActivity.this, UpdateSiteDetailActivity.class);
        intent.putExtra("campaigntype", campaignType);
        intent.putExtra("position", position);
        intent.putExtra("logintoken", loginToken);
        startActivity( intent);


        //        Intent intent= new Intent(VenderDashBoardActivity.this, VenderDashBoardActivity.class);
  //      startActivity(intent);
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

        return fileContent;
    }


}