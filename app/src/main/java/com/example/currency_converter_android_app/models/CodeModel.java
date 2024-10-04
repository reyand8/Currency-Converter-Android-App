package com.example.currency_converter_android_app.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class CodeModel {

    @SerializedName("result")
    private String result;

    @SerializedName("supported_codes")
    private ArrayList<ArrayList<String>> supportedCodes;

    public String getResult() {
        return result;
    }

    public ArrayList<ArrayList<String>> getSupportedCodes() {
        return supportedCodes;
    }
}
