package com.acme.acmevendor.activity.dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityAddClientBinding;
import com.acme.acmevendor.databinding.ActivityAddVenderBinding;
import com.acme.acmevendor.utility.NetworkUtils;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;

import org.json.JSONObject;

public class AddVenderActivity extends AppCompatActivity implements ApiInterface {

    private ActivityAddVenderBinding binding;
    String siteNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_vender);
        logintoken= getIntent().getStringExtra("logintoken");
        Log.d("whichclass", "AddVendorActivity");
        //siteNumber= getIntent().getStringExtra("siteNumber");
    }

    String name="";
    String email="";
    String phone_number="";
    String company_name="";
    String company_address="";
    String gst_no="";
    //TODO add image type post
    //Byte[] logo;
    String logintoken;

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

            APIreferenceclass api= new APIreferenceclass(jsonPayload, this, logintoken);


           /* binding.etFullName.setText("");
            binding.etEmail.setText("");
            binding.etCompanyName.setText("");
            binding.etCompanyAddress.setText("");
            binding.etGst.setText("");
            binding.etPhone.setText("");
            showSuccessMessage();
        */
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
                        binding.etFullName.setText("");
                        binding.etEmail.setText("");
                        binding.etCompanyName.setText("");
                        binding.etCompanyAddress.setText("");
                        binding.etGst.setText("");
                        binding.etPhone.setText("");
                        showSuccessMessage();
                        //Toast.makeText(AddClientActivity.this, "Client successfully created" , Toast.LENGTH_SHORT).show();
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
        tvMsg.setText("Vendor Added Successfully");

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
