package com.example.tmdb.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import com.example.tmdb.Api.TMDbAPI;
import com.example.tmdb.R;

public class Settings extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ImageButton upBtn;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction().replace(R.id.idFrameLayout, new SettingsFragment()).commit();

        upBtn = findViewById(R.id.upButton);
        upBtn.setImageResource(R.drawable.ic_back);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        saveBtn = findViewById(R.id.save_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTheme();
                setLocale();
            }
        });

    }

    private void updateTheme() {
        boolean darkModeEnabled = sharedPreferences.getBoolean("pref_dark_theme", false);
        if (darkModeEnabled) {
            setTheme(R.style.AppTheme_Dark);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            setTheme(R.style.AppTheme_Light);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void setLocale() {
        String languageCode = sharedPreferences.getString("pref_language", "en");
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }


}

