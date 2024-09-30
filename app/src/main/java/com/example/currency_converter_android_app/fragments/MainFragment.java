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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.currency_converter_android_app.R;
import com.example.currency_converter_android_app.adapters.CodeAdapter;
import com.example.currency_converter_android_app.databinding.FragmentMainBinding;
import com.example.currency_converter_android_app.models.CurrenciesViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private CurrenciesViewModel currenciesViewModel;
    private ArrayList<ArrayList<String>> currencyCodes;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currenciesViewModel.getCurrencyCodesLiveData().observe(getViewLifecycleOwner(), codes -> {
            if (codes != null && !codes.isEmpty()) {
                currencyCodes = codes;
                setupCurrencyMenu(binding.mainFrom);
                setupCurrencyMenu(binding.mainTo);
            } else {
                Log.e("CurrencyCodes", "Error");
            }
        });
    }

    private void setupCurrencyMenu(CircleImageView imageView) {
        imageView.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_menu_layout, null);

            PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);

            RecyclerView recyclerView = popupView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            List<String> currencyList = new ArrayList<>();
            for (List<String> code : currencyCodes) {
                if (code.size() >= 2) {
                    String currencyCode = code.get(0);
                    String currencyName = code.get(1);
                    currencyList.add(currencyCode + " - " + currencyName);
                }
            }

            CodeAdapter adapter = new CodeAdapter(currencyList, selectedCurrency -> {
                Toast.makeText(getActivity(), "Your code: " + selectedCurrency,
                        Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            });

            recyclerView.setAdapter(adapter);

            popupWindow.showAsDropDown(imageView, 0, 0);
        });
    }


    public static MainFragment newInstance() {
        return new MainFragment();
    }
}
