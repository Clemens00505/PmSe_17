package com.example.tmdb.Dagger.Components;


import com.example.tmdb.Dagger.AppScope;
import com.example.tmdb.Dagger.Modules.ApplicationModule;
import com.example.tmdb.Dagger.Modules.HttpClientModule;

import javax.inject.Singleton;

import dagger.Component;

@AppScope
@Singleton
@Component(modules = {
        ApplicationModule.class,
        HttpClientModule.class,
})

public interface ApplicationComponent {

//    void inject(MainActivity mainActivity);


}
