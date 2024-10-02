package com.example.currency_converter_android_app.models;

public class RateModel {
    private String code;
    private String fullName;
    private Double rate;

    public RateModel(String code, String fullName, Double rate) {
        this.code = code;
        this.fullName = fullName;
        this.rate = rate;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public Double getRate() {
        return rate;
    }
}