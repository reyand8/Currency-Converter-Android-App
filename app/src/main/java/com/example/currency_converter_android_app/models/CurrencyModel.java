package com.example.currency_converter_android_app.models;


import com.google.gson.annotations.SerializedName;

public class CurrencyModel {

    @SerializedName("result")
    private String result;
    @SerializedName("documentation")
    private String documentation;
    @SerializedName("time_last_update_utc")
    private String timeLastUpdateUtc;
    @SerializedName("base_code")
    private String baseCode;
    @SerializedName("target_code")
    private String targetCode;
    @SerializedName("conversion_rate")
    private double conversionRate;
    @SerializedName("conversion_result")
    private double conversionResult;

    public String getResult() {
        return result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getTimeLastUpdateUtc() {
        return timeLastUpdateUtc;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public double getConversionResult() {
        return conversionResult;
    }
}