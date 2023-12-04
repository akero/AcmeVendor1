package com.acme.acmevendor.activity.login;

import static android.view.View.VISIBLE;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    String emailInput;

    ProgressBar progressBar;
    Animation rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d("whichclass", "OTP");

        int loginType= 1;

        //animation code
        progressBar= findViewById(R.id.progressBar);
        rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_animation);
        //animation code

        Log.d("tag51", Integer.toString(loginType));

        EditText email= findViewById(R.id.etEmailId);
        Button btn= findViewById(R.id.btnNext);

        Context context= this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailInput = email.getText().toString().trim();

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

                    if (loginType == 0) {//client

                        //TODO replace email1 with emailinput
                        Log.d("tag23", "0");

                        //animation code
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.startAnimation(rotateAnimation);
                        //view.setVisibility(View.VISIBLE);
                        //animation code

                        APIreferenceclass api= new APIreferenceclass(loginType, context, emailInput, "");

                    } else if (loginType == 1) {//admin

                        //TODO replace email1 with emailinput
                        Log.d("tag23", "1");

                        //animation code
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.startAnimation(rotateAnimation);
                        //view.setVisibility(View.VISIBLE);
                        //animation code

                        APIreferenceclass api= new APIreferenceclass(loginType, context, emailInput, "");
                        //startActivity(new Intent(OTP.this, AdminDashboardActivity.class));
                    } else {//vendor
                        //TODO replace email1 with emailinput
                        Log.d("tag23", "2");

                        //animation code
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.startAnimation(rotateAnimation);
                        //view.setVisibility(View.VISIBLE);
                        //animation code

                        APIreferenceclass api= new APIreferenceclass(loginType, context, emailInput, "");
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
        intent.putExtra("email", emailInput);
        //intent.putExtra("loginType", loginType);

        //animation code
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.clearAnimation();
                progressBar.setVisibility(View.GONE);
                //view.setVisibility(View.GONE);
            }
        });
        //animation code

        startActivity(intent);
    }
}