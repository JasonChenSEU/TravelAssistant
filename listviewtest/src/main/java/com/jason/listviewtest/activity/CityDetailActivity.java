package com.jason.listviewtest.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jason.listviewtest.R;
import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.imageloader.ImageLoader;
import com.jason.listviewtest.model.City;

import java.util.ArrayList;
import java.util.List;

public class CityDetailActivity extends AppCompatActivity {

    private static final String TAG = "CityDetailActivity";
    private ViewPager mViewPager; //ViewPager
    private List<View> mPagerViews; // Views for viewPager
    private List<String> mPagerTitles; //Titles for viewPager

    //Layout TextViews
    private TextView mTvCityName;
    private TextView mTvCityProvince;
    private TextView mTvCitySimpleDes;
    private TextView mTvCityCode;
    private TextView mTvCityPostcode;

    //ImageLoader
    private ImageLoader imageLoader;

    //Header Layout
    private LinearLayout mLayout;

    private LinearLayout mTileSpot;
    private LinearLayout mTileWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        mViewPager = (ViewPager) findViewById(R.id.city_detail_pager);

        //Parse xml for city data
        Utils.initCityList(getApplicationContext());

        //instance of imageLoader
        imageLoader = ImageLoader.build(CityDetailActivity.this);

        Intent i = getIntent();

        City city = Utils.listCity.get(i.getIntExtra("CityPos",0));

        //Prepare three views for viewpager
        View view1 = getLayoutInflater().inflate(R.layout.city_detail_view_des,null);
        TextView tv1 = (TextView) view1.findViewById(R.id.city_des_detail);
        tv1.setText("       " + city.getStrCityGeneralDes());
        tv1.setMovementMethod(new ScrollingMovementMethod());

        View view2 = getLayoutInflater().inflate(R.layout.city_detail_view_des,null);
        TextView tv2 = (TextView) view2.findViewById(R.id.city_des_detail);
        tv2.setText("       " + city.getStrRouteRecmd().replaceAll("@","\n       "));
        tv2.setMovementMethod(new ScrollingMovementMethod());

        View view3 = getLayoutInflater().inflate(R.layout.city_detail_view_des,null);
        TextView tv3 = (TextView) view3.findViewById(R.id.city_des_detail);
        tv3.setText("       " + city.getStrTimeRecmd());
        tv3.setMovementMethod(new ScrollingMovementMethod());


        //Set header layout text
        initAndSetOtherTextViews(city);

        mPagerViews = new ArrayList<>();
        mPagerViews.add(view1);
        mPagerViews.add(view2);
        mPagerViews.add(view3);

        mPagerTitles = new ArrayList<>();
        mPagerTitles.add("城市概述");
        mPagerTitles.add("游览路线");
        mPagerTitles.add("最佳旅游时间");

        //Viewpager's adapter
        PagerAdapter pa = new PagerAdapter() {
            @Override
            public int getCount() {
                return mPagerViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mPagerViews.get(position));
                return mPagerViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mPagerViews.get(position));
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mPagerTitles.get(position);
            }
        };

        mViewPager.setAdapter(pa);
    }

    private void initAndSetOtherTextViews(final City city) {
        mLayout = (LinearLayout) findViewById(R.id.city_detail_layout_image);
        mTvCityName = (TextView) findViewById(R.id.city_detail_name);
        mTvCityProvince = (TextView) findViewById(R.id.city_detail_province);
        mTvCitySimpleDes = (TextView) findViewById(R.id.city_detail_simpledes);
        mTvCityCode = (TextView) findViewById(R.id.city_detail_code);
        mTvCityPostcode = (TextView) findViewById(R.id.city_detail_postcode);

        imageLoader.bindBitmap(city.getStrURL(),mLayout,0,0);

        mTvCityName.setText(city.getStrCityName());
        mTvCityProvince.setText(city.getStrProvince());
        mTvCitySimpleDes.setText(city.getStrCitySimpleDes());
        mTvCityCode.setText("区 号： " + city.getStrCityCode());
        mTvCityPostcode.setText("邮 编： " + city.getStrPostCode());

        mTileSpot = (LinearLayout) findViewById(R.id.city_detail_spot);
        ((ImageView)mTileSpot.findViewById(R.id.tile_image_view)).
                setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.spot));
        ((TextView)mTileSpot.findViewById(R.id.tile_title)).setText("景 点");
        mTileSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CityDetailActivity.this, CityQueryListActivity.class);
                i.putExtra("CityProvince", city.getStrProvince());
                i.putExtra("CityName", city.getStrCityName());
                startActivity(i);
            }
        });

        mTileWeather = (LinearLayout) findViewById(R.id.city_detail_weather);
        ((ImageView)mTileWeather.findViewById(R.id.tile_image_view)).
                setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.weather));
        ((TextView)mTileWeather.findViewById(R.id.tile_title)).setText("天 气");
        mTileWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CityDetailActivity.this, WeatherActivity.class);
                i.putExtra("Name", city.getStrCityName());
                startActivity(i);
            }
        });

    }

    private City getCity(String strCityName) {
        for (City city:Utils.listCity) {
            if(city.getStrCityName().equals(strCityName)){
                return city;
            }
        }
        return null;
    }
}
