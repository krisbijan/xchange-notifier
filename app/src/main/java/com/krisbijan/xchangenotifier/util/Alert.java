package com.krisbijan.xchangenotifier.util;


import java.util.ArrayList;

public class Alert {

    public static ArrayList<Alert> allAlerts = new ArrayList<Alert>();

    private int id;

    //rate is defined firstCurrency/secondCurrency
    private String firstCurrency;
    private String secondCurrency;
    private double rate;

    //if 1, alert is over
    private int over_under;


    public Alert(int id, String firstCurrency, String secondCurrency, double rate, int over_under) {
        this.id = id;
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.rate = rate;
        this.over_under = over_under;
    }

    public Alert(String firstCurrency, String secondCurrency, double rate, int over_under) {
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.rate = rate;
        this.over_under = over_under;
    }

    public Alert(String firstCurrency, String secondCurrency, double rate, String over_under) {
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.rate = rate;

        if (over_under.equalsIgnoreCase("over"))
            this.over_under = 1;
        else
            this.over_under = 0;

    }

    public Alert() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstCurrency() {
        return firstCurrency;
    }

    public void setFirstCurrency(String firstCurrency) {
        this.firstCurrency = firstCurrency;
    }

    public String getSecondCurrency() {
        return secondCurrency;
    }

    public void setSecondCurrency(String secondCurrency) {
        this.secondCurrency = secondCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getOver_under() {
        return over_under;
    }

    public void setOver_under(int over_under) {

        if (over_under==0 || over_under==1)
            this.over_under = over_under;
        else
            this.over_under = 0;

    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", firstCurrency='" + firstCurrency + '\'' +
                ", secondCurrency='" + secondCurrency + '\'' +
                ", rate=" + rate +
                ", over_under=" + over_under +
                '}';
    }

    public static ArrayList<Alert> getAllAlerts() {
        return allAlerts;
    }

    public static void setAllAlerts(ArrayList<Alert> allAlerts) {
        Alert.allAlerts = allAlerts;
    }
}
