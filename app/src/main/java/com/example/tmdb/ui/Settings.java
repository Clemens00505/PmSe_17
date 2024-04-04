package com.example.tmdb.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.example.tmdb.R;

public class Settings extends AppCompatActivity {
    
    SharedPreferences sharedPreferences;

    ImageButton upBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        // Display the fragment as the main content.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.idFrameLayout, new SettingsFragment())
                .commit();

        upBtn = findViewById(R.id.upButton);
        upBtn.setImageResource(R.drawable.ic_back);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    
    private void updateTheme() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkModeEnabled = sharedPreferences.getBoolean("pref_dark_theme", false);
        if (darkModeEnabled) {
            setTheme(R.style.AppTheme_Dark);
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            setTheme(R.style.AppTheme_Light);
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTheme();
    }
}


//
//
//
//
//import android.content.SharedPreferences;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.preference.EditTextPreference;
//import androidx.preference.Preference;
//import androidx.preference.PreferenceFragmentCompat;
//
//import com.example.tmdb.R;
//
//import java.util.Locale;
//
//public class Settings extends AppCompatActivity {
//
//    ImageButton upBtn;
//    Button saveBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        Log.i("lala", "Settings onCreate");
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean darkModeEnabled = sharedPreferences.getBoolean("pref_dark_theme", false);
//        if (darkModeEnabled) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new
//                        SettingsFragment())
//                .commit();
//
//        upBtn = findViewById(R.id.upButton);
//        upBtn.setImageResource(R.drawable.ic_back);
//        upBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        saveBtn = findViewById(R.id.save_button);
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateAppSettings();
//                recreate();
//            }
//        });
//
//
//        //updateAppSettings();
//    }
//
//    private void updateAppSettings() {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean darkModeEnabled = sharedPreferences.getBoolean("pref_dark_theme", false);
//        setTheme(darkModeEnabled);
//        String language = sharedPreferences.getString("pref_language", "en");
//        setLocale(language);
//    }
//
////    private void saveSettings() {
////        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
////        SharedPreferences.Editor editor = sharedPreferences.edit();
////
////        // Save dark mode preference
////        boolean darkModeEnabled = sharedPreferences.getBoolean("pref_dark_theme", false);
////        editor.putBoolean("pref_dark_theme", darkModeEnabled);
////
////        // Save language preference
////        String languageCode = sharedPreferences.getString("pref_language", "en");
////        editor.putString("pref_language", languageCode);
////
////        // Apply changes
////        editor.apply();
////
////        // Apply dark mode and language changes immediately
////        setTheme(darkModeEnabled);
////        setLocale(languageCode);
////    }
//
////    private void applyTheme() {
////        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
////        boolean darkModeEnabled = sharedPreferences.getBoolean("pref_dark_theme", false);
////        setTheme(darkModeEnabled);
////    }
//
//    private void setTheme(boolean darkModeEnabled) {
//        if (darkModeEnabled) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//    }
//
//    private void setLocale(String languageCode) {
//        Locale locale = new Locale(languageCode);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
//    }
//
//    public static class SettingsFragment extends PreferenceFragmentCompat {
//
//        @Override
//        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.preferences);
//        }
//    }
//
//}


//}
//import android.app.Activity;
//import android.content.SharedPreferences;
//import android.content.res.Configuration;
//import android.os.Bundle;
//
//import android.preference.PreferenceFragment;
//import android.view.View;
//
//import android.widget.Button;
//import android.widget.ImageButton;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.preference.Preference;
//import androidx.preference.PreferenceFragmentCompat;
//import androidx.preference.PreferenceManager;
//
//import com.example.tmdb.R;
//
//import java.util.Locale;
//
//public class Settings extends Activity implements View.OnClickListener{
//
//    ImageButton upBtn;
//    Button saveBtn;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//
//
//        // below line is to change
//        // the title of our action bar.
//        upBtn = findViewById(R.id.upButton);
//        upBtn.setImageResource(R.drawable.ic_back);
//        upBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        if (findViewById(R.id.idFrameLayout) != null) {
//            if (savedInstanceState != null) {
//                return;
//            }
//            // below line is to inflate our fragment.
//            getFragmentManager().beginTransaction().add(R.id.idFrameLayout, new SettingsFragment()).commit();
//        }
//
//        saveBtn = findViewById(R.id.save_button);
//
//
//
//
//        updateAppSettings();
//
//
//        // below line is used to check if
//        // frame layout is empty or not.
////        if (findViewById(R.id.idFrameLayout) != null) {
////            if (savedInstanceState != null) {
////                return;
////            }
////            // below line is to inflate our fragment.
////            getFragmentManager().beginTransaction().add(R.id.idFrameLayout, new SettingsFragment()).commit();
////        }
//
//
//    }
//
//    public static class SettingsFragment extends PreferenceFragmentCompat {
//        @Override
//        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
//            setPreferencesFromResource(R.xml.preferences, rootKey);
//
//            findPreference("pref_dark_theme").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
//                    boolean darkModeEnabled = (boolean) newValue;
//                    return true;
//                }
//            });
//
//            findPreference("pref_api_key").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
//                    String prefApiKey = (String) newValue;
//                    return true;
//                }
//            });
//
//            findPreference("pref_language").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
//                    String prefLanguage = (String) newValue;
//                    return true;
//                }
//            });
//        }
//
//
//
//
//    }
//
//    private void setSettings() {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean darkModeEnabled = sharedPreferences.getBoolean("pref_dark_theme", false);
//        setTheme(darkModeEnabled);
//        String language = sharedPreferences.getString("pref_language", "en");
//        setLocale(language);
//    }
//
//    private void setTheme(boolean darkModeEnabled) {
//        if (darkModeEnabled) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//    }
//
//    private void setLocale(String languageCode) {
//        Locale locale = new Locale(languageCode);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
//    }
//
//    public void onClickSave(View v) {
//        saveSettings();
//    }
//
//    private void saveSettings() {
//        // Save dark mode preference
//        ///
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("pref_dark_theme", darkModeEnabled);
//
//        // Save language preference
//        String languageCode = /* get language code from UI */;
//        editor.putString("pref_language", languageCode);
//
//        // Apply changes
//        editor.apply();
//
//        // Apply dark mode and language changes immediately
//        applyTheme();
//        setLocale(languageCode);
//    }
//

//
//
//import static com.example.tmdb.Api.TMDbAPI.TMDb_API_KEY;
//
//import android.app.Activity;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.View;
//
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.ImageButton;
//import android.widget.Spinner;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.appcompat.widget.Toolbar;
//
//import com.example.tmdb.R;
//import com.google.android.material.textfield.TextInputEditText;
//
//
//public class Settings extends Activity implements AdapterView.OnItemSelectedListener{
//
//    String apiKey;
//    String language;
//
//    ImageButton upBtn;
//
//    TextView apiKeytv;
//
//    TextInputEditText apiKeyInput;
//    TextView languagetv;
//    Spinner languageInput;
//    TextView darkModetv;
//    static Switch darkModeSwitch;
//    Button saveBtn;
//
//    SharedPreferences sharedPreferences;
//
//
//
//     @Override
//    public void onCreate(Bundle savedInstanceState) {
//         Log.i("lala", "in ONCreate Settings");
//
//         sharedPreferences = getSharedPreferences("tmdb", MODE_PRIVATE);
//         boolean darkMode = sharedPreferences.getBoolean("darkMode", false);
//         if (darkMode) {
//             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//         } else {
//             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//         }
//
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_settings);
//         sharedPreferences = getSharedPreferences("tmdb", MODE_PRIVATE);
//         String[] languages = {getResources().getString(R.string.dutch), getResources().getString(R.string.english)};
//         upBtn = findViewById(R.id.upButton);
//
//
//         apiKeytv = findViewById(R.id.api_key_tv);
//         languagetv = findViewById(R.id.language_tv);
//         darkModetv = findViewById(R.id.darkmode_tv);
//         saveBtn = findViewById(R.id.save_button);
//         darkModeSwitch = findViewById(R.id.darkmode_switch);
////         if (sharedPreferences.getBoolean("darkMode", false)) {
////             darkModeSwitch.setChecked(true);
////         }
////         if (!sharedPreferences.getString("apiKey", TMDb_API_KEY).isBlank()) {
////             apiKeyInput.setText(apiKey);
////         }
//
//
////         languageInput = (Spinner) findViewById(R.id.language_spinner);
////
////         languageInput.setOnItemSelectedListener(this);
////         ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, languages);
////         arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////         languageInput.setAdapter(arrayAdapter);
//         darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//             @Override
//             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                 if (isChecked) {
//                     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//
//                 } else {
//                     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                 }
//                 SharedPreferences.Editor editor = sharedPreferences.edit();
//                 editor.putBoolean("darkMode", isChecked);
//                 editor.apply();
//             }
//         });
//
//
//     }
//
//     public static Switch getDarkModeSwitch() {
//         return darkModeSwitch;
//     }
//
//
//
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//}
