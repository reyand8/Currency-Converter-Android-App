package com.example.currency_converter_android_app.models;

import java.util.ArrayList;

public class CodeModel {
    private String result;
    private String documentation;
    private String terms_of_use;
    private ArrayList<ArrayList<String>> supported_codes;

    // Getter для result
    public String getResult() {
        return result;
    }

    // Getter для documentation
    public String getDocumentation() {
        return documentation;
    }

    // Getter для terms_of_use
    public String getTermsOfUse() {
        return terms_of_use;
    }

    // Getter для supported_codes
    public ArrayList<ArrayList<String>> getSupportedCodes() {
        return supported_codes;
    }
}
