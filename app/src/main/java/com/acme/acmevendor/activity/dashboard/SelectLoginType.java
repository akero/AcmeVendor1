package com.acme.acmevendor.activity.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.acme.acmevendor.R;
import com.acme.acmevendor.activity.login.SplashActivity;
import com.acme.acmevendor.activity.login.WelcomeActivity;

public class SelectLoginType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_type);

        Intent mainIntent = new Intent(SelectLoginType.this, WelcomeActivity.class);
        startActivity(mainIntent);
    }
}