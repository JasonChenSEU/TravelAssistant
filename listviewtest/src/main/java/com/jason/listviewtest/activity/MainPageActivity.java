package com.jason.listviewtest.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.R;
import com.jason.listviewtest.fragment.CityListFragment;
import com.jason.listviewtest.fragment.RecommendFragment;
import com.jason.listviewtest.fragment.SpotListFragment;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainPageActivity";

    private ImageView mImageView;

    private int mCurrentFragment = 0;

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
        navigationView.setCheckedItem(R.id.nav_recommend);

        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_img);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.my_image);
        mImageView.setImageBitmap(Utils.toRoundBitmap(bitmap));

        initData();

        RecommendFragment fragment = RecommendFragment.newInstance("a","b");
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.default_main_content,fragment).commit();

//        initMainPage();
    }

    private void initData() {
        if(Utils.listCity.size() == 0){
            Utils.initCityList(MainPageActivity.this);
        }
        if(Utils.listSpot.size() == 0){
            Utils.initSpotList(MainPageActivity.this);
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Utils.isSpotStyleCollpase = sp.getBoolean("isCollapsingToolbar",false);
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
            if(mCurrentFragment != 1) {
                CityListFragment fragment = CityListFragment.newInstance("a", "b");
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.default_main_content, fragment).commit();
            }
            mCurrentFragment = 1;
        } else if (id == R.id.nav_spot_list) {
            if(mCurrentFragment != 2) {
                SpotListFragment fragment = SpotListFragment.newInstance("a", "b");
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.default_main_content, fragment).commit();
            }
            mCurrentFragment = 2;
        } else if (id == R.id.nav_recommend) {
            if(mCurrentFragment != 0){
                RecommendFragment fragment = RecommendFragment.newInstance("a","b");
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.default_main_content,fragment).commit();
            }
            mCurrentFragment = 0;
        } else if (id == R.id.nav_query) {
            Intent i = new Intent(MainPageActivity.this, QueryActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_setting) {
            Intent i = new Intent(MainPageActivity.this, SettingActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
