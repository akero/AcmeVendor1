package com.acme.acmevendor.activity.dashboard;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.acme.acmevendor.R;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;

import org.json.JSONObject;

public class RecceHistory extends AppCompatActivity implements ApiInterface {

    int id;
    String logintoken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recce_history);

        id= 0;
        logintoken= "";

        try{

            id= getIntent().getIntExtra("id", 0);
            logintoken= getIntent().getStringExtra("logintoken");

        }catch(Exception e){
            Log.d("asdsad", e.toString());
        }

        APIreferenceclass api= new APIreferenceclass(logintoken, this, id, 1);

    }

    void implementUI(String response){

        try{

            JSONObject jsonobj= new JSONObject(response);


        }catch (Exception e){
            Log.d("asddsabgfr", e.toString());
        }

    }

    @Override
    public void onResponseReceived(String response) {

        implementUI(response);


    }
}