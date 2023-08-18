package com.acme.acmevendor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.acme.acmevendor.api.CampaignService;
import com.acme.acmevendor.models.SendOtpResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivityViewModel extends ViewModel {
    public MutableLiveData<SendOtpResponseModel> successresponse = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public void callOtp(String countrycode, String mobile) {
        CampaignService.Creator.getInstance().sendOtp(countrycode, mobile).enqueue(new Callback<SendOtpResponseModel>() {
            @Override
            public void onResponse(Call<SendOtpResponseModel> call, Response<SendOtpResponseModel> response) {
                if (response.isSuccessful()) {
                    successresponse.setValue(response.body());
                } else {
                    errorMessage.setValue(response.message());
                }
            }


            public void onSuccess(Call<SendOtpResponseModel> call, Throwable t){
                errorMessage.setValue(t.toString());
            }

            @Override
            public void onFailure(Call<SendOtpResponseModel> call, Throwable t) {
                errorMessage.setValue(t.toString());
            }
        });
    }
}
