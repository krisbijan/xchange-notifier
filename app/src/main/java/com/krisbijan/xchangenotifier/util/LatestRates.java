package com.krisbijan.xchangenotifier.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class LatestRates {

    //all rates are based on EUR
    private static LatestRates instance;
    private HashMap<String,Double>  rates = new HashMap<String,Double> ();
    private Date latestUpdate;

    private LatestRates(){
        latestUpdate = new Date();
        
        rates.put("AUD",placeholder());
        rates.put("BGN",placeholder());
        rates.put("BRL",placeholder());
        rates.put("BTX",placeholder());
        rates.put("CAD",placeholder());
        rates.put("CHF",placeholder());
        rates.put("CNY",placeholder());
        rates.put("CZK",placeholder());
        rates.put("DKK",placeholder());
        rates.put("GBP",placeholder());
        rates.put("HKD",placeholder());
        rates.put("HRK",placeholder());
        rates.put("HUF",placeholder());
        rates.put("EUR",placeholder());
        rates.put("IDR",placeholder());
        rates.put("ILS",placeholder());
        rates.put("INR",placeholder());
        rates.put("JPY",placeholder());
        rates.put("KRW",placeholder());
        rates.put("MXN",placeholder());
        rates.put("MYR",placeholder());
        rates.put("NOK",placeholder());
        rates.put("NZD",placeholder());
        rates.put("PHP",placeholder());
        rates.put("PLN",placeholder());
        rates.put("RON",placeholder());
        rates.put("RUB",placeholder());
        rates.put("SEK",placeholder());
        rates.put("SGD",placeholder());
        rates.put("THB",placeholder());
        rates.put("TRY",placeholder());
        rates.put("USD",placeholder());
        rates.put("ZAR",placeholder());
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Double> rates) {
        this.rates = rates;
    }

    public static LatestRates getInstance(){
        if (instance == null) {
            instance = new LatestRates();
        }
        return instance;
    }
    
    private double placeholder(){
        //DecimalFormat df = new DecimalFormat("#.####");
        //return Double.parseDouble(df.format(Math.random()));
        return 1.0D;
    }

    public String getLastUpdate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(latestUpdate);
    }

    public void update() {
        latestUpdate = new Date();
    }

}
