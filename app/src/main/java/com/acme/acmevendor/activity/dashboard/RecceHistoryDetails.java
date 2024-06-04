package com.acme.acmevendor.activity.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.acme.acmevendor.R;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.acme.acmevendor.databinding.ActivityRecceHistoryDetailsBinding;

public class RecceHistoryDetails extends AppCompatActivity implements ApiInterface {
    int id;
    String logintoken;
    String projectId;

    ActivityRecceHistoryDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_recce_history_details);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recce_history_details);


        id= 0;
        logintoken= "";
        projectId= "";

        try{

            id= getIntent().getIntExtra("id", 0);
            logintoken= getIntent().getStringExtra("logintoken");
            projectId= getIntent().getStringExtra("projectid");

        }catch(Exception e){
            Log.d("asdsad", e.toString());
        }

        binding.btnDownload.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {


                                                   }
                                               });


        APIreferenceclass api= new APIreferenceclass(logintoken, this, id, projectId);

    }

    void implementUI(String response){



    }

    @Override
    public void onResponseReceived(String response){

        implementUI(response);

    }


}