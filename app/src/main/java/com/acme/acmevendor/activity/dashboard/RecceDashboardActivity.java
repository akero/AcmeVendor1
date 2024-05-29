package com.acme.acmevendor.activity.dashboard;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.acme.acmevendor.utility.RoundRectCornerImageView;
import com.acme.acmevendor.viewmodel.APIreferenceclass;
import com.acme.acmevendor.viewmodel.ApiInterface;
import com.acme.acmevendor.viewmodel.SiteDetail;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.acme.acmevendor.databinding.ActivityRecceDashboardBinding;

import com.acme.acmevendor.R;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class RecceDashboardActivity extends AppCompatActivity implements ApiInterface, LocationCallback {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRecceDashboardBinding binding;
    private static final int REQUEST_CODE_PERMISSIONS = 123;
    static final int REQUEST_TAKE_PHOTO = 1;
    private final Context ctxt= this;

    private static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION
            //Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ,Manifest.permission.READ_EXTERNAL_STORAGE
    };

    Boolean locationtaken, picturetaken;
    String latlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecceDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init locationhelper
        locationHelper = new LocationHelper();
        picturetaken= false;
        latlong= "";
        locationtaken= false;


        //initializing photo buttons
        Button storePhotoButton, storePhotoButton1, storePhotoButton2, storePhotoButton3, ownerSignButton, uploadSiteDetailsButton;
        storePhotoButton= findViewById(R.id.btnUpdatePhoto);
        storePhotoButton1= findViewById(R.id.btnUpdatePhoto1);
        storePhotoButton2= findViewById(R.id.btnUpdatePhoto2);
        storePhotoButton3= findViewById(R.id.btnUpdatePhoto3);
        ownerSignButton= findViewById(R.id.btnUpdatePhoto4);


        storePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    //latlong();
                    Log.d("latlong", latlong);
                    //TODO uncomment
                    openCamera();
                }
            }
        });

        storePhotoButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    //latlong();
                    Log.d("latlong", latlong);
                    //TODO uncomment
                    openCamera();
                }
            }
        });

        storePhotoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    //latlong();
                    Log.d("latlong", latlong);
                    //TODO uncomment
                    openCamera();
                }
            }
        });

        storePhotoButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    //latlong();
                    Log.d("latlong", latlong);
                    //TODO uncomment
                    openCamera();
                }
            }
        });

        ownerSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    //latlong();
                    Log.d("latlong", latlong);
                    //TODO uncomment
                    openCamera();
                }
            }
        });







        //Signage area


        //EditText height, width, remarks, projectname, state, district, city, retailname, ownername, owneremail, ownerphone, yourname, location;



        EditText date;

        RoundRectCornerImageView imageButton= findViewById(R.id.ivCampaignImage);
        
        Button uploadSignageDetails= findViewById(R.id.btnUpdatePhoto5);

        uploadSignageDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etHeight.getText().toString().isEmpty() || binding.etWidth.getText().toString().isEmpty()) {
                    Toast.makeText(ctxt, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }

                //TODO add check for image
            }
        }
        );



    }

    private LocationHelper locationHelper;

    void latlong(){

        locationHelper.requestLocationPermission(this, this);
    }

    Uri photoURI;

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoURI= null;
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Handle error
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(RecceDashboardActivity.this,
                        "com.example.android.fileprovider",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                Log.d("opencamerauri", photoURI.toString());
                startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
            }
        } else {
            Log.d("camera", "no permission1");
            Toast.makeText(RecceDashboardActivity.this, "Don't have camera permissions", Toast.LENGTH_SHORT).show();

        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getFilesDir();
        Log.d("tag222", "created image");
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    //auto opens when addimage is clicked
    public void addImage(){

    }

    Uri imageUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Image captured successfully
            // Access the image file using the Uri you provided earlier

            Log.d("tag22", data.toString());

            try {
                imageUri = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        createImageFile());
                Log.d("tag222", imageUri.toString());



                //String imageuristring= imageUri.toString();
                //String[] a= imageuristring.split("Pictures/");
                //imageuristring= a[1];
                //imageuristring= "content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fdata%2Fcom.acme.acmevendor%2Ffiles%2FPictures%2F"+ imageuristring;
                //imageUri= Uri.parse(imageuristring);

               /* try (InputStream in = getContentResolver().openInputStream(imageUri)) {
                    // Open a private file for writing
                    try (FileOutputStream out = openFileOutput("ImageName.jpg", Context.MODE_PRIVATE)) {
                        byte[] buffer = new byte[1024];
                        Log.d("tag22", "copying file");

                        int read;
                        while ((read = in.read(buffer)) != -1) {
                            Log.d("tag22", "copying file");

                            out.write(buffer, 0, read);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Get a Uri for the file in internal storage
                File file = new File(getFilesDir(), "ImageName.jpg");
                imageUri = Uri.fromFile(file);

*/


                picturetaken= true;
                if(locationtaken){
                    apicallforvendorimageupdate(latlong, imageUri);
                    Log.d("tag22", "activity result works.");

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
            JSONObject jsonobj1= null;
            Log.d("response1", response1);
            JSONObject jsonobj= new JSONObject(response1);
            Log.d("tag44", jsonobj.toString());
            if(jsonobj.has("site")) {
                jsonobj1 = jsonobj.getJSONObject("site");
            }else if(jsonobj.has("datas")){
                jsonobj1 = jsonobj.getJSONObject("datas");
                latlong();
            }
            Log.d("tag44", jsonobj1.toString());
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

            jsonobj1.remove("image");
            jsonobj.putOpt("site", jsonobj1);

            JSONObject jsonobj2= new JSONObject();
            jsonobj2.putOpt("campaign_id", jsonobj1.getInt("campaign_id"));
            jsonobj2.putOpt("vendor_id", jsonobj1.getString("vendor_id"));
            jsonobj2.putOpt("site_id", jsonobj1.getString("id"));
            jsonobj2.putOpt("longitute", longitude);
            jsonobj2.putOpt("latitude", latitude);


            Log.d("tag333", jsonobj.toString());
            Log.d("livetest", jsonobj.toString()+ "site no"+ siteno+ uri + "jsonobj2"+ jsonobj2.toString());

            /*compress photoURI here
            // Load the image from a file
Bitmap bitmap = BitmapFactory.decodeFile("/path/to/your/image.jpg");

// Compress the image
FileOutputStream out = new FileOutputStream("/path/to/compressed/image.jpg");
bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out); // 50 is the quality parameter

out.close();
            * */
            APIreferenceclass api= new APIreferenceclass(1, ctxt, logintoken1, jsonobj2.toString(), photoURI);


        }catch (Exception e){
            Log.d("tag41", e.toString());
            e.printStackTrace();
        }
        //TODO handle response
    }

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
                } else if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
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
                Toast.makeText(RecceDashboardActivity.this, "Location/camera permission denied", Toast.LENGTH_SHORT).show();

            }
        }

}
    @Override
    public void callback(String a) {
        if(!locationtaken) {
            latlong = a;
            locationtaken = true;
            if(picturetaken) {
                apicallforvendorimageupdate(latlong, imageUri);
            }
        }
        Log.d("tag22", "inside callback, latlong "+ latlong+ "locationtaken"+ locationtaken);
    }

    String response1;
    @Override
    public void onResponseReceived(String response){

        response1= response;
        Log.d("tag41 response is", response);
        response1= response;
        implementUI(response);
    }

    SiteDetail siteDetail;
    JSONObject jsonobj;

    private void implementUI(String response) {
        try {

            Log.d("tagresponse is", response);
            JSONObject jsonResponse = new JSONObject(response);

            if(jsonResponse.getString("message").equals("Sites retrieved successfully.")) {
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
                        siteDetail.setEndDate(dataObject.optString("end_date"));
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

                                //TextView tvLastInspection = findViewById(R.id.tvStartDate);
                                //tvLastInspection.setText(siteDetail.getCreatedAt());

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

                                TextView tvEndDate = findViewById(R.id.tvEndDate);
                                tvEndDate.setText(siteDetail.getEndDate());

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
                            Toast.makeText(RecceDashboardActivity.this, "Error retrieving or parsing data.", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }else if(jsonResponse.getString("message").equals("Data Saved successfully.")){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecceDashboardActivity.this, "Image updated successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecceDashboardActivity.this, "Error retrieving or parsing data..", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } catch (Exception e) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("tag123", e.toString());
                    Toast.makeText(RecceDashboardActivity.this, "Error retrieving or parsing data", Toast.LENGTH_SHORT).show();
                }
            });
        }



    }
}