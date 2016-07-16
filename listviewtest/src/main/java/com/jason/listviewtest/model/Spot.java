package com.jason.listviewtest.model;

/**
 * Created by Jason on 2016/7/6.
 */
public class Spot extends SpotBase{

    private String strAbstract;
    private String strAddress;
    private String strTel;
    private String strOpenTime;
    private String strTicketInfo;
    private String strDescription;

    private String strSpotCity;

    private String strBookUrl;

    private String strStar;

    private String strSpotUpdateTime;
    private String strTicketUpdateTime;

    public Spot(){}

    public Spot(SpotBase spotBase){
        this.setStrSpotName(spotBase.getStrSpotName());
        this.setStrSpotNameEn(spotBase.getStrSpotNameEn());
        this.setStrSpotSeasonRecmd(spotBase.getStrSpotSeasonRecmd());
        this.setStrSpotTravelTips(spotBase.getStrSpotTravelTips());
        this.setStrSpotImgUrl(spotBase.getStrSpotImgUrl());
        this.setStrQueryID(spotBase.getStrQueryID());
    }


    public String getStrAbstract() {
        return strAbstract;
    }

    public void setStrAbstract(String strAbstract) {
        this.strAbstract = strAbstract;
    }

    public String getStrAddress() {
        return strAddress;
    }

    public void setStrAddress(String strAddress) {
        this.strAddress = strAddress;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrTel() {
        return strTel;
    }

    public void setStrTel(String strTel) {
        this.strTel = strTel;
    }

    public String getStrOpenTime() {
        return strOpenTime;
    }

    public void setStrOpenTime(String strOpenTime) {
        this.strOpenTime = strOpenTime;
    }

    public String getStrTicketInfo() {
        return strTicketInfo;
    }

    public void setStrTicketInfo(String strTicketInfo) {
        this.strTicketInfo = strTicketInfo;
    }



    public String getStrSpotCity() {
        return strSpotCity;
    }

    public void setStrSpotCity(String strSpotCity) {
        this.strSpotCity = strSpotCity;
    }

    public String getStrBookUrl() {
        return strBookUrl;
    }

    public void setStrBookUrl(String strBookUrl) {
        this.strBookUrl = strBookUrl;
    }

    public String getStrStar() {
        return strStar;
    }

    public void setStrStar(String strStar) {
        this.strStar = strStar;
    }

    public String getStrTicketUpdateTime() {
        return strTicketUpdateTime;
    }

    public void setStrTicketUpdateTime(String strTicketUpdateTime) {
        this.strTicketUpdateTime = strTicketUpdateTime;
    }

    public String getStrSpotUpdateTime() {
        return strSpotUpdateTime;
    }

    public void setStrSpotUpdateTime(String strSpotUpdateTime) {
        this.strSpotUpdateTime = strSpotUpdateTime;
    }
}
