package com.krisbijan.xchangenotifier.util;


import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class RestHandler extends AsyncTask<String, Void, String> {

    private final static String bitcoinURL = "https://blockchain.info/ticker";
    private final static String traditionalURL = "https://api.fixer.io/latest";

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


    public static void getExchangeInfo() {

        String result_bitcoinURL ="{}";
        String result_traditionalURL ="{}";
        RestHandler rh = new RestHandler();


        try {
            result_bitcoinURL = rh.execute(bitcoinURL).get();
            Log.i("REST recieved", result_bitcoinURL);

            result_traditionalURL = rh.execute(traditionalURL).get();
            Log.i("REST recieved", result_traditionalURL);

        } catch (InterruptedException e) {
            Log.e("Rest ERROR", e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("Rest ERROR", e.getMessage());
            e.printStackTrace();
        }

    }



}
