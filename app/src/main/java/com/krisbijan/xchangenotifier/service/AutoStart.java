package com.krisbijan.xchangenotifier.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AutoStart extends WakefulBroadcastReceiver
{
    StartMyServiceAtBootReceiver receiver = new StartMyServiceAtBootReceiver();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            receiver.setAlarm(context);
        }
    }
}