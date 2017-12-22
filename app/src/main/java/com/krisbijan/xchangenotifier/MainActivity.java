package com.krisbijan.xchangenotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.krisbijan.xchangenotifier.service.CurrencyRateNotificationService;
import com.krisbijan.xchangenotifier.util.Alert;
import com.krisbijan.xchangenotifier.util.DatabaseHandler;
import com.krisbijan.xchangenotifier.util.RestHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        Log.i(this.getClass().getName(), "onCreate MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    @Override
    protected void onStart() {
        super.onStart();
        new RestHandler().updateTradExchangeInfo();
        new RestHandler().updateBitcoinExchangeInfo();

        databaseGet();
        populateList();

        Intent serviceIntent = new Intent(getApplicationContext(), CurrencyRateNotificationService.class);
        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startService(serviceIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(this.getClass().getName(), "Created");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newAlert:
                Log.i(this.getClass().getName(), "New Alert pressed");
                startActivity(new Intent(this, NewAlertActivity.class));
                return true;
            case R.id.settings:
                Log.i(this.getClass().getName(), "Settings pressed");
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.rates:
                Log.i(this.getClass().getName(), "Latest rates pressed");
                startActivity(new Intent(this, RatesActivity.class));
                return true;
            default:
                Log.i(this.getClass().getName(), "Nothing pressed");
                return super.onOptionsItemSelected(item);
        }
    }

    void populateList() {
        final ListView alertList = (ListView) findViewById(R.id.alertlist);

        final ArrayList<String> alerts = new ArrayList<String>();
        final ArrayList<Integer> alertsId = new ArrayList<Integer>();

        if (Alert.getAllAlerts().size() < 1) {

            alerts.add("No alerts set up :(");
            alertsId.add(-1);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, alerts);

            alertList.setAdapter(adapter);

        } else {

            for (int i = 0; i < Alert.getAllAlerts().size(); i++) {
                Alert a = Alert.getAllAlerts().get(i);

                String over_under = "";

                if (a.getOver_under() == 0)
                    over_under = " under ";
                else
                    over_under = " over ";

                alerts.add(a.getFirstCurrency() + "/" + a.getSecondCurrency() + over_under + a.getRate());
                alertsId.add(a.getId());

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, alerts);

            alertList.setAdapter(adapter);

            alertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    editAlert(alertsId.get(position));
                }
            });

        }
    }

    void editAlert(Integer id) {

        if ( id > -1) {
            Intent intent = new Intent(this, EditAlertActivity.class);
            intent.putExtra("alert_db_id", ""+id);
            startActivity(intent);
        }
    }


    void databaseGet() {

        Log.i(this.getClass().getName(), "Reading all alerts..");
        Alert.setAllAlerts((ArrayList<Alert>) DatabaseHandler.getInstance(getApplicationContext()).getAllAlerts());

        for (Alert cn : Alert.getAllAlerts()) {
            Log.i(this.getClass().getName(), cn.toString());
        }


    }

}
