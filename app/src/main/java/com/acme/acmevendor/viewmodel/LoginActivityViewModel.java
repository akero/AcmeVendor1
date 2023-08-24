package com.acme.acmevendor.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.acme.acmevendor.api.CampaignService;
import com.acme.acmevendor.models.SendOtpResponseModel;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UploadDataProvider;
import org.chromium.net.UploadDataSink;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.zip.GZIPOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends ViewModel {

    private MutableLiveData<SendOtpResponseModel> successresponse = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<SendOtpResponseModel> getSuccessresponse() {
        return successresponse;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    Executor cronetExecutor= Executors.newSingleThreadExecutor();

    public void callLogin(String email, String pass, Context context) {
        //TODO REMOVE
        email= "ven@gmail.com";
        pass = "123456";
        //TODO

        CronetEngine.Builder builder = new CronetEngine.Builder(context);
        CronetEngine cronetEngine = builder.build();

        // Create a JSON payload with the email and password
        String jsonPayload = "{\"email\":\"" + email + "\",\"password\":\"" + pass + "\"}";

        // Convert the JSON payload to bytes for uploading
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

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                        "https://acme.warburttons.com/api/login", new MyUrlRequestCallback((ApiInterface) context), cronetExecutor)
                .setHttpMethod("POST")  // Set the method to POST
                .addHeader("Content-Type", "application/json")  // Indicate we're sending JSON data
                .setUploadDataProvider(uploadDataProvider, cronetExecutor);  // Attach the payload

        UrlRequest request = requestBuilder.build();
        request.start();

        }

    class MyUrlRequestCallback extends UrlRequest.Callback {
        private static final String TAG = "MyUrlRequestCallback";
        ApiInterface ai;

        public MyUrlRequestCallback(ApiInterface logincontext){
            this.ai= logincontext;

        }

        @Override
        public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) {
            Log.i(TAG, "onRedirectReceived method called.");
            // You should call the request.followRedirect() method to continue
            // processing the request.
            request.followRedirect();
        }

        @Override
        public void onResponseStarted(UrlRequest request, UrlResponseInfo info) {
            Log.i(TAG, "onResponseStarted method called.");
            // You should call the request.read() method before the request can be
            // further processed. The following instruction provides a ByteBuffer object
            // with a capacity of 102400 bytes for the read() method. The same buffer
            // with data is passed to the onReadCompleted() method.
            int httpStatusCode = info.getHttpStatusCode();
            if (httpStatusCode == 200) {
                request.read(ByteBuffer.allocateDirect(102400));
            }else
            {
                request.read(ByteBuffer.allocateDirect(102400));
            }
        }


        ;
        private final ByteArrayOutputStream outputByte= new ByteArrayOutputStream();

        @Override
        public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) {
            Log.i(TAG, "onReadCompleted method called.");

            byteBuffer.flip();
            try {
                byte[] bytes = new byte[byteBuffer.remaining()];
                byteBuffer.get(bytes);  // Ensure you get the bytes from the ByteBuffer
                outputByte.write(bytes, 0, bytes.length);

                byteBuffer.clear();
                request.read(byteBuffer);
            } catch (Exception e) {
                Log.d("tag4", e.toString());
            }
        }


        @Override
        public void onSucceeded(UrlRequest request, UrlResponseInfo info) {

            String responseBody = new String(outputByte.toByteArray(), StandardCharsets.UTF_8);
            Log.d("ResponseBody", responseBody);

            // If you also want the UrlResponseInfo as a string:
            String responseInfoString = urlresponseinfotostring(info);

            //sending to interface
            ai.onResponseReceived(responseBody);


            Log.d("tag4", responseBody+"------INFO______"+responseInfoString);

        }

        public String urlresponseinfotostring(UrlResponseInfo info){
            String ret="";
            StringBuilder sb = new StringBuilder();

            // Append URL chain
            sb.append("URL Chain: ").append(info.getUrlChain().toString()).append("\n");

            // Append HTTP status code
            sb.append("HTTP Status: ").append(info.getHttpStatusCode()).append("\n");

            // Append headers
            sb.append("Headers:\n");
            for (Map.Entry<String, List<String>> header : info.getAllHeaders().entrySet()) {
                for (String value : header.getValue()) {
                    sb.append(header.getKey()).append(": ").append(value).append("\n");
                }


            }

            // Append other details
            sb.append("Was Cached: ").append(info.wasCached()).append("\n");
            sb.append("Negotiated Protocol: ").append(info.getNegotiatedProtocol()).append("\n");
            sb.append("Proxy Server: ").append(info.getProxyServer()).append("\n");
            sb.append("Received Byte Count: ").append(info.getReceivedByteCount()).append("\n");
            ret=sb.toString();

            return ret;
        }

        @Override
        public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
            // The request has failed. If possible, handle the error.
            Log.e(TAG, "The request failed.", error);
        }

        @Override
        public void onCanceled(UrlRequest request, UrlResponseInfo info) {
            // Free resources allocated to process this request.

        }
    }
}
