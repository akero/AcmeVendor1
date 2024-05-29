package com.acme.acmevendor.activity.dashboard;

import android.os.Bundle;

import com.acme.acmevendor.utility.RoundRectCornerImageView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        //initializing buttons
        Button storePhotoButton, storePhotoButton1, storePhotoButton2, storePhotoButton3, ownerSignButton, uploadSiteDetailsButton;
        storePhotoButton= findViewById(R.id.btnUpdatePhoto);
        storePhotoButton1= findViewById(R.id.btnUpdatePhoto1);
        storePhotoButton2= findViewById(R.id.btnUpdatePhoto2);
        storePhotoButton3= findViewById(R.id.btnUpdatePhoto3);
        ownerSignButton= findViewById(R.id.btnUpdatePhoto4);


        storePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        storePhotoButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        storePhotoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        storePhotoButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ownerSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//Signage area
        EditText height, width, remarks;
        RoundRectCornerImageView imageButton= findViewById(R.id.ivCampaignImage);;
        Button uploadSignageDetails= findViewById(R.id.btnUpdatePhoto5);

        uploadSignageDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    //auto opens when addimage is clicked
    public void addImage(){

    }


}