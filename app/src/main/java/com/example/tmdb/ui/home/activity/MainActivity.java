package com.example.tmdb.ui.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.tmdb.Api.TMDbAPI;
import com.example.tmdb.R;
import com.example.tmdb.ui.Settings;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    TMDbAPI tmDbAPI;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private FragmentAdapter fragmentAdapter;
    private String[] tabLabels = new String[]{"Populair Movies", "My Lists", "Upcoming Movies"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView settingsIcon = findViewById(R.id.settings_icon);
        ImageView menuIcon = findViewById(R.id.menu_icon);



        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), Settings.class);
               v.getContext().startActivity(intent);
            }
        });

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on menu icon
            }
        });

        viewPager = findViewById(R.id.view_pager);
        fragmentAdapter = new FragmentAdapter(this);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabLabels[position]);
        }).attach();

    }



    private static class FragmentAdapter extends FragmentStateAdapter {

        public FragmentAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position) {
                case 0:
                    return PopularMoviesFragment.newInstance();
                case 1:
                    return new ListsFragment();
                case 2:
                    return new UpcomingMoviesFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}