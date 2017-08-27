package com.example.work.weather.data.entities.weatherData;

public class WeatherDataByHours {
    private int time;
    private String icon;
    private int temperature;

    public WeatherDataByHours(int temperature,int time,String icon){
        this.temperature=temperature;
        this.time=time;
        this.icon=icon;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time=time;
    }

    public int getTemperature(){
        return temperature;
    }

    public void setTemperature(int temperature){
        this.temperature=temperature;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon=icon;
    }
}
