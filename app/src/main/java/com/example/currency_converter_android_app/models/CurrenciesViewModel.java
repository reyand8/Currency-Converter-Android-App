package com.example.currency_converter_android_app.models;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.currency_converter_android_app.CurrenciesApi;
import com.example.currency_converter_android_app.FlagsAPI;
import com.example.currency_converter_android_app.RetrofitCurrenciesClient;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesViewModel extends ViewModel {

    private MutableLiveData<CurrencyModel> currencyLiveData;
    private MutableLiveData<LatestRatesModel> currencyLatestLiveData;
    private MutableLiveData<ArrayList<ArrayList<String>>> currencyCodesLiveData;

    private MutableLiveData<String> flagUrlFromLiveData;
    private MutableLiveData<String> flagUrlToLiveData;

    private CurrenciesApi currenciesApi;
    private FlagsAPI flagsApi;

    public CurrenciesViewModel() {
        currencyLiveData = new MutableLiveData<>();
        currencyLatestLiveData = new MutableLiveData<>();
        currencyCodesLiveData = new MutableLiveData<>();

        flagUrlFromLiveData = new MutableLiveData<>();
        flagUrlToLiveData = new MutableLiveData<>();

        currenciesApi = RetrofitCurrenciesClient.getCurrenciesRetrofitInstance().create(CurrenciesApi.class);
        flagsApi = RetrofitCurrenciesClient.getFlagsRetrofitInstance().create(FlagsAPI.class);
    }

    public void fetchCurrencyPair(String fromCurrency, String toCurrency, String amount) {
        Call<CurrencyModel> call = currenciesApi.getPair(fromCurrency, toCurrency, amount);
        call.enqueue(new Callback<CurrencyModel>() {
            @Override
            public void onResponse(Call<CurrencyModel> call, Response<CurrencyModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CurrencyModel currencyModel = response.body();
                    currencyLiveData.setValue(currencyModel);
                } else {
                    Log.e("Response error: ", response.message());
                }
            }
            @Override
            public void onFailure(Call<CurrencyModel> call, Throwable t) {
                currencyLiveData.setValue(null);
                Log.e("Failure: ", t.getMessage());
            }
        });
    }

    public void fetchCurrencyLatest(String mainCurrency) {
        Call<LatestRatesModel> call = currenciesApi.getLatest(mainCurrency);
        call.enqueue(new Callback<LatestRatesModel>() {
            @Override
            public void onResponse(Call<LatestRatesModel> call, Response<LatestRatesModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LatestRatesModel latestRatesModel = response.body();
                    currencyLatestLiveData.setValue(latestRatesModel);
                } else {
                    Log.e("Response error: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<LatestRatesModel> call, Throwable t) {
                currencyLatestLiveData.setValue(null);
                Log.e("Failure: ", t.getMessage());
            }
        });
    }

    public void fetchCurrencyCodes() {
        Call<CodeModel> call = currenciesApi.getCodes();
        call.enqueue(new Callback<CodeModel>() {
            @Override
            public void onResponse(Call<CodeModel> call, Response<CodeModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<ArrayList<String>> codesList = response.body().getSupportedCodes();
                    currencyCodesLiveData.setValue(codesList);
                } else {
                    Log.e("Response error: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<CodeModel> call, Throwable t) {
                currencyCodesLiveData.setValue(null);
                Log.e("Failure: ", t.getMessage());
            }
        });
    }

    public void fetchFlagByCurrencyCode(String convertType, String currencyCode) {
        Call<List<FlagModel>> call = flagsApi.getFlag(currencyCode);
        call.enqueue(new Callback<List<FlagModel>>() {
            @Override
            public void onResponse(Call<List<FlagModel>> call, Response<List<FlagModel>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<FlagModel> flags = response.body();
                    String flagPngUrl = flags.get(0).getFlags().getPng();
                    if ("from".equals(convertType)) {
                        flagUrlFromLiveData.setValue(flagPngUrl);
                    } else if ("to".equals(convertType)) {
                        flagUrlToLiveData.setValue(flagPngUrl);
                    }
                } else {
                    if ("from".equals(convertType)) {
                        flagUrlFromLiveData.setValue(null);
                    } else {
                        flagUrlToLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FlagModel>> call, Throwable t) {
                if ("from".equals(convertType)) {
                    flagUrlFromLiveData.setValue(null);
                } else {
                    flagUrlToLiveData.setValue(null);
                }
                Log.e("Failure: ", t.getMessage());
            }
        });
    }


    public MutableLiveData<CurrencyModel> getCurrencyLiveData() {
        return currencyLiveData;
    }

    public MutableLiveData<LatestRatesModel> getCurrencyLatestLiveData() {
        return currencyLatestLiveData;
    }

    public MutableLiveData<ArrayList<ArrayList<String>>> getCurrencyCodesLiveData() {
        return currencyCodesLiveData;
    }

    public MutableLiveData<String> getFlagUrlFromLiveData() {
        return flagUrlFromLiveData;
    }

    public MutableLiveData<String> getFlagUrlToLiveData() {
        return flagUrlToLiveData;
    }
}