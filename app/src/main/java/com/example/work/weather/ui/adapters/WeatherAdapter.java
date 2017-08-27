package com.example.work.weather.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.work.weather.R;
import com.example.work.weather.data.entities.weatherData.WeatherDataByHours;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends  RecyclerView.Adapter{
    private List<WeatherDataByHours> weatherDataByHoursArrayList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;
    public WeatherAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.weather_data_by_hours, parent, false);
        return new WeatherDataByHoursViewHolder(v);
    }

    public void updateWeatherData(List<WeatherDataByHours> list ){
        weatherDataByHoursArrayList.clear();
        weatherDataByHoursArrayList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WeatherDataByHoursViewHolder contactsViewHolder=(WeatherDataByHoursViewHolder)holder;
        contactsViewHolder.tvTime.setText(String.valueOf(weatherDataByHoursArrayList.get(position).getTime())+".00");
        contactsViewHolder.tvTemperature.setText(String.valueOf(weatherDataByHoursArrayList.get(position).getTemperature())+"°");
        String icon=weatherDataByHoursArrayList.get(position).getIcon();
        if(icon.equals("01d")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p01d);
        }
        if(icon.equals("02d")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p02d);
        }
        if(icon.equals("03d")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p03d);
        }
        if(icon.equals("04d")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p04d);
        }
        if(icon.equals("09d")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p09d);
        }
        if(icon.equals("10d")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p10d);
        }
        if(icon.equals("11d")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p11d);
        }
        if(icon.equals("13d")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p13d);
        }
        if(icon.equals("50d")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p50d);
        }
        if(icon.equals("01n")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p01n);
        }
        if(icon.equals("02n")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p02n);
        }
        if(icon.equals("03n")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p03n);
        }
        if(icon.equals("04n")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p04n);
        }
        if(icon.equals("09n")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p09n);
        }
        if(icon.equals("10n")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p10n);
        }
        if(icon.equals("11n")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p11n);
        }
        if(icon.equals("13n")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p13n);
        }
        if(icon.equals("50n")){
            contactsViewHolder.ivWeather.setBackgroundResource(R.drawable.p50n);
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return weatherDataByHoursArrayList.size();
    }

    static class WeatherDataByHoursViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvTime) TextView tvTime;
        @BindView(R.id.tvTemperature) TextView tvTemperature;
        @BindView(R.id.ivWeather) ImageView ivWeather;

        WeatherDataByHoursViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
