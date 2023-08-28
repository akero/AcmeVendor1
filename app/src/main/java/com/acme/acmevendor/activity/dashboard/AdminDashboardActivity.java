package com.acme.acmevendor.activity.dashboard;

import android.annotation.SuppressLint;
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

    int vendorclientorcampaign=0; //campaign is 0, client is 1, vendor is 2

    //TODO- populate this token
    String logintoken="";


//onresponsereceived from api
    public void onResponseReceived(String response){

        Log.d("tag20", "response is "+ response);
        Log.d("tag21","5");
//TODO: handle population



    }

    private void campaignList() {
        vendorclientorcampaign=0;
        logintoken="Bearer 322|7Dor2CuPXz4orJV5GUleBAUcmgYnbswVMLQ5EUNM";
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



    }

    public void onPlusClick(View view) {

        Log.d("tag20", "onplusclick");
        // ... your logic ...
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