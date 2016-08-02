package com.jason.listviewtest.activity;

import android.content.Intent;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.R;
import com.jason.listviewtest.model.City;
import com.jason.listviewtest.model.RecyclerViewItemClickListener;
import com.jason.listviewtest.model.SpotBase;

import java.util.ArrayList;
import java.util.List;

public class CityQueryListActivity extends AppCompatActivity {

//    private TextView tvExactTitle;
    private TextView tvFuzzyTitle;

//    private RecyclerView listExactListView;
    private RecyclerView listFuzzyListView;

//    private List<ItemText> list_exact = new ArrayList<>();
    private List<ItemText> list_fuzzy = new ArrayList<>();

    private String strCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_query_list);

        initTextViewsAndRecyclerViews();

        strCityName = getIntent().getStringExtra("CityName");
        initData(getIntent().getStringExtra("CityProvince"));

//        listExactListView.setAdapter(new QueryAdapter(list_exact));
        String fuzzyHint = strCityName + " 附近的景点有 " + list_fuzzy.size() + " 个.";
        tvFuzzyTitle.setText(fuzzyHint);
        QueryAdapter adapter_fuzzy = new QueryAdapter(list_fuzzy);
        adapter_fuzzy.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //根据ItemText中保存的type决定是跳转到城市详细页面还是景点详细页面
                ItemText it = list_fuzzy.get(position);
                switch (it.type){
                    case 0:
                        Intent i = new Intent(CityQueryListActivity.this,CityDetailActivity.class);
                        i.putExtra("CityPos", it.pos);
                        startActivity(i);
                        break;
                    case 1:
                        Intent i2 = new Intent(CityQueryListActivity.this,SpotDetailActivity.class);
                        i2.putExtra("SpotPos", it.pos);
                        startActivity(i2);
                        break;
                }
            }
        });
        listFuzzyListView.setAdapter(adapter_fuzzy);

//        listFuzzyListView.getAdapter().notifyDataSetChanged();
    }

    private void initData(String cityProvince) {
        //Search : name和province
        String[] strsStyle = {"自然景观", "文化古迹", "公园/主题公园", "古镇/特色街区","其他"};
        String strFullCityName = strCityName + "市";

        for(SpotBase sb: Utils.listSpot){
            if(cityProvince.equals(sb.getStrSpotProvince()) || strFullCityName.equals(sb.getStrSpotProvince())){
                String strDes = sb.getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(sb.getStrSpotStyle()) - 1 ];
                ItemText it = new ItemText(sb.getStrSpotName(),strDes,1, Utils.listSpot.indexOf(sb));
                list_fuzzy.add(it);
            }
        }
    }

    private void initTextViewsAndRecyclerViews() {
//        tvExactTitle = (TextView) findViewById(R.id.city_query_exact_text);
        tvFuzzyTitle = (TextView) findViewById(R.id.city_query_fuzzy_text);

//        listExactListView = (RecyclerView) findViewById(R.id.city_query_exact_list);
//        listExactListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        listFuzzyListView = (RecyclerView) findViewById(R.id.city_query_fuzzy_list);
        listFuzzyListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }

    /**
     * Adapter for listView
     * 复用了spot_list_item_detail，没有左边的竖线
     */
    private class QueryAdapter extends RecyclerView.Adapter{

        RecyclerViewItemClickListener mListener = null;
        List<ItemText> list_data;

        public QueryAdapter(List<ItemText> list){
            list_data = list;
        }

        public void setOnItemClickListener(RecyclerViewItemClickListener listener){
            mListener = listener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spot_list_item_detail,parent,false);
            view.setPadding(16,6,0,6);

            return new SpotItemView(view, mListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SpotItemView itemView = (SpotItemView) holder;

            itemView.getTvSpotName().setText(list_data.get(position).strTitle);
            itemView.getTvSpotDes().setText(list_data.get(position).strDes);
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }

    /**
     * 为适配器保存的信息
     */
    private class ItemText {
        String strTitle;
        String strDes;
        int type; // 0 for city, 1 for spot
        int pos; // pos for Util list

        public ItemText(String title, String des, int type, int pos){
            strTitle = title;
            strDes = des;
            this.type = type;
            this.pos = pos;
        }
    }

    /**
     * List Item
     */
    private class SpotItemView extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvSpotName;
        private TextView tvSpotDes;

        private RecyclerViewItemClickListener listener = null;

        public SpotItemView(View itemView, RecyclerViewItemClickListener listener) {
            super(itemView);
            tvSpotName = (TextView) itemView.findViewById(R.id.spot_item_name);
            tvSpotDes = (TextView) itemView.findViewById(R.id.spot_item_des);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        public TextView getTvSpotDes() {
            return tvSpotDes;
        }

        public TextView getTvSpotName() {
            return tvSpotName;
        }

        @Override
        public void onClick(View v) {
            if(listener != null)
                listener.onItemClick(v,getAdapterPosition());
        }
    }
}
