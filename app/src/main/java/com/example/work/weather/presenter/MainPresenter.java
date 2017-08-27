package com.example.work.weather.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.example.work.weather.R;
import com.example.work.weather.WeatherApp;
import com.example.work.weather.data.DataManager;
import com.example.work.weather.data.entities.weatherData.ListWeatherInformation;
import com.example.work.weather.data.entities.weatherData.WeatherData;
import com.example.work.weather.data.entities.weatherData.WeatherDataByCity;
import com.example.work.weather.data.entities.weatherData.WeatherDataByHours;
import com.example.work.weather.ui.viewstate.MainView;
import com.example.work.weather.utills.LocListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

@InjectViewState
public class MainPresenter  extends BasePresenter<MainView>{
    @Inject DataManager dataManager;
    @Inject Context context;
    private WeatherDataByCity weatherDataByCity;
    private List<WeatherDataByCity> weatherDataByCities;
    private boolean isError=false;
    private double lat;
    private double lon;

    public MainPresenter(){
        WeatherApp.getAppComponent().inject(this);
    }

    public void ubpdateWeather(String city,String country){
        for(int i=0;i<weatherDataByCities.size();i++){
            if(weatherDataByCities.get(i).getCity().equals(city)&&weatherDataByCities.get(i).getCountry().equals(country)){
                weatherDataByCity=weatherDataByCities.get(i);
                break;
            }
        }
            dataManager.getWeatherInformation(weatherDataByCity.getLat(),weatherDataByCity.getLon()).
                onErrorResumeNext( Observable.create(s->{
                            s.onNext(new WeatherData());
                            s.onCompleted();
                        }
                )).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(s -> {
                    if(s.getList()!=null){
                        saveWeatherData(s);
                    }else {
                        getViewState().setData(weatherDataByCity);
                    }
                });
    }

    public String[][] getCity(){
        String[][] strings=new String[weatherDataByCities.size()][2];
        for(int i=0;i<weatherDataByCities.size();i++){
            strings[i][0]=weatherDataByCities.get(i).getCity();
            strings[i][1]=weatherDataByCities.get(i).getCountry();
        }
        return strings;
    }

    public boolean getIsError(){
        return isError;
    }

    public void loadWeather(){
        if(weatherDataByCities==null){
             weatherDataByCities=dataManager.getWeatherDataByCities();
        }
    }

    public void ubpdateWeatherByLocation(){
        ubpdateWeatherByLocation(true);
    }

    public void ubpdateWeatherByLocation(boolean defaultValue){
        if(weatherDataByCity==null){
        isError=true;
        LocListener.setUpLocationListener(context);
        if(LocListener.imHere!=null){
        double lat=LocListener.imHere.getLatitude();
        double lon=LocListener.imHere.getLongitude();
        addLocation(lat,lon,defaultValue);
    }else {
            if(weatherDataByCities.size()!=0&&defaultValue){
                getViewState().setData(weatherDataByCities.get(0));
            }else{
                getViewState().onError(context.getString(R.string.connection_error));
                getViewState().setData(null);
                lon=0;
                lat=0;
                weatherDataByCity=null;
            }
    }
        }else {
            if(weatherDataByCity!=null){
            getViewState().setData(weatherDataByCity);}
        }
    }

    public void clearData(){
        weatherDataByCity=null;
    }

    public void ubpdateWeatherByLocationOnError(){
        if(lat==0&&lon==0){
            ubpdateWeatherByLocation(false);
        }else {
        addLocation(lat,lon);}
    }

    public void addLocation(double lat,double lng) {
        addLocation(lat,lng,false);
    }

    public void addLocation(double lat,double lng,boolean defaultValue){
        dataManager.getWeatherInformation(lat,lng).
                onErrorResumeNext( Observable.create(s->{
                            s.onNext(new WeatherData());
                            s.onCompleted();
                        }
                )).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(s -> {
                    if(s.getList()!=null){
                        if(s.getCity()!=null&&s.getCity().getCountry()!=null){
                            saveWeatherData(s);
                            isError=false;
                        }else {
                            if(weatherDataByCities.size()!=0){
                                getViewState().setData(weatherDataByCities.get(0),context.getString(R.string.not_found));
                                getViewState().setData(weatherDataByCities.get(0),null);
                                weatherDataByCity=weatherDataByCities.get(0);
                            }
                        }
                    }else {
                        if(weatherDataByCities.size()!=0&&defaultValue){
                            getViewState().setData(weatherDataByCities.get(0));
                        }else{
                            this.lat=lat;
                            this.lon=lng;
                            isError=true;
                            getViewState().onError(context.getString(R.string.connection_error));
                            weatherDataByCity=null;
                            getViewState().setData(null);
                        }

                    }
                });
    }

    public void loadCity(double lat,double lng){
        for(int i=0;i<weatherDataByCities.size();i++){
            if(weatherDataByCities.get(i).getLat()==lat&&weatherDataByCities.get(i).getLon()==lng){
                getViewState().setData(weatherDataByCities.get(i));
            }
        }
    }

    private void saveWeatherData(WeatherData data){
        WeatherDataByCity weatherDataByCity=new WeatherDataByCity();
        weatherDataByCity.setCityid((int)data.getCity().getId());
        weatherDataByCity.setCity(data.getCity().getName());
        weatherDataByCity.setCountry(data.getCity().getCountry());
        weatherDataByCity.setDescription(data.getList().get(0).getWeather().get(0).getDescription());
        weatherDataByCity.setPressure(data.getList().get(0).getMain().getPressure());
        weatherDataByCity.setHumidity(data.getList().get(0).getMain().getHumidity());
        weatherDataByCity.setClouds(data.getList().get(0).getClouds().getAll());
        weatherDataByCity.setWind(data.getList().get(0).getWind().getSpeed());
        weatherDataByCity.setTempMax((int)(data.getList().get(0).getMain().getTempMax()-273.15));
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd.MM HH:mm");
        weatherDataByCity.setDate(f.format(d));
        weatherDataByCity.setTempMin((int)(data.getList().get(0).getMain().getTempMin()-273.15));
        weatherDataByCity.setLat(data.getCity().getCoord().getLat());
        weatherDataByCity.setLon(data.getCity().getCoord().getLon());
        List<WeatherDataByHours> weatherDataByHoursList=new ArrayList<WeatherDataByHours>();
        List<ListWeatherInformation> list=data.getList();
        for (ListWeatherInformation l:list){
            weatherDataByHoursList.add(new WeatherDataByHours(((int)(l.getMain().getTemp()-273.15)),(int) ((l.getDt()-(l.getDt()/86400)*86400)/3600),l.getWeather().get(0).getIcon()));
        }
        weatherDataByCity.setWeathers(weatherDataByHoursList);
        this.weatherDataByCity=weatherDataByCity;
        getViewState().setData(weatherDataByCity);
        for(int i=0;i<weatherDataByCities.size();i++){
            if(weatherDataByCities.get(i).getCity().equals(weatherDataByCity.getCity())&&
                    weatherDataByCities.get(i).getCountry().equals(weatherDataByCity.getCountry())){
                dataManager.updateWeatheData(weatherDataByCity);
                weatherDataByCities.remove(i);
                weatherDataByCities.add(i,weatherDataByCity);
                return;
            }
        }
        weatherDataByCities.add(weatherDataByCity);
        dataManager.insertCity(weatherDataByCity);
    }


}
