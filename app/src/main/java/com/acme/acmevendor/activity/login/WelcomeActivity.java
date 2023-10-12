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
import com.acme.acmevendor.activity.dashboard.AddVenderActivity;
import com.acme.acmevendor.activity.dashboard.BaseActivity;
import com.acme.acmevendor.activity.vender.UpdateSiteDetailActivity;
import com.acme.acmevendor.databinding.ActivityWelcomeBinding;
import com.acme.acmevendor.utility.NetworkUtils;
import com.acme.acmevendor.viewmodel.WelcomeActivityViewModel;

public class WelcomeActivity extends BaseActivity {

    private ActivityWelcomeBinding binding;
    private WelcomeActivityViewModel welcomeActivityViewModel;

    //TODO add flow to enter otp etc and finish login after saving login token

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("tag2","start of welcomeactivity");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);

        welcomeActivityViewModel = new ViewModelProvider(this).get(WelcomeActivityViewModel.class);
        welcomeActivityViewModel.successresponse.observe(this, response -> {
            hideProgressDialog();
            Toast.makeText(WelcomeActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("tag2", "response " + response);


            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            intent.putExtra("Email", binding.etEmailId.getText().toString());
            startActivity(intent);

        });

        welcomeActivityViewModel.errorMessage.observe(this, errorMessage -> {
            hideProgressDialog();
            Toast.makeText(WelcomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();

    //TODO: REMOVE
            //Intent intent= new Intent(WelcomeActivity.this, LoginActivity.class);
            //Intent intent = new Intent(WelcomeActivity.this, AddVenderActivity.class);
            //Intent intent = new Intent(WelcomeActivity.this, UpdateSiteDetailActivity.class);
            // Intent intent = new Intent(WelcomeActivity.this, VenderDashBoardActivity.class);
            // Intent intent = new Intent(WelcomeActivity.this, WelcomeActivity.class);
            // Intent intent = new Intent(WelcomeActivity.this, ChangePasswordActivity.class);
            // Intent intent = new Intent(WelcomeActivity.this, ViewSiteDetailActivity.class);
            // Intent intent = new Intent(WelcomeActivity.this, CampaignListActivity.class);
            // Intent intent = new Intent(WelcomeActivity.this, AddSiteDetailActivity.class);
            // Intent intent = new Intent(WelcomeActivity.this, AddClientActivity.class);
            // Intent intent = new Intent(WelcomeActivity.this, ClientDashBoardActivity.class);
// Intent intent = new Intent(WelcomeActivity.this, BaseActivity.class);
// Intent intent = new Intent(WelcomeActivity.this, ForgotPasswordActivity.class);
// Intent intent = new Intent(WelcomeActivity.this, ClientListActivity.class);
// Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
// Intent intent = new Intent(WelcomeActivity.this, AdminDashboardActivity.class);
// Intent intent = new Intent(WelcomeActivity.this, SplashActivity.class);



            //intent.putExtra("Email","rish1994@gmail.com");
            //startActivity(intent);
    //TODO


            Log.d("tag2", "responseerror " + errorMessage);
        });
    }

    public void btnSubmitClick(View view) {
        if (binding.etEmailId.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fill the Email", Toast.LENGTH_LONG).show();
        } else if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet Connection and Try Again", Toast.LENGTH_LONG).show();
        } else {

            //TODO change
            Intent intent = new Intent(WelcomeActivity.this, OTP.class);
            intent.putExtra("Email", binding.etEmailId.getText().toString());
            startActivity(intent);
            //Intent intent= new Intent(WelcomeActivity.this, LoginActivity.class);
            //startActivity(intent);

            //TODO uncomment this
            //showProgressDialog();
            //welcomeActivityViewModel.callOtp("+91", binding.etEmailId.getText().toString(), this);
        }
    }
}
