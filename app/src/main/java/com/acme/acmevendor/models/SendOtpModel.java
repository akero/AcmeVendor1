package com.acme.acmevendor.models;

public class SendOtpModel {
    private String county_code;
    private String mobile;

    public SendOtpModel(String county_code, String mobile) {
        this.county_code = county_code;
        this.mobile = mobile;
    }

    public String getCounty_code() {
        return county_code;
    }

    public void setCounty_code(String county_code) {
        this.county_code = county_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    // Optional: Override toString for a better representation if required.
    @Override
    public String toString() {
        return "SendOtpModel{" +
                "county_code='" + county_code + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
