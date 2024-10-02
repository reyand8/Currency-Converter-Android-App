package com.example.currency_converter_android_app.models;

import java.util.ArrayList;

public class CodeModel {
    private String result;
    private String documentation;
    private String terms_of_use;
    private ArrayList<ArrayList<String>> supported_codes;

    public String getResult() {
        return result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getTermsOfUse() {
        return terms_of_use;
    }

    public ArrayList<ArrayList<String>> getSupportedCodes() {
        return supported_codes;
    }
}
