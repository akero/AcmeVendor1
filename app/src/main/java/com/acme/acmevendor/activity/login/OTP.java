package com.acme.acmevendor.activity.login;

import android.content.Intent;
import android.os.Bundle;

import com.acme.acmevendor.activity.dashboard.AdminDashboardActivity;
import com.acme.acmevendor.activity.dashboard.ClientDashBoardActivity;
import com.acme.acmevendor.activity.dashboard.SelectLoginType;
import com.acme.acmevendor.activity.vender.VenderDashBoardActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
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

    //TODO accept otp, make api call

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int loginType= getIntent().getIntExtra("loginType", 1);

        if (loginType == 0) {

            //todo implement fn to save/retreive login data and pass ot class. Then add receiver on other class.
            Log.d("tag23", "0");
            startActivity(new Intent(OTP.this, ClientDashBoardActivity.class));

        } else if (loginType == 1) {


            //todo implement fn to save/retreive login data and pass otp class. Then add receiver on other class.
            Log.d("tag23", "1");
            startActivity(new Intent(OTP.this, AdminDashboardActivity.class));

        } else {

            //todo implement fn to save/retreive login data and pass ot class. Then add receiver on other class.
            Log.d("tag23", "2");
            startActivity(new Intent(OTP.this, VenderDashBoardActivity.class));
        }
    }
}