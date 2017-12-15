package com.krisbijan.xchangenotifier.util;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RestHandler extends AsyncTask<String, Void, String> {

    private final String bitcoinURL = "https://blockchain.info/ticker";
    private final String traditionalURL = "https://api.fixer.io/latest";

    @Override
    protected String doInBackground(String... urls) {

        String result = "";
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


    public void updateTradExchangeInfo() {

        String result_traditionalURL = "{}";


        try {

            result_traditionalURL = execute(traditionalURL).get();
            Log.i("REST recieved", result_traditionalURL);
            parseTradCurrencyInfo(result_traditionalURL);
            LatestRates.getInstance().update();

        } catch (InterruptedException e) {
            Log.e("Rest ERROR", e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("Rest ERROR", e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateBitcoinExchangeInfo() {

        String result_bitcoinURL = "{}";


        try {
            result_bitcoinURL = execute(bitcoinURL).get();
            Log.i("REST recieved", result_bitcoinURL);
            parseBTXCurrencyInfo(result_bitcoinURL);
            LatestRates.getInstance().update();
        } catch (InterruptedException e) {
            Log.e("Rest ERROR", e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("Rest ERROR", e.getMessage());
            e.printStackTrace();
        }

    }

    private void parseTradCurrencyInfo(String response) {
        try {
            JSONObject reader = new JSONObject(response);
            Log.i("JSON TRAD Parse", reader.toString());
            JSONObject rates = reader.getJSONObject("rates");
            Log.i("JSON TRAD Rates Parse", rates.toString());

            final ArrayList<String> ratesList = new ArrayList<String>(LatestRates.getInstance().getRates().keySet());

            for (int i = 0; i < ratesList.size(); i++) {
                try {
                    Double dValue = rates.getDouble(ratesList.get(i));
                    Log.i("JSON TRAD " + ratesList.get(i) + " Parse", dValue.toString());

                    LatestRates.getInstance().getRates().remove(ratesList.get(i));
                    LatestRates.getInstance().getRates().put(ratesList.get(i), dValue);
                } catch (JSONException e) {
                    Log.e("JSON TRAD Parse ERROR", e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            Log.e("JSON TRAD Parse ERROR", e.getMessage());
            e.printStackTrace();
        }

    }

    private void parseBTXCurrencyInfo(String response) {
        try {
            JSONObject reader = new JSONObject(response);
            Log.i("JSON BTX Parse", reader.toString());
            JSONObject EUR = reader.getJSONObject("EUR");
            Log.i("JSON BTX/EUR Parse", EUR.toString());
            Double dValue = EUR.getDouble("last");
            LatestRates.getInstance().getRates().remove("BTX");
            LatestRates.getInstance().getRates().put("BTX", dValue);
        } catch (JSONException e) {
            Log.e("JSON BTX Parse ERROR", e.getMessage());
            e.printStackTrace();
        }
    }

}
