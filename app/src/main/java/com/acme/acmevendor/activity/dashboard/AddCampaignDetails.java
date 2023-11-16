package com.acme.acmevendor.activity.dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityAddCampaignDetailsBinding;
import com.acme.acmevendor.databinding.ActivityAddClientBinding;
import com.acme.acmevendor.utility.NetworkUtils;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;

import org.json.JSONObject;

import java.util.Calendar;

public class AddCampaignDetails extends AppCompatActivity implements ApiInterface {

    private ActivityAddCampaignDetailsBinding binding;
    String siteNumber;
    String selectedItem;
    String selectedItem1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_campaign_details);
        logintoken= getIntent().getStringExtra("logintoken");
        selectedItem="";
        selectedItem1="";

        binding.etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        AddCampaignDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                binding.etStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        binding.etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        AddCampaignDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                binding.etEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });


        String[] items = new String[]{"Item 1", "Item 2", "Item 3"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        binding.spinnermediatype.setAdapter(adapter);

        // Inside your onCreate method
        binding.spinnermediatype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(AddCampaignDetails.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        String[] items1 = new String[]{"Item 1", "Item 2", "Item 3"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        binding.spinnerillumination.setAdapter(adapter1);

        // Inside your onCreate method
        binding.spinnerillumination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem1 = parent.getItemAtPosition(position).toString();
                //Toast.makeText(AddCampaignDetails.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
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
    //TODO ask content for spinner
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
                binding.etWidth.getText().toString().isEmpty() ||
                binding.etStartDate.getText().toString().isEmpty()||
                binding.etEndDate.getText().toString().isEmpty()||
                selectedItem.equals("")||
                selectedItem1.equals("")){

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
            String mediatype=selectedItem;
            String illumination=selectedItem1;



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

    public void addImage(){



    }

}
