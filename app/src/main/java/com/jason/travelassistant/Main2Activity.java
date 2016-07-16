package com.jason.travelassistant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView mImageView;

    private LinearLayout mLayout_hotSpot;
    private LinearLayout mLayout_Around;
    private LinearLayout mLayout_RMB_Spot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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

        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.my_image);
        mImageView.setImageBitmap(Utils.toRoundBitmap(bitmap));

        initMainPage();
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
        switch(type){
            case 0:
                LinearLayout layout = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_1);
                ((TextView)layout.findViewById(R.id.main_page_list_title)).setText("北京");
                ((TextView)layout.findViewById(R.id.main_page_list_title_des)).setText("ABCDEFGHIJK");

                LinearLayout layout2 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_2);
                ((TextView)layout2.findViewById(R.id.main_page_list_title)).setText("上海");
                ((TextView)layout2.findViewById(R.id.main_page_list_title_des)).setText("ABCDEFGHIJK");

                LinearLayout layout3 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_3);
                ((TextView)layout3.findViewById(R.id.main_page_list_title)).setText("天津");
                ((TextView)layout3.findViewById(R.id.main_page_list_title_des)).setText("ABCDEFGHIJK");
                break;
            case 1:
                LinearLayout layout4 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_1);
                ((TextView)layout4.findViewById(R.id.main_page_list_title)).setText("苏州");
                ((TextView)layout4.findViewById(R.id.main_page_list_title_des)).setText("ABCDEFGHIJK");

                LinearLayout layout5 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_2);
                ((TextView)layout5.findViewById(R.id.main_page_list_title)).setText("扬州");
                ((TextView)layout5.findViewById(R.id.main_page_list_title_des)).setText("ABCDEFGHIJK");

                LinearLayout layout6 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_3);
                ((TextView)layout6.findViewById(R.id.main_page_list_title)).setText("常州");
                ((TextView)layout6.findViewById(R.id.main_page_list_title_des)).setText("ABCDEFGHIJK");
                break;
            case 2:
                LinearLayout layout7 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_1);
                ((TextView)layout7.findViewById(R.id.main_page_list_title)).setText("青岛");
                ((TextView)layout7.findViewById(R.id.main_page_list_title_des)).setText("ABCDEFGHIJK");


                LinearLayout layout8 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_2);
                ((TextView)layout8.findViewById(R.id.main_page_list_title)).setText("济南");
                ((TextView)layout8.findViewById(R.id.main_page_list_title_des)).setText("ABCDEFGHIJK");

                LinearLayout layout9 = (LinearLayout) innerLayout.findViewById(R.id.main_page_list_3);
                ((TextView)layout9.findViewById(R.id.main_page_list_title)).setText("烟台");
                ((TextView)layout9.findViewById(R.id.main_page_list_title_des)).setText("ABCDEFGHIJK");
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
