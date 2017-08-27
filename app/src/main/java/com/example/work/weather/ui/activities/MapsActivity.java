package com.example.work.weather.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.work.weather.R;
import com.example.work.weather.WeatherApp;
import com.example.work.weather.presenter.MapsPresenter;
import com.example.work.weather.ui.viewstate.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.work.weather.utills.StringUtils.LATITUDE;
import static com.example.work.weather.utills.StringUtils.LONGITUDE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,MapView {

    private GoogleMap mMap;
    @BindView(R.id.etSearchCity) EditText etSearchCity;
    @BindView(R.id.etLat) EditText etLat;
    @BindView(R.id.etLong) EditText etLong;
    @BindView(R.id.bCity) Button bCity;
    @BindView(R.id.bSearch) Button bSearch;
    @Inject MapsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ButterKnife.bind(this);
        WeatherApp.getAppComponent().inject(this);
        presenter.onAtach(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(latLng -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(getString(R.string.Add_location)+" "+getString(R.string.latitude)+" "+String.valueOf(latLng.latitude)+","+getString(R.string.longitude)+" "+String.valueOf(latLng.longitude)+"?");
            alert.setPositiveButton(getString(R.string.yes), ((dialog,withButton)->{
                Intent intent = new Intent();
                intent.putExtra(LATITUDE, latLng.latitude);
                intent.putExtra(LONGITUDE, latLng.longitude);
                setResult(0,intent);
                finish();
            })).setNegativeButton(getString(R.string.no),((dialog,withButton)->{
            }));
            alert.show();
    });
    }

    @OnClick(R.id.bSearch)
    public void bSearchClick(View view) {
        if(etLong.getText().toString().length()==0&&etLat.getText().toString().length()==0){
            etLong.setError(getString(R.string.empty_field));
            etLat.setError(getString(R.string.empty_field));
            return;
        }
        if(etLong.getText().toString().length()==0){
            etLong.setError(getString(R.string.empty_field));
            return;
        }
        if(etLat.getText().toString().length()==0){
            etLat.setError(getString(R.string.empty_field));
            return;
        }
        try{
        double lat=Double.valueOf(etLat.getText().toString());
        double lon=Double.valueOf(etLong.getText().toString());
        setCoord(lat,lon);
        }catch (NumberFormatException e){
            Toast.makeText(MapsActivity.this,getString(R.string.error_format),Toast.LENGTH_LONG).show();
            etLat.setText("");
            etLong.setText("");
        }
    }

    @OnClick(R.id.bCity)
    public void bCityClick(View view) {
        String address=etSearchCity.getText().toString();
        if(address.length()!=0){
            presenter.getAddressCoord(address);
        }
    }

    @Override
    public void setMessage(String message) {
        Toast.makeText(MapsActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setCoord(double lat, double lon) {
        LatLng cord = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cord,11));
    }
}
