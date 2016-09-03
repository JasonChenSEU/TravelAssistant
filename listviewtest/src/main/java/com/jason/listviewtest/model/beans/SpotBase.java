package com.jason.listviewtest.model.beans;

/**
 * Created by Jason on 2016/7/6.
 */
public class SpotBase {
    //Basic info about spot, get from xml
    public String strSpotName;
    public String strQueryID = "-1";

    private String strSpotNameEn;
    private String strSpotSeasonRecmd;
    private String strSpotTravelTips;
    private String strSpotImgUrl;
    private String strSpotStyle;
    private String strSpotProvince;

    public String getStrSpotName() {
        return strSpotName;
    }

    public void setStrSpotName(String strSpotName) {
        this.strSpotName = strSpotName;
    }

    public String getStrQueryID() {
        return strQueryID;
    }

    public void setStrQueryID(String strQueryID) {
        this.strQueryID = strQueryID;
    }

    public String getStrSpotNameEn() {
        return strSpotNameEn;
    }

    public void setStrSpotNameEn(String strSpotNameEn) {
        this.strSpotNameEn = strSpotNameEn;
    }

    public String getStrSpotSeasonRecmd() {
        return strSpotSeasonRecmd;
    }

    public void setStrSpotSeasonRecmd(String strSpotSeasonRecmd) {
        this.strSpotSeasonRecmd = strSpotSeasonRecmd;
    }

    public String getStrSpotTravelTips() {
        return strSpotTravelTips;
    }

    public void setStrSpotTravelTips(String strSpotTravelTips) {
        this.strSpotTravelTips = strSpotTravelTips;
    }

    public String getStrSpotImgUrl() {
        return strSpotImgUrl;
    }

    public void setStrSpotImgUrl(String strSpotImgUrl) {
        this.strSpotImgUrl = strSpotImgUrl;
    }

    public String getStrSpotStyle() {
        return strSpotStyle;
    }

    public void setStrSpotStyle(String strSpotStyle) {
        this.strSpotStyle = strSpotStyle;
    }

    public String getStrSpotProvince() {
        return strSpotProvince;
    }

    public void setStrSpotProvince(String strSpotProvince) {
        this.strSpotProvince = strSpotProvince;
    }
}
