package com.acme.acmevendor.activity.dashboard;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.acme.acmevendor.databinding.ActivityRecceDashboardBinding;

import com.acme.acmevendor.R;

public class RecceDashboardActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRecceDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecceDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_recce_dashboard);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}