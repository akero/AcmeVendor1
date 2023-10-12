package com.acme.acmevendor.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.acme.acmevendor.R;
import com.acme.acmevendor.activity.dashboard.SelectLoginType;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, SelectLoginType.class);
                startActivity(mainIntent);
                finish();
            }
        }, 3000);
    }
}
