package com.jason.listviewtest.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.listviewtest.helper.Utils;
import com.jason.listviewtest.R;
import com.jason.listviewtest.activity.CityDetailActivity;
import com.jason.listviewtest.adapter.CityListAdapter;
import com.jason.listviewtest.imageloader.ImageLoader;
import com.jason.listviewtest.model.beans.City;
import com.jason.listviewtest.model.RecyclerViewItemClickListener;
import com.jason.listviewtest.view.HeaderView;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityListFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private RecyclerView mRecyclerView;
    private ImageLoader imageLoader;

    private Bitmap defaultRoundBitmap;
    
    private View mFragView;

    public CityListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityListFragment.
     */
    public static CityListFragment newInstance(String param1, String param2) {
        CityListFragment fragment = new CityListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragView = inflater.inflate(R.layout.activity_city_list, container, false);
        mRecyclerView = (RecyclerView) mFragView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
        
        //init imageLoader
        imageLoader = ImageLoader.build(getContext());

        //set default bitmap in the list
        defaultRoundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_default);

        initRecyclerView();
        
        return mFragView;
    }

    private void initRecyclerView() {
        CityListWithHeaderAdapter adapter = new CityListWithHeaderAdapter();


        if(!Utils.listCity.isEmpty())
            adapter.addAll(Utils.listCity);

        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                City city = Utils.listCity.get(position);
                if(city != null) {
                    Intent i = new Intent(getContext(),CityDetailActivity.class);
                    i.putExtra("CityPos",position);
                    startActivity(i);
                }
            }
        });
        mRecyclerView.setAdapter(adapter);

        //set header
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        mRecyclerView.addItemDecoration(headersDecor);
    }

    /**
     * View for RecyclerView
     */
    private class ListItemView extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvName;
        private TextView tvDes;
        private ImageView imageView;

        private RecyclerViewItemClickListener mlistener = null;

        public ListItemView(View itemView, RecyclerViewItemClickListener listener) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.item_name);
            tvDes = (TextView) itemView.findViewById(R.id.item_simple_des);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);

            this.mlistener = listener;
            itemView.setOnClickListener(this);
        }

        public TextView getTextViewName(){
            return tvName;
        }

        public TextView getTextViewDes(){
            return tvDes;
        }

        public ImageView getImageView(){
            return imageView;
        }

        @Override
        public void onClick(View v) {
            if(mlistener != null){
                mlistener.onItemClick(v,getAdapterPosition());
            }
        }
    }

    /**
     * Implements StickyRecyclerHeadersAdapter.
     *
     */
    private class CityListWithHeaderAdapter extends CityListAdapter<RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>{

        private RecyclerViewItemClickListener mitemClickListener = null;

        public void setOnItemClickListener(RecyclerViewItemClickListener listener){
            mitemClickListener = listener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Create ListItemView
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.city_list_item,parent,false);
//            ListItemView ltv = new ListItemView(view);
            return new ListItemView(view, mitemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //Bind ListItemView
            ListItemView listItemView = (ListItemView) holder;

            //Compare the url and tag in imageView. If they are not the same(first load), show the default bitmap
            String strURL = getItem(position).getStrURL();
            ImageView imageView = listItemView.getImageView();
            String strTag = (String) imageView.getTag();

            if(!strURL.equals(strTag))
                listItemView.getImageView().setImageBitmap(defaultRoundBitmap);

            //Set tag
            imageView.setTag(strURL);
            //Load image from cache or download async.
            imageLoader.bindBitmap(strURL, imageView, false);

            listItemView.getTextViewName().setText(getItem(position).getStrCityName());
            listItemView.getTextViewDes().setText(getItem(position).getStrCitySimpleDes());
        }

        @Override
        public long getHeaderId(int position) {

            Map<String,Integer> map = Utils.getMapProvince();
            if((map.get(getItem(position).getStrProvince()) != null)){
                return map.get(getItem(position).getStrProvince());
            }else{
                int size = map.size();
                map.put(getItem(position).getStrProvince(),size);
                return size;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            //Init header view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_header, parent, false);
            return new HeaderView(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            //Bind header view
            HeaderView hv = (HeaderView) holder;
            hv.getmTextView().setText(getItem(position).getStrProvince());
        }


    }

}
