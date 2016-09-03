package com.jason.listviewtest.model.interfaces;

import com.jason.listviewtest.model.beans.SpotTicketInfoFromQUNAER;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jason on 2016/8/27.
 */
public interface GetSpotTicketInfo {
    @GET("querydetail")
    Observable<SpotTicketInfoFromQUNAER> dataByRxJava(@Header("apikey") String apikey, @Query("id") String id);
}
