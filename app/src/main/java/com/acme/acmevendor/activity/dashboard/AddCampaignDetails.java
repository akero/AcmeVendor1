package com.acme.acmevendor.activity.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityAddCampaignDetailsBinding;
import com.acme.acmevendor.utility.NetworkUtils;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddCampaignDetails extends AppCompatActivity implements ApiInterface {

    private ActivityAddCampaignDetailsBinding binding;
    String siteNumber;
    String selectedItem;
    String selectedItem1;
    private UploadHelper uploadHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_campaign_details);
        logintoken= getIntent().getStringExtra("logintoken");
        selectedItem="";
        selectedItem1="";
        imageStream= null;

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

//spinner code
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

        //end of spinner code
    }

    //TODO ask content for spinner
    //TODO add image type post
    //Byte[] logo;
    String logintoken;

    public void btnSaveClick(View view) {
        if (    binding.etName.getText().toString().isEmpty() ||
                binding.etVendor.getText().toString().isEmpty() ||
                binding.etStartDate.getText().toString().isEmpty() ||
                binding.etEndDate.getText().toString().isEmpty() ||
                selectedItem.equals("")||
                selectedItem1.equals("")){

            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_LONG).show();
        } else if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet Connection and Try Again", Toast.LENGTH_LONG).show();
        } else {

            String name=binding.etName.getText().toString();
            String vendor=binding.etVendor.getText().toString();
            String startdate=binding.etStartDate.getText().toString();
            String enddate=binding.etEndDate.getText().toString();
            String mediatype=selectedItem;
            String illumination=selectedItem1;

            JSONObject jsonPayload= new JSONObject();
            try{
                jsonPayload.put("name", name);
                jsonPayload.put("vendor", vendor);
                jsonPayload.put("startdate", startdate);
                jsonPayload.put("enddate", enddate);
                jsonPayload.put("image", imageStream);

                //TODO fix both
                jsonPayload.put("uid", 1);
                jsonPayload.put("user_id", 1);
                jsonPayload.put("mediatype", mediatype);
                jsonPayload.put("illumination", illumination);

            }catch(Exception e){
                Log.d("tg6", e.toString());
            }
            APIreferenceclass api= new APIreferenceclass(jsonPayload, this, logintoken, 1);
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
                        binding.etName.setText("");
                        binding.etVendor.setText("");
                        binding.etStartDate.setText("");
                        binding.etEndDate.setText("");
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

    ByteArrayOutputStream imageStream;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
        uploadHelper.handleActivityResult(requestCode, resultCode, data);
        imageStream= null;
        imageStream= uploadHelper.getImageStream();}
        catch(Exception e){
            Log.d("tag998", e.toString());
        }
        // Use the image stream as needed
    }

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 101;

    private void requestReadStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open gallery
                if (uploadHelper == null) {
                    uploadHelper = new UploadHelper(this);
                }
                uploadHelper.openGallery();
            } else {
                // Permission denied
                Toast.makeText(this, "Permission to access storage is required to select an image", Toast.LENGTH_LONG).show();
            }
        }
    }



    public void btnCloseClick(View view) {
        finish();
    }

    public void addImage(View view){
       //TODO remove
        //requestReadStoragePermission();
       }
}
