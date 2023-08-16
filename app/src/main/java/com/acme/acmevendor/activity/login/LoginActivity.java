package com.acme.campaignproject.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.acme.campaignproject.R;
import com.acme.campaignproject.activity.dashboard.AdminDashboardActivity;
import com.acme.campaignproject.activity.dashboard.BaseActivity;
import com.acme.campaignproject.activity.dashboard.ClientDashBoardActivity;
import com.acme.campaignproject.activity.vender.VenderDashBoardActivity;
import com.acme.campaignproject.databinding.ActivityLoginBinding;
import com.acme.campaignproject.utility.AppPreferences;
import com.acme.campaignproject.utility.NetworkUtils;
import com.acme.campaignproject.viewmodel.LoginActivityViewModel;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private boolean hidePassword = true;
    private int loginType = 1;
    private LoginActivityViewModel loginActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.etLoginid.setText(getIntent().getStringExtra("Email"));

        loginActivityViewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        loginActivityViewModel.getSuccessresponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideProgressDialog();
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
                Log.d("result", "response " + s);

                AppPreferences.getInstance(LoginActivity.this).saveUserData(s);

                if (loginType == 0) {
                    startActivity(new Intent(LoginActivity.this, ClientDashBoardActivity.class));
                } else if (loginType == 1) {
                    startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                } else {
                    startActivity(new Intent(LoginActivity.this, VenderDashBoardActivity.class));
                }
            }
        });

        loginActivityViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideProgressDialog();
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
                Log.d("result", "response " + s);
            }
        });
    }

    public void btnloginClick(View view) {
        if (binding.etLoginid.getText().toString().isEmpty() || binding.etPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_LONG).show();
        } else if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet Connection and Try Again", Toast.LENGTH_LONG).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    showProgressDialog();
                    loginActivityViewModel.callLogin(binding.etLoginid.getText().toString(), binding.etPassword.getText().toString());
                }
            }).start();
        }
    }

    @SuppressLint("ResourceAsColor")
    public void btnAdminClick(View view) {
        loginType = 1;
        binding.tvAdminLogin.setBackgroundResource(R.drawable.primaryround);
        binding.tvClientLogin.setBackgroundResource(R.color.coloryellow);
        binding.tvVenderLogin.setBackgroundResource(R.color.coloryellow);

        binding.tvAdminLogin.setTextColor(Color.parseColor("#FFFFFF"));
        binding.tvClientLogin.setTextColor(Color.parseColor("#0089BE"));
        binding.tvVenderLogin.setTextColor(Color.parseColor("#0089BE"));
    }

    @SuppressLint("ResourceAsColor")
    public void btnVenderClick(View view) {
        loginType = 2;
        binding.tvVenderLogin.setBackgroundResource(R.drawable.primaryround);
        binding.tvClientLogin.setBackgroundResource(R.color.coloryellow);
        binding.tvAdminLogin.setBackgroundResource(R.color.coloryellow);

        binding.tvAdminLogin.setTextColor(Color.parseColor("#0089BE"));
        binding.tvClientLogin.setTextColor(Color.parseColor("#0089BE"));
        binding.tvVenderLogin.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @SuppressLint("ResourceAsColor")
    public void btnClientClick(View view) {
        loginType = 0;
        binding.tvAdminLogin.setBackgroundResource(R.color.coloryellow);
        binding.tvClientLogin.setBackgroundResource(R.drawable.primaryround);
        binding.tvVenderLogin.setBackgroundResource(R.color.coloryellow);

        binding.tvAdminLogin.setTextColor(Color.parseColor("#0089BE"));
        binding.tvClientLogin.setTextColor(Color.parseColor("#FFFFFF"));
        binding.tvVenderLogin.setTextColor(Color.parseColor("#0089BE"));
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
