package com.acme.acmevendor.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.acme.acmevendor.api.MyUrlRequestCallback;
import com.acme.acmevendor.models.SendOtpResponseModel;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.chromium.net.CronetEngine;
import org.chromium.net.UploadDataProvider;
import org.chromium.net.UploadDataSink;
import org.chromium.net.UrlRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class APIreferenceclass {

    private MutableLiveData<SendOtpResponseModel> successresponse = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<SendOtpResponseModel> getSuccessresponse() {
        return successresponse;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    Executor cronetExecutor = Executors.newSingleThreadExecutor();

    int querytype=0; //0 for get, 1 for post, 2 for put

    //constructors for admin dashboard
    //campaign is 0, client is 1, vendor is 2
    public APIreferenceclass(int vendorclientorcampaign, String logintoken, Context context) {

        //campaign
        if (vendorclientorcampaign == 0) {
            Log.d("tag21","1");

            String url="https://acme.warburttons.com/api/campaigns";
            querytype=0;
            String jsonPayload = "{\"Authorization\": \"" + logintoken +"\"}";

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("addbatest","Inside admin api");

            callapi(headers, jsonPayload, context, querytype, url);
        }

        //client
        else if (vendorclientorcampaign == 1) {
            String url="https://acme.warburttons.com/api/clients";
            querytype= 0;

            String jsonPayload= "{\"Authorization\":\"" +logintoken+"\"}";

            Map<String, String> headers= new HashMap<>();
            headers.put("Authorization", "Bearer "+logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("cldbatest","jsonpayload is "+ jsonPayload);

            callapi(headers, jsonPayload, context, querytype, url);
        }

        //vendor
        else if (vendorclientorcampaign == 2) {
            String url="https://acme.warburttons.com/api/vendors";

            querytype=0; //GET
            String jsonPayload="{\"Authorization\":\"" +logintoken+"\"}";

            Map<String, String> headers= new HashMap<>();
            headers.put("Authorization", "Bearer "+logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("vdbatest","Inside apirefapiclass");

            callapi(headers, jsonPayload, context, querytype, url);
      }
    }

    //get campaigns in client login
    public APIreferenceclass(int vendorclientorcampaign, String logintoken, Context context, int clientid, int b) {
        String url="https://acme.warburttons.com/api/get_admin_client_campaigns/";
        url= url+ clientid;
        querytype= 0;

        String jsonPayload= "{\"Authorization\":\"" +logintoken+"\"}";

        Map<String, String> headers= new HashMap<>();
        headers.put("Authorization", "Bearer "+logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("cldbatest","jsonpayload is "+ jsonPayload);

        callapi(headers, jsonPayload, context, querytype, url);
    }

    public APIreferenceclass(int id, String logintoken, Context context, boolean a){
        String url= "https://acme.warburttons.com/api/clients/"+ id;
        querytype= 0;

        Log.d("tg01", "a");

        String jsonPayload= "{\"Authorization\":\"" +logintoken+"\"}";

        Map<String, String> headers= new HashMap<>();
        headers.put("Authorization", "Bearer "+logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("cldbatest","jsonpayload is "+ jsonPayload);

        callapi(headers, jsonPayload, context, querytype, url);

    }

    //to fetch client's campaigns in clientdashfirstpage
    public APIreferenceclass(int clientId, int vendorclientorcampaign, String logintoken, Context context) {
        String url="https://acme.warburttons.com/api/get_admin_client_campaigns";
        querytype= 0;
        url= url+"/"+clientId;

        String jsonPayload= "{\"Authorization\":\"" +logintoken+"\"}";

        Map<String, String> headers= new HashMap<>();
        headers.put("Authorization", "Bearer "+logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("clientdashfirstpage","url is "+ url);

        Log.d("clientdashfirstpage","jsonpayload is "+ jsonPayload);

        callapi(headers, jsonPayload, context, querytype, url);
    }

    //to fetch client's old campaigns in clientdashfirstpage
    public APIreferenceclass(int clientId, int vendorclientorcampaign, String logintoken, Context context, int i) {
        String url="https://acme.warburttons.com/api/clients";
        querytype= 0;
        url= url+"/"+Integer.toString(clientId);

        Log.d("tg99", url);

        String jsonPayload= "{\"Authorization\":\"" +logintoken+"\"}";

        Map<String, String> headers= new HashMap<>();
        headers.put("Authorization", "Bearer "+logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("cldbatest","jsonpayload is "+ jsonPayload);

        callapi(headers, jsonPayload, context, querytype, url);
    }

    //for vendor flow first page
    public APIreferenceclass(String logintoken, Context context, int vendorid) {
            Log.d("tag21","1");


            //TODO here
            String url="https://acme.warburttons.com/api/get_vendor_campaigns/";
            //url= url+ vendorid;
            querytype=0;
            String jsonPayload = "{\"Authorization\": \"" + logintoken +"\"}";

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("addbatest","Inside admin api");

            callapi(headers, jsonPayload, context, querytype, url);
        }

    //admindashvendorsites- to get all campaigns for the vendor
    public APIreferenceclass(String logintoken, Context context, String vendorid, boolean a){
        querytype= 0;
        String url="https://acme.warburttons.com/api/get_admin_vendor_campaigns/"+vendorid;
        String jsonPayload = "{\"Authorization\": \"" + logintoken +"\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("addbatest","Inside admin api");

        callapi(headers, jsonPayload, context, querytype, url);

    }

    //admindashvendorsites- to get all sites for a campaign
    public APIreferenceclass(String logintoken, Context context, String url1, boolean a, boolean b){
        querytype= 0;
        String url=url1;
        String jsonPayload = "{\"Authorization\": \"" + logintoken +"\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("addbatest","Inside admin api");

        callapi(headers, jsonPayload, context, querytype, url);

    }

    //admindashvendorsites- to get all sites for a campaign
    public APIreferenceclass(String logintoken, Context context, String id,int a, boolean b){
        querytype= 0;
        String url="https://acme.warburttons.com/api/vendors/"+id;
        String jsonPayload = "{\"Authorization\": \"" + logintoken +"\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("addbatest","Inside admin api");

        callapi(headers, jsonPayload, context, querytype, url);

    }

    //TODO endpoint for otp get when this person gets his email
    //login
    public APIreferenceclass(int vendorclientorcampaign, Context context, String email) {

        //TODO make api call now
//client
        if (vendorclientorcampaign == 0) {

            Log.d("tag21","1");
        }

        //admin
        else if (vendorclientorcampaign == 1) {

            String url="https://acme.warburttons.com/api/login";
            querytype= 0;
        }

        //vendor
        else if (vendorclientorcampaign == 2) {

            String url="https://acme.warburttons.com/api/login";
        }
    }

    //for viewsitedetail
    public APIreferenceclass(String loginToken, String siteNumber, Context context){

        //TODO add siteNumber to api call

        Log.d("tag41","8");

        String url="https://acme.warburttons.com/api/get_site_by_id/"+ siteNumber;

        //String url="https://acme.warburttons.com/api/get_site_by_id/39";

        String jsonPayload = "{\"Authorization\": \"" + loginToken +"\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + loginToken);
        headers.put("Content-Type", "application/json");

        Log.d("tag41","Inside viewsitedetail api");

        callapi1(headers, jsonPayload, context, url);

    }


    //TAKE THE FUCKING PHOTO
    public APIreferenceclass(Uri selectedImage){



    }

    //for viewcampaignsites
    public APIreferenceclass(String loginToken, Context context, String id){

        //TODO add siteNumber to api call

        Log.d("tag58",id);
        //id="18";
        String url="https://acme.warburttons.com/api/get_campaign_sites/"+id;
        Log.d("tag58",url);

        String jsonPayload = "{\"Authorization\": \"" + loginToken +"\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + loginToken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");

        callapi1(headers, jsonPayload, context, url);
    }

    //for AdminViewClientDetails
    public APIreferenceclass(String loginToken, Context context, String id, String padding){

        //TODO add siteNumber to api call

        Log.d("tag58",id);

        String url="https://acme.warburttons.com/api/get_campaign_sites/"+id;
        Log.d("tag58",url);

        String jsonPayload = "{\"Authorization\": \"" + loginToken +"\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + loginToken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");
        callapi1(headers, jsonPayload, context, url);
    }

    //addclientactivity
    public APIreferenceclass(JSONObject jsonPayload1, Context context, String logintoken){

        //TODO add siteNumber to api call

        String url="https://acme.warburttons.com/api/clients";
        Log.d("tag23", "jsonpayload"+ jsonPayload1.toString());

        String jsonPayload = jsonPayload1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");

        querytype= 1; ////post

        callapi(headers, jsonPayload, context, querytype ,url);
    }

    //addvendoractivity
    public APIreferenceclass(JSONObject jsonPayload1, Context context, String logintoken, int a){

        //TODO add siteNumber to api call

        String url="https://acme.warburttons.com/api/vendors";
        Log.d("tag23", "jsonpayload"+ jsonPayload1.toString());

        String jsonPayload = jsonPayload1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside addvendor api");

        querytype= 1; ////post

        callapi(headers, jsonPayload, context, querytype ,url);
    }

    //editvendoractivity
    public APIreferenceclass(JSONObject jsonPayload1, Context context, String logintoken, String vendorid){

        //TODO add siteNumber to api call

        String url="https://acme.warburttons.com/api/vendors/";
        Log.d("tag23", "jsonpayload"+ jsonPayload1.toString());

        String jsonString= fixjsonstring(jsonPayload1.toString());

        String urlEncodedParams = encodeJsonToUrl(jsonString);
        url= url+vendorid+"?"+urlEncodedParams;

        String jsonPayload = "{\"Authorization\": \"" + logintoken +"\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside addvendor api");

        querytype= 2; ////post

        callapi(headers, jsonPayload, context, querytype ,url);
    }

    //addclientdetailactivity
    public APIreferenceclass(JSONObject jsonPayload1, Context context, String logintoken, String clientid, int b, String jsonString){

        //TODO add siteNumber to api call

        Log.d("tag21","1");

        String url="https://acme.warburttons.com/api/clients/";
        jsonString= fixjsonstring(jsonString);

        String urlEncodedParams = encodeJsonToUrl(jsonString);
        url= url+clientid+"?"+urlEncodedParams;

        querytype= 2; //PUT

        Log.d("tg99", "url "+ url +  "jsonstring"+ jsonString );

        String jsonPayload = "{\"Authorization\": \"" + logintoken +"\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("addbatest","Inside admin api");

        callapi(headers, jsonPayload, context, querytype, url);
    }

    //addcampaignactivity
    public APIreferenceclass(JSONObject jsonPayload1, Context context, String logintoken, Uri selectedImage) {
        String url = "https://acme.warburttons.com/api/campaigns";
        querytype = 1; // POST

        Log.d("tg912", jsonPayload1.toString());

        if (selectedImage != null) {
            // Prepare multipart body
            String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
            StringBuilder bodyBuilder = new StringBuilder();

            // Add JSON fields to multipart body
            Iterator<String> keys = jsonPayload1.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                bodyBuilder.append("--").append(boundary).append("\r\n");
                bodyBuilder.append("Content-Disposition: form-data; name=\"").append(key).append("\"\r\n\r\n");
                bodyBuilder.append(jsonPayload1.optString(key)).append("\r\n");
            }

            // Read file content from Uri
            byte[] fileBytes = readFileContent(context, selectedImage);
            String fileName = getFileName(context, selectedImage);

            // Start image part of multipart body
            bodyBuilder.append("--").append(boundary).append("\r\n");
            bodyBuilder.append("Content-Disposition: form-data; name=\"image\"; filename=\"")
                    .append(fileName).append("\"\r\n");
            bodyBuilder.append("Content-Type: ").append(guessContentTypeFromName(fileName))
                    .append("\r\n\r\n");

            // Convert the initial part of the multipart body to bytes
            byte[] initialPartBytes = bodyBuilder.toString().getBytes(StandardCharsets.UTF_8);

            try {
                // Create a ByteArrayOutputStream to combine everything
                ByteArrayOutputStream multipartOutputStream = new ByteArrayOutputStream();
                multipartOutputStream.write(initialPartBytes);
                multipartOutputStream.write(fileBytes);

                // Write the final boundary
                String finalBoundary = "\r\n--" + boundary + "--\r\n";
                multipartOutputStream.write(finalBoundary.getBytes(StandardCharsets.UTF_8));
                // Final multipart body
                final byte[] multipartBody = multipartOutputStream.toByteArray();

            // Set headers for multipart request
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "multipart/form-data; boundary=" + boundary);

            // Call API with multipart data
            Log.d("tg97", "multipart");
            callapi2(headers, multipartBody, context, 1, url); // Using POST method
            }catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Existing JSON payload handling
            String jsonPayload = jsonPayload1.toString();
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "application/json");

            callapi(headers, jsonPayload, context, 1, url); // Using POST method
        }
    }

    //for otp
    public APIreferenceclass(int loginType, Context context, String email, String a){

        //TODO add siteNumber to api call

        String url="https://acme.warburttons.com/api/login";
        Log.d("tag23", "logintype "+ loginType + " email " + email);

        String jsonPayload = "{\"email\": \""+ email +"\"}";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");

        querytype= 1; ////post

        callapi(headers, jsonPayload, context, querytype ,url);
    }

    //for otp enter
    public APIreferenceclass(String otp, Context context, String email, int a){

        //TODO add siteNumber to api call

        String url="https://acme.warburttons.com/api/verifyLogin";
        //Log.d("tag23", "logintype "+ loginType + " email " + email);

        String jsonPayload = "{\"email\": \"" + email +"\", \"password\": \"" + otp + "\"}";
        Log.d("tg4", jsonPayload);

        Map<String, String> headers = new HashMap<>();
        //headers.put("Authorization", "Bearer " + loginToken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");

        querytype=1; //post

        callapi(headers, jsonPayload, context, querytype ,url);
    }

    //addsitedetailactivity- edit site
    public APIreferenceclass(int queryType, Context context, String logintoken, String jsonString, String siteno, Uri selectedImage) {
        Log.d("tag21", "addsitedetailactivity edit site");

        String url = "https://acme.warburttons.com/api/sites/";
        jsonString = fixjsonstring(jsonString);

        String urlEncodedParams = encodeJsonToUrl(jsonString);
        url = url + siteno + "?" + urlEncodedParams;

        int querytype = queryType;
        Log.d("tg9", "url " + url + " querytype " + querytype + " jsonstring " + jsonString+ selectedImage);

        if (selectedImage != null) {
            // Read file content directly from Uri
            String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";

            // Modify headers for multipart request
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "multipart/form-data; boundary=" + boundary);

            Log.d("yes", "yes");

            // Call the API with multipart data
            callapi2(headers, multipart(context, selectedImage, jsonString), context, querytype, url);
        } else {
            // Regular API call with JSON payload
            String jsonPayload = "{\"Authorization\": \"" + logintoken + "\"}";
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("addbatest", "Inside admin api");

            callapi(headers, jsonPayload, context, querytype, url);
        }
    }



    private byte[] readFileContent(Context context, Uri uri) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            // Handle the exception
            return null;
        }
    }

    private String getFileName(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        String name = cursor.getString(nameIndex);
        cursor.close();
        return name;
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    // Method to convert file to byte array
    private byte[] convertFileToByteArray(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fileInputStream.read(data);
            return data;
        } catch (IOException e) {
            Log.e("APIreferenceclass", "Error reading file", e);
            return null;
        }
    }

    // Method to guess content type from file name
    private String guessContentTypeFromName(String fileName) {
        // Simple implementation based on file extension
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else {
            return "application/octet-stream"; // Default MIME type
        }
    }

    //fetch clients
    public APIreferenceclass(Context context, String logintoken){
        String url= "https://acme.warburttons.com/api/clients/";

        String jsonPayload = "{\"Authorization\": \"" + logintoken + "\"}";
        Log.d("tg4", jsonPayload);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");

        querytype=0; //get

        callapi(headers, jsonPayload, context, querytype ,url);
    }

    //fetch vendors
    public APIreferenceclass(Context context, String logintoken, int i){
        String url= "https://acme.warburttons.com/api/vendors/";

        String jsonPayload = "{\"Authorization\": \"" + logintoken + "\"}";
        Log.d("tg4", jsonPayload);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");

        querytype=0; //get

        callapi(headers, jsonPayload, context, querytype ,url);
    }

    //addsitedetailactivity- add new site
    public APIreferenceclass(int queryType, Context context, String logintoken, String jsonString, String siteno, Uri selectedImage, int i) {
        Log.d("tag21", "addsitedetailactivity add site");

        //TODO have to add data into form data not params
        String url = "https://acme.warburttons.com/api/sites";
        Log.d("tg3", jsonString);

        //TODO remove placeholders
        jsonString = fixjsonstring(jsonString); //placeholder
        Log.d("tg3", jsonString);
        Log.d("tg3", url);

        int querytype = queryType;

        //image call
        if (selectedImage != null) {
            // Read file content directly from Uri

            Log.d("tg33", "not null");
            String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
            // Modify headers for multipart request
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "multipart/form-data; boundary=" + boundary);

            // Call the API with multipart data
            callapi2(headers, multipart(context, selectedImage, jsonString), context, querytype, url);
        } else {//normal call
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "application/x-www-form-urlencoded");

            Log.d("addbatest", "Inside admin api");
            String formData = convertJsonToFormData(jsonString);
            //TODO add image
            //here
            callapi(headers, formData, context, querytype, url);
        }
    }

    byte[] multipart(Context context, Uri selectedImage, String jsonString){
        // Read file content directly from Uri
        byte[] fileBytes = readFileContent(context, selectedImage);
        String fileName = getFileName(context, selectedImage);
        String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
        // Add File Part
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(("Content-Disposition: form-data; name=\"image\"; filename=\"" + fileName + "\"\r\n").getBytes());
        outputStream.write(("Content-Type: " + guessContentTypeFromName(fileName) + "\r\n\r\n").getBytes());
        outputStream.write(fileBytes);
        outputStream.write(("\r\n").getBytes());

        // Add JSON Part
        String formData = convertJsonToFormData(jsonString);
        for (String field : formData.split("&")) {
            String[] keyValue = field.split("=");
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(("Content-Disposition: form-data; name=\"" + keyValue[0] + "\"\r\n\r\n").getBytes());
            outputStream.write((keyValue.length > 1 ? keyValue[1] : "").getBytes());
            outputStream.write(("\r\n").getBytes());
        }

        // End of multipart/form-data
        outputStream.write(("--" + boundary + "--").getBytes());

        return outputStream.toByteArray();
        }catch (Exception e){
        e.printStackTrace();
    }
    return outputStream.toByteArray();
    }


    private String convertJsonToFormData(String jsonString) {
        StringBuilder formData = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                formData.append(URLEncoder.encode(key, "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(jsonObject.getString(key), "UTF-8"));
                if (keys.hasNext()) {
                    formData.append("&");
                }
            }
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return formData.toString();
    }


    String fixjsonstring(String originalJsonString) {
        try {
            // Create a JSON object from the original JSON string
            JSONObject jsonObject = new JSONObject(originalJsonString);
            Log.d("tg09", originalJsonString);

            // Check for "campaign_id"
            if (!jsonObject.has("campaign_id") || jsonObject.isNull("campaign_id")) {
                jsonObject.put("campaign_id", "placeholder_campaign_id");
            }

            // Check for "vendor_id"
            if (!jsonObject.has("vendor_id") || jsonObject.isNull("vendor_id") || "".equals(jsonObject.optString("vendor_id"))) {
                jsonObject.put("vendor_id", "placeholder_vendor_id");
            }

            // Check for "end_date"
            if (!jsonObject.has("end_date") || jsonObject.isNull("end_date")) {
                jsonObject.put("end_date", "placeholder_end_date");
            }

            // Check for "longitude"
            if (!jsonObject.has("longitute") || jsonObject.isNull("longitute") || "".equals(jsonObject.optString("longitute"))) {
                jsonObject.put("longitute", "placeholder_longitute");
            }

            // Check for "media_type"
            if (!jsonObject.has("media_type") || jsonObject.isNull("media_type") || jsonObject.get("media_type").equals("")) {
                jsonObject.put("media_type", "placeholder_media_type");
            }

            // Check for "illumination"
            if (!jsonObject.has("illumination") || jsonObject.isNull("illumination") || jsonObject.get("illumination").equals("")) {
                jsonObject.put("illumination", "placeholder_illumination");
            }

            // Check for "updated_at"
            if (!jsonObject.has("updated_at") || jsonObject.isNull("updated_at")) {
                jsonObject.put("updated_at", "placeholder_updated_at");
            }

            // Convert the modified JSON object back to a JSON string
            String jsonStringWithPlaceholders = jsonObject.toString();
            Log.d("tg11", jsonStringWithPlaceholders);

            return jsonStringWithPlaceholders;
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }

    public String encodeJsonToUrl(String jsonstring) {
        try {
            // Parse the JSON string into a map
            Map<String, String> jsonMap = new HashMap<>();

            // Parse the provided JSON string into a map using a JSON library.
            // Assuming jsonstring is a valid JSON object, you can parse it into a map.

            // Example of parsing JSON using the Gson library:
            Gson gson = new Gson();
            jsonMap = gson.fromJson(jsonstring, new TypeToken<Map<String, String>>() {}.getType());

            // Create a StringBuilder to build the URL-encoded string
            StringBuilder encodedParams = new StringBuilder();

            for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                // Encode each key and value and append to the URL-encoded string
                encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                encodedParams.append("=");
                encodedParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                encodedParams.append("&");
            }

            // Remove the trailing '&' character
            if (encodedParams.length() > 0) {
                encodedParams.setLength(encodedParams.length() - 1);
            }

            // Return the URL-encoded string
            return encodedParams.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }

    //delete
    public APIreferenceclass(JSONObject jsonPayload, int vendorclientorcampaign, String logintoken, Context ctxt) {
        int id= 0;
        String url= "";

        try {

            if (vendorclientorcampaign == 0) {//campaign
                id= jsonPayload.getInt("id");
                url="https://acme.warburttons.com/api/campaigns/";
                querytype= 0;

            } else if (vendorclientorcampaign == 1) {//client
                id= jsonPayload.getInt("id");
                url="https://acme.warburttons.com/api/clients/";
                querytype= 1;

            } else if (vendorclientorcampaign == 2) {//vendor
                id= jsonPayload.getInt("id");
                url="https://acme.warburttons.com/api/vendors/";
                querytype= 2;
            }

            url= url+Integer.toString(id);
            String jsonPayload1 = "{\"Authorization\": \"" + logintoken + "\"}";

            Log.d("tg94", jsonPayload1);

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("addbatest","Inside admin api");

            callapi4(headers, jsonPayload1, ctxt, querytype, url);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //delete site
    public APIreferenceclass(int siteid, String logintoken, Context ctxt, int i) {

        int id= 0;
        String url= "https://acme.warburttons.com/api/sites/"+ siteid;

            String jsonPayload1 = "{\"Authorization\": \"" + logintoken + "\"}";

            Log.d("tg94", jsonPayload1);

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "application/json");
            querytype= 2;

            callapi4(headers, jsonPayload1, ctxt, querytype, url);
    }

    //edit campaign. EditCampaign class.
    public APIreferenceclass(JSONObject jsonPayload1, Context context, String logintoken, Uri selectedImage, int campaignId) {

        String url = "https://acme.warburttons.com/api/campaigns";
        //here change to put
        querytype = 2; // PUT

        String jsonString= fixjsonstring(jsonPayload1.toString());

        String urlEncodedParams= encodeJsonToUrl(jsonString);
        url= url+"/"+ campaignId+ "?"+ urlEncodedParams;

        Log.d("tg92", jsonPayload1.toString());

        if (selectedImage != null) {
            String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";

            // Modify headers for multipart request
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "multipart/form-data; boundary=" + boundary);

            Log.d("yes", "yes");

            int querytype= 2;

            // Call the API with multipart data
            callapi2(headers, multipart(context, selectedImage, jsonString), context, querytype, url);

        } else {
            // Existing JSON payload handling
            String jsonPayload = jsonPayload1.toString();
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "application/json");

            callapi(headers, jsonPayload, context, 2, url); // Using POST method
        }
    }


    //edit campaign. to fetch site data
    public APIreferenceclass(String logintoken, int id, Context context) {
            Log.d("tag21", "1");

            String url = "https://acme.warburttons.com/api/campaigns/";
            url= url+ id;
            querytype = 0;
            String jsonPayload = "{\"Authorization\": \"" + logintoken + "\"}";

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("addbatest", "Inside admin api");

            callapi(headers, jsonPayload, context, querytype, url);
        }


    public void callapi(Map<String, String> headers, String jsonPayload, Context context, int querytype, String url) {
        try {
            //adding api here
            Log.d("tag21","2");

            CronetEngine.Builder builder = new CronetEngine.Builder(context);
            CronetEngine cronetEngine = builder.build();

            // Create a JSON payload with the email and password
            //String jsonPayload = "{\"email\":\"" + email + "\",\"password\":\"" + pass + "\"}";

            // Convert the JSON payload to bytes for uploading
            Log.d("tag21",jsonPayload);
            final byte[] postData = jsonPayload.getBytes(StandardCharsets.UTF_8);

            // Create an upload data provider to send the POST data
            UploadDataProvider uploadDataProvider = new UploadDataProvider() {
                @Override
                public long getLength() throws IOException {
                    return postData.length;
                }

                @Override
                public void read(UploadDataSink uploadDataSink, ByteBuffer byteBuffer) throws IOException {
                    byteBuffer.put(postData);
                    uploadDataSink.onReadSucceeded(false);
                }

                @Override
                public void rewind(UploadDataSink uploadDataSink) throws IOException {
                    uploadDataSink.onRewindSucceeded();
                }

                @Override
                public void close() throws IOException {
                    // No-op
                }
            };

            UrlRequest.Builder requestBuilder;

            if(querytype==0) {Log.d("tag21","3");
                requestBuilder = cronetEngine.newUrlRequestBuilder(
                                url, new MyUrlRequestCallback((ApiInterface) context), cronetExecutor)
                        .setHttpMethod("GET")  // Set the method to POST
                        .addHeader("Content-Type", "application/json")  // Indicate we're sending JSON data
                        .setUploadDataProvider(uploadDataProvider, cronetExecutor);  // Attach the payload

                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }

            }else if (querytype==1){
                requestBuilder = cronetEngine.newUrlRequestBuilder(
                                url, new MyUrlRequestCallback((ApiInterface) context), cronetExecutor)
                        .setHttpMethod("POST")  // Set the method to POST
                        .addHeader("Content-Type", "application/json")  // Indicate we're sending JSON data
                        .setUploadDataProvider(uploadDataProvider, cronetExecutor);  // Attach the payload

                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }

            }else if(querytype== 2) {
                requestBuilder = cronetEngine.newUrlRequestBuilder(
                                url, new MyUrlRequestCallback((ApiInterface) context), cronetExecutor)
                        .setHttpMethod("PUT")  // Set the method to PUT
                        .addHeader("Content-Type", "application/json")  // Indicate we're sending JSON data
                        .setUploadDataProvider(uploadDataProvider, cronetExecutor);  // Attach the payload

                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }
            }

            else {
                    requestBuilder = cronetEngine.newUrlRequestBuilder(
                                    url, new MyUrlRequestCallback((ApiInterface) context), cronetExecutor)
                            .setHttpMethod("POST")  // Set the method to POST
                            .addHeader("Content-Type", "application/json")  // Indicate we're sending JSON data
                            .setUploadDataProvider(uploadDataProvider, cronetExecutor);  // Attach the payload

                    for (Map.Entry<String, String> header : headers.entrySet()) {
                        requestBuilder.addHeader(header.getKey(), header.getValue());
                    }
                }
            UrlRequest request = requestBuilder.build();
            request.start();Log.d("tag21","4");
        } catch (Exception e) {
            Log.d("tag21", e.toString());
        }
    }

    public void callapi1(Map<String, String> headers, String jsonPayload, Context context, String url) {
        try {
            //adding api here
            Log.d("tag21","2");
            Log.d("tag58","Inside callapi1 1");

            CronetEngine.Builder builder = new CronetEngine.Builder(context);
            CronetEngine cronetEngine = builder.build();
            Log.d("tag58","Inside callapi1 2");

            // Create a JSON payload with the email and password
            //String jsonPayload = "{\"email\":\"" + email + "\",\"password\":\"" + pass + "\"}";

            // Convert the JSON payload to bytes for uploading
            Log.d("tag21",jsonPayload);
            final byte[] postData = jsonPayload.getBytes(StandardCharsets.UTF_8);

            Log.d("tag58","Inside callapi1 3");

            // Create an upload data provider to send the POST data
            UploadDataProvider uploadDataProvider = new UploadDataProvider() {
                @Override
                public long getLength() throws IOException {
                    return postData.length;
                }

                @Override
                public void read(UploadDataSink uploadDataSink, ByteBuffer byteBuffer) throws IOException {
                    byteBuffer.put(postData);
                    uploadDataSink.onReadSucceeded(false);
                }

                @Override
                public void rewind(UploadDataSink uploadDataSink) throws IOException {
                    uploadDataSink.onRewindSucceeded();
                }

                @Override
                public void close() throws IOException {
                    // No-op
                }
            };
            Log.d("tag58","Inside callapi1 4");

            UrlRequest.Builder requestBuilder;

            Log.d("tag58","Inside callapi1 5");

                requestBuilder = cronetEngine.newUrlRequestBuilder(
                                url, new MyUrlRequestCallback((ApiInterface) context), cronetExecutor)
                        .setHttpMethod("GET")
                        .addHeader("Content-Type", "application/json")  // Indicate we're sending JSON data
                        .setUploadDataProvider(uploadDataProvider, cronetExecutor);  // Attach the payload

                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }
            Log.d("tag58","Inside callapi1 6");

            UrlRequest request = requestBuilder.build();
            Log.d("tag58","Inside callapi1 7");

            request.start();Log.d("tag21","4");
            Log.d("tag58","Inside callapi1 8");
        } catch (Exception e) {
            Log.d("tag21", e.toString());
        }
    }

    //to upload image in addsitedetails. for edit. and campaign add
    public void callapi2(Map<String, String> headers, final byte[] multipartBody, Context context, int queryType, String url) {
        Log.d("tg92", Integer.toString(queryType)+ " "+ url);

        try {
            CronetEngine cronetEngine = new CronetEngine.Builder(context).build();

            UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                    url,
                    new MyUrlRequestCallback((ApiInterface) context),
                    Executors.newSingleThreadExecutor()
            );

            Log.d("tg23", Integer.toString(queryType));

            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        if(queryType== 2) {
            Log.d("tg92", "put");
            requestBuilder.setHttpMethod("PUT");
        }else{
            requestBuilder.setHttpMethod("POST");
        }
            requestBuilder.setUploadDataProvider(new UploadDataProvider() {

                @Override
                public long getLength() {
                    return multipartBody.length;
                }

                private int position = 0; // Position in multipartBody

                @Override
                public void read(UploadDataSink uploadDataSink, ByteBuffer byteBuffer) {
                    while (byteBuffer.hasRemaining() && position < multipartBody.length) {
                        int length = Math.min(byteBuffer.remaining(), multipartBody.length - position);
                        byteBuffer.put(multipartBody, position, length);
                        position += length;
                    }

                    if (position == multipartBody.length) {
                        // All data has been written to the buffer, no more data to send
                        uploadDataSink.onReadSucceeded(false);
                    } else {
                        // Buffer is full but there is still data left, Cronet will call read() again
                        uploadDataSink.onReadSucceeded(false);
                    }
                }

                @Override
                public void rewind(UploadDataSink uploadDataSink) {
                    position = 0; // Reset the position for rewinding
                    uploadDataSink.onRewindSucceeded();
                }

                @Override
                public void close() throws IOException {
                    // No-op
                }
            }, Executors.newSingleThreadExecutor());

            UrlRequest request = requestBuilder.build();
            request.start();

        } catch (Exception e) {
            Log.e("APIreferenceclass", "Error in callapi2", e);
        }
    }

    //to upload image in addcampaign. POST
    public void callapi3(Map<String, String> headers, final byte[] multipartBody, Context context, int queryType, String url) {
        Log.d("tg92", Integer.toString(queryType)+ " "+ url);

        try {
            CronetEngine cronetEngine = new CronetEngine.Builder(context).build();

            UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                    url,
                    new MyUrlRequestCallback((ApiInterface) context),
                    Executors.newSingleThreadExecutor()
            );

            Log.d("tg23", Integer.toString(queryType));

            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        if(querytype==2) {
            Log.d("tg92", "put");
            requestBuilder.setHttpMethod("PUT");
        }else{
            requestBuilder.setHttpMethod("POST");
        }
            requestBuilder.setUploadDataProvider(new UploadDataProvider() {

                @Override
                public long getLength() {
                    return multipartBody.length;
                }

                private int position = 0; // Position in multipartBody

                @Override
                public void read(UploadDataSink uploadDataSink, ByteBuffer byteBuffer) {
                    while (byteBuffer.hasRemaining() && position < multipartBody.length) {
                        int length = Math.min(byteBuffer.remaining(), multipartBody.length - position);
                        byteBuffer.put(multipartBody, position, length);
                        position += length;
                    }

                    if (position == multipartBody.length) {
                        // All data has been written to the buffer, no more data to send
                        uploadDataSink.onReadSucceeded(false);
                    } else {
                        // Buffer is full but there is still data left, Cronet will call read() again
                        uploadDataSink.onReadSucceeded(false);
                    }
                }

                @Override
                public void rewind(UploadDataSink uploadDataSink) {
                    position = 0; // Reset the position for rewinding
                    uploadDataSink.onRewindSucceeded();
                }

                @Override
                public void close() throws IOException {
                    // No-op
                }
            }, Executors.newSingleThreadExecutor());

            UrlRequest request = requestBuilder.build();
            request.start();

        } catch (Exception e) {
            Log.e("APIreferenceclass", "Error in callapi2", e);
        }
    }

    //DELETE
    public void callapi4(Map<String, String> headers, String jsonPayload, Context context, int querytype, String url) {

        try {
            //adding api here
            Log.d("tag21","2");

            CronetEngine.Builder builder = new CronetEngine.Builder(context);
            CronetEngine cronetEngine = builder.build();

            // Create a JSON payload with the email and password
            //String jsonPayload = "{\"email\":\"" + email + "\",\"password\":\"" + pass + "\"}";

            // Convert the JSON payload to bytes for uploading
            Log.d("tag21",jsonPayload);
            final byte[] postData = jsonPayload.getBytes(StandardCharsets.UTF_8);


            // Create an upload data provider to send the POST data
            UploadDataProvider uploadDataProvider = new UploadDataProvider() {
                @Override
                public long getLength() throws IOException {
                    return postData.length;
                }

                @Override
                public void read(UploadDataSink uploadDataSink, ByteBuffer byteBuffer) throws IOException {
                    byteBuffer.put(postData);
                    uploadDataSink.onReadSucceeded(false);
                }

                @Override
                public void rewind(UploadDataSink uploadDataSink) throws IOException {
                    uploadDataSink.onRewindSucceeded();
                }

                @Override
                public void close() throws IOException {
                    // No-op
                }
            };

                UrlRequest.Builder requestBuilder;
                requestBuilder = cronetEngine.newUrlRequestBuilder(
                                url, new MyUrlRequestCallback((ApiInterface) context), cronetExecutor)
                        .setHttpMethod("DELETE")
                        .addHeader("Content-Type", "application/json")  // Indicate we're sending JSON data
                        .setUploadDataProvider(uploadDataProvider, cronetExecutor);  // Attach the payload

                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
            }

                UrlRequest request = requestBuilder.build();
                request.start();Log.d("tag21","4");
        } catch (Exception e) {
            Log.d("tag21", e.toString());
        }
    }
}