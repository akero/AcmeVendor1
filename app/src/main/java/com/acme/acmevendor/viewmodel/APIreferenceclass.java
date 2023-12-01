package com.acme.acmevendor.viewmodel;

import android.content.Context;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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

    //constructors
    //for admin dashboard
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

    //for vendor flow first page
    public APIreferenceclass(String logintoken, Context context, int a) {
            Log.d("tag21","1");

            String url="https://acme.warburttons.com/api/get_vendor_campaigns";
            querytype=0;
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

        /*    String url="https://acme.warburttons.com/api/login";
            querytype=0;
            String jsonPayload = "{\"Authorization\": \"";

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " );
            headers.put("Content-Type", "application/json");

            Log.d("otptest","Inside otp api");


            callapi(headers, jsonPayload, context, querytype, url);*/
        }

        //admin
        else if (vendorclientorcampaign == 1) {

            String url="https://acme.warburttons.com/api/login";
            querytype= 0;


//TODO remove comments
/*

            String jsonPayload= "{\"Authorization\":\"" +logintoken+"\"}";

            Map<String, String> headers= new HashMap<>();
            headers.put("Authorization", "Bearer "+logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("cldbatest","jsonpayload is "+ jsonPayload);


            callapi(headers, jsonPayload, context, querytype, url); */
        }

        //vendor
        else if (vendorclientorcampaign == 2) {

            String url="https://acme.warburttons.com/api/login";

            /*
            querytype=0; //GET
            String jsonPayload="{\"Authorization\":\"" +logintoken+"\"}";

            Map<String, String> headers= new HashMap<>();
            headers.put("Authorization", "Bearer "+logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("vdbatest","Inside apirefapiclass");


            callapi(headers, jsonPayload, context, querytype, url); */

        }

    }



    public APIreferenceclass(String email, String pass, String logintoken, Context context) {
    }

    public APIreferenceclass(Context context) {
    }

  /*  public APIreferenceclass(String countrycode, String mobile, Context context){

        //TODO- change
        String url="https://acme.warburttons.com/api/register";
        querytype=1; //POST
        Map<String, String> headers= new HashMap<>();
        headers.put("Content-Type", "application/json");
    } */


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

    //for viewcampaignsites
    public APIreferenceclass(String loginToken, Context context, String id){

        //TODO add siteNumber to api callYou explained

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
        //Log.d("tag23", "logintype "+ loginType + " email " + email);

        String jsonPayload = jsonPayload1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");

        querytype= 1; ////post

        callapi(headers, jsonPayload, context, querytype ,url);

    }

    //addclientdetailactivity
    public APIreferenceclass(JSONObject jsonPayload1, Context context, String logintoken, int a, int b){

        //TODO add siteNumber to api call


        String url="https://acme.warburttons.com/api/clients";
        //Log.d("tag23", "logintype "+ loginType + " email " + email);

        String jsonPayload = jsonPayload1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");

        querytype= 1; ////post //TODO change to put (2)

        callapi(headers, jsonPayload, context, querytype ,url);

    }

    //addcampaignactivity
    public APIreferenceclass(JSONObject jsonPayload1, Context context, String logintoken, int a){

        //TODO add siteNumber to api call


        String url="https://acme.warburttons.com/api/campaigns";
        //Log.d("tag23", "logintype "+ loginType + " email " + email);

        String jsonPayload = jsonPayload1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside ad campaign api");

        querytype= 1; ////post

        callapi(headers, jsonPayload, context, querytype ,url);

    }

    //for otp
    public APIreferenceclass(int loginType, Context context, String email, String a){

        //TODO add siteNumber to api call


        String url="https://acme.warburttons.com/api/login";
        Log.d("tag23", "logintype "+ loginType + " email " + email);

        String jsonPayload = "{\"email\": \"" + email +"\"}";

        Map<String, String> headers = new HashMap<>();
        //headers.put("Authorization", "Bearer " + loginToken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");

        querytype= 1; ////post

        callapi(headers, jsonPayload, context, querytype ,url);

    }//for otp enter
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


    //addsitedetailactivity
    public APIreferenceclass(int queryType, Context context, String logintoken, String jsonString, String siteno) {



        Log.d("tag21","1");

        String url="https://acme.warburttons.com/api/sites/";
        jsonString= fixjsonstring(jsonString);

        String urlEncodedParams = encodeJsonToUrl(jsonString);



        url= url+siteno+"?"+urlEncodedParams;

        querytype= queryType;

        Log.d("tg9", "url "+ url + " querytype" +queryType+ "jsonstring"+ jsonString );


        String jsonPayload = "{\"Authorization\": \"" + logintoken +"\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("addbatest","Inside admin api");


        callapi(headers, jsonPayload, context, querytype, url);
    }

    String fixjsonstring(String originalJsonString) {
        try {
            // Create a JSON object from the original JSON string
            JSONObject jsonObject = new JSONObject(originalJsonString);

            Log.d("tg09", originalJsonString);

            // Assuming you are using org.json.JSONObject;

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

}