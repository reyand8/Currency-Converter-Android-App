package com.example.currency_converter_android_app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitCurrenciesClient {
    private static Retrofit currenciesRetrofit;
    private static Retrofit flagsRetrofit;

    private static final String CURRENCY_API_KEY = "YOUR KEY/";

    private static final String CURRENCY_BASE_URL =
            "https://v6.exchangerate-api.com/v6/" + CURRENCY_API_KEY;

    private static final String FLAG_BASE_URL = "https://restcountries.com/v3.1/";


    public static Retrofit getCurrenciesRetrofitInstance() {
        if (currenciesRetrofit == null) {
            currenciesRetrofit = new Retrofit.Builder()
                    .baseUrl(CURRENCY_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return currenciesRetrofit;
    }

    public static Retrofit getFlagsRetrofitInstance() {
        if (flagsRetrofit == null) {
            flagsRetrofit = new Retrofit.Builder()
                    .baseUrl(FLAG_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return flagsRetrofit;
    }
}
