package com.example.tmdb.ui;


import android.app.Activity;
import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.tmdb.R;
import com.google.android.material.textfield.TextInputEditText;


public class Settings extends Activity implements AdapterView.OnItemSelectedListener{

    ImageButton upBtn;

    TextView apiKey;
    TextInputEditText apiKeyInput;
    TextView language;
    Spinner languageInput;
    TextView colorMode;
    Switch colorModeSwitch;
    Button save;



     @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_settings);
         String[] languages = {getResources().getString(R.string.dutch), getResources().getString(R.string.english)};
         upBtn = findViewById(R.id.upButton);

         upBtn.setImageResource(R.drawable.ic_back);
         upBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onBackPressed();
             }
         });
         apiKey = findViewById(R.id.api_key_tv);
         language = findViewById(R.id.language_tv);
         colorMode = findViewById(R.id.darkmode_tv);



         languageInput = (Spinner) findViewById(R.id.language_spinner);

         languageInput.setOnItemSelectedListener(this);
         ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, languages);
         arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         languageInput.setAdapter(arrayAdapter);



    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
