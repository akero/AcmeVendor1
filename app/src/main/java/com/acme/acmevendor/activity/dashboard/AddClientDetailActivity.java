package com.acme.acmevendor.activity.dashboard;

import android.os.Bundle;

import com.acme.acmevendor.utility.NetworkUtils;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.acme.acmevendor.databinding.ActivityAddClientDetailBinding;

import com.acme.acmevendor.R;

import org.json.JSONObject;

public class AddClientDetailActivity extends AppCompatActivity implements ApiInterface {
    private ActivityAddClientDetailBinding binding;
    String response1;
    String logintoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddClientDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("whichclass", "AddClientDetailActivity");
        try {
            logintoken = getIntent().getStringExtra("logintoken");

            //api response from last thread
            response1 = getIntent().getStringExtra("response");
            //siteNumber= getIntent().getStringExtra("siteNumber");



            //Log.d("tg23", response1);

        }catch(Exception e){
            e.printStackTrace();
        }
        implementUI(response1);
    }

    void implementUI(String apiresponse){
        JSONObject jsonobj= null;

        try {
        jsonobj= new JSONObject(apiresponse);
        }catch(Exception e){
            Log.d("tg23", e.toString());
        }

        if(jsonobj!=null){

            //TODO here
            Log.d("tg23", "sadasgdsgds"+apiresponse);

            try {

                //inserting data into UI
                binding.etFullName.setText(jsonobj.getString("name"));
                binding.etEmail.setText(jsonobj.getString("email"));
                binding.etCompanyName.setText(jsonobj.getString("company_name"));
                binding.etCompanyAddress.setText(jsonobj.getString("company_address"));
                binding.etGst.setText(jsonobj.getString("gst_no"));
                binding.etPhone.setText(jsonobj.getString("phone_number"));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void btnSaveClick(View view) {
        if (    binding.etFullName.getText().toString().isEmpty() ||
                binding.etEmail.getText().toString().isEmpty() ||
                binding.etCompanyName.getText().toString().isEmpty() ||
                binding.etCompanyAddress.getText().toString().isEmpty() ||
                binding.etGst.getText().toString().isEmpty() ||
                binding.etPhone.getText().toString().isEmpty()) {

            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_LONG).show();
        } else if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet Connection and Try Again", Toast.LENGTH_LONG).show();
        } else {

            String name=binding.etFullName.getText().toString();
            String email=binding.etEmail.getText().toString();
            String phone_number=binding.etPhone.getText().toString();
            String company_name=binding.etCompanyName.getText().toString();
            String company_address=binding.etCompanyAddress.getText().toString();
            String gst_no= binding.etGst.getText().toString();

            JSONObject jsonPayload= new JSONObject();
            try{
                jsonPayload.put("name", name);
                jsonPayload.put("email", email);
                jsonPayload.put("phone_number", phone_number);
                jsonPayload.put("company_name", company_name);
                jsonPayload.put("company_address", company_address);
                jsonPayload.put("gst_no", gst_no);

            }catch(Exception e){
                Log.d("tg6", e.toString());
            }

            //TODO here- PENDING FROM BACKEND. PUT not working
            APIreferenceclass api= new APIreferenceclass(jsonPayload, this, logintoken, 0, 0);
        }
    }

    @Override
    public void onResponseReceived(String response){

        Log.d("tg6", response);
        try{
            JSONObject jsonobj= new JSONObject(response);
            if(jsonobj.get("success").equals("false")) {
                Toast.makeText(this, "Please check the fields", Toast.LENGTH_SHORT);

            }else{


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
            /*            binding.etFullName.setText("");
                        binding.etEmail.setText("");
                        binding.etCompanyName.setText("");
                        binding.etCompanyAddress.setText("");
                        binding.etGst.setText("");
                        binding.etPhone.setText("");

             */
                        showSuccessMessage();
                    }
                });
            }
        }catch (Exception e){
            Log.d("tg9", e.toString());
        }
    }

    public void showSuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.custom_emailsent, null);

        TextView tvMsg = view.findViewById(R.id.tvMsg);
        TextView tvResubmit = view.findViewById(R.id.tvResubmit);
        tvResubmit.setVisibility(View.GONE);
        tvMsg.setText("Client Edited Successfully");

        Button btnClose = view.findViewById(R.id.btnClose);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        if (dialog != null) {
            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        if (dialog != null) {
            dialog.show();
        }
    }

    public void btnCloseClick(View view) {
        finish();
    }

}
