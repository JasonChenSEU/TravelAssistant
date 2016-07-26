package com.jason.listviewtest.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jason.listviewtest.R;
import com.jason.listviewtest.imageloader.ImageLoader;

import java.io.IOException;

/**
 * Created by Jason on 2016/7/25.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SettingActivity";
    private TextView tvImgCachedSize;
    private TextView tvTextCachedSize;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        initCacheData();
    }

    private void initCacheData() {
        long size = mImageLoader.getCachedSize();
        String cachedSize = String.format("%.2f M",size/1024.0/1024.0);
        tvImgCachedSize.setText(cachedSize);

    }

    private void initView() {
        tvImgCachedSize = (TextView) findViewById(R.id.tv_cache_img_size);
        tvTextCachedSize = (TextView) findViewById(R.id.tv_cache_text_size);

        findViewById(R.id.setting_clear_text_cache).setOnClickListener(this);
        findViewById(R.id.setting_about).setOnClickListener(this);
        findViewById(R.id.setting_clear_img_cache).setOnClickListener(this);

        mImageLoader = ImageLoader.build(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_clear_img_cache:

                new AlertDialog.Builder(this)
                        .setMessage("确认清除缓存？")
                        .setTitle("注意")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    mImageLoader.clearCache();
                                } catch (IOException e) {
                                    Log.v(TAG,"IO Exception while clearing cache");
                                    e.printStackTrace();
                                }
                                tvImgCachedSize.setText("0 M");
                            }
                        })
                        .setNegativeButton("NO",null)
                        .show();
                break;
            case R.id.setting_clear_text_cache:
                break;
            case R.id.setting_about:
                Intent i = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(i);
                break;
        }

    }
}
