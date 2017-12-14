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
import android.widget.Toast;

import com.krisbijan.xchangenotifier.util.DatabaseHandler;
import com.krisbijan.xchangenotifier.util.LatestRates;
import com.krisbijan.xchangenotifier.util.RestHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        Log.i("Activity Info", "onCreate MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateList();

    }

    //Set main menu in toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("Menu Info", "Created");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //Menu item pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newAlert:
                Log.i("Menu Info", "New Alert pressed");
                startActivity(new Intent(this, NewAlertActivity.class));
                return true;
            case R.id.settings:
                Log.i("Menu Info", "Settings pressed");
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.rates:
                Log.i("Menu Info", "Latest rates pressed");
                startActivity(new Intent(this, RatesActivity.class));
                return true;
            default:
                Log.i("Menu Info", "Nothing pressed");
                return super.onOptionsItemSelected(item);
        }
    }

    void populateList(){
        final ListView alertList = (ListView) findViewById(R.id.alertlist);

        final ArrayList<String> alerts = new ArrayList<String>();

        if (alerts.size()<1){

            alerts.add("No alerts set up :(");

            ArrayAdapter <String> adapter = new ArrayAdapter <String>(MainActivity.this, android.R.layout.simple_list_item_1, alerts);

            alertList.setAdapter(adapter);

        } else {

            ArrayAdapter <String> adapter = new ArrayAdapter <String>(MainActivity.this, android.R.layout.simple_list_item_1, alerts);

            alertList.setAdapter(adapter);

            alertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    editAlert(alerts.get(position));
                }
            });

        }
    }

    void editAlert(String text){
    }

}
