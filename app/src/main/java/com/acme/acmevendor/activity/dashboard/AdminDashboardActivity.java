package com.acme.acmevendor.activity.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.TranslateAnimation;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.acme.acmevendor.R;
import com.acme.acmevendor.adapters.CampaignListAdapter;
import com.acme.acmevendor.adapters.ClientListAdapter;
import com.acme.acmevendor.databinding.ActivityMainBinding;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdminDashboardActivity extends AppCompatActivity {
    MainViewModel mainViewModel;
    ActivityMainBinding binding;
    JSONArray jsonArray;
    boolean showMenus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        clientList();
        campaignList();
    }

    private void campaignList() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rvCampaignList.setLayoutManager(layoutManager);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObjectairbnb = new JSONObject();
        // ... similar code for other JSON objects...

        // ... rest of your initialization logic ...

        CampaignListAdapter adapter = new CampaignListAdapter(this, jsonArray);
        binding.rvCampaignList.setAdapter(adapter);
    }

    private void clientList() {
        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 2);
        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 2);

        binding.rvClientList.setLayoutManager(layoutManager1);
        binding.rvVenderList.setLayoutManager(layoutManager2);

        jsonArray = new JSONArray();
        JSONObject jsonObjectairbnb = new JSONObject();
        // ... similar code for other JSON objects...

        // ... rest of your initialization logic ...

        ClientListAdapter adapter1 = new ClientListAdapter(this, jsonArray);
        ClientListAdapter adapter2 = new ClientListAdapter(this, jsonArray);

        binding.rvVenderList.setAdapter(adapter1);
        binding.rvClientList.setAdapter(adapter2);
    }

    public void onPlusClick(View view) {
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
        // ... your logic ...
    }

    @SuppressLint("ResourceAsColor")
    public void btnVenderClick(View view) {
        // ... your logic ...
    }

    @SuppressLint("ResourceAsColor")
    public void btnClientClick(View view) {
        // ... your logic ...
    }

    public void onItemClick(int position) {
        startActivity(new Intent(this, ViewSiteDetailActivity.class)
                .putExtra("position", position)
                .putExtra("campaignType", "old"));
    }
}
