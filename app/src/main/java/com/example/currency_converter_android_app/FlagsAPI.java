package com.example.currency_converter_android_app;

import com.example.currency_converter_android_app.models.FlagModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FlagsAPI {

    @GET("currency/{code}")
    Call<List<FlagModel>> getFlag(
            @Path("code") String currCode
    );
}