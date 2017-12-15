package com.krisbijan.xchangenotifier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.krisbijan.xchangenotifier.util.LatestRates;

import java.util.ArrayList;

public class NewAlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Activity Info", "onCreate NewAlertActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newalert);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        final ArrayList<String> currencies = new ArrayList<String>(LatestRates.getInstance().getRates().keySet());


        ArrayAdapter<String> adapterSpinner1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currencies);
        ArrayAdapter<String> adapterSpinner2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currencies);

        adapterSpinner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapterSpinner1);
        adapterSpinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapterSpinner2);


    }
}
