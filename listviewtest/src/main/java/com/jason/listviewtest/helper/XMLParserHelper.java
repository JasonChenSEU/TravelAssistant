package com.jason.listviewtest.helper;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.jason.listviewtest.model.beans.City;
import com.jason.listviewtest.R;
import com.jason.listviewtest.model.beans.Spot;
import com.jason.listviewtest.model.beans.SpotBase;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jason on 2016/6/29.
 */
public class XMLParserHelper {

    private static final String TAG = "XMLParserHelper";

    public static List<City> ParseXMLFromCityData(Context context) {
        List<City> list = new ArrayList<>();
        try {
            XmlResourceParser parser = context.getResources().getXml(R.xml.city_data);
            int eventType = parser.getEventType();

            while (eventType != XmlResourceParser.END_DOCUMENT) {
                String strNodeName = parser.getName();
                if (eventType == XmlResourceParser.START_TAG && "CityData".equals(strNodeName)) {

                    City city = new City();

                    while (!(eventType == XmlResourceParser.END_TAG && parser.getName().equals("CityData"))) {
                        eventType = parser.next();
                        switch (eventType){
                            case XmlResourceParser.START_TAG:
                                if("CityProvince".equals(parser.getName())){
                                    String strProvince = parser.nextText();
                                    city.setStrProvince(strProvince);

                                    //Set RankNumber
                                    Map<String,Integer> map = Utils.getMapProvince();
                                    if((map.get(strProvince) != null)) {
                                        city.setRankNum(map.get(strProvince));
                                    }else{
                                        int size = map.size();
                                        map.put(strProvince, size);
                                        city.setRankNum(size);
                                    }
                                }else if("CityName".equals(parser.getName())){
                                    city.setStrCityName(parser.nextText());
                                }else if("CitySimpleDes".equals(parser.getName())){
                                    city.setStrCitySimpleDes(parser.nextText());
                                }else if("CityImgUri".equals(parser.getName())){
                                    city.setStrURL(parser.nextText());
                                }else if("CityDescription".equals(parser.getName())){
                                    city.setStrCityGeneralDes(parser.nextText());
                                }else if("CityRouteRcmd".equals(parser.getName())){
                                    city.setStrRouteRecmd(parser.nextText());
                                }else if("CityTimeRcmd".equals(parser.getName())){
                                    city.setStrTimeRecmd(parser.nextText());
                                }else if("CityCode".equals(parser.getName())){
                                    city.setStrCityCode(parser.nextText());
                                }else if("CityPostCode".equals(parser.getName())){
                                    city.setStrPostCode(parser.nextText());
                                }
                                break;
                        }
                    }
                    list.add(city);

                }
                eventType = parser.next();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<SpotBase> ParseXMLFromSpotData(Context context){
        List<SpotBase> list = new ArrayList<>();
        try {
            XmlResourceParser parser = context.getResources().getXml(R.xml.spot_data);
            int eventType = parser.getEventType();

            while (eventType != XmlResourceParser.END_DOCUMENT) {
                String strNodeName = parser.getName();
                if (eventType == XmlResourceParser.START_TAG && "SpotData".equals(strNodeName)) {

                    Spot spot = new Spot();

                    while (!(eventType == XmlResourceParser.END_TAG && parser.getName().equals("SpotData"))) {
                        eventType = parser.next();
                        switch (eventType){
                            case XmlResourceParser.START_TAG:
                                if("SpotName".equals(parser.getName())){
                                    spot.setStrSpotName(parser.nextText());
                                }else if("SpotNameEn".equals(parser.getName())){
                                    spot.setStrSpotNameEn(parser.nextText());
                                }else if("SpotSeasonRecmd".equals(parser.getName())){
                                    spot.setStrSpotSeasonRecmd(parser.nextText());
                                }else if("SpotTravelTips".equals(parser.getName())) {
                                    spot.setStrSpotTravelTips(parser.nextText());
                                }else if("SpotQueryID".equals(parser.getName())){
                                    spot.setStrQueryID(parser.nextText());
                                }else if("SpotImgUri".equals(parser.getName())){
                                    spot.setStrSpotImgUrl(parser.nextText());
                                }else if("SpotStyle".equals(parser.getName())){
                                    spot.setStrSpotStyle(parser.nextText());
                                }else if("SpotProvince".equals(parser.getName())){
                                    spot.setStrSpotProvince(parser.nextText());
                                }
                                break;
                        }
                    }
                    list.add(spot);

                }
                eventType = parser.next();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return list;
    }
}
