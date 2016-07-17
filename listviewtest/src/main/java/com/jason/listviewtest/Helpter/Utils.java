package com.jason.listviewtest.Helpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.jason.listviewtest.db.TravelDB;
import com.jason.listviewtest.model.City;
import com.jason.listviewtest.model.Spot;
import com.jason.listviewtest.model.SpotBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jason on 2016/6/29.
 */
public class Utils {

    public static Map<String,Integer> mapProvince = new HashMap<>();

    public static List<City> listCity = new ArrayList<>();

    public static List<SpotBase> listSpot = new ArrayList<>();


    /**
     * Provide index of Province
     * Used only by CityList
     */
    public static void initProvinceMap(){
        if(mapProvince.size() != 0)
            return;
        String[] strProvince = {"直辖市","安徽","福建","广西","河北","河南","黑龙江","湖南","江苏","江西","内蒙古","山东","四川","西藏","新疆","云南","浙江"};
        for(int i = 0; i < strProvince.length; i++)
            mapProvince.put(strProvince[i],i);
    }

    public static Map<String, Integer> getMapProvince() {
        return mapProvince;
    }

    /**
     * 转换图片成圆形
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2 -5;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2 -5;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst_left+15, dst_top+15, dst_right-20, dst_bottom-20);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public static void initCityList(Context context) {
        if(listCity.size() != 0)
            return;
        listCity = XMLParserHelper.ParseXMLFromCityData(context);
        Collections.sort(listCity, new Comparator<City>() {
            @Override
            public int compare(City lhs, City rhs) {
                if(lhs.getRankNum() == rhs.getRankNum())
                    return lhs.getStrCityName().compareTo(rhs.getStrCityName());
                return lhs.getRankNum() < rhs.getRankNum() ? -1 : 1;
            }
        });
    }

    /**
     * @param context context
     * Init spot base List
     */
    public static void initSpotList(Context context){
        if(listSpot.size() != 0)
            return;
        listSpot = XMLParserHelper.ParseXMLFromSpotData(context);
        Collections.sort(listSpot, new Comparator<SpotBase>() {
            @Override
            public int compare(SpotBase lhs, SpotBase rhs) {
                return lhs.getStrSpotNameEn().charAt(0) - rhs.getStrSpotNameEn().charAt(0);
            }
        });
    }

//    public static void storeToDB(Context context){
//        TravelDB db = TravelDB.getInstance(context);
//        for(SpotBase spotbase : Utils.listSpot){
//            db.saveSpot(spot);
//        }
//    }

    public static SpotBase findSpot(String strSpotName){
        for(SpotBase sb : listSpot){
            if(sb.getStrSpotName().equals(strSpotName))
                return sb;
        }
        return null;
    }

    public static City findCity(String strCityName){
        for(City city : listCity){
            if(city.getStrCityName().equals(strCityName))
                return city;
        }
        return null;
    }

}
