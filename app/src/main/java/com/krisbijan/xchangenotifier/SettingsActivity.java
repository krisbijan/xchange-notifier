package com.krisbijan.xchangenotifier;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.krisbijan.xchangenotifier.util.LatestRates;
import com.krisbijan.xchangenotifier.util.Settings;

import java.util.ArrayList;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Activity Info", "onCreate SettingsActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    protected void onStart() {

        super.onStart();


        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);

        int hour = Settings.getInstance(getApplicationContext()).getHour();
        int minute = Settings.getInstance(getApplicationContext()).getMinute();
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);


        Spinner spinner_mainCurr = (Spinner) findViewById(R.id.spinner_mainCurr);

        final ArrayList<String> currencies = new ArrayList<String>(LatestRates.getInstance().getRates().keySet());


        ArrayAdapter<String> adapterSpinner1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currencies);

        adapterSpinner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mainCurr.setAdapter(adapterSpinner1);


        spinner_mainCurr.setSelection(adapterSpinner1.getPosition(Settings.getInstance(getApplicationContext()).getMainCurrency()));

    }

    public void saveSettings(View view){

        Spinner spinner_mainCurr = (Spinner) findViewById(R.id.spinner_mainCurr);
        String selectedMainCurr  = spinner_mainCurr.getSelectedItem().toString();
        Settings.getInstance(getApplicationContext()).setMainCurrency(selectedMainCurr);


        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Settings.getInstance(getApplicationContext()).setHour(hour);
        Settings.getInstance(getApplicationContext()).setMinute(minute);

    }



}
