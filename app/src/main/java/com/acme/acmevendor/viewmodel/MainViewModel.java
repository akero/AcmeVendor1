package com.acme.campaignproject.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewModelScope;
import com.acme.campaignproject.models.SendOtpModel;
import com.acme.campaignproject.repository.CampaignRepository;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.launch;
import java.lang.Exception;

public class MainViewModel extends ViewModel {
    private CampaignRepository repository;
    private Job viewModelJob = new Job();
    private CoroutineScope viewModelScope = new CoroutineScope(Dispatchers.IO + viewModelJob);

    public MainViewModel(CampaignRepository repository) {
        this.repository = repository;
    }

    public void callOtp(SendOtpModel sendOtpModel) {
        viewModelScope.launch(new Runnable() {
            @Override
            public void run() {
                try {
                    SendOtpModel otpResponse = repository.sendOtp(sendOtpModel.getCountry_code(), sendOtpModel.getMobile());
                } catch (Exception e) {
                    // Handle exception
                }
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        viewModelJob.cancel();
    }
}
