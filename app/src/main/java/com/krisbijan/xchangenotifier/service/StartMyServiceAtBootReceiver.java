package com.krisbijan.xchangenotifier.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class StartMyServiceAtBootReceiver  extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.i(this.getClass().getName(),"Starting CurrencyRateNotificationService");
            Intent serviceIntent = new Intent(context, CurrencyRateNotificationService.class);
            serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(serviceIntent);
        }
    }
}
