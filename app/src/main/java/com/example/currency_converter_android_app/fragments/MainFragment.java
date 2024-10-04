package com.example.currency_converter_android_app.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.currency_converter_android_app.R;
import com.example.currency_converter_android_app.adapters.CodeAdapter;
import com.example.currency_converter_android_app.databinding.FragmentMainBinding;
import com.example.currency_converter_android_app.models.CurrenciesViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private CurrenciesViewModel currenciesViewModel;
    private ArrayList<ArrayList<String>> currencyCodes;

    private String currencyFrom = "EUR";
    private String currencyTo = "USD";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currenciesViewModel = new ViewModelProvider(this).get(CurrenciesViewModel.class);
        currenciesViewModel.fetchCurrencyCodes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        clearResultText();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChildFragment();
        getCurrencyCodes();
        clickReplaceBtn();
        submitMainBtn();
        getCurrencyConversionResult();
    }


    private void setupCurrencyMenu(CircleImageView imageView, boolean isFromCurrency) {

        imageView.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_menu_layout, null);

            PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);

            setupPopupRecyclerView(popupView, popupWindow, isFromCurrency);
            popupWindow.showAtLocation(imageView.getRootView(), Gravity.CENTER, 0, 0);
        });
    }


    private void setupPopupRecyclerView(View popupView, PopupWindow popupWindow, boolean isFromCurrency) {
        RecyclerView recyclerView = popupView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<String> currencyList = new ArrayList<>();

        for (List<String> code : currencyCodes) {
            currencyList.add(code.get(0) + " - " + code.get(1));
        }

        CodeAdapter adapter = new CodeAdapter(currencyList, selectedCurrency -> {
            if (isFromCurrency) {
                currencyFrom= selectedCurrency.split(" - ")[0];
                if (Objects.equals(currencyFrom, "EUR")) {
                    Picasso.get().load(R.drawable.flag_eu).into(binding.mainFrom);
                    popupWindow.dismiss();
                    return;
                }
                currenciesViewModel.fetchFlagByCurrencyCode("from", currencyFrom);
                currenciesViewModel.getFlagUrlFromLiveData()
                        .observe(getViewLifecycleOwner(), result -> {
                            if (result != null && !result.isEmpty() ) {
                                Picasso.get().load(result).into(binding.mainFrom);
                            } else {
                                Log.e("CurrencyFlags", "Error");
                            }
                        });
            } else {
                currencyTo = selectedCurrency.split(" - ")[0];
                if (Objects.equals(currencyTo, "EUR")) {
                    Picasso.get().load(R.drawable.flag_eu).into(binding.mainTo);
                    popupWindow.dismiss();
                    return;
                }
                currenciesViewModel.fetchFlagByCurrencyCode("to", currencyTo);
                currenciesViewModel.getFlagUrlToLiveData()
                        .observe(getViewLifecycleOwner(), result -> {
                            if (result != null && !result.isEmpty() ) {
                                Picasso.get().load(result).into(binding.mainTo);
                            } else {
                                Log.e("CurrencyFlags", "Error");
                            }
                        });
            }
            popupWindow.dismiss();
        });

        recyclerView.setAdapter(adapter);
    }

    private void submitMainBtn() {
        binding.mainSubmit.setOnClickListener(v -> {
            String userAmount = binding.mainInputQty.getText().toString();
            boolean isNumeric = userAmount.matches("\\d+[,.]?\\d*");
            if (isNumeric && !currencyFrom.isEmpty() && !currencyTo.isEmpty()) {
                String amount = userAmount.replace(",", ".");
                currenciesViewModel.fetchCurrencyPair(currencyFrom, currencyTo, amount);
            } else {
                Toast.makeText(getActivity(), "Enter your amount and select the codes!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickReplaceBtn() {
        binding.mainReplace.setOnClickListener(v -> {
            clearResultText();
            swapCurrencies();
        });
    }

    public void getCurrencyCodes() {
        currenciesViewModel.getCurrencyCodesLiveData().observe(getViewLifecycleOwner(), codes -> {
            if (codes != null && !codes.isEmpty()) {
                currencyCodes = codes;
                setupCurrencyMenu(binding.mainFrom, true);
                setupCurrencyMenu(binding.mainTo, false);
            } else {
                Log.e("CurrencyCodes", "Currency codes error");
            }
        });
    }

    public void getCurrencyConversionResult() {
        currenciesViewModel.getCurrencyLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String formattedResult = decimalFormat.format(result.getConversionResult());
                String formattedDate = formatDate(result.getTimeLastUpdateUtc());
                binding.mainResult.setText(formattedResult + " " +  currencyTo);
                binding.mainLastUpd.setText("Last update: " + formattedDate);
            } else {
                Log.e("CurrencyConvert", "Error");
            }
        });
    }

    public String formatDate(String inputDate) {
        String outputDate = "";
        SimpleDateFormat inputFormat =
                new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        SimpleDateFormat outputFormat =
                new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);
        try {
            Date date = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    private void initChildFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        RatesFragment childFragment = new RatesFragment();
        transaction.replace(R.id.childFragment, childFragment);
        transaction.commit();
    }

    private void clearResultText() {
        binding.mainResult.setText("");
        binding.mainLastUpd.setText("");
    }

    private void swapCurrencies() {
        String temp = currencyFrom;
        currencyFrom = currencyTo;
        currencyTo = temp;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }
}