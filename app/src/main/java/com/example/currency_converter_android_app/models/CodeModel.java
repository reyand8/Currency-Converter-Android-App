package com.example.currency_converter_android_app.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class CodeModel {

    @SerializedName("result")
    private String result;

    @SerializedName("documentation")
    private String documentation;

    @SerializedName("terms_of_use")
    private String termsOfUse;

    @SerializedName("supported_codes")
    private ArrayList<ArrayList<String>> supportedCodes;

    public String getResult() {
        return result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public ArrayList<ArrayList<String>> getSupportedCodes() {
        return supportedCodes;
    }
}
