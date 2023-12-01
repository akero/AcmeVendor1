package com.acme.acmevendor.activity.dashboard;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityViewSiteDetailBinding;
import com.acme.acmevendor.utility.RoundRectCornerImageView;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.acme.acmevendor.viewmodel.SiteDetail;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;

public class ViewSiteDetailActivity extends AppCompatActivity implements ApiInterface {

    private String campaignType = "";
    private int position = 0;
    String siteNumber= "";
    String logintoken="";
    String campaignId="";
    private ActivityViewSiteDetailBinding binding;

    //TODO populate all fields. pass api call data from prev activity
    //have to pass logintoken and siteid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_site_detail);
        
        Log.d("tag41", "1");

        if (getIntent().getExtras() != null) {
            Log.d("tag41", "2");
            campaignId = getIntent().getExtras().getString("campaignId", "");

            campaignType = getIntent().getExtras().getString("campaignType", "");
            siteNumber= getIntent().getExtras().getString("siteNumber", "");
            logintoken= getIntent().getExtras().getString("logintoken","");
            Log.d("tg2", siteNumber);
        }

        Log.d("tag41", "4");
        apicall(logintoken, siteNumber);
        Log.d("tag41", "5");
    }
    SiteDetail siteDetail;
    JSONObject jsonobj;

    private void implementUI(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            if(jsonResponse.getBoolean("success")) {
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                if(dataArray != null && dataArray.length() > 0) {
                    JSONObject dataObject = dataArray.getJSONObject(0);
                    jsonobj= dataObject;
                    if(dataObject != null) {
                        siteDetail = new SiteDetail();
                        siteDetail.setId(dataObject.optInt("id"));
                        siteDetail.setVendorId(dataObject.optString("vendor_id"));
                        siteDetail.setLocation(dataObject.optString("location"));
                        siteDetail.setCreatedAt(dataObject.optString("created_at"));
                        siteDetail.setEndDate(dataObject.optString("end_date"));
                        siteDetail.setLatitude(dataObject.optString("latitude"));
                        siteDetail.setLongitude(dataObject.optString("longitute")); // Consider renaming "longitute" to "longitude" in your JSON or code for consistency
                        siteDetail.setMediaType(dataObject.optString("media_type"));
                        siteDetail.setIllumination(dataObject.optString("illumination"));
                        siteDetail.setStartDate(dataObject.optString("start_date"));
                        siteDetail.setName(dataObject.optString("name"));
                        siteDetail.setSiteNo(dataObject.optString("site_no"));
                        siteDetail.setWidth(dataObject.optString("width"));
                        siteDetail.setHeight(dataObject.optString("height"));
                        siteDetail.setTotalArea(dataObject.optString("total_area"));
                        siteDetail.setUpdatedAt(dataObject.optString("updated_at"));
                        try {
                            String imageUrl = dataObject.optString("image");
                            imageUrl= "https://acme.warburttons.com/"+ imageUrl;
                            Log.d("tag41", "imageurl is "+ imageUrl);
                            Log.d("tg2", "image code not executing 1");

                            if(imageUrl != "null" && !imageUrl.isEmpty()) {
                                URL url = new URL(imageUrl);
                                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                Log.d("tg2", "image code executing");
                                siteDetail.setImage(bitmap);
                            }
                        } catch (Exception e) {
                            Log.d("tg2", "image code not executing 2");
                            Log.d("tag41", "error in implementui" +e.toString());
                            Log.e("tag41", "sdfdg", e);
                            // Handle error
                        }

                        // Update UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                TextView tvSiteId = findViewById(R.id.etSiteNo);
                                //TODO after person implements site name then change this
                                tvSiteId.setText(String.valueOf(siteDetail.getSiteNo()));

                                TextView tvLocation = findViewById(R.id.tvLocation);
                                tvLocation.setText(siteDetail.getLocation());

                                TextView tvSiteName = findViewById(R.id.tvAddSiteDetail);
                                tvSiteName.setText(siteDetail.getName());

                                TextView tvLastInspection = findViewById(R.id.tvStartDate);
                                tvLastInspection.setText(siteDetail.getCreatedAt());

                                TextView tvLatitude = findViewById(R.id.tvLatitude);
                                tvLatitude.setText(siteDetail.getLatitude());

                                TextView tvLongitude = findViewById(R.id.tvLongitude);
                                tvLongitude.setText(siteDetail.getLongitude());

                                TextView tvMediaType = findViewById(R.id.tvMediaType);
                                tvMediaType.setText(siteDetail.getMediaType());

                                TextView tvIllumination = findViewById(R.id.tvIllumination);
                                tvIllumination.setText(siteDetail.getIllumination());

                                TextView tvStartDate = findViewById(R.id.tvStartDate);
                                tvStartDate.setText(siteDetail.getStartDate());

                                // Set the site number
                                TextView tvSiteNo = findViewById(R.id.etSiteNo);
                                tvSiteNo.setText(String.valueOf(siteDetail.getSiteNo())); // assuming getter method exists

                                // Set the width
                                TextView tvWidth = findViewById(R.id.tvWidth);
                                tvWidth.setText(siteDetail.getWidth()); // assuming getter method exists

                                // Set the height
                                TextView tvHeight = findViewById(R.id.tvHeight);
                                tvHeight.setText(siteDetail.getHeight()); // assuming getter method exists

                                // Set the total area
                                TextView tvTotalArea = findViewById(R.id.tvTotalArea);
                                tvTotalArea.setText(siteDetail.getTotalArea()); // assuming getter method exists
                                Log.d("tg2", "image code not executing");

                                RoundRectCornerImageView tvImage = findViewById(R.id.ivCampaignImage);
                                if(siteDetail.getImage()!=null) {
                                    Log.d("tg2", "image code executing");
                                    tvImage.setImageBitmap(siteDetail.getImage());
                                }
                                }
                        });
                    }
                } else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ViewSiteDetailActivity.this, "Error retrieving or parsing data", Toast.LENGTH_SHORT).show();
                            }
                        });

                }
            } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ViewSiteDetailActivity.this, "Error retrieving or parsing data", Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ViewSiteDetailActivity.this, "Error retrieving or parsing data", Toast.LENGTH_SHORT).show();
                    }
                });
        }

        // Assigning values and listeners to Buttons
            Button btnNext = findViewById(R.id.btnNext);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent= new Intent(ViewSiteDetailActivity.this, AddSiteDetailActivity.class);
                    intent.putExtra("loginToken", logintoken);
                    intent.putExtra("campaignId", campaignId);
                    Log.d("tag000", logintoken+ "| "+ campaignId+"| "+  siteNumber+ "| "+ jsonobj.toString());
                    Log.d("tag000", siteNumber);

                    intent.putExtra("siteNumber", siteNumber);
                    intent.putExtra("siteDetail", jsonobj.toString());
                    startActivity(intent);
                }
            });

            Button btnDownload = findViewById(R.id.btnDownload);
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDownloadClick(v);
                    // Handle download button click
                }
            });

            Button btnClose = findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle close button click
                    finish();
                }
            });
        }

    void apicall(String logintoken, String siteNumber){

        Log.d("tag41", "6");
        Context context= this;
        APIreferenceclass api= new APIreferenceclass(logintoken, siteNumber, context);
        Log.d("tag41", "7");
    }

    String response1="";

    @Override
    public void onResponseReceived(String response){

        response1= response;
        implementUI(response);
        Log.d("tag41", response);
    }

    public void btnCloseClick(View view) {
        finish();
    }

    public void btnNextClick(View view) {
        //TODO ask what this does
        // finish();
    }

    public void onNotificationClick(View view){
        //TODO ask dev what this does
    }

    public void onDownloadClick(View view) {
        //handle click
        Log.d("tag46", response1);

        File txtFile;
        String a = response1;
        FileWriter writer = null; // Initialize writer outside try-catch so it can be accessed in the finally block
        try {
            txtFile = new File(getExternalFilesDir(null), "Site Details.txt");
            Log.d("tag46", "File path: " + txtFile.getAbsolutePath());
            writer = new FileWriter(txtFile);
            writer.write(a); // 'a' is your JSON string
            String fullPath = txtFile.getAbsolutePath();
            String shortenedPath = fullPath.replace("/storage/emulated/0/", "Internal Storage > ... > ");

            Toast.makeText(this, "File downloaded to Internal Storage> Android> data> com.acme.acmevendor", Toast.LENGTH_LONG).show();
            writer.flush(); // Good practice to flush before closing, though close() does this too
        } catch (Exception e) {
            Log.d("tag46", e.toString());
        } finally {
            try {
                if (writer != null) {
                    writer.close(); // Ensure the writer is closed, this will also flush the buffer
                }
            } catch (IOException e) {
                Toast.makeText(this, "Download failed check your internet", Toast.LENGTH_LONG).show();
                Log.d("tag46", e.toString());
            }
        }
    }

    void writeToFile(String response, Context context){
        String name="logintoken";
        String content= response;
        FileOutputStream fostream= null;

        try{
            fostream= context.openFileOutput(name,Context.MODE_PRIVATE);
            fostream.write(response.getBytes());
            fostream.close();

        }catch(Exception e){
            Log.d("tag24", "error-" +e.toString());
        }
        finally{
            try{

                if(fostream!=null){
                    fostream.close();
                }
            }catch(Exception e){
                Log.d("tag25","Closing outputstream failed");
            }
        }
    }

    public String formatJSONString(String unformattedJson) {
        try {
            JSONObject jsonObject = new JSONObject(unformattedJson);
            return jsonObject.toString(4); // `4` is the number of spaces to use for indentation
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeToFile(String data, String fileName) {
        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "YourAppName");
        // Create the folder if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Create the file
        File file = new File(directory, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
            Toast.makeText(this, "Data saved at " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving data to file", Toast.LENGTH_SHORT).show();
        }
    }

    public void oldCampaignClick(View view) {
        //binding.tvOldCampaign.setBackgroundResource(R.drawable.primaryround);
        //binding.tvLiveCampaign.setBackgroundResource(R.color.coloryellow);
    }

    public void liveCampaignClick(View view) {
        //binding.tvLiveCampaign.setBackgroundResource(R.drawable.primaryround);
        //binding.tvOldCampaign.setBackgroundResource(R.color.coloryellow);
    }

    //TODO delete. download code

    private static final int PERMISSION_REQUEST_CODE = 1;

 /*   private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        Log.d("tag45","5");
        View v= null;
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
*/
   /* private long downloadReference;
    private BroadcastReceiver onDownloadComplete;

    private void startDownload(String fileURL, String fileName) {
        try {
            if (checkPermission()) {
                Uri downloadUri = Uri.parse(fileURL);
                String destination = Environment.DIRECTORY_DOWNLOADS;

                // Set up the request
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setTitle(fileName);
                request.setDescription("Downloading...");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(destination, fileName);

                // Enqueue the download
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadReference = downloadManager.enqueue(request);

                Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();
            } else {
                requestPermission();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Download failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permissions granted, continue with download
                Log.d("tag45","7");
                View v = null;
                onDownloadClick(v);
            } else {
                Log.d("tag45","8");

                // permissions denied, show a message to the user
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
*/
}
