package com.acme.acmevendor.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.acme.acmevendor.R;
import com.acme.acmevendor.activity.dashboard.BaseActivity;
import com.acme.acmevendor.databinding.ActivityWelcomeBinding;
import com.acme.acmevendor.utility.NetworkUtils;
import com.acme.acmevendor.viewmodel.WelcomeActivityViewModel;

public class WelcomeActivity extends BaseActivity {

    private ActivityWelcomeBinding binding;
    private WelcomeActivityViewModel welcomeActivityViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);

        welcomeActivityViewModel = new ViewModelProvider(this).get(WelcomeActivityViewModel.class);
        welcomeActivityViewModel.successresponse.observe(this, response -> {
            hideProgressDialog();
            Toast.makeText(WelcomeActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("result", "response " + response);

            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            intent.putExtra("Email", binding.etEmailId.getText().toString());
            startActivity(intent);
        });

        welcomeActivityViewModel.errorMessage.observe(this, errorMessage -> {
            hideProgressDialog();
            Toast.makeText(WelcomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            Log.d("result", "response " + errorMessage);
        });
    }

    public void btnSubmitClick(View view) {
        if (binding.etEmailId.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fill the Email", Toast.LENGTH_LONG).show();
        } else if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet Connection and Try Again", Toast.LENGTH_LONG).show();
        } else {
            showProgressDialog();
            welcomeActivityViewModel.callOtp("+91", binding.etEmailId.getText().toString());
        }
    }
}