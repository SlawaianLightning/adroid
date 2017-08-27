package com.example.work.weather.presenter;

import android.content.Context;

import com.example.work.weather.R;
import com.example.work.weather.WeatherApp;
import com.example.work.weather.data.DataManager;
import com.example.work.weather.data.entities.addresData.AddresData;
import com.example.work.weather.ui.viewstate.MapView;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MapsPresenter {
    @Inject DataManager dataManager;
    @Inject Context context;
    private MapView view;

    public MapsPresenter(){
        WeatherApp.getAppComponent().inject(this);
    }

    public void onAtach(MapView view){
        this.view=view;
    }

    public void getAddressCoord(String addres){
            dataManager.
                    getAddressData(addres).
                    onErrorResumeNext(
                            Observable.create(s -> {
                                s.onNext(new AddresData());
                                s.onCompleted();}
                            )).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(s -> {
                        if(s.getStatus()==null){
                            view.setMessage(context.getString(R.string.connection_error));
                            return;
                        }
                        if (s != null && s.getStatus() != null&&s.getResults().size()!=0) {
                            view.setCoord(s.getResults().get(0).getGeometry().getBounds().getNortheast().getLat(),
                                    s.getResults().get(0).getGeometry().getBounds().getNortheast().getLng());
                        } else {
                            view.setMessage(context.getString(R.string.no_such_address));
                        }
                    });
    }

}
