package com.acme.acmevendor.activity.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acme.acmevendor.R;
import com.acme.acmevendor.activity.login.LoginActivity;
import com.acme.acmevendor.activity.login.OTP;
import com.acme.acmevendor.activity.login.SplashActivity;
import com.acme.acmevendor.activity.login.WelcomeActivity;
import com.acme.acmevendor.activity.vender.VenderDashBoardActivity;
import com.acme.acmevendor.utility.AppPreferences;
import com.acme.acmevendor.utility.NetworkUtils;
import com.acme.acmevendor.viewmodel.LoginActivityViewModel;

public class SelectLoginType extends BaseActivity {

    Button bt1,bt2,bt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_type);
        Log.d("whichclass", "SelectLoginType");
        bt1= findViewById(R.id.button1); //client 1
        bt2= findViewById(R.id.button2); //campaign 0
        bt3= findViewById(R.id.button3); //vendor 2

        bt1.setOnClickListener(new View.OnClickListener() {//vendor
            @Override
            public void onClick(View v) {
                // Handle the button click here
                int loginType = 0;
                Intent intent= new Intent(SelectLoginType.this, OTP.class);
                intent.putExtra("loginType", loginType);
                startActivity(intent);
                }
        });

        bt2.setOnClickListener(new View.OnClickListener() {//acme
            @Override
            public void onClick(View v) {
                // Handle the button click here
                int loginType = 1;
                Intent intent= new Intent(SelectLoginType.this, OTP.class);
                intent.putExtra("loginType", loginType);
                startActivity(intent);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {//client
            @Override
            public void onClick(View v) {
                // Handle the button click here
                int loginType = 2;
                Intent intent= new Intent(SelectLoginType.this, OTP.class);
                intent.putExtra("loginType", loginType);
                startActivity(intent);
            }
        });
    }
}