package com.example.tmdb.Dagger.Components;

import com.example.tmdb.App;
import com.example.tmdb.ui.home.activity.ListDetailActivity;
import com.example.tmdb.ui.home.adapters.MovieAdapter;
import com.example.tmdb.Dagger.Modules.HttpClientModule;
import com.example.tmdb.Dagger.Modules.ApplicationModule;
import com.example.tmdb.Dagger.AppScope;
import com.example.tmdb.ui.detail.adapters.MovieCastAdapter;
import com.example.tmdb.ui.detail.MovieDetailActivity;
import com.example.tmdb.ui.home.activity.MainActivity;
import com.example.tmdb.ui.home.activity.PopularMoviesFragment;
import com.example.tmdb.ui.home.activity.UpcomingMoviesFragment;
import com.example.tmdb.ui.home.activity.ListsFragment;

import javax.inject.Singleton;

import dagger.Component;

@AppScope
@Singleton
@Component(modules = {
        ApplicationModule.class,
        HttpClientModule.class,
})

public interface ApplicationComponent {

    void inject(App app);

    void inject(MainActivity mainActivity);

    void inject(MovieDetailActivity movieDetailActivity);

    void inject(MovieAdapter movieAdapter);

    void inject(MovieCastAdapter movieCastAdapter);

    void inject(PopularMoviesFragment popularMoviesFragment);

    void inject(UpcomingMoviesFragment upcomingMoviesFragment);

    void inject(ListsFragment listsFragment);

    void inject(ListDetailActivity listDetailActivity);


}
