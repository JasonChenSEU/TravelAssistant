package com.jason.listviewtest.model.interfaces;

import com.jason.listviewtest.model.beans.WeatherRawInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jason on 2016/9/3.
 */
public interface GetWeatherInfo {
    @GET("weather")
    Observable<WeatherRawInfo> getWeatherRawInfo(@Query("location") String location, @Query("ak") String ak, @Query("output") String output);

}
