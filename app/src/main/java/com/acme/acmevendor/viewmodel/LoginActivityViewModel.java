package com.acme.acmevendor.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.acme.acmevendor.api.CampaignService;
import com.acme.acmevendor.models.SendOtpResponseModel;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

        CronetEngine.Builder builder= new CronetEngine.Builder(context);
        CronetEngine cronetEngine= builder.build();

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                "https://acme.warburttons.com/api/login", new MyUrlRequestCallback(), cronetExecutor);

        UrlRequest request = requestBuilder.build();
        request.start();


        /*
        CampaignService.Creator.getInstance().loginUser(email, pass).enqueue(new Callback<SendOtpResponseModel>() {
            @Override
            public void onResponse(Call<SendOtpResponseModel> call, Response<SendOtpResponseModel> response) {
                if (response.isSuccessful()) {
                    successresponse.setValue(response.body());
                } else {
                    errorMessage.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<SendOtpResponseModel> call, Throwable t) {
                errorMessage.setValue(t.toString());
            }
        });
    */}

    class MyUrlRequestCallback extends UrlRequest.Callback {
        private static final String TAG = "MyUrlRequestCallback";

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

        @Override
        public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) {
            Log.i(TAG, "onReadCompleted method called.");
            // You should keep reading the request until there's no more data.
            byteBuffer.clear();
            request.read(byteBuffer);
        }

        @Override
        public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
            Log.i(TAG, "onSucceeded method called.");

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
