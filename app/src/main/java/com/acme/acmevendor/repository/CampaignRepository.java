package com.acme.campaignproject.repository

import androidx.lifecycle.MutableLiveData
import com.acme.campaignproject.api.CampaignService
import com.acme.campaignproject.models.SendOtpResponseModel
import retrofit2.Callback
import retrofit2.Response

class CampaignRepository(private val campaignService: CampaignService) {

    val sendotpLiveData = MutableLiveData<SendOtpResponseModel>()

    fun sendOtp(countrycode: String, mobile: String) {
        campaignService.sendOtp(countrycode, mobile).enqueue(object: Callback<SendOtpResponseModel> {
            override fun onResponse(
                    call: retrofit2.Call<SendOtpResponseModel>,
            response: Response<SendOtpResponseModel>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    sendotpLiveData.postValue(response.body())
                } else {
                    // Handle the error case. Maybe post a different kind of data to the LiveData.
                }
            }

            override fun onFailure(call: retrofit2.Call<SendOtpResponseModel>, t: Throwable) {
                // Handle the failure case. Maybe post a different kind of data to the LiveData.
            }
        })
    }
}
