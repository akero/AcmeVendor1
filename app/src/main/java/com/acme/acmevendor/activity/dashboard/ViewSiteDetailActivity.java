package com.acme.acmevendor.activity.dashboard;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityViewSiteDetailBinding;
import com.acme.acmevendor.databinding.ActivityViewSiteDetailVendorBinding;
import com.acme.acmevendor.utility.RoundRectCornerImageView;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.acme.acmevendor.viewmodel.SiteDetail;
import com.google.android.gms.common.api.Api;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class ViewSiteDetailActivity extends AppCompatActivity implements ApiInterface, LocationCallback {

    private String campaignType = "";
    static final int REQUEST_TAKE_PHOTO = 1;

    private int position = 0;
    String siteNumber= "";
    String logintoken="";
    String campaignId="";
    String camefrom;
    private ActivityViewSiteDetailBinding binding;
    private ActivityViewSiteDetailVendorBinding binding1;

    //TODO populate all fields. pass api call data from prev activity
    //have to pass logintoken and siteid

    String latlong;
    private LocationHelper locationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            Log.d("tag41", "2");
            campaignId = getIntent().getExtras().getString("campaignId", "");
            campaignType = getIntent().getExtras().getString("campaignType", "");
            siteNumber= getIntent().getExtras().getString("siteNumber", "");
            logintoken= getIntent().getExtras().getString("logintoken","");
            camefrom= getIntent().getExtras().getString("camefrom", "");
            picturetaken= false;
            latlong= "";
            locationtaken= false;
            locationHelper = new LocationHelper();


            Log.d("tag1001", campaignType+" "+ campaignId+" "+ siteNumber+" "+ logintoken+" "+ camefrom);


        if(!camefrom.equals("ViewVendorSites")){
            binding = DataBindingUtil.setContentView(this, R.layout.activity_view_site_detail);
        }else{
            binding1 = DataBindingUtil.setContentView(this, R.layout.activity_view_site_detail_vendor);
            Log.d("camera", "click registered");

            Button btn= findViewById(R.id.btnUpdatePhoto);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("camera", "click registered");
                    if (ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(ViewSiteDetailActivity.this, "Please give camera and location permissions", Toast.LENGTH_SHORT).show();

                        ActivityCompat.requestPermissions(ViewSiteDetailActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                        Log.d("camera", "dont have permission");
                    } else {

                       // temporaryuploadchecker();
                        latlong();
                        Log.d("latlong", latlong);
                        //TODO uncomment
                        openCamera();
                    }
                }

            });
        }

            if(camefrom.equals("ClientDashBoardActivity")){
                binding.btnNext.setVisibility(View.GONE);
            }

            if(camefrom.equals("ViewVendorSites")){
                binding1.btnNext.setVisibility(View.GONE);
            }

            Log.d("tg2", siteNumber);
        }

        //= "";
        Log.d("tag41", "1");
        Log.d("whichclass", "ViewSiteDetailActivity");

        Log.d("tag41", "4");
        apicall(logintoken, siteNumber);
        Log.d("tag41", "5");
    }

    boolean locationtaken;

    @Override
    public void callback(String a) {
        if(!locationtaken) {
            latlong = a;
            locationtaken = true;
            apicallforvendorimageupdate(latlong, imageUri);

        }
        Log.d("tag22", "inside callback, latlong "+ latlong+ "locationtaken"+ locationtaken);
    }

    private static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION//,
            //Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int REQUEST_CODE_PERMISSIONS = 123;

    void latlong(){

        locationHelper.requestLocationPermission(this, this);
    }

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 123;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            boolean cameraGranted = false;
            boolean locationGranted = false;
            boolean storageGranted= false;
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.CAMERA) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    cameraGranted = true;
                    Log.d("permissions", "camera");
                } else if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    locationGranted = true;

                    Log.d("permissions", "location");
                } else if (permissions[i].equals(WRITE_EXTERNAL_STORAGE) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    storageGranted = true;

                    Log.d("permissions", "storage");
                }
            }

            if (cameraGranted && locationGranted) {
                // Both permissions granted, proceed with your app logic
               // temporaryuploadchecker();
                latlong();
                Log.d("latlong", latlong);
                //TODO uncomment

                openCamera();
            } else {
                // At least one permission was denied, handle accordingly
                Toast.makeText(ViewSiteDetailActivity.this, "Location/camera permission denied", Toast.LENGTH_SHORT).show();

            }
        }
        /*
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(ViewSiteDetailActivity.this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == LocationHelper.REQUEST_CODE_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationHelper.requestLocationPermission(this);
            } else {
                Toast.makeText(ViewSiteDetailActivity.this, "Location permission denied", Toast.LENGTH_SHORT).show();

            }
        }
    */}
    public static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    void temporaryuploadchecker(){
        File imageFile = new File("/storage/emulated/0/Pictures/image.jpg");
        //TODO handle image here
        Uri imageUri;
        try {
            imageUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", imageFile);
        }catch(Exception e){
            Log.d("tag22", e.toString());
        }


    }

    private void openCamera1() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
            } else {
                Log.d("camera", "no camera app found");
                Toast.makeText(ViewSiteDetailActivity.this, "No camera app found", Toast.LENGTH_SHORT).show();
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Handle error
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(ViewSiteDetailActivity.this,
                        "com.example.android.fileprovider",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
            }
        } else {
           Log.d("camera", "no permission1");
            Toast.makeText(ViewSiteDetailActivity.this, "Don't have camera permissions", Toast.LENGTH_SHORT).show();

    }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    boolean picturetaken;
    Uri imageUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Image captured successfully
            // Access the image file using the Uri you provided earlier

            try {
                imageUri = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        createImageFile());
                picturetaken= true;
                if(locationtaken){
                    apicallforvendorimageupdate(latlong, imageUri);

                }
                Log.d("tag22", "activity result works");

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Image update failed", Toast.LENGTH_SHORT).show();
            }
            // Do something with the imageUri, e.g., display the image or upload it
        }else{
            Log.d("tag22", "something went wrong");
        }
    }

    void apicallforvendorimageupdate(String latlong, Uri uri){

        String logintoken1= "";
        //response is response1 (with site)

        try {
            FileHelper fh = new FileHelper();
            logintoken1 = fh.readLoginToken(this);
        }catch(Exception e){
            Log.d("tag22", e.toString());
        }
        String siteno= "";

        try{
            JSONObject jsonobj= new JSONObject(response1);
            JSONObject jsonobj1= jsonobj.getJSONObject("site");
            String latitude= "";
            String longitude= "";

            StringTokenizer str= new StringTokenizer(latlong, ",");
            if(str!= null){
                latitude= str.nextToken();
                longitude= str.nextToken();

                Log.d("tag44", "latitude"+ latitude+ "long"+ longitude);
            }

            jsonobj1.putOpt("latitude", latitude);
            jsonobj1.putOpt("longitute", longitude);
            Log.d("tag511", "jsonobj"+jsonobj.toString()+ " jsonobj1"+ jsonobj1.toString()+ "siteno"+ siteno);

            siteno= Integer.toString(jsonobj1.getInt("id"));

            jsonobj.putOpt("site", jsonobj1);

            Log.d("tag333", jsonobj.toString());



        }catch (Exception e){
            Log.d("tag41", e.toString());
        }

        Log.d("livetest", jsonobj.toString()+ "site no"+ siteno+ uri );

        APIreferenceclass api= new APIreferenceclass(2, this, logintoken1, jsonobj.toString(), siteno, uri);

        //TODO handle response
    }

    SiteDetail siteDetail;
    JSONObject jsonobj;

    private void implementUI(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            if(jsonResponse.getString("status").equals("success")) {
                JSONObject dataArray = new JSONObject(jsonResponse.getString("site"));
                if(dataArray != null && dataArray.length() > 0) {
                    JSONObject dataObject = dataArray;
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

                        Log.d("SiteDetailLog",
                                "ID: " + siteDetail.getId() +
                                        ", Vendor ID: " + siteDetail.getVendorId() +
                                        ", Location: " + siteDetail.getLocation() +
                                        ", Created At: " + siteDetail.getCreatedAt() +
                                        ", End Date: " + siteDetail.getEndDate() +
                                        ", Latitude: " + siteDetail.getLatitude() +
                                        ", Longitude: " + siteDetail.getLongitude() +
                                        ", Media Type: " + siteDetail.getMediaType() +
                                        ", Illumination: " + siteDetail.getIllumination() +
                                        ", Start Date: " + siteDetail.getStartDate() +
                                        ", Name: " + siteDetail.getName() +
                                        ", Site No: " + siteDetail.getSiteNo() +
                                        ", Width: " + siteDetail.getWidth() +
                                        ", Height: " + siteDetail.getHeight() +
                                        ", Total Area: " + siteDetail.getTotalArea() +
                                        ", Updated At: " + siteDetail.getUpdatedAt());


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
                                Toast.makeText(ViewSiteDetailActivity.this, "Error retrieving or parsing data.", Toast.LENGTH_SHORT).show();
                            }
                        });

                }
            } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ViewSiteDetailActivity.this, "Error retrieving or parsing data..", Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("tag123", e.toString());
                        Toast.makeText(ViewSiteDetailActivity.this, "Error retrieving or parsing data", Toast.LENGTH_SHORT).show();
                    }
                });
        }

        // Assigning values and listeners to Buttons

        /*Button btnUpdatePhoto = findViewById(R.id.btnUpdatePhoto);
        btnUpdatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        });

*/
            Button btnNext = findViewById(R.id.btnNext);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent= new Intent(ViewSiteDetailActivity.this, AddSiteDetailActivity.class);
                    intent.putExtra("loginToken", logintoken);
                    intent.putExtra("campaignId", campaignId);
                    intent.putExtra("editingsite", "yes");
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

    public String response1="";

    @Override
    public void onResponseReceived(String response){

        response1= response;
        Log.d("tag41 response is", response);
        response1= response;
        implementUI(response);

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

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            Uri imageUri = data.getData();
            APIreferenceclass api= new APIreferenceclass(imageUri);

        }
    }*/
}
