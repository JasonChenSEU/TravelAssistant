package com.jason.listviewtest.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jason.listviewtest.Helpter.APIHelper;
import com.jason.listviewtest.R;
import com.jason.listviewtest.model.WeatherInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

public class WeatherActivity extends AppCompatActivity {

    TextView tvCurrentCity;
    TextView tvDate;
    TextView tvCurWeather;
    TextView tvCurWind;
    TextView tvCurtemp;
    TextView tvCurPM;

    TextView tvUpdateTime;

    ImageView img_current_weather;

    LinearLayout mFutureDay1;
    LinearLayout mFutureDay2;
    LinearLayout mFutureDay3;

    TextView tvDay1Week;
    TextView tvDay1Weather;
    TextView tvDay1Temp;
    ImageView imgWea1;

    TextView tvDay2Week;
    TextView tvDay2Weather;
    TextView tvDay2Temp;
    ImageView imgWea2;

    TextView tvDay3Week;
    TextView tvDay3Weather;
    TextView tvDay3Temp;
    ImageView imgWea3;

    private String strCurrentHandle;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initViews();

        strCurrentHandle = getIntent().getStringExtra("Name");

        if(strCurrentHandle != null){
            handleData(strCurrentHandle);
        }else {
            strCurrentHandle = "北京";
            handleData("北京");
        }
    }

    private void handleData(String strCurrentCity) {
        String strUrl = null;
        try {
            String strLocation = URLEncoder.encode(strCurrentCity,"UTF-8");

            strUrl = String.format(APIHelper.BAIDU_WEATHER_QUERY_URL_FORMAT,strLocation,APIHelper.BAIDU_KEY);

            new ProgressTask().execute(strUrl);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void initViews() {
        tvCurrentCity = (TextView)findViewById(R.id.weather_currentCity);
        tvDate = (TextView)findViewById(R.id.weather_currentDate);
        tvCurWeather = (TextView)findViewById(R.id.weather_currentWeather);
        tvCurtemp = (TextView)findViewById(R.id.weather_temperature);
        tvCurWind = (TextView)findViewById(R.id.weather_currentWind);
        tvCurPM = (TextView)findViewById(R.id.weather_pm25);
        tvUpdateTime = (TextView) findViewById(R.id.weather_updateTime);

        img_current_weather = (ImageView) findViewById(R.id.weather_pictureUrl);

        mFutureDay1 = (LinearLayout) findViewById(R.id.weather_future_day1);
        mFutureDay2 = (LinearLayout) findViewById(R.id.weather_future_day2);
        mFutureDay3 = (LinearLayout) findViewById(R.id.weather_future_day3);

        tvDay1Week = (TextView)mFutureDay1.findViewById(R.id.weather_day_Date);
        tvDay1Weather = (TextView)mFutureDay1.findViewById(R.id.weather_day_Weather);
        tvDay1Temp = (TextView)mFutureDay1.findViewById(R.id.weather_day_Temp);

        tvDay2Week = (TextView)mFutureDay2.findViewById(R.id.weather_day_Date);
        tvDay2Weather = (TextView)mFutureDay2.findViewById(R.id.weather_day_Weather);
        tvDay2Temp = (TextView)mFutureDay2.findViewById(R.id.weather_day_Temp);

        tvDay3Week = (TextView)mFutureDay3.findViewById(R.id.weather_day_Date);
        tvDay3Weather = (TextView)mFutureDay3.findViewById(R.id.weather_day_Weather);
        tvDay3Temp = (TextView)mFutureDay3.findViewById(R.id.weather_day_Temp);

        imgWea1 = (ImageView) mFutureDay1.findViewById(R.id.weather_day_Picture);
        imgWea2 = (ImageView) mFutureDay2.findViewById(R.id.weather_day_Picture);
        imgWea3 = (ImageView) mFutureDay3.findViewById(R.id.weather_day_Picture);


    }


    private class ProgressTask extends AsyncTask<String,Integer,WeatherInfo> {

        WeatherInfo info = new WeatherInfo();
        @Override
        protected WeatherInfo doInBackground(String... params) {

            StringBuffer buffer = new StringBuffer();

            try {

                //URL url = new URL(URLEncoder.encode(params[0].toString(),"gb2312").replaceAll("%3A", ":").replaceAll("%2F", "/"));

                URL url = new URL(params[0]);
                HttpURLConnection httpUrlCon = (HttpURLConnection) url.openConnection();

                httpUrlCon.setDoOutput(false);
                httpUrlCon.setDoInput(true);
                httpUrlCon.setUseCaches(false);

                httpUrlCon.setRequestMethod("GET");
                httpUrlCon.connect();

                InputStream inputStream = httpUrlCon.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                //InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");

                try {
                    parser.setInput(inputStream ,"UTF-8");
                    int eventType = parser.getEventType();
                    boolean isSuccess = true;
                    while (isSuccess && eventType != XmlPullParser.END_DOCUMENT)
                    {
                        switch(eventType)
                        {
                            case XmlPullParser.START_TAG:
                                String strTagName = parser.getName();
                                if (strTagName.equalsIgnoreCase("status")) {
                                    if(!parser.nextText().equals("success")){
                                        isSuccess = false;
                                    }
                                }
                                if (strTagName.equalsIgnoreCase("currentCity"))
                                    info.setStrCurCity(parser.nextText());
                                if(strTagName.equalsIgnoreCase("date"))
                                {
                                    if(info.getStrCurDate() == null)
                                        info.setStrCurDate(parser.nextText());
                                    else if(info.getStrCurWeek() == null)
                                    {
                                        String strTemp = parser.nextText();
                                        String NewStr=strTemp.substring(strTemp.indexOf("：")+1, strTemp.lastIndexOf(")"));
                                        info.setStrCurWeek(NewStr.replace('℃','°'));
                                    }
                                    else if(info.getStrWeek1() == null)
                                        info.setStrWeek1(parser.nextText());
                                    else if(info.getStrWeek2() == null)
                                        info.setStrWeek2(parser.nextText());
                                    else if(info.getStrWeek3() == null)
                                        info.setStrWeek3(parser.nextText());
                                }
                                else if(strTagName.equalsIgnoreCase("Weather"))
                                {
                                    if(info.getStrCurWeather() == null)
                                        info.setStrCurWeather(parser.nextText());
                                    else if(info.getStrWeather1() == null)
                                        info.setStrWeather1(parser.nextText());
                                    else if(info.getStrWeather2() == null)
                                        info.setStrWeather2(parser.nextText());
                                    else if(info.getStrWeather3() == null)
                                        info.setStrWeather3(parser.nextText());
                                }
                                else if(strTagName.equalsIgnoreCase("temperature"))
                                {
                                    if(info.getStrCurTemp() == null)
                                        info.setStrCurTemp(parser.nextText());
                                    else if(info.getStrTemp1() == null)
                                        info.setStrTemp1(parser.nextText());
                                    else if(info.getStrTemp2() == null)
                                        info.setStrTemp2(parser.nextText());
                                    else if(info.getStrTemp3() == null)
                                        info.setStrTemp3(parser.nextText());
                                }
                                else if(strTagName.equalsIgnoreCase("wind"))
                                {
                                    if(info.getStrCurWind() == null)
                                        info.setStrCurWind(parser.nextText());
                                    else if(info.getStrWind1() == null)
                                        info.setStrWind1(parser.nextText());
                                    else if(info.getStrWind2() == null)
                                        info.setStrWind2(parser.nextText());
                                    else if(info.getStrWind3() == null)
                                        info.setStrWind3(parser.nextText());
                                }
                                else if(strTagName.equalsIgnoreCase("pm25"))
                                {
                                    if(info.getStrPM() == null) {
                                        String str = parser.nextText();
                                        if(!str.isEmpty())
                                            info.setStrCurPM("PM2.5: " + str);
                                        else
                                            info.setStrCurPM("PM2.5: Unknown");
                                    }
                                }
                                else if(strTagName.equalsIgnoreCase("dayPictureUrl"))
                                {
                                    if(info.getStrImgUrl() == null)
                                    {
                                        String strTemp = parser.nextText();
                                        String strsub = strTemp.substring(strTemp.lastIndexOf("/")+1,strTemp.length());
                                        info.setStrImgUrl(strsub);
                                    }
                                    else if(info.getStrImgUrl1() == null)
                                    {
                                        String strTemp = parser.nextText();
                                        String strsub = strTemp.substring(strTemp.lastIndexOf("/")+1,strTemp.length());
                                        info.setStrImgUrl1(strsub);
                                    }
                                    else if(info.getStrImgUrl2() == null)
                                    {
                                        String strTemp = parser.nextText();
                                        String strsub = strTemp.substring(strTemp.lastIndexOf("/")+1,strTemp.length());
                                        info.setStrImgUrl2(strsub);
                                    }
                                    else if(info.getStrImgUrl3() == null)
                                    {
                                        String strTemp = parser.nextText();
                                        String strsub = strTemp.substring(strTemp.lastIndexOf("/")+1,strTemp.length());
                                        info.setStrImgUrl3(strsub);
                                    }
                                }
//                                else if(strTagName.equalsIgnoreCase("title"))
//                                    listIndexInfos.add(parser.nextText());
//                                else if(strTagName.equalsIgnoreCase("zs"))
//                                    listIndexInfos.add(parser.nextText());
//                                else if(strTagName.equalsIgnoreCase("tipt"))
//                                    listIndexInfos.add(parser.nextText());
//                                else if(strTagName.equalsIgnoreCase("des"))
//                                    listIndexInfos.add(parser.nextText());
                                //if (strTagName.equalsIgnoreCase("weather_data"))
                                //info.setStrCurTemp(parser.nextText());
                        }
                        eventType = parser.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                inputStream.close();
                httpUrlCon.disconnect();

            }catch (IOException e){
                e.printStackTrace();
            }

            return info;
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(WeatherActivity.this, "Loading...","数据获取中");
        }

        @Override
        protected void onPostExecute(WeatherInfo info) {
            super.onPostExecute(info);

            if(info.getStrCurCity() == null) {
                AlertDialog.Builder ab = new AlertDialog.Builder(WeatherActivity.this);
                ab.setTitle("Error");
                ab.setMessage("Cannot find weather info...");
                ab.setCancelable(false);
                ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                ab.show();
            }else {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putBoolean("recordExist", true);
                editor.putString("selected_country", info.getStrCurCity());
                editor.commit();

            }

            tvCurrentCity.setText(info.getStrCurCity());
            tvDate.setText(info.getStrCurDate());
            tvCurWeather.setText(info.getStrCurWeather());
            tvCurtemp.setText(info.getStrCurWeek());
            tvCurWind.setText(info.getStrCurWind());
            tvCurPM.setText(info.getStrPM());


            tvDay1Week.setText(info.getStrWeek1());
            tvDay1Weather.setText(info.getStrWeather1());
            tvDay1Temp.setText(info.getStrTemp1());

            tvDay2Week.setText(info.getStrWeek2());
            tvDay2Weather.setText(info.getStrWeather2());
            tvDay2Temp.setText(info.getStrTemp2());

            tvDay3Week.setText(info.getStrWeek3());
            tvDay3Weather.setText(info.getStrWeather3());
            tvDay3Temp.setText(info.getStrTemp3());

            String strUrl = "WeatherPic/"+info.getStrImgUrl();
            String strUrl1 = "WeatherPic/"+info.getStrImgUrl1();
            String strUrl2 = "WeatherPic/"+info.getStrImgUrl2();
            String strUrl3 = "WeatherPic/"+info.getStrImgUrl3();

            InputStream is;
            try {
                is = getAssets().open(strUrl);
                Bitmap bitmap= BitmapFactory.decodeStream(is);
                img_current_weather.setImageBitmap(bitmap);

                is = getAssets().open(strUrl1);
                bitmap=BitmapFactory.decodeStream(is);
                imgWea1.setImageBitmap(bitmap);

                is = getAssets().open(strUrl2);
                bitmap=BitmapFactory.decodeStream(is);
                imgWea2.setImageBitmap(bitmap);

                is = getAssets().open(strUrl3);
                bitmap=BitmapFactory.decodeStream(is);
                imgWea3.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String date = sDateFormat.format(new java.util.Date());

            tvUpdateTime.setText(date + " 发布");

            mProgressDialog.dismiss();

//            pB.setVisibility(View.INVISIBLE);
        }
    }
}
