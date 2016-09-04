package com.jason.listviewtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jason.listviewtest.R;
import com.jason.listviewtest.imageloader.ImageLoader;
import com.pkmmte.view.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * PanelManager管理的每个具体Item
 * Created by Jason on 2016/9/3.
 */
public class DisplayItemView extends LinearLayout {

    @BindView(R.id.main_page_list_image)
    CircularImageView imageView;

    @BindView(R.id.main_page_list_title)
    TextView tvTitle;

    @BindView(R.id.main_page_list_title_des)
    TextView tvDes;

    public DisplayItemView(Context context) {
        super(context);
    }

    public DisplayItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisplayItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setData(String strTitle, String strDes){
        tvTitle.setText(strTitle);
        tvDes.setText(strDes);
    }

    public CircularImageView getImageView() {
        return imageView;
    }
}
