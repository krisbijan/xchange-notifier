package com.krisbijan.xchangenotifier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.krisbijan.xchangenotifier.util.LatestRates;
import com.krisbijan.xchangenotifier.util.RestHandler;
import com.krisbijan.xchangenotifier.util.Settings;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class RatesActivity extends AppCompatActivity {

    // EUR/HRK or HRK/EUR
    boolean other_EUR = false;

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

        String mainCurrency = Settings.getInstance(getApplicationContext()).getMainCurrency();

        if(mainCurrency==null ||mainCurrency.equalsIgnoreCase(""))
            mainCurrency="EUR";

        if (rates.size() < 1) {

            rates.add("No rates set up :(");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RatesActivity.this, android.R.layout.simple_list_item_1, rates);

            rateslist.setAdapter(adapter);

        } else {
            //DecimalFormat df = new DecimalFormat("#####.#########");
            Double mainCurrencyRate = LatestRates.getInstance().getRates().get(mainCurrency);
            for (int i = 0; i < rates.size(); i++) {
                if (other_EUR) {
                    //double ratio = Double.parseDouble(df.format(1.0D / LatestRates.getInstance().getRates().get(rates.get(i))));
                    double ratio = mainCurrencyRate / LatestRates.getInstance().getRates().get(rates.get(i));
                    rates.set(i, rates.get(i) + "/"+mainCurrency+" - " + ratio);
                } else {
                    //double ratio = Double.parseDouble(df.format(LatestRates.getInstance().getRates().get(rates.get(i))));
                    double ratio = LatestRates.getInstance().getRates().get(rates.get(i))/mainCurrencyRate;

                    rates.set(i, mainCurrency+"/" + rates.get(i) + " - " + ratio);
                }
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RatesActivity.this, android.R.layout.simple_list_item_1, rates);

            rateslist.setAdapter(adapter);

        }

        Toast.makeText(getApplicationContext(), "Last update "+LatestRates.getInstance().getLastUpdate(), Toast.LENGTH_LONG).show();

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

                if (other_EUR)
                    other_EUR = false;
                else
                    other_EUR = true;

                populateList();
                Log.i("Menu Info", "Switch ratio pressed");
                return true;

            case R.id.refresh:
                Log.i("Menu Info", "Refresh pressed");
                new RestHandler().updateTradExchangeInfo();
                new RestHandler().updateBitcoinExchangeInfo();
                populateList();
                return true;

            default:
                Log.i("Menu Info", "Nothing pressed");
                return super.onOptionsItemSelected(item);
        }
    }
}
