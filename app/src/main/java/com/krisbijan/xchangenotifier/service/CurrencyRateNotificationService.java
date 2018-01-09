package com.krisbijan.xchangenotifier.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
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


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        while (true) {
            try {
                runCheck();
                Integer freq = Settings.getInstance(getApplicationContext()).getFreq();

                Thread.sleep(freq*1000*60*60);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private void runCheck() {

        CurrencyRateNotificationService.CHANNEL_ID = 0;

        for (Alert a : Alert.getAllAlerts()) {

            String rateReached = rateReached(a);

            if (rateReached!=null)
                sendNotification(a.toString2(),rateReached);

        }

    }

    private String rateReached(Alert a) {
        Log.i(this.getClass().getName(), "----------------------------------------------------");

        Log.i(this.getClass().getName(), a.toString2());

        Double firstCurrencyValue = LatestRates.getInstance().getRates().get(a.getFirstCurrency());
        Double secondCurrencyValue = LatestRates.getInstance().getRates().get(a.getSecondCurrency());
        Double currentRate = secondCurrencyValue / firstCurrencyValue;

        Log.i(this.getClass().getName(), "firstCurrencyValue: " + firstCurrencyValue + " secondCurrencyValue: " + secondCurrencyValue + " currentRate: " + currentRate);

        if (a.getOver_under() == 0) {
            if (currentRate < a.getRate()) {
                Log.i(this.getClass().getName(), "Rate under " + a.getRate());

                return ""+currentRate;
            }
        } else {
            if (currentRate > a.getRate()) {
                Log.i(this.getClass().getName(), "Rate over " + a.getRate());

                return ""+currentRate;
            }
        }

        Log.i(this.getClass().getName(), "Rate not reached");

        return null;
    }

    public void sendNotification(String notification, String currentRate) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        String CHANNEL_ID = "xchange_notifier_channel";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(format.format(new Date()))
                        .setContentText(notification+" ("+currentRate+")");
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
