package com.example.currency_converter_android_app.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currency_converter_android_app.databinding.ItemCodeBinding;

import java.util.List;


public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.CurrencyViewHolder> {
    private final List<String> currencyList;
    private final OnCurrencyClickListener listener;

    public CodeAdapter(List<String> currencyList, OnCurrencyClickListener listener) {
        this.currencyList = currencyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCodeBinding binding = ItemCodeBinding.inflate(inflater, parent, false);
        return new CurrencyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        String currency = currencyList.get(position);
        holder.bind(currency);
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private final ItemCodeBinding binding;

        public CurrencyViewHolder(@NonNull ItemCodeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String currency) {
            binding.tvCode.setText(currency);
            itemView.setOnClickListener(v -> listener.onCurrencyClick(currency));
        }
    }

    public interface OnCurrencyClickListener {
        void onCurrencyClick(String currency);
    }
}
