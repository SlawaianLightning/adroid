package com.example.work.weather.ui.activities;


import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.work.weather.R;
import com.example.work.weather.data.entities.weatherData.WeatherDataByCity;
import com.example.work.weather.presenter.MainPresenter;
import com.example.work.weather.ui.adapters.WeatherAdapter;
import com.example.work.weather.ui.viewstate.MainView;
import com.example.work.weather.utills.WeatherService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.work.weather.utills.StringUtils.LATITUDE;
import static com.example.work.weather.utills.StringUtils.LONGITUDE;

public class MainActivity extends MvpAppCompatActivity implements MainView{

    @InjectPresenter MainPresenter presenter;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.rvWeather) RecyclerView rvWeather;
    @BindView(R.id.tvTemperature) TextView tvTemperature;
    @BindView(R.id.tvTempMax) TextView tvTempMax;
    @BindView(R.id.tvTempMin) TextView tvTempMin;
    @BindView(R.id.tvLabel) TextView tvLabel;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.tvDescriptionLabel) TextView tvDescriptionLabel;
    @BindView(R.id.tvPressure) TextView tvPressure;
    @BindView(R.id.tvPressureLabel) TextView tvPressureLabel;
    @BindView(R.id.tvHumidity) TextView tvHumidity;
    @BindView(R.id.tvHumidityLabel) TextView tvHumidityLabel;
    @BindView(R.id.tvClouds) TextView tvClouds;
    @BindView(R.id.tvCloudsLabel) TextView tvCloudsLabel;
    @BindView(R.id.tvWind) TextView tvWind;
    @BindView(R.id.tvWindLabel) TextView tvWindLabel;
    @BindView(R.id.ivFon) ImageView ivFon;
    @BindView(R.id.ivTempMin) ImageView ivTempMin;
    @BindView(R.id.ivTempMax) ImageView ivTempMax;
    @BindView(R.id.v1) View v1;
    @BindView(R.id.v2) View v2;
    @BindView(R.id.v3) View v3;
    @BindView(R.id.v4) View v4;
    @BindView(R.id.v5) View v5;
    @BindView(R.id.v6) View v6;
    @BindView(R.id.v7) View v7;
    @BindView(R.id.v8) View v8;
    @BindView(R.id.pbUpdated) ProgressBar pbUpdated;
    @BindView(R.id.tvTextError) TextView tvTextError;
    @BindView(R.id.bError) Button bError;
    @BindView(R.id.llError) LinearLayout llError;
    private WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitle("");
        tvTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvTitle.setSingleLine(true);
        tvTitle.setMarqueeRepeatLimit(-1);
        tvTitle.setSelected(true);
        startService(new Intent(MainActivity.this, WeatherService.class));
        weatherAdapter=new WeatherAdapter(this);
        LinearLayoutManager lvm=new LinearLayoutManager(this);
        lvm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvWeather.setLayoutManager(lvm);
        rvWeather.setAdapter(weatherAdapter);
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        pbUpdated.setX((int)(((double)size.x)/2.25));
        pbUpdated.setY((int)(((double)size.y)/2.25));
        pbUpdated.setEnabled(true);
        llError.setX((int)(((double)size.x)/2.75));
        llError.setY((int)(((double)size.y)/2.75));
        bError.setVisibility(View.INVISIBLE);
        bError.setEnabled(false);
        tvTextError.setVisibility(View.INVISIBLE);
        presenter.loadWeather();
        load();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(getIntent().getIntExtra(String.valueOf(1), -1));
        Intent i=getIntent();
        Bundle extras = i.getExtras();
        if(extras!=null){
        double lat= extras.getDouble(LATITUDE,0);
        double lon= extras.getDouble(LONGITUDE,0);
            if(lat!=0&&lon!=0){
        presenter.loadCity(lat,lon);
        return;}}
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    @Override
    public void setData(WeatherDataByCity data) {
        if(data!=null){
        weatherAdapter.updateWeatherData(data.getWeathers());
        tvTemperature.setText(" "+data.getWeathers().get(0).getTemperature()+"°");
        tvTempMax.setText(""+data.getTempMax()+"°");
        tvTempMin.setText(""+data.getTempMin()+"°");
        tvDescription.setText(data.getDescription());
        tvPressure.setText(String.valueOf(data.getPressure()));
        tvHumidity.setText(String.valueOf(data.getHumidity()));
        tvClouds.setText(String.valueOf(data.getClouds()));
        tvWind.setText(String.valueOf(data.getWind()));
        tvTitle.setText("   "+data.getCity()+" "+getString(R.string.updated)+" "+data.getDate());
        show();}
    }

    @Override
    public void setData(WeatherDataByCity data, String message) {
        setData(data);
        if(message!=null){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();}
    }

    @Override
    public void onError(String text) {
        load();
        pbUpdated.setVisibility(View.INVISIBLE);
        bError.setVisibility(View.VISIBLE);
        bError.setEnabled(true);
        tvTextError.setVisibility(View.VISIBLE);
        tvTextError.setText(text);
    }

    private void load(){
        tvTitle.setText("");
        rvWeather.setVisibility(View.INVISIBLE);
        tvTemperature.setVisibility(View.INVISIBLE);
        tvTempMax.setVisibility(View.INVISIBLE);
        tvTempMin.setVisibility(View.INVISIBLE);
        tvLabel.setVisibility(View.INVISIBLE);
        tvDescription.setVisibility(View.INVISIBLE);
        tvDescriptionLabel.setVisibility(View.INVISIBLE);
        tvPressure.setVisibility(View.INVISIBLE);
        tvPressureLabel.setVisibility(View.INVISIBLE);
        tvHumidity.setVisibility(View.INVISIBLE);
        tvHumidityLabel.setVisibility(View.INVISIBLE);
        tvClouds.setVisibility(View.INVISIBLE);
        tvCloudsLabel.setVisibility(View.INVISIBLE);
        tvWind.setVisibility(View.INVISIBLE);
        tvWindLabel.setVisibility(View.INVISIBLE);
        v1.setVisibility(View.INVISIBLE);
        v2.setVisibility(View.INVISIBLE);
        v3.setVisibility(View.INVISIBLE);
        v4.setVisibility(View.INVISIBLE);
        v5.setVisibility(View.INVISIBLE);
        v6.setVisibility(View.INVISIBLE);
        v7.setVisibility(View.INVISIBLE);
        v8.setVisibility(View.INVISIBLE);
        ivFon.setVisibility(View.INVISIBLE);
        ivTempMin.setVisibility(View.INVISIBLE);
        ivTempMax.setVisibility(View.INVISIBLE);
        bError.setVisibility(View.INVISIBLE);
        bError.setEnabled(false);
        tvTextError.setVisibility(View.INVISIBLE);
        pbUpdated.setVisibility(View.VISIBLE);
    }

    private void show(){
        rvWeather.setVisibility(View.VISIBLE);
        tvTemperature.setVisibility(View.VISIBLE);
        tvTempMax.setVisibility(View.VISIBLE);
        tvTempMin.setVisibility(View.VISIBLE);
        tvLabel.setVisibility(View.VISIBLE);
        tvDescription.setVisibility(View.VISIBLE);
        tvDescriptionLabel.setVisibility(View.VISIBLE);
        tvPressure.setVisibility(View.VISIBLE);
        tvPressureLabel.setVisibility(View.VISIBLE);
        tvHumidity.setVisibility(View.VISIBLE);
        tvHumidityLabel.setVisibility(View.VISIBLE);
        tvClouds.setVisibility(View.VISIBLE);
        tvCloudsLabel.setVisibility(View.VISIBLE);
        tvWind.setVisibility(View.VISIBLE);
        tvWindLabel.setVisibility(View.VISIBLE);
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.VISIBLE);
        v3.setVisibility(View.VISIBLE);
        v4.setVisibility(View.VISIBLE);
        v5.setVisibility(View.VISIBLE);
        v6.setVisibility(View.VISIBLE);
        v7.setVisibility(View.VISIBLE);
        v8.setVisibility(View.VISIBLE);
        ivFon.setVisibility(View.VISIBLE);
        ivTempMin.setVisibility(View.VISIBLE);
        ivTempMax.setVisibility(View.VISIBLE);
        pbUpdated.setVisibility(View.INVISIBLE);
        bError.setVisibility(View.INVISIBLE);
        bError.setEnabled(false);
        tvTextError.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.bError)
    public void onErrorClick(View view) {
        load();
        presenter.ubpdateWeatherByLocationOnError();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.change_city) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            String data[][] = presenter.getCity();
            String[] listData=new String[data.length];
            for(int i=0;i<data.length;i++){
                listData[i]=data[i][0]+","+data[i][1];            }
            DialogInterface.OnClickListener onItemClickListener =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.ubpdateWeather(data[which][0],data[which][1]);
                            load();
                        }
                    };
            builder.setItems(listData, onItemClickListener);
            builder.show();

        }
        if(id == R.id.addCity){
            Intent intent=new Intent(MainActivity.this,MapsActivity.class);
            startActivityForResult(intent,0);
        }
        if(id==R.id.updateWeatherByLocation){
            load();
            presenter.clearData();
            presenter.ubpdateWeatherByLocation(false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (data == null) {return;}
        double lat=data.getExtras().getDouble(LATITUDE);
        double lng=data.getExtras().getDouble(LONGITUDE);
        load();
        presenter.addLocation(lat,lng);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(!presenter.getIsError()){
                    presenter.ubpdateWeatherByLocation();}
                }
                return;
            }
        }
    }
}
