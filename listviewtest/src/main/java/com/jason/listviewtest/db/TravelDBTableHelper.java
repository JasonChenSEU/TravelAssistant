package com.jason.listviewtest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jason on 2016/7/6.
 */
public class TravelDBTableHelper extends SQLiteOpenHelper {

    private static final String CREATE_SPOT_TABLE = "create table Spot ("
            + "id integer primary key autoincrement, "
            + "spot_name text, "
            + "spot_nameEn text, "
            + "spot_queryId text, "
            + "spot_seasonRecmd text, "
            + "spot_travelTips text, "
            + "spot_imgURL text, "
            + "spot_abstract text, "
            + "spot_address text, "
            + "spot_telephone text, "
            + "spot_openTime text, "
            + "spot_ticketInfo text, "
            + "spot_description text, "
            + "spot_province text, "
            + "spot_city text, "
            + "spot_bookURL text, "
            + "spot_star text, "
            + "spot_SDUpdateTime text, "
            + "spot_style text, "
            + "spot_ticketUpdateTime text)";

    public TravelDBTableHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SPOT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
