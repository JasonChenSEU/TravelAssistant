package com.jason.listviewtest.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jason.listviewtest.R;

/**
 * Created by Jason on 2016/7/13.
 */
public class SpotContentView extends RecyclerView.ViewHolder {

    private TextView tvTitle;
    private TextView tvDes;

    public SpotContentView(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.spot_content_title);
        tvDes = (TextView) itemView.findViewById(R.id.spot_content_des);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvDes() {
        return tvDes;
    }
}
