package com.acme.campaignproject.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.acme.campaignproject.api.CampaignService;
import com.acme.campaignproject.models.SendOtpResponseModel;
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

    public void callLogin(String countrycode, String mobile) {
        CampaignService.getInstance().loginUser(countrycode, mobile).enqueue(new Callback<SendOtpResponseModel>() {
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
    }
}
