package com.acme.acmevendor.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.acme.acmevendor.api.MyUrlRequestCallback;
import com.acme.acmevendor.models.SendOtpResponseModel;
import com.google.android.gms.common.api.Api;

import org.chromium.net.CronetEngine;
import org.chromium.net.UploadDataProvider;
import org.chromium.net.UploadDataSink;
import org.chromium.net.UrlRequest;
import org.json.JSONObject;

import java.io.IOException;
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

    int querytype=0; //0 for get, 1 for post

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

            String url="https://acme.warburttons.com/api/get_client_campaigns";
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


        String url="https://acme.warburttons.com/api/login";
        //Log.d("tag23", "logintype "+ loginType + " email " + email);

        String jsonPayload = jsonPayload1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + logintoken);
        headers.put("Content-Type", "application/json");

        Log.d("tag58","Inside viewsitedetail api");

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

            }else {
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