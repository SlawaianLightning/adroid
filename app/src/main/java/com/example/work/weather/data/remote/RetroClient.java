package com.example.work.weather.data.remote;

import com.example.work.weather.utills.StringUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;


public class RetroClient {
    private static final String baseUrl= StringUtils.WEATHER_URL;
    private static final String adrresUrl= StringUtils.ADRRES_URL;
    private static Retrofit getRetrofitInstaceWeather(){
        return new Retrofit.Builder().baseUrl(baseUrl).
                addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static ApiService getApiServiceWeather(){
        return getRetrofitInstaceWeather().create(ApiService.class);
    }

    private static Retrofit getRetrofitInstaceAdrres(){
        return new Retrofit.Builder().baseUrl(adrresUrl).
                addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ApiService getApiServiceAdrres(){
        return getRetrofitInstaceAdrres().create(ApiService.class);
    }
}
