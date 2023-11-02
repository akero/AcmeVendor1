package com.acme.acmevendor.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.acme.acmevendor.activity.dashboard.AdminDashboardActivity;
import com.acme.acmevendor.activity.dashboard.ClientDashBoardActivity;
import com.acme.acmevendor.activity.dashboard.SelectLoginType;
import com.acme.acmevendor.activity.vender.VenderDashBoardActivity;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.acme.acmevendor.databinding.ActivityOtpBinding;

import com.acme.acmevendor.R;

public class OTP extends AppCompatActivity implements ApiInterface {

    private AppBarConfiguration appBarConfiguration;
    private ActivityOtpBinding binding;

    //TODO accept otp, make api call

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int loginType= getIntent().getIntExtra("loginType", 1);


        Log.d("tag51", Integer.toString(loginType));

        EditText email= findViewById(R.id.etEmailId);
        Button btn= findViewById(R.id.btnNext);

        Context context= this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO remove this hardcode
                String email1= "rishabh@acmemedia.in";

                String emailInput = email.getText().toString().trim();

                // Check if the input is empty
                if(emailInput.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                }
                // Check if the email format is correct using a regex pattern.
                else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                }
                // If the email input is valid, start the new activity.
                else {
                    //TODO: make api call here, put intent in onresponsereceived. pending till they guy gets email.

                    //TODO call api with email and logintype then start the next class. comment this once otp is implemented

                    if (loginType == 0) {

                        //TODO replace email1 with emailinput
                        Log.d("tag23", "0");
                        APIreferenceclass api= new APIreferenceclass(loginType, context, email1, "");

                    } else if (loginType == 1) {

                        //TODO replace email1 with emailinput
                        Log.d("tag23", "1");

                        APIreferenceclass api= new APIreferenceclass(loginType, context, email1, "");



                        //startActivity(new Intent(OTP.this, AdminDashboardActivity.class));

                    } else {

                        //TODO replace email1 with emailinput
                        Log.d("tag23", "2");
                        APIreferenceclass api= new APIreferenceclass(loginType, context, email1, "");

                        //APIreferenceclass api= new APIreferenceclass(loginType, context, emailInput);
                        //startActivity(new Intent(OTP.this, VenderDashBoardActivity.class));
                    }



                }
            }

        });



    }

    @Override
    public void onResponseReceived(String response){

        Log.d("tag23", response);

        Intent intent = new Intent(OTP.this, ContentOtp.class);

        //TODO change 2nd param with emailInput
        intent.putExtra("email", "rishabh@acmemedia.in");
        //intent.putExtra("loginType", loginType);
        startActivity(intent);
    }
}