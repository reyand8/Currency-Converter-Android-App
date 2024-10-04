package com.example.currency_converter_android_app.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.currency_converter_android_app.R;
import com.example.currency_converter_android_app.adapters.CodeAdapter;
import com.example.currency_converter_android_app.adapters.RatesAdapter;
import com.example.currency_converter_android_app.databinding.FragmentRatesBinding;
import com.example.currency_converter_android_app.models.CurrenciesViewModel;
import com.example.currency_converter_android_app.models.RateModel;
import com.example.currency_converter_android_app.CurrencyCodeAndName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RatesFragment extends Fragment {

    private FragmentRatesBinding binding;
    private CurrenciesViewModel currenciesViewModel;
    private ArrayList<ArrayList<String>> currencyCodes;
    private RatesAdapter ratesAdapter;
    private List<String> ratesList = new ArrayList<>();
    private String currencyMain;
    private final List<String> baseCurrencies =
            new ArrayList<>(Arrays.asList("UAH", "USD", "PLN"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currenciesViewModel = new ViewModelProvider(this).get(CurrenciesViewModel.class);
        currenciesViewModel.fetchCurrencyCodes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRatesBinding.inflate(inflater, container, false);
        currencyMain = "EUR";
        updateCodes("Base");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getCurrencyCodes();
        selectCurrency();
        getRatesBtn();

        LinearLayout itemRate = binding.itemAdd.itemRate;
        itemRate.setOnClickListener(v -> showCurrencyPopup(v));
    }

    private void getCurrencyCodes() {
        currenciesViewModel.getCurrencyCodesLiveData().observe(getViewLifecycleOwner(), codes -> {
            if (codes != null && !codes.isEmpty()) {
                currencyCodes = codes;
                List<String> currencyList = new ArrayList<>();
                currencyList.add("EUR - Euro");
                for (List<String> code : currencyCodes) {
                    String currencyCode = code.get(0);
                    String currencyName = code.get(1);
                    currencyList.add(currencyCode + " - " + currencyName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item, currencyList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                binding.selectCurr.setAdapter(adapter);

                fetchCurrencyRates();
            } else {
                Log.e("CurrencyCodes", "Currency codes error");
            }
        });
    }

    private void selectCurrency() {
        binding.selectCurr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCurrency = parent.getItemAtPosition(position).toString();
                currencyMain = selectedCurrency.split(" - ")[0];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void getRatesBtn() {
        binding.btnGetRates.setOnClickListener(v -> {
            fetchCurrencyRates();
            updateCodes("Base");
        });
    }

    private void showCurrencyPopup(View anchor) {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_menu_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        RecyclerView recyclerView = popupView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<String> currencyList = getCurrencyList();

        CodeAdapter adapter = new CodeAdapter(currencyList, selectedCurrency -> {
            currencyMain = selectedCurrency.split(" - ")[0];
            baseCurrencies.add(currencyMain);
            fetchCurrencyRates();
            popupWindow.dismiss();
        });

        recyclerView.setAdapter(adapter);
        popupWindow.showAtLocation(anchor.getRootView(), Gravity.CENTER, 0, 0);
    }

    private void updateCodes(String codeType) {
        if (Objects.equals(codeType, "Base")) {
            TextView rateCodeBase = binding.itemBase.rateCodeBase;
            TextView rateFullNameBase = binding.itemBase.rateFullNameBase;
            String currencyName = CurrencyCodeAndName.findCurrencyName(currencyMain);
            rateCodeBase.setText(currencyMain);
            rateFullNameBase.setText(currencyName);
        }
    }

    private List<String> getCurrencyList() {
        List<String> currencyList = new ArrayList<>();
        currencyList.add("EUR - Euro");
        for (List<String> code : currencyCodes) {
            String currencyCode = code.get(0);
            String currencyName = code.get(1);
            currencyList.add(currencyCode + " - " + currencyName);
        }
        return currencyList;
    }

    private void fetchCurrencyRates() {
        currenciesViewModel.fetchCurrencyLatest(currencyMain);
        currenciesViewModel.getCurrencyLatestLiveData()
                .observe(getViewLifecycleOwner(), latestRatesModel -> {
            if (latestRatesModel != null) {
                Map<String, Double> rates = latestRatesModel.getConversionRates();
                List<RateModel> filteredRates = new ArrayList<>();

                for (Map.Entry<String, Double> entry : rates.entrySet()) {
                    String currencyCode = entry.getKey();
                    Double rateValue = entry.getValue();
                    String currencyName = CurrencyCodeAndName.findCurrencyName(currencyCode);
                    if (baseCurrencies.contains(currencyCode)) {
                        filteredRates.add(new RateModel(currencyCode, currencyName, rateValue));
                    }
                }

                ratesAdapter = new RatesAdapter(filteredRates, baseCurrencies);
                binding.recyclerView.setAdapter(ratesAdapter);
            } else {
                Log.e("CurrencyRates", "Currency rates error");
            }
        });
    }
}