package com.krisbijan.xchangenotifier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.krisbijan.xchangenotifier.util.Alert;
import com.krisbijan.xchangenotifier.util.DatabaseHandler;
import com.krisbijan.xchangenotifier.util.LatestRates;
import com.krisbijan.xchangenotifier.util.Settings;

import java.util.ArrayList;
import java.util.Collections;

public class EditAlertActivity extends AppCompatActivity {

    String alert_db_id;
    Integer alert_id;
    Alert alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Activity Info", "onCreate EditAlertActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editalert);

    }

    @Override
    protected void onStart() {
        super.onStart();
        alert_db_id = getIntent().getStringExtra("alert_db_id");
        alert_id = Integer.parseInt(alert_db_id);

        alert = DatabaseHandler.getInstance(getApplicationContext()).getAlert(alert_id);


        Spinner spinner_firstCurr = (Spinner) findViewById(R.id.spinner_firstCurr);
        Spinner spinner_secondCurr = (Spinner) findViewById(R.id.spinner_secondCurr);
        Spinner spinner_over_under = (Spinner) findViewById(R.id.spinner_over_under);

        final ArrayList<String> currencies = new ArrayList<String>(LatestRates.getInstance().getRates().keySet());
        Collections.sort(currencies);


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

        spinner_firstCurr.setSelection(adapterSpinner1.getPosition(alert.getFirstCurrency()));
        spinner_secondCurr.setSelection(adapterSpinner2.getPosition(alert.getSecondCurrency()));

        if (alert.getOver_under()==0)
            spinner_over_under.setSelection(adapterSpinner3.getPosition("Under"));
        else
            spinner_over_under.setSelection(adapterSpinner3.getPosition("Over"));

        EditText editText = (EditText) findViewById(R.id.editText_number);
        editText.setText(""+alert.getRate(), TextView.BufferType.EDITABLE);

    }

    public void editAlert(View view) {

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

            Alert alert_new = new Alert(alert_id,firstCurr,secondCurr,rateDouble,over_under);

            DatabaseHandler.getInstance(getApplicationContext()).updateAlert(alert_new);

            Toast.makeText(getApplicationContext(), "Alert edited!", Toast.LENGTH_SHORT).show();

            return;
        }catch(NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Rate not a number!", Toast.LENGTH_SHORT).show();
            return;
        }catch(Exception e){
            Log.e("NewAlert",e.getMessage());
            return;
        }
    }

}
