package com.example.work.weather.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.work.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends RecyclerView.Adapter{
    private List<String> strings = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;

    public CityAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.city, parent, false);
        return new CityAdapter.CityViewHolder(v);
    }

    public void updateCities(List<String> list ){
        strings.clear();
        strings.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CityViewHolder cityViewHolder=(CityViewHolder)holder;
        cityViewHolder.tvCity.setText(strings.get(position));
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    static class CityViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvCity) TextView tvCity;

        CityViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
