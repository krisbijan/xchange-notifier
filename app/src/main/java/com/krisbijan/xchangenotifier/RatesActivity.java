package com.krisbijan.xchangenotifier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.krisbijan.xchangenotifier.util.LatestRates;

import java.util.ArrayList;
import java.util.Collections;

public class RatesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Activity Info", "onCreate RatesActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);
        populateList();
    }

    void populateList(){
        final ListView rateslist = (ListView) findViewById(R.id.rateslist);

        final ArrayList<String> rates = new ArrayList<String>(LatestRates.getInstance().getRates().keySet());

        Collections.sort(rates);



        if (rates.size()<1){

            rates.add("No rates set up :(");

            ArrayAdapter<String> adapter = new ArrayAdapter <String>(RatesActivity.this, android.R.layout.simple_list_item_1, rates);

            rateslist.setAdapter(adapter);

        } else {

            for (int i= 0;i<rates.size();i++){
                rates.set(i,rates.get(i)+"/EUR");
            }


            ArrayAdapter <String> adapter = new ArrayAdapter <String>(RatesActivity.this, android.R.layout.simple_list_item_1, rates);

            rateslist.setAdapter(adapter);

        }
    }
}
