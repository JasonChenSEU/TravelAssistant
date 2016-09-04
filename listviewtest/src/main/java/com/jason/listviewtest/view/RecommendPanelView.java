package com.jason.listviewtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jason.listviewtest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * recommend的每一项，包含title以及PanelManager
 * Created by Jason on 2016/9/4.
 */
public class RecommendPanelView extends LinearLayout {

    @BindView(R.id.main_page_title)
    TextView tvPanelTitle;
    @BindView(R.id.main_page_title_en)
    TextView tvPanelTitleEn;
    @BindView(R.id.main_page_list)
    RecommendPanelManager manager;

    public RecommendPanelView(Context context) {
        super(context);
    }

    public RecommendPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecommendPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setData(String strTitle, String strTitleEn){
        tvPanelTitle.setText(strTitle);
        tvPanelTitleEn.setText(strTitleEn);
    }

    public RecommendPanelManager getManager() {
        return manager;
    }
}
