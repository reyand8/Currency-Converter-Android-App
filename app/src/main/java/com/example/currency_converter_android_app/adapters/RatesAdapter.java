package com.example.currency_converter_android_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currency_converter_android_app.R;
import com.example.currency_converter_android_app.models.RateModel;

import java.util.List;


public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.RateViewHolder> {

    private List<RateModel> rates;
    private List<String> baseCurrencies;

    public RatesAdapter(List<RateModel> rates, List<String> baseCurrencies) {
        this.rates = rates;
        this.baseCurrencies = baseCurrencies;
    }

    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rate, parent, false);
        return new RateViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
        RateModel rate = rates.get(position);
        holder.rateCode.setText(rate.getCode());
        holder.rateFullName.setText(rate.getFullName());
        holder.rateRate.setText(String.valueOf(rate.getRate()));

        holder.removeButton.setOnClickListener(v -> {
            String currencyCode = rate.getCode();
            rates.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, rates.size());

            baseCurrencies.remove(currencyCode);
        });
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }

    static class RateViewHolder extends RecyclerView.ViewHolder {
        TextView rateCode;
        TextView rateFullName;
        TextView rateRate;
        Button removeButton;

        public RateViewHolder(@NonNull View itemView) {
            super(itemView);
            rateCode = itemView.findViewById(R.id.rateCode);
            rateFullName = itemView.findViewById(R.id.rateFullName);
            rateRate = itemView.findViewById(R.id.rateRate);
            removeButton = itemView.findViewById(R.id.rateRemove);
        }
    }
}