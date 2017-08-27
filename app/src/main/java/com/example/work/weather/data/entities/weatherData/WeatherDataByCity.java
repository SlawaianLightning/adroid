package com.example.work.weather.data.entities.weatherData;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataByCity {
    private int cityid;
    private String city;
    private String country;
    private String description;
    private double pressure;
    private double humidity;
    private double clouds;
    private double lat;
    private double lon;
    private double wind;
    private int tempMax;
    private int tempMin;
    private String date;
    private List<WeatherDataByHours> weathers;

    public void setCityid(int cityid){
        this.cityid=cityid;
    }

    public int getCityid(){
        return cityid;
    }
    public void setCity(String city){
        this.city=city;
    }

    public String getCity(){
        return city;
    }

    public void setCountry(String country){
        this.country=country;
    }

    public String getCountry(){
        return country;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public String getDescription(){
        return description;
    }

    public void setPressure(double pressure){
        this.pressure=pressure;
    }

    public double getPressure(){
        return pressure;
    }

    public void setHumidity(double humidity){
        this.humidity=humidity;
    }

    public double getHumidity(){
        return humidity;
    }

    public double getClouds(){
        return clouds;
    }

    public void setClouds(double clouds){
        this.clouds=clouds;
    }

    public void setWind(double wind){
        this.wind=wind;
    }

    public double getWind(){
        return wind;
    }

    public void setWeathers(List<WeatherDataByHours> weathers){
        if(this.weathers==null){
            this.weathers=new ArrayList<>();
        }
        this.weathers.clear();
        this.weathers.addAll(weathers);
    }

    public void setTempMax(int tempMax){
        this.tempMax=tempMax;
    }

    public int getTempMax(){
        return tempMax;
    }

    public void setTempMin(int tempMin){
        this.tempMin=tempMin;
    }

    public int getTempMin(){
        return tempMin;
    }

    public void setDate(String date){
        this.date=date;
    }

    public String getDate(){
        return date;
    }

    public List<WeatherDataByHours> getWeathers(){
        return weathers;
    }

    public void setLat(double lat){
        this.lat=lat;
    }

    public double getLat(){
        return lat;
    }

    public void setLon(double lon){
        this.lon=lon;
    }

    public double getLon(){return lon;}
}
