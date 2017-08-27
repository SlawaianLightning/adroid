package com.example.work.weather.data.remote;

import com.example.work.weather.data.entities.weatherData.WeatherData;
import com.example.work.weather.data.entities.addresData.AddresData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.example.work.weather.utills.StringUtils.WEATHER_APP_ID;

public interface ApiService {

    @GET("forecast?appid="+WEATHER_APP_ID+"&")
    public Observable<WeatherData> getWeatherInformation(@Query("q")String city);

    @GET("forecast?appid="+WEATHER_APP_ID+"&")
    public Observable<List<WeatherData>> getWeatherInformations(@Query("q")String city);

    @GET("forecast?appid="+WEATHER_APP_ID+"&")
    public Observable<WeatherData> getWeatherInformation(@Query("lat")double lat,@Query("lon")double lon);

    @GET("json?")
    public Observable<AddresData> getAddres(@Query("address")String addres);
}
