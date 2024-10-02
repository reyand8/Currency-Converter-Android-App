package com.example.currency_converter_android_app;

import com.example.currency_converter_android_app.models.CodeModel;
import com.example.currency_converter_android_app.models.CurrencyModel;
import com.example.currency_converter_android_app.models.LatestRatesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrenciesApi {

    @GET("pair/{from}/{to}/{amount}")
    Call<CurrencyModel> getPair(
            @Path("from") String fromCurrency,
            @Path("to") String toCurrency,
            @Path("amount") String amount
    );

    @GET("latest/{code}")
    Call<LatestRatesModel> getLatest(
            @Path("code") String mainCurrency
    );

    @GET("codes")
    Call<CodeModel> getCodes();
}