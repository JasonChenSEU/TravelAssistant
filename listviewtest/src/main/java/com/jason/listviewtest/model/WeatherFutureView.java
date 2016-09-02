package com.jason.listviewtest.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jason.listviewtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jason on 2016/9/2.
 */
public class WeatherFutureView extends LinearLayout {

    @BindView(R.id.weather_day_Date)
    TextView tvDate;
    @BindView(R.id.weather_day_Picture)
    ImageView imgDayPic;
    @BindView(R.id.weather_day_Weather)
    TextView tvDayWeather;
    @BindView(R.id.weather_day_Temp)
    TextView tvTemp;


    public WeatherFutureView(Context context) {
        super(context);
    }

    public WeatherFutureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setData(String strDate, String strWeather, Bitmap imgPic, String strTemp){
        tvDate.setText(strDate);
        tvDayWeather.setText(strWeather);
        tvTemp.setText(strTemp);
        imgDayPic.setImageBitmap(imgPic);
    }

}
