package com.example.work.weather.data;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.work.weather.WeatherApp;
import com.example.work.weather.data.entities.weatherData.WeatherData;
import com.example.work.weather.data.entities.weatherData.WeatherDataByCity;
import com.example.work.weather.data.entities.weatherData.WeatherDataByHours;
import com.example.work.weather.data.entities.addresData.AddresData;
import com.example.work.weather.data.local.DBHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.example.work.weather.WeatherApp.apiServiseAddres;
import static com.example.work.weather.WeatherApp.apiServiseWeather;
import static com.example.work.weather.utills.StringUtils.CITY_ID;
import static com.example.work.weather.utills.StringUtils.CITY_NAME;
import static com.example.work.weather.utills.StringUtils.CLOUDS;
import static com.example.work.weather.utills.StringUtils.COLUMN_ID_CITY;
import static com.example.work.weather.utills.StringUtils.COUNTRY_NAME;
import static com.example.work.weather.utills.StringUtils.DATE_UPDATE;
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

public class DataManager {
    @Inject DBHelper dbHelper;

    public DataManager(){
        WeatherApp.getAppComponent().inject(this);
    }

    public Observable<WeatherData> getWeatherInformation(String city){
        return apiServiseWeather.getWeatherInformation(city);
    }

    public Observable<WeatherData> getWeatherInformation(double lat,double lon){
        return apiServiseWeather.getWeatherInformation(lat,lon);
    }

    public Observable<AddresData> getAddressData(String address){
        return apiServiseAddres.getAddres(address);
    }

    public void insertCity(WeatherDataByCity weatherDataByCity){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put(COLUMN_ID_CITY,weatherDataByCity.getCityid());
        cv.put(CITY_NAME,weatherDataByCity.getCity());
        cv.put(COUNTRY_NAME,weatherDataByCity.getCountry());
        cv.put(DESCRIPTION,weatherDataByCity.getDescription());
        cv.put(PRESSURE,weatherDataByCity.getPressure());
        cv.put(HUMIDITY,weatherDataByCity.getHumidity());
        cv.put(CLOUDS,weatherDataByCity.getClouds());
        cv.put(WIND,weatherDataByCity.getWind());
        cv.put(TEMP_MAX,weatherDataByCity.getTempMax());
        cv.put(TEMP_MIN,weatherDataByCity.getTempMin());
        cv.put(LATITUDE,weatherDataByCity.getLat());
        cv.put(LONGITUDE,weatherDataByCity.getLon());
        cv.put(DATE_UPDATE,weatherDataByCity.getDate());
        db.insert(TABLE_NAME_CITY, null, cv);
        for(int i=0;i<weatherDataByCity.getWeathers().size();i++){
            insertWeatherDataByHours(weatherDataByCity.getWeathers().get(i),weatherDataByCity.getCityid());
        }
    }

    private void insertWeatherDataByHours(WeatherDataByHours weatherDataByHours,int cityid){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put(TEMPERATURE,weatherDataByHours.getTemperature());
        cv.put(TIME,weatherDataByHours.getTime());
        cv.put(ICON,weatherDataByHours.getIcon());
        cv.put(CITY_ID,cityid);
        db.insert(TABLE_NAME_LIST_WEATHER, null, cv);
    }

    public List<WeatherDataByCity> getWeatherDataByCities(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<WeatherDataByCity> weatherDataByCities=new ArrayList<>();
        Cursor c = db.query(TABLE_NAME_CITY, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int city_index=c.getColumnIndex(COLUMN_ID_CITY);
            int city_name=c.getColumnIndex(CITY_NAME);
            int country_name=c.getColumnIndex(COUNTRY_NAME);
            int description=c.getColumnIndex(DESCRIPTION);
            int ptessure=c.getColumnIndex(PRESSURE);
            int humidity=c.getColumnIndex(HUMIDITY);
            int clouds=c.getColumnIndex(CLOUDS);
            int wind=c.getColumnIndex(WIND);
            int temp_max=c.getColumnIndex(TEMP_MAX);
            int temp_min=c.getColumnIndex(TEMP_MIN);
            int lat=c.getColumnIndex(LATITUDE);
            int lon=c.getColumnIndex(LONGITUDE);
            int date=c.getColumnIndex(DATE_UPDATE);
            do {
                WeatherDataByCity weatherDataByCity=new WeatherDataByCity();
                weatherDataByCity.setCityid(c.getInt(city_index));
                weatherDataByCity.setCity(c.getString(city_name));
                weatherDataByCity.setCountry(c.getString(country_name));
                weatherDataByCity.setDescription(c.getString(description));
                weatherDataByCity.setPressure(c.getDouble(ptessure));
                weatherDataByCity.setHumidity(c.getDouble(humidity));
                weatherDataByCity.setClouds(c.getDouble(clouds));
                weatherDataByCity.setWind(c.getDouble(wind));
                weatherDataByCity.setWeathers(getWeatherDataByHours(c.getInt(city_index)));
                weatherDataByCity.setTempMax(c.getInt(temp_max));
                weatherDataByCity.setTempMin(c.getInt(temp_min));
                weatherDataByCity.setLat(c.getDouble(lat));
                weatherDataByCity.setLon(c.getDouble(lon));
                weatherDataByCity.setDate(c.getString(date));
                weatherDataByCities.add(weatherDataByCity);
            } while (c.moveToNext());}
            return weatherDataByCities;
    }

    private List<WeatherDataByHours> getWeatherDataByHours(int cityId){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<WeatherDataByHours> weatherDataByHoursList=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME_LIST_WEATHER+" where "+CITY_ID+"="+String.valueOf(cityId),null);
        if (cursor.moveToFirst()) {
            int temperature=cursor.getColumnIndex(TEMPERATURE);
            int time=cursor.getColumnIndex(TIME);
            int icon=cursor.getColumnIndex(ICON);
            do {
              weatherDataByHoursList.add(new WeatherDataByHours(cursor.getInt(temperature),cursor.getInt(time),cursor.getString(icon)));
            } while (cursor.moveToNext());}
        return weatherDataByHoursList;
    }

    public void updateWeatheData(WeatherDataByCity weatherDataByCity){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put(COLUMN_ID_CITY,weatherDataByCity.getCityid());
        cv.put(CITY_NAME,weatherDataByCity.getCity());
        cv.put(COUNTRY_NAME,weatherDataByCity.getCountry());
        cv.put(DESCRIPTION,weatherDataByCity.getDescription());
        cv.put(PRESSURE,weatherDataByCity.getPressure());
        cv.put(HUMIDITY,weatherDataByCity.getHumidity());
        cv.put(CLOUDS,weatherDataByCity.getClouds());
        cv.put(WIND,weatherDataByCity.getWind());
        cv.put(TEMP_MAX,weatherDataByCity.getTempMax());
        cv.put(TEMP_MIN,weatherDataByCity.getTempMin());
        cv.put(LATITUDE,weatherDataByCity.getLat());
        cv.put(LONGITUDE,weatherDataByCity.getLon());
        cv.put(DATE_UPDATE,weatherDataByCity.getDate());
        db.update(TABLE_NAME_CITY, cv, CITY_NAME+"='"+weatherDataByCity.getCity()+
                "' and "+COUNTRY_NAME+"='"+weatherDataByCity.getCountry()+"'",null);
        delete(weatherDataByCity.getCityid());
        for(int i=0;i<weatherDataByCity.getWeathers().size();i++){
            insertWeatherDataByHours(weatherDataByCity.getWeathers().get(i),weatherDataByCity.getCityid());
        }
    }

    private void delete(int cityid){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME_LIST_WEATHER,CITY_ID+"="+String.valueOf(cityid),null);
    }


}
