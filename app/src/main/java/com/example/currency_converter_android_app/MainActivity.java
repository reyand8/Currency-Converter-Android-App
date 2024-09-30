package com.example.currency_converter_android_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.currency_converter_android_app.databinding.ActivityMainBinding;
import com.example.currency_converter_android_app.fragments.MainFragment;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, MainFragment.newInstance())
                .commit();
    }
}
