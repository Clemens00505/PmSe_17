package com.example.tmdb.ui;


import android.app.Activity;
import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;


import com.example.tmdb.R;


public class Settings extends Activity implements AdapterView.OnItemSelectedListener{
    String[] languages = {"Dutch", "English"};

     @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_settings);
         Toolbar toolb

         Spinner spinner = (Spinner) findViewById(R.id.language_spinner);

         spinner.setOnItemSelectedListener(this);
         ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, languages);
         arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(arrayAdapter);



    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
