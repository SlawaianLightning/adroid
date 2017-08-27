package com.example.work.weather.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context appContex;
    public AppModule(@NonNull Context context){
        appContex=context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return appContex;
    }
}
