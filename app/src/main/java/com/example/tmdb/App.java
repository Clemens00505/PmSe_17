package com.example.tmdb;


import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.tmdb.Dagger.Components.ApplicationComponent;
import com.example.tmdb.Dagger.Components.DaggerApplicationComponent;
import com.example.tmdb.Dagger.Modules.ApplicationModule;
import com.example.tmdb.Dagger.Modules.HttpClientModule;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class App extends Application {

    private static App instance;
    private ApplicationComponent mApplicationComponent;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (BuildConfig.DEBUG) Timber.plant(new DebugTree());

        // Creates Dagger Graph
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpClientModule(new HttpClientModule())
                .build();

        mApplicationComponent.inject(this);

    }

    public static App instance() {
        return instance;
    }

    public ApplicationComponent appComponent() {
        return mApplicationComponent;
    }
}
