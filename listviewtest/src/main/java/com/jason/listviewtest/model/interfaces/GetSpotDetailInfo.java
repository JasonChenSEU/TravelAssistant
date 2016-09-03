package com.jason.listviewtest.model.interfaces;

import com.jason.listviewtest.model.beans.SpotDetailInfoFromBAIDU;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jason on 2016/8/29.
 */
public interface GetSpotDetailInfo {
    @GET("travel_attractions")
    Observable<SpotDetailInfoFromBAIDU> dataByRxJava_Detail(@Query("id") String id, @Query("ak") String ak, @Query("output") String output);
}
