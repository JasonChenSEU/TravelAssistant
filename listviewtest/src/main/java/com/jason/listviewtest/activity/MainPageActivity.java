package com.jason.listviewtest.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.R;
import com.jason.listviewtest.imageloader.ImageLoader;
import com.jason.listviewtest.model.City;
import com.jason.listviewtest.model.SpotBase;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainPageActivity";

    private ImageView mImageView;
    private ImageLoader mImageLoader;

    private LinearLayout mLayout_hotSpot;
    private LinearLayout mLayout_Around;
    private LinearLayout mLayout_RMB_Spot;

    private String[] mHotSpots = {"鼓浪屿","庐山","九寨沟"};
    private String[] mAound = {"南京","扬州","苏州"};
    private String[] mRMBSpot = {"长江三峡","漓江","天涯海角"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_img);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.my_image);
        mImageView.setImageBitmap(Utils.toRoundBitmap(bitmap));

        initData();

        //init imageLoader
        mImageLoader = ImageLoader.build(MainPageActivity.this);

        initMainPage();
    }

    private void initData() {
        if(Utils.listCity.size() == 0){
            Utils.initCityList(MainPageActivity.this);
        }
        if(Utils.listSpot.size() == 0){
            Utils.initSpotList(MainPageActivity.this);
        }
    }

    private void initMainPage() {
        mLayout_hotSpot = (LinearLayout) findViewById(R.id.hot_spot);
        ((TextView)mLayout_hotSpot.findViewById(R.id.main_page_title)).setText("当季热门");
        ((TextView)mLayout_hotSpot.findViewById(R.id.main_page_title_en)).setText("Hot Spot");

        LinearLayout innerLayout = (LinearLayout) mLayout_hotSpot.findViewById(R.id.main_page_list);

        initMainPageContentWithList(innerLayout,0);

        mLayout_Around = (LinearLayout) findViewById(R.id.Around);
        ((TextView)mLayout_Around.findViewById(R.id.main_page_title)).setText("周边");
        ((TextView)mLayout_Around.findViewById(R.id.main_page_title_en)).setText("Around");

        LinearLayout innerLayout2 = (LinearLayout) mLayout_Around.findViewById(R.id.main_page_list);

        initMainPageContentWithList(innerLayout2,1);

        mLayout_RMB_Spot = (LinearLayout) findViewById(R.id.RMB_spot);
        ((TextView)mLayout_RMB_Spot.findViewById(R.id.main_page_title)).setText("人民币景点");
        ((TextView)mLayout_RMB_Spot.findViewById(R.id.main_page_title_en)).setText("RMB spots");

        LinearLayout innerLayout3 = (LinearLayout) mLayout_RMB_Spot.findViewById(R.id.main_page_list);

        initMainPageContentWithList(innerLayout3,2);
    }

    private void initMainPageContentWithList(LinearLayout innerLayout, int type) {

        String[] strsStyle = {"自然景观", "文化古迹", "公园/主题公园", "古镇/特色街区","其他"};
        String strDes = null;

        switch(type){
            case 0:
                LinearLayout layout = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_1);
                final SpotBase sb1 = Utils.findSpot(mHotSpots[0]);
                strDes = sb1.getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(sb1.getStrSpotStyle()) - 1 ];
                ((TextView)layout.findViewById(R.id.main_page_list_title)).setText(sb1.getStrSpotName());
                ((TextView)layout.findViewById(R.id.main_page_list_title_des)).setText(strDes);
                mImageLoader.bindBitmap(sb1.getStrSpotImgUrl(), (ImageView)layout.findViewById(R.id.main_page_list_image),true);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainPageActivity.this,SpotDetailActivity.class);
                        i.putExtra("SpotPos", Utils.listSpot.indexOf(sb1));
                        startActivity(i);
                    }
                });

                LinearLayout layout2 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_2);
                SpotBase sb2 = Utils.findSpot(mHotSpots[1]);
                strDes = sb2.getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(sb2.getStrSpotStyle()) - 1 ];
                ((TextView)layout2.findViewById(R.id.main_page_list_title)).setText(sb2.getStrSpotName());
                ((TextView)layout2.findViewById(R.id.main_page_list_title_des)).setText(strDes);
                mImageLoader.bindBitmap(sb2.getStrSpotImgUrl(), (ImageView)layout2.findViewById(R.id.main_page_list_image),true);

                LinearLayout layout3 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_3);
                SpotBase sb3 = Utils.findSpot(mHotSpots[2]);
                strDes = sb3.getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(sb3.getStrSpotStyle()) - 1 ];
                ((TextView)layout3.findViewById(R.id.main_page_list_title)).setText(sb3.getStrSpotName());
                ((TextView)layout3.findViewById(R.id.main_page_list_title_des)).setText(strDes);
                mImageLoader.bindBitmap(sb3.getStrSpotImgUrl(), (ImageView)layout3.findViewById(R.id.main_page_list_image),true);
                break;
            case 1:
                LinearLayout layout4 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_1);
                final City c1 = Utils.findCity(mAound[0]);
                strDes = c1.getStrCitySimpleDes();
                ((TextView)layout4.findViewById(R.id.main_page_list_title)).setText(c1.getStrCityName());
                ((TextView)layout4.findViewById(R.id.main_page_list_title_des)).setText(strDes);
                mImageLoader.bindBitmap(c1.getStrURL(), (ImageView)layout4.findViewById(R.id.main_page_list_image),true);
                layout4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainPageActivity.this,CityDetailActivity.class);
                        i.putExtra("CityPos",Utils.listCity.indexOf(c1));
                        startActivity(i);
                    }
                });

                LinearLayout layout5 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_2);
                City c2 = Utils.findCity(mAound[1]);
                strDes = c2.getStrCitySimpleDes();
                ((TextView)layout5.findViewById(R.id.main_page_list_title)).setText(c2.getStrCityName());
                ((TextView)layout5.findViewById(R.id.main_page_list_title_des)).setText(strDes);
                mImageLoader.bindBitmap(c2.getStrURL(), (ImageView)layout5.findViewById(R.id.main_page_list_image),true);

                LinearLayout layout6 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_3);
                City c3 = Utils.findCity(mAound[2]);
                strDes = c3.getStrCitySimpleDes();
                ((TextView)layout6.findViewById(R.id.main_page_list_title)).setText(c3.getStrCityName());
                ((TextView)layout6.findViewById(R.id.main_page_list_title_des)).setText(strDes);
                mImageLoader.bindBitmap(c3.getStrURL(), (ImageView)layout6.findViewById(R.id.main_page_list_image),true);

                break;
            case 2:
                LinearLayout layout7 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_1);
                SpotBase sb4 = Utils.findSpot(mRMBSpot[0]);
                strDes = sb4.getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(sb4.getStrSpotStyle()) - 1 ];
                ((TextView)layout7.findViewById(R.id.main_page_list_title)).setText(sb4.getStrSpotName());
                ((TextView)layout7.findViewById(R.id.main_page_list_title_des)).setText(strDes);
                mImageLoader.bindBitmap(sb4.getStrSpotImgUrl(), (ImageView)layout7.findViewById(R.id.main_page_list_image),true);


                LinearLayout layout8 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_2);
                SpotBase sb5 = Utils.findSpot(mRMBSpot[1]);
                strDes = sb5.getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(sb5.getStrSpotStyle()) - 1 ];
                ((TextView)layout8.findViewById(R.id.main_page_list_title)).setText(sb5.getStrSpotName());
                ((TextView)layout8.findViewById(R.id.main_page_list_title_des)).setText(strDes);
                mImageLoader.bindBitmap(sb5.getStrSpotImgUrl(), (ImageView)layout8.findViewById(R.id.main_page_list_image),true);

                LinearLayout layout9 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_3);
                SpotBase sb6 = Utils.findSpot(mRMBSpot[2]);
                strDes = sb6.getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(sb6.getStrSpotStyle()) - 1 ];
                ((TextView)layout9.findViewById(R.id.main_page_list_title)).setText(sb6.getStrSpotName());
                ((TextView)layout9.findViewById(R.id.main_page_list_title_des)).setText(strDes);
                mImageLoader.bindBitmap(sb6.getStrSpotImgUrl(), (ImageView)layout9.findViewById(R.id.main_page_list_image),true);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_city_list) {
            Log.v(TAG, "city list clicked");
            Intent i = new Intent(MainPageActivity.this, CityListActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_spot_list) {
            Log.v(TAG, "spot list clicked");
            Intent i = new Intent(MainPageActivity.this, SpotListActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_footprint) {

        } else if (id == R.id.nav_query) {

        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
