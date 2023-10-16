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
                    //TODO: make api call here, put intent in onresponsereceived

                    APIreferenceclass api= new APIreferenceclass(loginType, context, emailInput);


                }
            }

        });


        //TODO call api with email and logintype then start the next class

      /*  if (loginType == 0) {

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
        }*/
    }

    @Override
    public void onResponseReceived(String response){
        Intent intent = new Intent(OTP.this, ContentOtp.class);
        //intent.putExtra("email", emailInput);
        //intent.putExtra("loginType", loginType);
        startActivity(intent);
    }
}