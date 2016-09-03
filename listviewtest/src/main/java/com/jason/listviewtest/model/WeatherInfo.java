package com.jason.listviewtest.model;

import java.util.List;

/**
 * Created by Jason on 2016/5/1.
 */
public class WeatherInfo {

    /*Current Weather*/
    private String strCurCity = null;
    private String strCurWeek = null;
    private String strCurDate = null;
    private String strCurTemp = null;
    private String strCurWeather = null;
    private String strCurWind = null;
    private String strCurPM = null;
    private String strImgUrl = null;

    /*Weather Day1*/
    private String strWeek1 = null;
    private String strWeather1 = null;
    private String strTemp1 = null;
    private String strWind1 = null;
    private String strImgUrl1 = null;

    /*Weather Day2*/
    private String strWeek2 = null;
    private String strTemp2 = null;
    private String strWeather2 = null;
    private String strWind2 = null;
    private String strImgUrl2 = null;

    /*Weather Day3*/
    private String strWeek3 = null;
    private String strWeather3 = null;
    private String strTemp3 = null;
    private String strWind3 = null;
    private String strImgUrl3 = null;

    public String getStrUpdateTime() {
        return strUpdateTime;
    }

    public void setStrUpdateTime(String strUpdateTime) {
        this.strUpdateTime = strUpdateTime;
    }

    private String strUpdateTime = null;


    /*get series*/
    public String getStrCurCity(){return strCurCity;}
    public String getStrCurWeek(){return strCurWeek;}
    public String getStrCurDate(){return strCurDate;}
    public String getStrCurTemp(){return strCurTemp;}
    public String getStrCurWeather() {
        return strCurWeather;
    }
    public String getStrPM(){return strCurPM;}
    public String getStrCurWind(){return strCurWind;}
    public String getStrImgUrl(){return strImgUrl;}

    public String getStrWeather1(){return strWeather1;}
    public String getStrWeek1(){return strWeek1;}
    public String getStrTemp1(){return strTemp1;}
    public String getStrWind1(){return strWind1;}
    public String getStrImgUrl1(){return strImgUrl1;}

    public String getStrWeather2(){return strWeather2;}
    public String getStrWeek2(){return strWeek2;}
    public String getStrTemp2(){return strTemp2;}
    public String getStrWind2(){return strWind2;}
    public String getStrImgUrl2(){return strImgUrl2;}

    public String getStrWeather3(){return strWeather3;}
    public String getStrWeek3(){return strWeek3;}
    public String getStrTemp3(){return strTemp3;}
    public String getStrWind3(){return strWind3;}
    public String getStrImgUrl3(){return strImgUrl3;}


    /*set series*/
    public void setStrCurCity(String s){this.strCurCity = s;}
    public void setStrCurWeek(String s){this.strCurWeek = s;}
    public void setStrCurDate(String s){this.strCurDate = s;}
    public void setStrCurTemp(String s){this.strCurTemp = s;}
    public void setStrCurPM(String s){this.strCurPM = s;}
    public void setStrCurWind(String s){this.strCurWind = s;}
    public void setStrImgUrl(String s){this.strImgUrl = s;}

    public void setStrWeek1(String s){this.strWeek1 = s;}
    public void setStrWeather1(String s){this.strWeather1 = s;}
    public void setStrTemp1(String s){this.strTemp1 = s;}
    public void setStrWind1(String s){this.strWind1 = s;}
    public void setStrImgUrl1(String s){this.strImgUrl1 = s;}

    public void setStrWeek2(String s){this.strWeek2 = s;}
    public void setStrWeather2(String s){this.strWeather2 = s;}
    public void setStrTemp2(String s){this.strTemp2 = s;}
    public void setStrWind2(String s){this.strWind2 = s;}
    public void setStrImgUrl2(String s){this.strImgUrl2 = s;}

    public void setStrWeek3(String s){this.strWeek3 = s;}
    public void setStrWeather3(String s){this.strWeather3 = s;}
    public void setStrTemp3(String s){this.strTemp3 = s;}
    public void setStrWind3(String s){this.strWind3 = s;}
    public void setStrImgUrl3(String s){this.strImgUrl3 = s;}

    public void setStrCurWeather(String strCurWeather) {
        this.strCurWeather = strCurWeather;
    }

    public String print()
    {
        String s = strCurCity + strCurDate + strCurWeek + strWeek1 + strWeek2 + strWeek3;
        return s ;
    }

    public void buildWithRawInfo(WeatherRawInfo info){
        WeatherRawInfo.ResultsBean cityResult = info.getResults().get(0);
        this.setStrCurCity(cityResult.getCurrentCity());
        this.setStrCurDate(info.getDate());

        if(!cityResult.getPm25().isEmpty())
            this.setStrCurPM("PM2.5: " + cityResult.getPm25());
        else
            this.setStrCurPM("PM2.5: N/A");

        List<WeatherRawInfo.ResultsBean.WeatherDataBean> list = cityResult.getWeather_data();
        if(list.size() == 4){

            //实时温度
            String strTemp = cityResult.getWeather_data().get(0).getDate();
            String strNew = strTemp.substring(strTemp.indexOf("：")+1, strTemp.lastIndexOf(")"));
            this.setStrCurWeek(strNew.replace('℃','°'));

            this.setStrWeek1(list.get(1).getDate());
            this.setStrWeek2(list.get(2).getDate());
            this.setStrWeek3(list.get(3).getDate());

            this.setStrCurWeather(list.get(0).getWeather());
            this.setStrWeather1(list.get(1).getWeather());
            this.setStrWeather2(list.get(2).getWeather());
            this.setStrWeather3(list.get(3).getWeather());

            this.setStrCurTemp(list.get(0).getTemperature());
            this.setStrTemp1(list.get(1).getTemperature());
            this.setStrTemp2(list.get(2).getTemperature());
            this.setStrTemp3(list.get(3).getTemperature());

            this.setStrCurWind(list.get(0).getWind());
            this.setStrWind1(list.get(1).getWind());
            this.setStrWind2(list.get(2).getWind());
            this.setStrWind3(list.get(3).getWind());

            //ImgPicUrl
            String strTempUrl = list.get(0).getDayPictureUrl();
            String strNeed = strTempUrl.substring(strTempUrl.lastIndexOf("/")+1,strTempUrl.length());
            this.setStrImgUrl(strNeed);

            strTempUrl = list.get(1).getDayPictureUrl();
            strNeed = strTempUrl.substring(strTempUrl.lastIndexOf("/")+1,strTempUrl.length());
            this.setStrImgUrl1(strNeed);

            strTempUrl = list.get(2).getDayPictureUrl();
            strNeed = strTempUrl.substring(strTempUrl.lastIndexOf("/")+1,strTempUrl.length());
            this.setStrImgUrl2(strNeed);

            strTempUrl = list.get(3).getDayPictureUrl();
            strNeed = strTempUrl.substring(strTempUrl.lastIndexOf("/")+1,strTempUrl.length());
            this.setStrImgUrl3(strNeed);
        }


    }
}
