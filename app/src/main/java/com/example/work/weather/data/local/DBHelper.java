package com.example.work.weather.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.work.weather.utills.StringUtils.CITY_ID;
import static com.example.work.weather.utills.StringUtils.CITY_NAME;
import static com.example.work.weather.utills.StringUtils.CLOUDS;
import static com.example.work.weather.utills.StringUtils.COLUMN_ID_CITY;
import static com.example.work.weather.utills.StringUtils.COLUMN_ID_WEATHEE;
import static com.example.work.weather.utills.StringUtils.COUNTRY_NAME;
import static com.example.work.weather.utills.StringUtils.DATE_UPDATE;
import static com.example.work.weather.utills.StringUtils.DB_NAME;
import static com.example.work.weather.utills.StringUtils.DESCRIPTION;
import static com.example.work.weather.utills.StringUtils.HUMIDITY;
import static com.example.work.weather.utills.StringUtils.ICON;
import static com.example.work.weather.utills.StringUtils.LATITUDE;
import static com.example.work.weather.utills.StringUtils.LONGITUDE;
import static com.example.work.weather.utills.StringUtils.PRESSURE;
import static com.example.work.weather.utills.StringUtils.TABLE_NAME_CITY;
import static com.example.work.weather.utills.StringUtils.TABLE_NAME_LIST_WEATHER;
import static com.example.work.weather.utills.StringUtils.TEMPERATURE;
import static com.example.work.weather.utills.StringUtils.TEMP_MAX;
import static com.example.work.weather.utills.StringUtils.TEMP_MIN;
import static com.example.work.weather.utills.StringUtils.TIME;
import static com.example.work.weather.utills.StringUtils.WIND;

public class DBHelper  extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "
                +TABLE_NAME_CITY
                +" ("+COLUMN_ID_CITY+" INTEGER NOT NULL PRIMARY KEY,"+
                CITY_NAME+" text, "+
                COUNTRY_NAME+" text, "+
                DESCRIPTION+" text, "+
                PRESSURE+" real,"+
                HUMIDITY+" real, "+
                CLOUDS+" real, "+
                WIND+" real, "+
                TEMP_MAX+" integer, "+
                TEMP_MIN+" integer, "+
                LATITUDE+" real, "+
                LONGITUDE+" real, "+
        DATE_UPDATE+" text);"
        );
        sqLiteDatabase.execSQL("create table if not exists "
                +TABLE_NAME_LIST_WEATHER
                +" ("+COLUMN_ID_WEATHEE+" INTEGER NOT NULL PRIMARY KEY,"+
                TEMPERATURE+" INTEGER, "+
                TIME+" INTEGER, "+
                ICON+" text, "+
                CITY_ID+" INTEGER);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
