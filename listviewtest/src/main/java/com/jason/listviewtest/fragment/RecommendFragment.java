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

import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.R;
import com.jason.listviewtest.activity.CityDetailActivity;
import com.jason.listviewtest.activity.SpotDetailActivity;
import com.jason.listviewtest.imageloader.ImageLoader;
import com.jason.listviewtest.model.City;
import com.jason.listviewtest.model.SpotBase;

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

    private LinearLayout mLayout_hotSpot;
    private LinearLayout mLayout_Around;
    private LinearLayout mLayout_RMB_Spot;

    private String[] mHotSpots = {"鼓浪屿","庐山","九寨沟"};
    private String[] mAound = {"南京","扬州","苏州"};
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
        initMainPage();
        return mFragView;
    }

    private void initMainPage() {
        mLayout_hotSpot = (LinearLayout) mFragView.findViewById(R.id.hot_spot);
        ((TextView)mLayout_hotSpot.findViewById(R.id.main_page_title)).setText("当季热门");
        ((TextView)mLayout_hotSpot.findViewById(R.id.main_page_title_en)).setText("Hot Spot");

        LinearLayout innerLayout = (LinearLayout) mLayout_hotSpot.findViewById(R.id.main_page_list);

        initMainPageContentWithList(innerLayout,0);

        mLayout_Around = (LinearLayout) mFragView.findViewById(R.id.Around);
        ((TextView)mLayout_Around.findViewById(R.id.main_page_title)).setText("周边");
        ((TextView)mLayout_Around.findViewById(R.id.main_page_title_en)).setText("Around");

        LinearLayout innerLayout2 = (LinearLayout) mLayout_Around.findViewById(R.id.main_page_list);

        initMainPageContentWithList(innerLayout2,1);

        mLayout_RMB_Spot = (LinearLayout) mFragView.findViewById(R.id.RMB_spot);
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
                        Intent i = new Intent(getActivity(),SpotDetailActivity.class);
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
                        Intent i = new Intent(getContext(),CityDetailActivity.class);
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


}
