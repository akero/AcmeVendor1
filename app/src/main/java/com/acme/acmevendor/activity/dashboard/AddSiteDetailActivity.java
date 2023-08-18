package com.acme.acmevendor.activity.dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityAddSiteDetailBinding;
import com.acme.acmevendor.utility.NetworkUtils;
import java.util.Calendar;
import java.util.Locale;

public class AddSiteDetailActivity extends AppCompatActivity implements LocationListener {

    private ActivityAddSiteDetailBinding binding;
    private Calendar cal = Calendar.getInstance();
    private int yy, mm, dd;
    private String imageUrl = "";
    private final int REQUEST_IMAGE_CAPTURE = 101;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_site_detail);
    }

    public void btnCloseClick(View view) {
        finish();
    }

    public void btnSaveClick(View view) {
        if (binding.etUnitId.getText().toString().isEmpty()
                || binding.etSiteNo.getText().toString().isEmpty()
                || binding.etStartDate.getText().toString().isEmpty()
                || binding.etLocation.getText().toString().isEmpty()
                || binding.etLatitude.getText().toString().isEmpty()
                || binding.etLongitude.getText().toString().isEmpty()
                || binding.etHeight.getText().toString().isEmpty()
                || binding.etWidth.getText().toString().isEmpty()
                || binding.etTotalArea.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_LONG).show();
        } else {
            showSuccessMessage();
        }
    }

    public void addImage(View view) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet Connection and Try Again", Toast.LENGTH_LONG).show();
        } else {
            if (!checkPermissions()) {
                requestPermissions();
            } else {
                dispatchTakePictureIntent();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            binding.ivCampaignImage.setImageBitmap(imageBitmap);
            getlatlong();
        }
    }

    private void getlatlong() {
        if (!checkPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions();
            }
        } else {
            getLastLocation();
        }
    }

    private void getLastLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        binding.etLatitude.setText(String.valueOf(location.getLatitude()));
        binding.etLongitude.setText(String.valueOf(location.getLongitude()));
    }

    private void showSnackbar(String mainTextStringId, String actionStringId, View.OnClickListener listener) {
        Toast.makeText(this, mainTextStringId, Toast.LENGTH_LONG).show();
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                showSnackbar("Permission was denied", "Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", Build.DISPLAY, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public void addMoreSiteClick(View view) {
    }

    public void showSuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.custom_emailsent, null);
        TextView tvMsg = view.findViewById(R.id.tvMsg);
        TextView tvResubmit = view.findViewById(R.id.tvResubmit);
        tvResubmit.setVisibility(View.INVISIBLE);
        tvMsg.setText("Campaign Added Successfully");
        Button btnClose = view.findViewById(R.id.btnClose);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showCalendar(View view) {
        yy = cal.get(Calendar.YEAR);
        mm = cal.get(Calendar.MONTH);
        dd = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yy = year;
                mm = month;
                dd = dayOfMonth;
                String dateStr = String.format(Locale.getDefault(), "%02d-%02d-%02d", dd, mm + 1, yy);
                binding.etStartDate.setText(dateStr);
            }
        }, yy, mm, dd);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();
    }

    private static final String TAG = "LocationProvider";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
}
