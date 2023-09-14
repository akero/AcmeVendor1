package com.acme.acmevendor.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.acme.acmevendor.api.CampaignService;
import com.acme.acmevendor.models.SendOtpResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivityViewModel extends ViewModel implements ApiInterface {
    public MutableLiveData<SendOtpResponseModel> successresponse = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public void callOtp(String countrycode, String mobile, Context context) {

        APIreferenceclass ap= new APIreferenceclass(countrycode, mobile, context);


    }

    @Override
    public void onResponseReceived(String response) {
        //if (response.isSuccessful()) {
           // successresponse.setValue(response.body());
        //} else {
         //   errorMessage.setValue(response.message());
        //}
    }
}
