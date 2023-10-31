package com.acme.acmevendor.activity.dashboard;

import android.content.Context;
import android.content.Intent;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ClientDashBoardActivity extends AppCompatActivity implements ApiInterface {

    private ActivityClientDashBoardBinding binding;
    private boolean oldcampaign = true;

    String loginToken="";

    //TODO handle clicks on old and live campaign, make api call, parse data, populate. pass site details api data to viewsitedetailactivity
    //TODO access token save to memory add to api call

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_dash_board);

        loginToken= readFromFile(this);
        campaignList();
    }

    public void onNotificationClick(View v){
        //TODO ask lodu what this does
    }

    String readFile(){
        String token="";
        try {
            FileReader fr = new FileReader("logintoken");
            int i=0;
            String a="";
            while((i=fr.read())!=-1){
                a=a+((char)i);
            }
        }catch(Exception e){
            Log.d("tag26", e.toString());
        }
        return token;
    }

    private void campaignList() {
        int vendorclientorcampaign= 1;
        String logintoken= loginToken;

        //TODO here
        //TODO remove after hardcode is removed
        logintoken= "534|ehyJudmoAsTjmkbTLBcIjzUOCFIui40OSBL01JJJ";
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

    public void onItemClick(int position) {
        startActivity(new Intent(this, ViewSiteDetailActivity.class)
                .putExtra("position", position)
                .putExtra("campaignType", "old"));
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

        Log.d("cldbatest", "filecontent for logintoken "+fileContent);
        return fileContent;
    }

    //TODO add token to future activity
    @Override
    public void onResponseReceived(String response){
        Log.d("cldbatest","response is "+ response);

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
    }
}
