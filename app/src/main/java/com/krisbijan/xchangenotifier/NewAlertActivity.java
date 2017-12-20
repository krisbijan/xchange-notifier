package com.krisbijan.xchangenotifier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.krisbijan.xchangenotifier.util.Alert;
import com.krisbijan.xchangenotifier.util.DatabaseHandler;
import com.krisbijan.xchangenotifier.util.LatestRates;

import java.util.ArrayList;

public class NewAlertActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Activity Info", "onCreate NewAlertActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newalert);


    }

    @Override
    protected void onStart() {

        super.onStart();

        Spinner spinner_firstCurr = (Spinner) findViewById(R.id.spinner_firstCurr);
        Spinner spinner_secondCurr = (Spinner) findViewById(R.id.spinner_secondCurr);
        Spinner spinner_over_under = (Spinner) findViewById(R.id.spinner_over_under);

        final ArrayList<String> currencies = new ArrayList<String>(LatestRates.getInstance().getRates().keySet());


        ArrayAdapter<String> adapterSpinner1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currencies);
        ArrayAdapter<String> adapterSpinner2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currencies);

        adapterSpinner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_firstCurr.setAdapter(adapterSpinner1);
        adapterSpinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_secondCurr.setAdapter(adapterSpinner2);


        final ArrayList<String> over_under = new ArrayList<String>();
        over_under.add("Over");
        over_under.add("Under");

        ArrayAdapter<String> adapterSpinner3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, over_under);

        adapterSpinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_over_under.setAdapter(adapterSpinner3);

    }


    public void addNewAlert(View view) {

        Spinner spinner_firstCurr = (Spinner) findViewById(R.id.spinner_firstCurr);
        Spinner spinner_secondCurr = (Spinner) findViewById(R.id.spinner_secondCurr);
        Spinner spinner_over_under = (Spinner) findViewById(R.id.spinner_over_under);
        EditText editText_rate = (EditText) findViewById(R.id.editText_number);

        String firstCurr = (String) spinner_firstCurr.getSelectedItem().toString();
        String secondCurr = (String) spinner_secondCurr.getSelectedItem().toString();
        String over_under = (String) spinner_over_under.getSelectedItem().toString();
        String rate = (String) editText_rate.getText().toString();

        if (firstCurr == null || firstCurr.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "No first currency selected!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (secondCurr == null || secondCurr.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "No second currency selected!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (over_under == null || over_under.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "No over/under selected!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rate == null || rate.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "No rate entered!", Toast.LENGTH_SHORT).show();
            return;
        }

        try{
            Double rateDouble = Double.parseDouble(rate);


            Alert alert = new Alert(firstCurr,secondCurr,rateDouble,over_under);

            DatabaseHandler.getInstance(getApplicationContext()).addAlert(alert);



            Toast.makeText(getApplicationContext(), "New alert added!", Toast.LENGTH_SHORT).show();

            return;
        }catch(NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Rate not a number!", Toast.LENGTH_SHORT).show();
            return;
        }catch(Exception e){
            Log.e("NewAlert",e.getMessage());
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
