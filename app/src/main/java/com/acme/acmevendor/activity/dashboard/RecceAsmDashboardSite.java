package com.acme.acmevendor.activity.dashboard;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.acme.acmevendor.databinding.ActivityRecceAsmDashboardSiteBinding;

import com.acme.acmevendor.R;

public class RecceAsmDashboardSite extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRecceAsmDashboardSiteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecceAsmDashboardSiteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }


}