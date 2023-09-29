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

    private void implementUi(String a[]){

        //"id":25,"image":"images\/qSFXR5qpbxW3lVEArBvczhyqg6OASG2J5OombCAS.png","vendor_id":"32","campaign_id":18,"start_date":"2023-05-03","end_date":"2023-05-05","location":"delhi","longitute":"34","latitude":"34","width":"23","height":"23","total_area":"32","media_type":"fssf","illumination":"fdf","created_at":"24\/07\/2023","updated_at":"24\/07\/2023"

        Log.d("tag40", "1");

        //TODO unit id is not clear yet
        int unitid=1;

        campaignidarray= new String[a.length][1];
        Log.d("tag40", "2");

        String[][] extractedcampaignids= extractcampaignids(a);
        Log.d("tag40", "3");

        //TODO here

        Log.d("tag40", "4");



        // Print the populated array
        for (int i = 0; i < extractedcampaignids.length; i++) {
            for (int j = 0; j < extractedcampaignids[i].length; j++) {
                Log.d("tag33",extractedcampaignids[i][j] + " ");
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

        Log.d("tag31","here");

        try {
            JSONObject jsonObjectairbnb = new JSONObject();
            jsonObjectairbnb.put("sitenumber", "009");
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

        CampaignListAdapter adapter = new CampaignListAdapter(ctxt, jsonArray);
        binding.rvCampaignList.setAdapter(adapter);
            }
        });
    }

    public String[][] extractcampaignids(String[] a){
//extracting data
        //TODO fix

        Log.d("tag41", "1");

        String b="";
        b=a[0]; //TODO change
        Log.d("tag41", "2");

        String[][] extractedcampaignids= new String[a.length][1];
        Log.d("tag41", "3");

        Log.d("tag32", "length of array"+a.length);

        for(int c=0; c<a.length; c++) {
            b= a[c];
            Log.d("tag41", "4");
            for (int i = 0; i < a.length; i++) {

                Log.d("tag41", "5");
                int j = 0;
                String id = "";


                //extracting from one

                outerloop1:
                for (int p = 0; p < a.length; p++) {
                    j = b.lastIndexOf("\"id\":");
                    char h = 'a';
                    Log.d("tag32", Character.toString(b.charAt(j)));

                    Log.d("tag41", "6");
                    //getting id
                    for (int k = j + 5; k < b.length(); k++) {

                        if (b.charAt(k) != ',') {
                            id = id + b.charAt(k);
                            Log.d("tag32", Character.toString(b.charAt(k)));

                        } else {
                            break outerloop1;
                        }
                    }
                }

                Log.d("tag41", "7");

                Log.d("tag31", "id is" + id);

                Log.d("tag30", "here");


                //extracting data
                String campaignid = "";

                Log.d("tag41", "8");


                int k = 0;
                outerloop:
                for (int o = 0; o < a.length; o++) {
                    //b=a[i];
                    k = b.lastIndexOf("\"campaign_id\":");
                    Log.d("tag30", Character.toString(b.charAt(k)));
                    Log.d("tag41", "9");

//TODO here
                    //getting id
                    for (int l = k + 13; l < b.length(); l++) {

                        if (b.charAt(l) != ',') {
                            Log.d("tag41", "10");

                            campaignid = campaignid + b.charAt(l);

                        } else {
                            Log.d("tag41", "11");

                            break outerloop;
                        }
                    }

                    Log.d("tag41", "12");

                    extractedcampaignids[c][0]= campaignid;

                    Log.d("tag41", "13");


                    //Site no.
                    Log.d("tag31", campaignid);
                }
            }
        }

        Log.d("tag41", "end. extractedcampaignids");


        return extractedcampaignids;
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