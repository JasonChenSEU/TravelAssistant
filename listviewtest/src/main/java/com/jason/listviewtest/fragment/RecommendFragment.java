package com.jason.listviewtest.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jason.listviewtest.helper.Utils;
import com.jason.listviewtest.R;
import com.jason.listviewtest.activity.CityDetailActivity;
import com.jason.listviewtest.activity.SpotDetailActivity;
import com.jason.listviewtest.activity.SpotDetailScrollActivity;
import com.jason.listviewtest.imageloader.ImageLoader;
import com.jason.listviewtest.model.beans.City;
import com.jason.listviewtest.model.beans.SpotBase;
import com.jason.listviewtest.view.DisplayItemView;
import com.jason.listviewtest.view.RecommendPanelManager;
import com.jason.listviewtest.view.RecommendPanelView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static RecommendFragment mFragment = null;

    private String mParam1;
    private String mParam2;

    private ImageLoader mImageLoader;

    private View mFragView;

    @BindView(R.id.hot_spot)
    RecommendPanelView mPanelHotSpot;
    @BindView(R.id.Around)
    RecommendPanelView mPanelAround;
    @BindView(R.id.RMB_spot)
    RecommendPanelView mPanelRMB;

    private String[] mHotSpots = {"鼓浪屿","庐山","九寨沟"};
    private String[] mAround = {"南京","扬州","苏州"};
    private String[] mRMBSpot = {"长江三峡","漓江","天涯海角"};


    public RecommendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendFragment.
     */
    public static RecommendFragment newInstance(String param1, String param2) {
        if(mFragment == null) {
            mFragment = new RecommendFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            mFragment.setArguments(args);
        }
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //init imageLoader
        mImageLoader = ImageLoader.build(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragView = inflater.inflate(R.layout.content_main_page, container, false);
        ButterKnife.bind(this,mFragView);
        initMainPage();
        return mFragView;
    }

    private void initMainPage() {

        String[] strsStyle = {"自然景观", "文化古迹", "公园/主题公园", "古镇/特色街区","其他"};
        int roundRadis = 160;

        //Panel1
        mPanelHotSpot.setData("当季热门", "Hot Spot");
        RecommendPanelManager managerHotSpot = mPanelHotSpot.getManager();
        for(int i = 0; i < 3; i++){
            DisplayItemView item = managerHotSpot.getList().get(i);
            final SpotBase base = Utils.findSpot(mHotSpots[i]);
            String strDes = base.getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(base.getStrSpotStyle()) - 1 ];
            item.setData(base.getStrSpotName(), strDes);

            mImageLoader.bindBitmap(base.getStrSpotImgUrl(),item.getImageView(),roundRadis,roundRadis,false);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.isSpotStyleCollpase){
                        Intent i = new Intent(getContext(), SpotDetailScrollActivity.class);
                        i.putExtra("SpotPos", Utils.listSpot.indexOf(base));
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getContext(), SpotDetailActivity.class);
                        i.putExtra("SpotPos", Utils.listSpot.indexOf(base));
                        startActivity(i);
                    }
                }
            });
        }

        //Panel2
        mPanelAround.setData("周边", "Around");
        RecommendPanelManager managerAround = mPanelAround.getManager();
        for(int i = 0; i < 3; i++){
            DisplayItemView itemView = managerAround.getList().get(i);
            final City city = Utils.findCity(mAround[i]);
            String strDes = city.getStrCitySimpleDes();
            itemView.setData(city.getStrCityName(),strDes);
            mImageLoader.bindBitmap(city.getStrURL(), itemView.getImageView(),roundRadis,roundRadis,false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(),CityDetailActivity.class);
                    i.putExtra("CityPos",Utils.listCity.indexOf(city));
                    startActivity(i);
                }
            });
        }

        //Panel3
        mPanelRMB.setData("人民币景点", "RMB Spots");
        RecommendPanelManager managerRMBSpot = mPanelRMB.getManager();
        for(int i = 0; i < 3; i++){
            DisplayItemView item = managerRMBSpot.getList().get(i);
            final SpotBase base = Utils.findSpot(mRMBSpot[i]);
            String strDes = base.getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(base.getStrSpotStyle()) - 1 ];
            item.setData(base.getStrSpotName(), strDes);

            mImageLoader.bindBitmap(base.getStrSpotImgUrl(),item.getImageView(),roundRadis,roundRadis,false);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.isSpotStyleCollpase){
                        Intent i = new Intent(getContext(), SpotDetailScrollActivity.class);
                        i.putExtra("SpotPos", Utils.listSpot.indexOf(base));
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getContext(), SpotDetailActivity.class);
                        i.putExtra("SpotPos", Utils.listSpot.indexOf(base));
                        startActivity(i);
                    }
                }
            });
        }
    }
}
