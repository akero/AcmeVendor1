package com.acme.acmevendor.activity.login;

import android.content.Intent;
import android.os.Bundle;

import com.acme.acmevendor.activity.dashboard.AdminDashboardActivity;
import com.acme.acmevendor.activity.dashboard.SelectLoginType;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.acme.acmevendor.databinding.ActivityOtpBinding;

import com.acme.acmevendor.R;

public class OTP extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO move intent to correct class.
        Intent mainIntent = new Intent(OTP.this, AdminDashboardActivity.class);
        startActivity(mainIntent);


    }
}