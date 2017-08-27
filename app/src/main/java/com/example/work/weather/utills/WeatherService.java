package com.example.work.weather.utills;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;

import com.example.work.weather.R;
import com.example.work.weather.WeatherApp;
import com.example.work.weather.data.DataManager;
import com.example.work.weather.data.entities.weatherData.Coord;
import com.example.work.weather.data.entities.weatherData.ListWeatherInformation;
import com.example.work.weather.data.entities.weatherData.WeatherData;
import com.example.work.weather.data.entities.weatherData.WeatherDataByCity;
import com.example.work.weather.data.entities.weatherData.WeatherDataByHours;
import com.example.work.weather.ui.activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.work.weather.utills.StringUtils.LATITUDE;
import static com.example.work.weather.utills.StringUtils.LONGITUDE;


public class WeatherService extends Service{

    @Inject DataManager dataManager;
    private double lat;
    private double lng;
    private boolean isEnabled=true;

    public void onCreate() {
        super.onCreate();
        WeatherApp.getAppComponent().inject(this);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        LocListener.setUpLocationListener(getApplicationContext());
        if(LocListener.imHere!=null){
            lat=LocListener.imHere.getLatitude();
            lng=LocListener.imHere.getLongitude();}
        controlTime();
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void controlTime(){
        Observable.create(sub->{
            while (true){
                Date date = (Date) Calendar.getInstance().getTime();
           if(date.getHours()==12&&date.getMinutes()==0&&date.getSeconds()==1){
                isEnabled=true;
           }
            if(date.getHours()==12&&date.getMinutes()==0&&date.getSeconds()==0&&isEnabled){
                isEnabled=false;
                if(LocListener.imHere!=null){
                    lat=LocListener.imHere.getLatitude();
                    lng=LocListener.imHere.getLongitude();
                }
                if(lat!=0&&lng!=0){
                        Coord coord=new Coord();
                        coord.setLat(lat);
                        coord.setLon(lng);
                        sub.onNext(coord);
                        sub.onCompleted();
                }
            }
            }
        }).
                onErrorResumeNext( Observable.create(s->{
                            s.onNext(new Coord());
                            s.onCompleted();
                        }
                )).subscribeOn(Schedulers.io()).
           observeOn(AndroidSchedulers.mainThread()).subscribe(s->{
           updateWeatherByLocation(((Coord) s).getLat(),((Coord) s).getLon());
        })

        ;
    }

    private void updateWeatherByLocation(double lat,double lng){
        dataManager.getWeatherInformation(lat,lng).
                onErrorResumeNext( Observable.create(s->{
                            s.onNext(new WeatherData());
                            s.onCompleted();
                        }
                )).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(s -> {
                    if(s.getList()!=null){
                    pushNotification(s);
                    saveData(s);}
                });
    }

    private void pushNotification(WeatherData data){
        Notification.Builder builder=new Notification.Builder(WeatherService.this);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.p01d);
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("01d")){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.p01d);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("02d")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p02d);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("03d")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p03d);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("04d")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p04d);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("09d")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p09d);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("10d")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p10d);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("11d")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p11d);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("13d")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p13d);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("50d")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p50d);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("01n")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p01n);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("02n")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p02n);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("03n")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p03n);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("04n")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p04n);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("09n")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p09n);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("10n")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p10n);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("11n")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p11n);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("13n")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p13n);
        }
        if(data.getList().get(0).getWeather().get(0).getIcon().equals("50n")){
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.p50n);
        }
        builder.setLargeIcon(bm);
        builder.setSmallIcon(R.drawable.weather);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setColor(getResources().getColor(R.color.vk_share_blue_color));}
        builder.setContentTitle(getString(R.string.app_name));
        builder.setContentText(data.getCity().getName()+" "+(int)((data.getList().get(0).getMain().getTemp()-273.15))+"°C");
        Intent intent = new Intent(WeatherService.this, MainActivity.class);
        intent.putExtra(LATITUDE, data.getCity().getCoord().getLat());
        intent.putExtra(LONGITUDE, data.getCity().getCoord().getLon());
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        Notification notification=builder.build();
        long[] vibrate = new long[] {0,1000};
        notification.vibrate = vibrate;
        NotificationManagerCompat mNotificationManager =  NotificationManagerCompat.from(this);
        mNotificationManager.notify(1, notification);
    }

    private void saveData(WeatherData data){
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
    }

}
