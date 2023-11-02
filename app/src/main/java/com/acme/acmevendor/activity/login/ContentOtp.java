package com.acme.acmevendor.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.acme.acmevendor.R;

public class ContentOtp extends AppCompatActivity {

    private EditText otp1, otp2, otp3, otp4, otp5;

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
        Button btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndContinue();
            }
        });
    }

    private void validateAndContinue() {
        String otp = otp1.getText().toString() + otp2.getText().toString() +
                otp3.getText().toString() + otp4.getText().toString() +
                otp5.getText().toString();

        // Add OTP validation logic as per your requirement.
        // If OTP is valid, proceed to the next Activity
        if (isValidOtp(otp)) {
            Intent intent = new Intent(ContentOtp.this, ContentOtp.class);
            // Add any other data to send to the next activity
            // intent.putExtra("email", emailInput);
            // intent.putExtra("loginType", loginType);
            startActivity(intent);
        } else {
            // Handle invalid OTP entry
            // This could be showing an error message, shaking animation, etc.
        }
    }

    private boolean isValidOtp(String otp) {
        // Replace with your OTP validation logic
        return otp.length() == 5;
    }
}
