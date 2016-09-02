package com.jason.listviewtest.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jason.listviewtest.Helpter.APIHelper;
import com.jason.listviewtest.R;
import com.jason.listviewtest.model.WeatherFutureView;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.weather_currentCity)
    TextView tvCurrentCity;
    @BindView(R.id.weather_currentDate)
    TextView tvDate;
    @BindView(R.id.weather_currentWeather)
    TextView tvCurWeather;
    @BindView(R.id.weather_currentWind)
    TextView tvCurWind;
    @BindView(R.id.weather_temperature)
    TextView tvCurtemp;
    @BindView(R.id.weather_pm25)
    TextView tvCurPM;
    @BindView(R.id.weather_updateTime)
    TextView tvUpdateTime;

    @BindView(R.id.weather_pictureUrl)
    ImageView img_current_weather;

    @BindView(R.id.weather_future_day1)
    WeatherFutureView futureViewDay1;
    @BindView(R.id.weather_future_day2)
    WeatherFutureView futureViewDay2;
    @BindView(R.id.weather_future_day3)
    WeatherFutureView futureViewDay3;

    private String strCurrentHandle;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Weather");
        }

        ButterKnife.bind(this);

        strCurrentHandle = getIntent().getStringExtra("Name");

        if(strCurrentHandle != null){
            handleData(strCurrentHandle);
        }else {
            strCurrentHandle = "北京";
            handleData("北京");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
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
                futureViewDay1.setData(info.getStrWeek1(),info.getStrWeather1(),bitmap, info.getStrTemp1());

                is = getAssets().open(strUrl2);
                bitmap=BitmapFactory.decodeStream(is);
                futureViewDay2.setData(info.getStrWeek2(),info.getStrWeather2(),bitmap, info.getStrTemp2());

                is = getAssets().open(strUrl3);
                bitmap=BitmapFactory.decodeStream(is);
                futureViewDay3.setData(info.getStrWeek3(),info.getStrWeather3(),bitmap, info.getStrTemp3());

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
