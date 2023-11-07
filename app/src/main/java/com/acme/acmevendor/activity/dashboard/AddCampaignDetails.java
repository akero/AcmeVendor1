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
import com.acme.acmevendor.databinding.ActivityAddCampaignDetailsBinding;
import com.acme.acmevendor.databinding.ActivityAddClientBinding;
import com.acme.acmevendor.utility.NetworkUtils;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;

import org.json.JSONObject;

public class AddCampaignDetails extends AppCompatActivity implements ApiInterface {

    private ActivityAddCampaignDetailsBinding binding;
    String siteNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_campaign_details);
        logintoken= getIntent().getStringExtra("logintoken");
        //siteNumber= getIntent().getStringExtra("siteNumber");
    }

    String unitid="";
    String siteno="";
    String startdate="";
    String enddate="";
    String location="";
    String latitude="";
    String longitude="";
    String dimensionheight="";
    String dimensionwidth="";
    String totalarea="";
    String mediatype="";
    String illumination="";
    //TODO add image type post
    //Byte[] logo;
    String logintoken;

    public void btnSaveClick(View view) {
        if (    binding.etUnitId.getText().toString().isEmpty() ||
                binding.etSiteNo.getText().toString().isEmpty() ||
                binding.etStartDate.getText().toString().isEmpty() ||
                binding.etEndDate.getText().toString().isEmpty() ||
                binding.etLocation.getText().toString().isEmpty() ||
                binding.etLatitude.getText().toString().isEmpty() ||
                binding.etLongitude.getText().toString().isEmpty() ||
                binding.etHeight.getText().toString().isEmpty() ||
                binding.etTotalArea.getText().toString().isEmpty() ||
                binding.etWidth.getText().toString().isEmpty()) {

            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_LONG).show();
        } else if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet Connection and Try Again", Toast.LENGTH_LONG).show();
        } else {

            String unitid=binding.etUnitId.getText().toString();
            String siteno=binding.etSiteNo.getText().toString();
            String startdate=binding.etStartDate.getText().toString();
            String enddate=binding.etEndDate.getText().toString();
            String location=binding.etLocation.getText().toString();
            String latitude=binding.etLatitude.getText().toString();
            String longitude=binding.etLongitude.getText().toString();
            String dimensionheight=binding.etHeight.getText().toString();
            String dimensionwidth=binding.etWidth.getText().toString();
            String totalarea=binding.etTotalArea.getText().toString();
            String mediatype=binding.etUnitId.getText().toString();
            String illumination=binding.etUnitId.getText().toString();



            JSONObject jsonPayload= new JSONObject();
            try{
                jsonPayload.put("unitid", unitid);
                jsonPayload.put("siteno", siteno);
                jsonPayload.put("startdate", startdate);
                jsonPayload.put("enddate", enddate);
                jsonPayload.put("location", location);
                jsonPayload.put("latitude", latitude);
                jsonPayload.put("longitude", longitude);
                jsonPayload.put("dimensionheight", dimensionheight);
                jsonPayload.put("dimensionwidth", dimensionwidth);
                jsonPayload.put("totalarea", totalarea);
                jsonPayload.put("mediatype", mediatype);
                jsonPayload.put("illumination", illumination);

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
                        binding.etUnitId.setText("");
                        binding.etSiteNo.setText("");
                        binding.etStartDate.setText("");
                        binding.etEndDate.setText("");
                        binding.etLocation.setText("");
                        binding.etLatitude.setText("");
                        binding.etLongitude.setText("");
                        binding.etHeight.setText("");
                        binding.etWidth.setText("");
                        binding.etTotalArea.setText("");
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
        tvMsg.setText("Campaign Added Successfully");

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
