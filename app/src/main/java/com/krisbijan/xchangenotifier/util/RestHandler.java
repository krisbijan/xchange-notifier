package com.krisbijan.xchangenotifier.util;


import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class RestHandler extends AsyncTask<String, Void, String> {

    private final String bitcoinURL = "https://blockchain.info/ticker";
    private final String traditionalURL = "https://api.fixer.io/latest";

    @Override
    protected String doInBackground(String... urls) {

        String result = "{}";
        URL url;
        HttpURLConnection urlConnection = null;

        try {

            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1) {

                char current = (char) data;

                result += current;

                data = reader.read();

            }


        } catch (Exception e) {
            Log.e("Rest ERROR", e.getMessage());
            e.printStackTrace();
        }

        return result;


    }


    private String getExchangeInfo(String url) {

        String result ="{}";

        try {
            result = execute(url).get();
            Log.i("REST recieved", result);
        } catch (InterruptedException e) {
            Log.e("Rest ERROR", e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("Rest ERROR", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }


    public String getBitcoinInfo() {
        return getExchangeInfo(bitcoinURL);
    }


    public String getTraditionalMoneyInfo() {

        return getExchangeInfo(traditionalURL);
    }
}
