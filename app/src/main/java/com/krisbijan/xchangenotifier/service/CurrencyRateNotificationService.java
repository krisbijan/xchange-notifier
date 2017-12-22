package com.krisbijan.xchangenotifier.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.krisbijan.xchangenotifier.MainActivity;
import com.krisbijan.xchangenotifier.R;
import com.krisbijan.xchangenotifier.RatesActivity;
import com.krisbijan.xchangenotifier.util.Alert;
import com.krisbijan.xchangenotifier.util.DatabaseHandler;
import com.krisbijan.xchangenotifier.util.LatestRates;
import com.krisbijan.xchangenotifier.util.RestHandler;
import com.krisbijan.xchangenotifier.util.Settings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CurrencyRateNotificationService extends IntentService {

    private static int CHANNEL_ID = 0;

    public CurrencyRateNotificationService() {
        super("CurrencyRateNotificationService");
    }


    private void runCheck() {

        CurrencyRateNotificationService.CHANNEL_ID = 0;

        Log.i(this.getClass().getName(), "Updating values from web");

        //updating currency rates
        LatestRates.getInstance().getRates();
        new RestHandler().updateTradExchangeInfo();
        new RestHandler().updateBitcoinExchangeInfo();
        Log.i(this.getClass().getName(), "All rates: "+LatestRates.getInstance().getRates());
        sendNotification("All rates: "+LatestRates.getInstance().getRates());CHANNEL_ID++;
        Log.i(this.getClass().getName(), "Updating database");

        //calling singelton to init
        DatabaseHandler.getInstance(getApplicationContext()).getAllAlerts();
        Settings.getInstance(getApplicationContext()).getMainCurrency();

        Log.i(this.getClass().getName(), "All alerts: "+Alert.getAllAlerts());
        Log.i(this.getClass().getName(), "Updating values from web");
        sendNotification("All alerts: "+Alert.getAllAlerts());CHANNEL_ID++;


        for (Alert a : Alert.getAllAlerts()) {

            if (rateReached(a))
                sendNotification(a.toString2());

        }

    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true) {
            try {
                runCheck();

                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean rateReached(Alert a) {
        Log.i(this.getClass().getName(), "----------------------------------------------------");

        Log.i(this.getClass().getName(), a.toString2());

        Double firstCurrencyValue = LatestRates.getInstance().getRates().get(a.getFirstCurrency());
        Double secondCurrencyValue = LatestRates.getInstance().getRates().get(a.getSecondCurrency());
        Double currentRate = secondCurrencyValue / firstCurrencyValue;

        Log.i(this.getClass().getName(), "firstCurrencyValue: " + firstCurrencyValue + " secondCurrencyValue: " + secondCurrencyValue + " currentRate: " + currentRate);

        if (a.getOver_under() == 0) {
            if (currentRate < a.getRate()) {
                Log.i(this.getClass().getName(), "Rate under " + a.getRate());

                return true;
            }
        } else {
            if (currentRate > a.getRate()) {
                Log.i(this.getClass().getName(), "Rate over " + a.getRate());

                return true;
            }
        }

        Log.i(this.getClass().getName(), "Rate not reached");

        return false;
    }

    public void sendNotification(String notification) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        String CHANNEL_ID = "xchange_notifier_channel";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(format.format(new Date()))
                        .setContentText(notification);
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(RatesActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(CurrencyRateNotificationService.CHANNEL_ID++, mBuilder.build());

    }

}
