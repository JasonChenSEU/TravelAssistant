package com.jason.listviewtest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jason.listviewtest.model.beans.Spot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016/7/6.
 */
public class TravelDB {

    private final String DB_NAME = "TravelDataBase";
    private final int VERSION = 1;

    public static TravelDB mTravelDB;
    private SQLiteDatabase mDataBase;

    private TravelDB(Context context) {
       TravelDBTableHelper helper = new TravelDBTableHelper(context,DB_NAME,null,VERSION);
        mDataBase = helper.getWritableDatabase();
    }

    public static synchronized TravelDB getInstance(Context context){
        if(mTravelDB == null)
            mTravelDB = new TravelDB(context);

        return mTravelDB;
    }

    public void saveSpot(Spot spot){
        if(spot != null){
            ContentValues values = new ContentValues();
            values.put("spot_name", spot.getStrSpotName());
            values.put("spot_nameEn", spot.getStrSpotNameEn());
            values.put("spot_queryId", spot.getStrQueryID());
            values.put("spot_seasonRecmd", spot.getStrSpotSeasonRecmd());
            values.put("spot_travelTips", spot.getStrSpotTravelTips());
            values.put("spot_imgURL", spot.getStrSpotImgUrl());
            values.put("spot_abstract", spot.getStrAbstract());
            values.put("spot_address", spot.getStrAddress());
            values.put("spot_telephone", spot.getStrTel());
            values.put("spot_openTime", spot.getStrOpenTime());
            values.put("spot_ticketInfo", spot.getStrTicketInfo());
            values.put("spot_description", spot.getStrDescription());
            values.put("spot_province", spot.getStrSpotProvince());
            values.put("spot_city", spot.getStrSpotCity());
            values.put("spot_bookURL", spot.getStrBookUrl());
            values.put("spot_star", spot.getStrStar());
            values.put("spot_SDUpdateTime", spot.getStrSpotUpdateTime());
            values.put("spot_ticketUpdateTime", spot.getStrTicketUpdateTime());
            values.put("spot_style", spot.getStrSpotStyle());
            mDataBase.insert("Spot",null,values);
        }
    }

    public List<Spot> rawQuerySpot(String strSQL){
        List<Spot> list = new ArrayList<>();
        Cursor cursor = mDataBase.rawQuery(strSQL, null);
        if(cursor.moveToNext()){
            do{
                Spot spot = new Spot();
                spot.setStrSpotName(cursor.getString(cursor.getColumnIndex("spot_name")));
                spot.setStrSpotNameEn(cursor.getString(cursor.getColumnIndex("spot_nameEn")));
                spot.setStrQueryID(cursor.getString(cursor.getColumnIndex("spot_queryId")));
                spot.setStrSpotSeasonRecmd(cursor.getString(cursor.getColumnIndex("spot_seasonRecmd")));
                spot.setStrSpotTravelTips(cursor.getString(cursor.getColumnIndex("spot_travelTips")));
                spot.setStrSpotImgUrl(cursor.getString(cursor.getColumnIndex("spot_imgURL")));
                spot.setStrAbstract(cursor.getString(cursor.getColumnIndex("spot_abstract")));
                spot.setStrAddress(cursor.getString(cursor.getColumnIndex("spot_address")));
                spot.setStrTel(cursor.getString(cursor.getColumnIndex("spot_telephone")));
                spot.setStrOpenTime(cursor.getString(cursor.getColumnIndex("spot_openTime")));
                spot.setStrTicketInfo(cursor.getString(cursor.getColumnIndex("spot_ticketInfo")));
                spot.setStrDescription(cursor.getString(cursor.getColumnIndex("spot_description")));
                spot.setStrSpotProvince(cursor.getString(cursor.getColumnIndex("spot_province")));
                spot.setStrSpotCity(cursor.getString(cursor.getColumnIndex("spot_city")));
                spot.setStrBookUrl(cursor.getString(cursor.getColumnIndex("spot_bookURL")));
                spot.setStrStar(cursor.getString(cursor.getColumnIndex("spot_star")));
                spot.setStrSpotUpdateTime(cursor.getString(cursor.getColumnIndex("spot_SDUpdateTime")));
                spot.setStrTicketUpdateTime(cursor.getString(cursor.getColumnIndex("spot_ticketUpdateTime")));
                spot.setStrSpotStyle(cursor.getString(cursor.getColumnIndex("spot_style")));
                list.add(spot);
            }while(cursor.moveToNext());
        }
        if(cursor != null)
            cursor.close();
        return list;
    }
}
