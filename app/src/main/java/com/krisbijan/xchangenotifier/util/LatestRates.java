package com.krisbijan.xchangenotifier.util;

import java.util.Collections;
import java.util.HashMap;

public class LatestRates {

    //all rates are based on EUR
    private static final LatestRates instance = new LatestRates();
    private HashMap<String,Double>  rates = new HashMap<String,Double> ();

    private LatestRates(){
        rates.put("AUD",0.0D);
        rates.put("BGN",0.0D);
        rates.put("BRL",0.0D);
        rates.put("BTX",0.0D);
        rates.put("CAD",0.0D);
        rates.put("CHF",0.0D);
        rates.put("CNY",0.0D);
        rates.put("CZK",0.0D);
        rates.put("DKK",0.0D);
        rates.put("GBP",0.0D);
        rates.put("HKD",0.0D);
        rates.put("HRK",0.0D);
        rates.put("HUF",0.0D);
        rates.put("IDR",0.0D);
        rates.put("ILS",0.0D);
        rates.put("INR",0.0D);
        rates.put("JPY",0.0D);
        rates.put("KRW",0.0D);
        rates.put("MXN",0.0D);
        rates.put("MYR",0.0D);
        rates.put("NOK",0.0D);
        rates.put("NZD",0.0D);
        rates.put("PHP",0.0D);
        rates.put("PLN",0.0D);
        rates.put("RON",0.0D);
        rates.put("RUB",0.0D);
        rates.put("SEK",0.0D);
        rates.put("SGD",0.0D);
        rates.put("THB",0.0D);
        rates.put("TRY",0.0D);
        rates.put("USD",0.0D);
        rates.put("ZAR",0.0D);
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Double> rates) {
        this.rates = rates;
    }

    public static LatestRates getInstance(){
        return instance;
    }
}
