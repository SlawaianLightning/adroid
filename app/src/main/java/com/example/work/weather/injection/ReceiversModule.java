package com.example.work.weather.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.work.weather.data.DataManager;
import com.example.work.weather.data.local.DBHelper;
import com.example.work.weather.presenter.MapsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ReceiversModule {

    @Provides
    @Singleton
    @NonNull
    public DataManager getDataManaget(){return new DataManager();}

    @Provides
    @Singleton
    @NonNull
    public DBHelper getDBHelper(Context context){return new DBHelper(context);}

    @Provides
    @Singleton
    @NonNull
    public MapsPresenter getMapsPresenter(){return new MapsPresenter();}

}
