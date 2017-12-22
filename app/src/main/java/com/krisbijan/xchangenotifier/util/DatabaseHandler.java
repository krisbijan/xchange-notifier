package com.krisbijan.xchangenotifier.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler sInstance;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "xchange_notifier";

    // Contacts table name
    private static final String TABLE_ALERTS = "alerts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST = "firstCurrency";
    private static final String KEY_SECOND = "secondCurrency";
    private static final String KEY_RATE = "rate";
    private static final String KEY_OVER_UNDER = "over_under";

    public static synchronized DatabaseHandler getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ALERT_TABLE = "CREATE TABLE " + TABLE_ALERTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRST + " TEXT,"
                + KEY_SECOND + " TEXT, " + KEY_RATE + " REAL, " + KEY_OVER_UNDER + " INTEGER" + ")";
        Log.d (this.getClass().getName(), CREATE_ALERT_TABLE);
        db.execSQL(CREATE_ALERT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALERTS);
        onCreate(db);
    }

    public void addAlert(Alert alert) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST, alert.getFirstCurrency());
        values.put(KEY_SECOND, alert.getSecondCurrency());
        values.put(KEY_RATE, alert.getRate());
        values.put(KEY_OVER_UNDER, alert.getOver_under());

        Log.d(this.getClass().getName(), "Adding "+alert.toString());

        db.insert(TABLE_ALERTS, null, values);
        db.close();
    }

    public List<Alert> getAllAlerts() {
        List<Alert> alertList = new ArrayList<Alert>();
        String selectQuery = "SELECT  * FROM " + TABLE_ALERTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Alert alert = new Alert();

                alert.setId(Integer.parseInt(cursor.getString(0)));
                alert.setFirstCurrency(cursor.getString(1));
                alert.setSecondCurrency(cursor.getString(2));
                alert.setRate(cursor.getDouble(3));
                alert.setOver_under(cursor.getInt(4));

                alertList.add(alert);
            } while (cursor.moveToNext());
        }
        Log.d(this.getClass().getName(), "Reading all alerts.. "+alertList);

        return alertList;
    }

    public int getAlertsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ALERTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public Alert getAlert(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALERTS, new String[] { KEY_ID, KEY_FIRST, KEY_SECOND, KEY_RATE, KEY_OVER_UNDER }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Alert alert = new Alert(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getDouble(3),cursor.getInt(4));

        return alert;
    }

    public int updateAlert(Alert alert) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST, alert.getFirstCurrency());
        values.put(KEY_SECOND, alert.getSecondCurrency());
        values.put(KEY_RATE, alert.getRate());
        values.put(KEY_OVER_UNDER, alert.getOver_under());

        return db.update(TABLE_ALERTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(alert.getId()) });
    }
}
