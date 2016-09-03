package com.jason.listviewtest.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jason.listviewtest.helper.APIHelper;
import com.jason.listviewtest.R;
import com.jason.listviewtest.model.interfaces.GetWeatherInfo;
import com.jason.listviewtest.view.WeatherFutureView;
import com.jason.listviewtest.model.beans.WeatherInfo;
import com.jason.listviewtest.model.beans.WeatherRawInfo;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.weather_currentCity)
    TextView tvCurrentCity;
    @BindView(R.id.weather_currentDate)
    TextView tvDate;
    @BindView(R.id.weather_currentWeather)
    TextView tvCurWeather;
    @BindView(R.id.weather_currentWind)
    TextView tvCurWind;
    @BindView(R.id.weather_temperature)
    TextView tvCurtemp;
    @BindView(R.id.weather_pm25)
    TextView tvCurPM;
    @BindView(R.id.weather_updateTime)
    TextView tvUpdateTime;

    @BindView(R.id.weather_pictureUrl)
    ImageView img_current_weather;

    @BindView(R.id.weather_future_day1)
    WeatherFutureView futureViewDay1;
    @BindView(R.id.weather_future_day2)
    WeatherFutureView futureViewDay2;
    @BindView(R.id.weather_future_day3)
    WeatherFutureView futureViewDay3;

    private String strCurrentHandle;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Weather");
        }

        ButterKnife.bind(this);

        strCurrentHandle = getIntent().getStringExtra("Name");

        if(strCurrentHandle != null){
            handleData(strCurrentHandle);
        }else {
            strCurrentHandle = "北京";
            handleData("北京");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void handleData(String strCurrentCity) {
        getDataWithRetrofit(strCurrentCity);
    }

    private void getDataWithRetrofit(String strLocation) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIHelper.BAIDU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GetWeatherInfo info = retrofit.create(GetWeatherInfo.class);
        info.getWeatherRawInfo(strLocation, APIHelper.BAIDU_KEY, "json")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mProgressDialog = ProgressDialog.show(WeatherActivity.this, "Loading...", "数据获取中");
                    }
                })
                .observeOn(Schedulers.newThread())
                .map(new Func1<WeatherRawInfo, WeatherInfo>() {
                    @Override
                    public WeatherInfo call(WeatherRawInfo weatherRawInfo) {
                        WeatherInfo info = new WeatherInfo();
                        info.buildWithRawInfo(weatherRawInfo);
                        return info;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherInfo>() {
                    @Override
                    public void onCompleted() {
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Error", e.getMessage());
                        Toast.makeText(WeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onNext(WeatherInfo weatherInfo) {
                        updateUI(weatherInfo);
                    }

                });
    }

    private void updateUI(WeatherInfo info) {

        if(info.getStrCurCity() == null) {
            AlertDialog.Builder ab = new AlertDialog.Builder(WeatherActivity.this);
            ab.setTitle("Error");
            ab.setMessage("Cannot find weather info...");
            ab.setCancelable(false);
            ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            ab.show();
        }

        tvCurrentCity.setText(info.getStrCurCity());
        tvDate.setText(info.getStrCurDate());
        tvCurWeather.setText(info.getStrCurWeather());
        tvCurtemp.setText(info.getStrCurWeek());
        tvCurWind.setText(info.getStrCurWind());
        tvCurPM.setText(info.getStrPM());

        String strUrl = "WeatherPic/"+info.getStrImgUrl();
        String strUrl1 = "WeatherPic/"+info.getStrImgUrl1();
        String strUrl2 = "WeatherPic/"+info.getStrImgUrl2();
        String strUrl3 = "WeatherPic/"+info.getStrImgUrl3();

        InputStream is;
        try {
            is = getAssets().open(strUrl);
            Bitmap bitmap= BitmapFactory.decodeStream(is);
            img_current_weather.setImageBitmap(bitmap);

            is = getAssets().open(strUrl1);
            bitmap=BitmapFactory.decodeStream(is);
            futureViewDay1.setData(info.getStrWeek1(),info.getStrWeather1(),bitmap, info.getStrTemp1());

            is = getAssets().open(strUrl2);
            bitmap=BitmapFactory.decodeStream(is);
            futureViewDay2.setData(info.getStrWeek2(),info.getStrWeather2(),bitmap, info.getStrTemp2());

            is = getAssets().open(strUrl3);
            bitmap=BitmapFactory.decodeStream(is);
            futureViewDay3.setData(info.getStrWeek3(),info.getStrWeather3(),bitmap, info.getStrTemp3());

        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());

        tvUpdateTime.setText(date + " 发布");

    }
}
