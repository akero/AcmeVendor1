package com.acme.acmevendor.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.acme.acmevendor.R;
import com.acme.acmevendor.activity.dashboard.AdminDashboardActivity;
import com.acme.acmevendor.activity.dashboard.ClientDashBoardActivity;
import com.acme.acmevendor.activity.dashboard.FileHelper;
import com.acme.acmevendor.activity.vender.VenderDashBoardActivity;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class ContentOtp extends AppCompatActivity implements ApiInterface {

    private EditText otp1, otp2, otp3, otp4, otp5;
    String email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_otp); // make sure to use your actual layout name

        // Initialize UI Elements
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        //otp5 = findViewById(R.id.otp5);
        //Button btnNext = findViewById(R.id.btnNext);

        // Setup auto-advance
        setupAutoAdvance(otp1, otp2);
        setupAutoAdvance(otp2, otp3);
        setupAutoAdvance(otp3, otp4);
        //setupAutoAdvance(otp4, otp5);
        setupAutoAdvance(otp4, null);

        email= getIntent().getStringExtra("email");

       // btnNext.setOnClickListener(new View.OnClickListener() {
           // @Override
         //   public void onClick(View v) {
              //  validateAndContinue();
          //  }
       // });
    }

    private void setupAutoAdvance(final EditText currentEditText, final EditText nextEditText) {
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this functionality
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Check if a single digit is entered
                if (s.length() == 1 && nextEditText != null) {
                    nextEditText.requestFocus();
                }else if (s.length() == 1 && nextEditText == null) {
                    // When the last EditText is filled, validate and continue
                    validateAndContinue();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for this functionality
            }
        });
    }

    private void validateAndContinue() {
        String otp = otp1.getText().toString() + otp2.getText().toString() +
                otp3.getText().toString() + otp4.getText().toString();

        // Add OTP validation logic as per your requirement.
        // If OTP is valid, proceed to the next Activity
        if (isValidOtp(otp)) {

            Context context= this;
            APIreferenceclass api= new APIreferenceclass(otp, context, email, 1);
            Log.d("tg4","otp works");

            //Intent intent = new Intent(ContentOtp.this, ContentOtp.class);
            // Add any other data to send to the next activity
            // intent.putExtra("email", emailInput);
            // intent.putExtra("loginType", loginType);
            //startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            //TODO
            // Handle invalid OTP entry
            // This could be showing an error message, shaking animation, etc.
        }
    }

    String name;
    String token;
    String loginType;

    @Override
    public void onResponseReceived(String response){
        Log.d("tg4","otp response works" +response);

        try {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
        if(jsonObject.getBoolean("success")== true){
            name= jsonObject1.getString("name");
            token= jsonObject1.getString("token");
            loginType= jsonObject1.getString("type");
            Log.d("tg4", loginType);

            boolean success = FileHelper.writeLoginToken(this, token);


            if(loginType.equals("admin")){

                Log.d("tg5","1");

                Intent intent= new Intent(ContentOtp.this, AdminDashboardActivity.class);
                intent.putExtra("logintoken", token);
                startActivity(intent);

            }else if(loginType.equals("vendor")){

                Intent intent= new Intent(ContentOtp.this, VenderDashBoardActivity.class);
                intent.putExtra("logintoken", token);

                startActivity(intent);
            }else if(loginType.equals("client")){

                Intent intent= new Intent(ContentOtp.this, ClientDashBoardActivity.class);
                intent.putExtra("logintoken", token);

                startActivity(intent);
            }
        }
    }catch(Exception e){
        Log.d("tg4", e.toString());
    }
}

    public static void writeLoginToken(Context context, String logintoken) {
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput("logintoken", Context.MODE_PRIVATE);
            fos.write(logintoken.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isValidOtp(String otp) {
        //TODO Replace with your OTP validation logic
        return otp.length() == 4;
    }
}
