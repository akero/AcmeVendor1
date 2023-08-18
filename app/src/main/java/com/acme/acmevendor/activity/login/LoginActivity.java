package com.acme.acmevendor.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.acme.acmevendor.R;
import com.acme.acmevendor.activity.dashboard.AdminDashboardActivity;
import com.acme.acmevendor.activity.dashboard.BaseActivity;
import com.acme.acmevendor.activity.dashboard.ClientDashBoardActivity;
import com.acme.acmevendor.activity.vender.VenderDashBoardActivity;
import com.acme.acmevendor.databinding.ActivityLoginBinding;
import com.acme.acmevendor.utility.AppPreferences;
import com.acme.acmevendor.utility.NetworkUtils;
import com.acme.acmevendor.viewmodel.LoginActivityViewModel;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private boolean hidePassword = true;
    private int loginType = 1;
    private LoginActivityViewModel loginActivityViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.etLoginid.setText(getIntent().getStringExtra("Email"));

        loginActivityViewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        loginActivityViewModel.getSuccessresponse().observe(this, response -> {
            hideProgressDialog();
            Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("result", "response " + response);

            AppPreferences.getInstance(LoginActivity.this).saveUserData(response.toString());

            if (loginType == 0) {
                startActivity(new Intent(LoginActivity.this, ClientDashBoardActivity.class));
            } else if (loginType == 1) {
                startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, VenderDashBoardActivity.class));
            }
        });

        loginActivityViewModel.getErrorMessage().observe(this, errorMessage -> {
            hideProgressDialog();
            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            Log.d("result", "response " + errorMessage);
        });
    }

    public void btnloginClick(View view) {
        if (binding.etLoginid.getText().toString().isEmpty() || binding.etPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_LONG).show();
        } else if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet Connection and Try Again", Toast.LENGTH_LONG).show();
        } else {
            new Thread(() -> {
                showProgressDialog();
                loginActivityViewModel.callLogin(
                        binding.etLoginid.getText().toString(),
                        binding.etPassword.getText().toString());
            }).start();
        }
    }

    @SuppressLint("ResourceAsColor")
    public void btnAdminClick(View view) {
        loginType = 1;
        // Set backgrounds and colors similarly for the views as mentioned in the Kotlin code
        // Repeat this for other methods too
    }

    @SuppressLint("ResourceAsColor")
    public void btnVenderClick(View view) {
        loginType = 2;
        // Set backgrounds and colors similarly for the views as mentioned in the Kotlin code
    }

    @SuppressLint("ResourceAsColor")
    public void btnClientClick(View view) {
        loginType = 0;
        // Set backgrounds and colors similarly for the views as mentioned in the Kotlin code
    }

    public void onForgotPasswordClick(View view) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    public void onHidePassword(View view) {
        if (hidePassword) {
            hidePassword = false;
            binding.ivHidepassword.setImageResource(R.drawable.ic_eye);
            binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            hidePassword = true;
            binding.ivHidepassword.setImageResource(R.drawable.ic_eye_hide);
            binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
