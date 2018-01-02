package com.krisbijan.xchangenotifier;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.krisbijan.xchangenotifier.service.CurrencyRateNotificationService;
import com.krisbijan.xchangenotifier.util.LatestRates;
import com.krisbijan.xchangenotifier.util.Settings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Activity Info", "onCreate SettingsActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    protected void onStart() {

        super.onStart();

/*
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);

        int hour = Settings.getInstance(getApplicationContext()).getHour();
        int minute = Settings.getInstance(getApplicationContext()).getMinute();
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);

*/

        EditText freq = (EditText) findViewById(R.id.freq);
        freq.setText(""+Settings.getInstance(getApplicationContext()).getFreq(), TextView.BufferType.EDITABLE);


        Spinner spinner_mainCurr = (Spinner) findViewById(R.id.spinner_mainCurr);

        final ArrayList<String> currencies = new ArrayList<String>(LatestRates.getInstance().getRates().keySet());

        Collections.sort(currencies);

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

/*
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Settings.getInstance(getApplicationContext()).setHour(hour);
        Settings.getInstance(getApplicationContext()).setMinute(minute);
*/

        EditText freq = (EditText) findViewById(R.id.freq);

        Integer frequency = Integer.parseInt(freq.getText().toString());
        Settings.getInstance(getApplicationContext()).setFreq(frequency);

        Toast.makeText(getApplicationContext(), "Settings saved!", Toast.LENGTH_SHORT).show();


        Log.i(this.getClass().getName(),"Starting CurrencyRateNotificationService");
        Intent serviceIntent = new Intent(getApplicationContext(), CurrencyRateNotificationService.class);
        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startService(serviceIntent);


    }



}
