package com.jason.listviewtest.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jason.listviewtest.R;

/**
 * View for header
 */
public class HeaderView extends RecyclerView.ViewHolder{

    private TextView mTextView;

    public HeaderView(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.textView_header);
    }

    public TextView getmTextView(){
        return mTextView;
    }


}
