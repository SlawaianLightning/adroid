package com.example.work.weather.injection;


import com.example.work.weather.data.DataManager;
import com.example.work.weather.presenter.MainPresenter;
import com.example.work.weather.presenter.MapsPresenter;
import com.example.work.weather.ui.activities.MapsActivity;
import com.example.work.weather.utills.WeatherService;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, ReceiversModule.class})
@Singleton
public interface AppComponents {
    public void inject(MainPresenter presenter);
    public void inject(DataManager dataManager);
    public void inject(MapsPresenter presenter);
    public void inject(MapsActivity mapsActivity);
    public void inject(WeatherService weatherService);
}
