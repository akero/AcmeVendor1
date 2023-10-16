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
    //campaign is 0, client is 1, vendor is 2
    public APIreferenceclass(int vendorclientorcampaign, String logintoken, Context context) {

        //campaign
        if (vendorclientorcampaign == 0) {

            Log.d("tag21","1");

            String url="https://acme.warburttons.com/api/get_campaign_sites/18";
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

            String url="https://acme.warburttons.com/api/get_vendor_campaigns";

            querytype=0; //GET
            String jsonPayload="{\"Authorization\":\"" +logintoken+"\"}";

            Map<String, String> headers= new HashMap<>();
            headers.put("Authorization", "Bearer "+logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("vdbatest","Inside apirefapiclass");


            callapi(headers, jsonPayload, context, querytype, url);

      }
    }

    //login
    public APIreferenceclass(int vendorclientorcampaign, Context context, String email) {

        //TODO make api call now
//campaign
        if (vendorclientorcampaign == 0) {

            Log.d("tag21","1");

            String url="https://acme.warburttons.com/api/get_campaign_sites/18";
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

            String url="https://acme.warburttons.com/api/get_vendor_campaigns";

            querytype=0; //GET
            String jsonPayload="{\"Authorization\":\"" +logintoken+"\"}";

            Map<String, String> headers= new HashMap<>();
            headers.put("Authorization", "Bearer "+logintoken);
            headers.put("Content-Type", "application/json");

            Log.d("vdbatest","Inside apirefapiclass");


            callapi(headers, jsonPayload, context, querytype, url);

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

        String url="https://acme.warburttons.com/api/get_site_by_id/18";

        String jsonPayload = "{\"Authorization\": \"" + loginToken +"\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + loginToken);
        headers.put("Content-Type", "application/json");

        Log.d("tag41","Inside viewsitedetail api");


        callapi1(headers, jsonPayload, context, url);

    }


    public APIreferenceclass(String logintoken, Context context) {
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
                        .setHttpMethod("GET")
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