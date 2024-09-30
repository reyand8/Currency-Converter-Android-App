package com.example.currency_converter_android_app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitCurrenciesClient {
    private static Retrofit retrofit;
    private static final String API_KEY = "YOUR_KEY";

    private static final String BASE_URL =
            "https://v6.exchangerate-api.com/v6/" + API_KEY;
    public static Retrofit getRetrofitInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
