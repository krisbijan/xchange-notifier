package com.krisbijan.xchangenotifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.krisbijan.xchangenotifier.util.LatestRates;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class RatesActivity extends AppCompatActivity {

    // EUR/HRK or HRK/EUR
    int ratioSwitch = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Activity Info", "onCreate RatesActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);
        populateList();
    }

    void populateList() {
        final ListView rateslist = (ListView) findViewById(R.id.rateslist);

        final ArrayList<String> rates = new ArrayList<String>(LatestRates.getInstance().getRates().keySet());

        Collections.sort(rates);


        if (rates.size() < 1) {

            rates.add("No rates set up :(");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RatesActivity.this, android.R.layout.simple_list_item_1, rates);

            rateslist.setAdapter(adapter);

        } else {
            DecimalFormat df = new DecimalFormat("#.####");

            for (int i = 0; i < rates.size(); i++) {
                if (ratioSwitch % 2 == 0) {
                    double ratio = Double.parseDouble(df.format(LatestRates.getInstance().getRates().get(rates.get(i))));
                    rates.set(i, rates.get(i) + "/EUR - " + ratio);
                } else {
                    double ratio = Double.parseDouble(df.format(1.0D / LatestRates.getInstance().getRates().get(rates.get(i))));
                    rates.set(i, "EUR/" + rates.get(i) + " - " + ratio);
                }
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RatesActivity.this, android.R.layout.simple_list_item_1, rates);

            rateslist.setAdapter(adapter);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("Menu Info", "Created");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rates_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_ratio:
                ratioSwitch++;
                populateList();
                Log.i("Menu Info", "Switch ratio pressed");
                return true;
            case R.id.refresh:
                Log.i("Menu Info", "Refresh pressed");
                return true;
            default:
                Log.i("Menu Info", "Nothing pressed");
                return super.onOptionsItemSelected(item);
        }
    }
}
