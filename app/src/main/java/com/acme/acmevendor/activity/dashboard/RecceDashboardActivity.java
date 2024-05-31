package com.acme.acmevendor.activity.dashboard;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

//TODO rearrange UI accoding to data required for store photos.
//TODO implement all api calls.


public class RecceDashboardActivity extends AppCompatActivity implements ApiInterface, LocationCallback {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRecceDashboardBinding binding;
    boolean wassendbuttonpressed;
    private static final int REQUEST_CODE_PERMISSIONS = 123;
    static final int REQUEST_TAKE_PHOTO = 1;
    private final Context ctxt = this;
    String selectedClient;

    boolean pictureandlatlongready; //in imageUri and latlong

    private static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION
            //Manifest.permission.MANAGE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE
    };

    Boolean locationtaken, picturetaken;
    String latlong, logintoken;
    int piccounter; //0 init, 1, 2, 3, 4, 5, 6
    boolean pic1taken, pic2taken, pic3taken, pic4taken, picsigntaken, picsitetaken;
    Uri pic1takenURI, pic2takenURI, pic3takenURI, pic4takenURI, picsigntakenURI, picsitetakenURI;
    int storephoto;//0 for init, 1 for store photo, 2 for sign, 3 for main upload with other stuff
    int recceid, clientspinnerboolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecceDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wassendbuttonpressed= false;
        clientspinnerboolean= 0;
        piccounter= 0;
        recceid= 0;
        pic1takenURI= null; pic2takenURI= null; pic3takenURI= null; pic4takenURI= null; picsigntakenURI= null; picsitetakenURI= null;
        allpicturestaken= false; pic1taken= false; pic2taken= false; pic3taken= false; pic4taken= false; picsigntaken= false; picsitetaken= false;
        selectedClient= "";

        try{
            recceid= getIntent().getIntExtra("recceid", 0);
        }catch (Exception e){
            e.printStackTrace();
        }

        //init locationhelper
        locationHelper = new LocationHelper();
        picturetaken = false;
        latlong = "";
        locationtaken = false;
        pictureandlatlongready = false;
        storephoto= 0;

        try {
            FileHelper fh = new FileHelper();
            logintoken = fh.readLoginToken(this);

        } catch (Exception e) {
            Log.d("tg223", e.toString());
        }


        //initializing photo buttons
        Button storePhotoButton, storePhotoButton1, storePhotoButton2, storePhotoButton3, ownerSignButton, uploadSiteDetailsButton;
        storePhotoButton = findViewById(R.id.btnUpdatePhoto);
        storePhotoButton1 = findViewById(R.id.btnUpdatePhoto1);
        storePhotoButton2 = findViewById(R.id.btnUpdatePhoto2);
        storePhotoButton3 = findViewById(R.id.btnUpdatePhoto3);
        ownerSignButton = findViewById(R.id.btnUpdatePhoto4);


        storePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    //latlong();
                    Log.d("latlong", latlong);

                    storephoto= 1;
                    piccounter= 1;
                    openCamera();
                }
            }
        });

        storePhotoButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    //latlong();
                    Log.d("latlong", latlong);

                    storephoto= 1;
                    piccounter= 2;
                    openCamera();
                }
            }
        });

        storePhotoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    //latlong();
                    Log.d("latlong", latlong);

                    storephoto= 1;
                    piccounter= 3;
                    openCamera();
                }
            }
        });

        storePhotoButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    //latlong();
                    Log.d("latlong", latlong);

                    storephoto= 1;
                    piccounter= 4;
                    openCamera();
                }
            }
        });

        ownerSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    //latlong();
                    Log.d("latlong", latlong);

                    storephoto= 2;
                    piccounter= 5;
                    openCamera();
                }
            }
        });

        //fetch retailer details and populate automatically
        binding.btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                APIreferenceclass api= new APIreferenceclass(logintoken, ctxt, binding.etFetch.getText().toString());
            }
        });


        //Signage area


        //EditText height, width, remarks, projectname, state, district, city, retailname, ownername, owneremail, ownerphone, yourname, location;


        //date
        binding.etWidth3.setOnClickListener(new View.OnClickListener() {
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
                        RecceDashboardActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                Log.d("date", year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                binding.etWidth3.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                Log.d("date", String.valueOf(binding.etWidth3.getText()));


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

        //photo of sign
        RoundRectCornerImageView imageButton = findViewById(R.id.ivCampaignImage);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("camera", "click registered");
                if (ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RecceDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//||ContextCompat.checkSelfPermission(ViewSiteDetailActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(RecceDashboardActivity.this, "Please give camera and location permissions", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(RecceDashboardActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    Log.d("camera", "dont have permission");
                } else {

                    // temporaryuploadchecker();
                    latlong();
                    Log.d("latlong", latlong);
                    //TODO uncomment

                    storephoto= 3;
                    piccounter= 6;
                    openCamera();
                }
            }

        });

        Context ctxt= this;
        try {
            clientspinnerboolean= 1;
            APIreferenceclass api = new APIreferenceclass(ctxt, logintoken);
        }catch(Exception e){
            Log.d("tg343", e.toString());
        }

        Button uploadSignageDetails = findViewById(R.id.btnUpdatePhoto5);

        uploadSignageDetails.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        if (binding.etHeight.getText().toString().isEmpty() || binding.etWidth.getText().toString().isEmpty() || binding.etHeight1.getText().toString().isEmpty() || binding.etWidth1.getText().toString().isEmpty() || binding.etHeight2.getText().toString().isEmpty() || binding.etWidth2.getText().toString().isEmpty() || binding.etHeight3.getText().toString().isEmpty() || binding.etWidth3.getText().toString().isEmpty() || binding.etHeight4.getText().toString().isEmpty() || binding.etWidth4.getText().toString().isEmpty() || binding.etHeight5.getText().toString().isEmpty() || binding.etWidth5.getText().toString().isEmpty() || binding.etTotalArea.getText().toString().isEmpty() || binding.etTotalArea1.getText().toString().isEmpty() || !pictureandlatlongready) {
                                                            Toast.makeText(ctxt, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                                            wassendbuttonpressed= true;
                                                            latlong();
                                                        } else {
                                                            //api call

                                                            apicall();

                                                        }

                                                        //TODO add check for image
                                                    }
                                                }
        );
    }

    void apicall() {
        String lat = "";
        String longitude = "";

        try {
            StringTokenizer str = new StringTokenizer(latlong, ",");
            lat = str.nextToken();
            longitude = str.nextToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonPayload = new JSONObject();
        try {

            jsonPayload.put("project", binding.etHeight1.getText().toString());
            jsonPayload.put("state", binding.etWidth1.getText().toString());
            jsonPayload.put("district", binding.etHeight2.getText().toString());
            jsonPayload.put("city", binding.etWidth2.getText().toString());
            jsonPayload.put("retail_name", binding.etHeight3.getText().toString());
            jsonPayload.put("length", binding.etHeight.getText().toString());
            jsonPayload.put("width", binding.etWidth.getText().toString());
            jsonPayload.put("date", binding.etWidth3.getText().toString());
            jsonPayload.put("owner_name", binding.etHeight4.getText().toString());
            jsonPayload.put("email", binding.etWidth4.getText().toString());
            jsonPayload.put("mobile", binding.etHeight5.getText().toString());
            jsonPayload.put("remarks", binding.etTotalArea.getText().toString());
            jsonPayload.put("location", binding.etTotalArea1.getText().toString());
            jsonPayload.put("area", binding.area.getText().toString());
            jsonPayload.put("lat", lat);
            jsonPayload.put("client_id", selectedClient);
            jsonPayload.put("long", longitude);

            //double area= 0;
            //TODO change after clarification on email
            //area= Double.parseDouble(binding.etHeight.getText().toString())*Double.parseDouble(binding.etWidth.getText().toString());
            //jsonPayload.put("area", String.valueOf(area));
            FileHelper fh = new FileHelper();
            jsonPayload.put("created_by", fh.readUserId(this));

        } catch (Exception e) {
            Log.d("tg6", e.toString());
        }
        Log.d("tg6", imageUri.toString());
        //apiboolean= 1;

        Log.d("tg66", jsonPayload.toString());

        APIreferenceclass api = new APIreferenceclass(jsonPayload, this, logintoken, recceid, pic1takenURI, pic2takenURI,pic3takenURI, pic4takenURI, picsigntakenURI, picsitetakenURI);
        imageUri = null;
    }


    private LocationHelper locationHelper;

    void latlong() {

        locationHelper.requestLocationPermission(this, this);
    }

    Uri photoURI;

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoURI = null;
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
    public void addImage() {

    }

    Uri imageUri;
boolean allpicturestaken;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Image captured successfully
            // Access the image file using the Uri you provided earlier

            Log.d("tag22", data.toString());
            Uri imageUri1;

            try {
                imageUri = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        createImageFile());
                imageUri1= imageUri;
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


                if(piccounter== 1){
                    pic1taken= true;
                    pic1takenURI= imageUri1;
                }else if(piccounter== 2){
                    pic2taken= true;
                    pic2takenURI= imageUri1;
                }else if(piccounter== 3){
                    pic3taken= true;
                    pic3takenURI= imageUri1;
                }else if(piccounter== 4){
                    pic4taken= true;
                    pic4takenURI= imageUri1;
                }else if(piccounter== 5){
                    picsigntaken= true;
                    picsigntakenURI= imageUri1;
                }else if(piccounter== 6){
                    picsitetaken= true;
                    picsitetakenURI= imageUri1;
                }
                if(pic1taken&&pic2taken&&pic3taken&&pic4taken&&picsigntaken&&picsitetaken){
                    allpicturestaken= true;
                    if(locationtaken){
                        pictureandlatlongready= true;
                    }else{
                        latlong();
                    }
                }


                picturetaken = true;
               // if (locationtaken && allpicturestaken) {//main upload
                 //   pictureandlatlongready = true;
                   // apicall();

                    //Log.d("tag22", "activity result works.");
                //}
             //   else if(storephoto== 1){//store photo upload

               //     apicallforstorephoto();
               // }else if(storephoto== 2){

                 //   apicallforsign();
                //}

                Log.d("tag22", "activity result works");

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Image update failed", Toast.LENGTH_SHORT).show();
            }
            // Do something with the imageUri, e.g., display the image or upload it
        } else {
            Log.d("tag22", "something went wrong");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            boolean cameraGranted = false;
            boolean locationGranted = false;
            boolean storageGranted = false;
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
        if (!locationtaken) {
            latlong = a;
            locationtaken = true;
            if (allpicturestaken) {
                pictureandlatlongready = true;
                if(wassendbuttonpressed){
                    apicallmain();
                }
               // apicallmain(latlong);
            }
        }
        Log.d("tag22", "inside callback, latlong " + latlong + "locationtaken" + locationtaken);
    }

    void apicallmain(){

    }

    String response1;


    @Override
    public void onResponseReceived(String response) {
        JSONObject jsono= null;
        try {

            jsono = new JSONObject(response);

    if(jsono.getString("message").equals("Clients retrieved successfully.")){
            clientspinnerboolean=0;
        response1 = response;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    clientlist(response);
                }
            });
        }else if(jsono.getString("message").equals("Data fetched successfully!")){

            //TODO populate fields with content
    }
    else{
        response1 = response;
        Log.d("tag222 response is", response);
        response1 = response;}
        //implementUI(response);
        }catch (Exception e){
        }
    }

    JSONArray jsonArray;

    void clientlist(String response){
        //TODO retreive client list

        Log.d("clientlist", response);
        //TODO put this spinner code after response is received

        String[] items2= null;

        try{
            JSONObject json= new JSONObject(response);
            jsonArray= json.getJSONArray("data");
            items2 = new String[jsonArray.length()];

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject json1= jsonArray.getJSONObject(i);
                items2[i]= json1.optString("name");
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        final String[] items3= items2;

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        binding.spinnermediatype1.setAdapter(adapter2);

        // Inside your onCreate method
        binding.spinnermediatype1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //TODO put the client id of the client in below and then add it to the jsonobject that is sent to api



                selectedClient = parent.getItemAtPosition(position).toString();

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json1 = jsonArray.getJSONObject(i);
                        items3[i] = json1.optString("name");
                        if(items3[i].equals(selectedClient)){
                            Log.d("selectedclient", json1.optString("id")+ " "+ selectedClient);
                            selectedClient= json1.optString("id");

                            break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


                Log.d("tg92", "selectedClient"+ selectedClient);


                //Toast.makeText(AddCampaignDetails.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


    }

    SiteDetail siteDetail;
    JSONObject jsonobj;

    private void implementUI(String response) {
        try {

            Log.d("tagresponse is", response);
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getString("message").equals("Sites retrieved successfully.")) {
                JSONObject dataArray = new JSONObject(jsonResponse.getString("site"));
                if (dataArray != null && dataArray.length() > 0) {
                    JSONObject dataObject = dataArray;
                    jsonobj = dataObject;
                    if (dataObject != null) {
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
                            imageUrl = "https://acme.warburttons.com/" + imageUrl;
                            Log.d("tag41", "imageurl is " + imageUrl);
                            Log.d("tg2", "image code not executing 1");

                            if (imageUrl != "null" && !imageUrl.isEmpty()) {
                                URL url = new URL(imageUrl);
                                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                Log.d("tg2", "image code executing");
                                siteDetail.setImage(bitmap);
                            }
                        } catch (Exception e) {
                            Log.d("tg2", "image code not executing 2");
                            Log.d("tag41", "error in implementui" + e.toString());
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
                                if (siteDetail.getImage() != null) {
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
            } else if (jsonResponse.getString("message").equals("Data Saved successfully.")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecceDashboardActivity.this, "Image updated successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {

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