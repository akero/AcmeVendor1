package com.acme.campaignproject.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.acme.campaignproject.R;
import com.acme.campaignproject.activity.dashboard.BaseActivity;
import com.acme.campaignproject.databinding.ActivityWelcomeBinding;
import com.acme.campaignproject.models.SendOtpResponseModel;
import com.acme.campaignproject.viewmodel.WelcomeActivityViewModel;

public class WelcomeActivity extends BaseActivity {

    private ActivityWelcomeBinding binding;
    private WelcomeActivityViewModel welcomeActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);

        welcomeActivityViewModel = new ViewModelProvider(this).get(WelcomeActivityViewModel.class);
        welcomeActivityViewModel.getSuccessresponse().observe(this, new Observer<SendOtpResponseModel>() {
            @Override
            public void onChanged(SendOtpResponseModel sendOtpResponseModel) {
                hideProgressDialog();
                Toast.makeText(WelcomeActivity.this, sendOtpResponseModel.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("result", "response " + sendOtpResponseModel);

                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                intent.putExtra("Email", binding.etEmailId.getText().toString());
                startActivity(intent);
            }
        });

        welcomeActivityViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideProgressDialog();
                Toast.makeText(WelcomeActivity.this, s, Toast.LENGTH_LONG).show();
                Log.d("result", "response " + s);
            }
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
