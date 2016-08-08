package com.jason.listviewtest.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.R;
import com.jason.listviewtest.libcore.io.DiskLruCache;
import com.jason.listviewtest.model.City;
import com.jason.listviewtest.model.RecyclerViewItemClickListener;
import com.jason.listviewtest.model.SpotBase;

import java.util.ArrayList;
import java.util.List;

public class QueryActivity extends AppCompatActivity {

    //自动补全文本框
    private AutoCompleteTextView autoCompleteTextView;

    //文本框的下拉框适配器
    private ArrayAdapter<String> adapterTextView;

    //提示文本
    private TextView tvHint;

    //显示listView
    private RecyclerView mRecyclerView;

    //用于适配器的List
    private List<ItemText> listForQuery = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        tvHint = (TextView) findViewById(R.id.query_text);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.query_auto_complete);

        //Prepare data for auto completion
        String[] res = prepareDataForTextView();

        adapterTextView = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,res);

        mRecyclerView = (RecyclerView) findViewById(R.id.query_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        //Prepare list view adapter
        QueryAdapter adapter = new QueryAdapter();
        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //根据ItemText中保存的type决定是跳转到城市详细页面还是景点详细页面
                ItemText it = listForQuery.get(position);
                switch (it.type){
                    case 0:
                        Intent i = new Intent(QueryActivity.this,CityDetailActivity.class);
                        i.putExtra("CityPos", it.pos);
                        startActivity(i);
                        break;
                    case 1:
                        if(Utils.isSpotStyleCollpase){
                            Intent i2 = new Intent(QueryActivity.this, SpotDetailScrollActivity.class);
                            i2.putExtra("SpotPos", it.pos);
                            startActivity(i2);
                        }else {
                            Intent i2 = new Intent(QueryActivity.this, SpotDetailActivity.class);
                            i2.putExtra("SpotPos", it.pos);
                            startActivity(i2);
                        }
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(adapter);

        autoCompleteTextView.setAdapter(adapterTextView);

        //更改自动补全文本框的软键盘监听，按search会直接处理事件
        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    ((InputMethodManager) autoCompleteTextView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(QueryActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

//                    Toast.makeText(QueryActivity.this, "Received..." + autoCompleteTextView.getText(), Toast.LENGTH_SHORT).show();
                    searchAndUpdate(autoCompleteTextView.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * Get input text and search for the suitable result.
     * @param text input text.
     */
    private void searchAndUpdate(String text) {

        //每次查询都清空列表
        listForQuery.clear();

        //空输入处理
        if(text == null || text.length() <= 0){
            String strHint = "请输入需要查询的城市或景点名...";
            tvHint.setText(strHint);
            mRecyclerView.getAdapter().notifyDataSetChanged();
            return;
        }

        //Search : name和province
        String[] strsStyle = {"自然景观", "文化古迹", "公园/主题公园", "古镇/特色街区","其他"};

        for(int i = 0; i < Utils.listCity.size(); i++){
            if(text.equals(Utils.listCity.get(i).getStrCityName()) || text.equals(Utils.listCity.get(i).getStrProvince())){
                City city = Utils.listCity.get(i);
                ItemText it = new ItemText(city.getStrCityName(),city.getStrCitySimpleDes(),0,i);
                listForQuery.add(it);
            }
        }

        for(int i = 0; i < Utils.listSpot.size(); i++){
            SpotBase sb = Utils.listSpot.get(i);
            if(text.equals(sb.getStrSpotName()) || text.equals(sb.getStrSpotProvince())){
                String strDes = sb.getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(sb.getStrSpotStyle()) - 1 ];
                ItemText it2 = new ItemText(sb.getStrSpotName(),strDes,1, Utils.listSpot.indexOf(sb));
                listForQuery.add(it2);
            }
        }

        mRecyclerView.getAdapter().notifyDataSetChanged();

        String strHint = "查找到关于 " + text + " 的城市/景点 " + listForQuery.size() + " 处";
        tvHint.setText(strHint);
    }

    /**
     * Prepare data for auto complete Edit box
     * @return data
     */
    private String[] prepareDataForTextView() {
        return Utils.autoCompleteSources();
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

    /**
     * Adapter for listView
     * 复用了spot_list_item_detail，没有左边的竖线
     */
    private class QueryAdapter extends RecyclerView.Adapter{

        RecyclerViewItemClickListener mListener = null;

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

            itemView.getTvSpotName().setText(listForQuery.get(position).strTitle);
            itemView.getTvSpotDes().setText(listForQuery.get(position).strDes);
        }

        @Override
        public int getItemCount() {
            return listForQuery.size();
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
}
