package com.example.work.weather;

import android.app.Application;

import com.example.work.weather.data.remote.ApiService;
import com.example.work.weather.data.remote.RetroClient;
import com.example.work.weather.injection.AppComponents;
import com.example.work.weather.injection.AppModule;
import com.example.work.weather.injection.DaggerAppComponents;

public class WeatherApp extends Application{
    private static AppComponents sAppComponent;
    public static ApiService apiServiseWeather= RetroClient.getApiServiceWeather();
    public static ApiService apiServiseAddres= RetroClient.getApiServiceAdrres();
    public static AppComponents getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponents.builder().appModule(new AppModule(this)).build();
    }
}
