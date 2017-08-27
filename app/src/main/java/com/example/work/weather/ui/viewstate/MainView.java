package com.example.work.weather.ui.viewstate;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.work.weather.data.entities.weatherData.WeatherDataByCity;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    public void setData(WeatherDataByCity data);
    public void setData(WeatherDataByCity data,String message);
    public void onError(String text);
}
