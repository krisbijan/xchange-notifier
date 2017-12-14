package com.krisbijan.xchangenotifier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Activity Info", "onCreate SettingsActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
