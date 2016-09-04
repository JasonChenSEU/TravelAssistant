package com.jason.listviewtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.jason.listviewtest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * 包含3个子Item的Manager
 * Created by Jason on 2016/9/4.
 */
public class RecommendPanelManager extends LinearLayout{

    @BindViews({R.id.main_page_list_1, R.id.main_page_list_2, R.id.main_page_list_3})
    List<DisplayItemView> list = new ArrayList<>();

    public RecommendPanelManager(Context context) {
        super(context);
    }

    public RecommendPanelManager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecommendPanelManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public List<DisplayItemView> getList() {
        return list;
    }
}
