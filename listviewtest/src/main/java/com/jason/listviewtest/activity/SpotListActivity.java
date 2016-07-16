package com.jason.listviewtest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jason.listviewtest.R;
import com.jason.listviewtest.adapter.SpotListAdapter;
import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.model.RecyclerViewItemClickListener;
import com.jason.listviewtest.model.SpotBase;
import com.jason.listviewtest.view.HeaderView;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

public class SpotListActivity extends AppCompatActivity {

    private static final String TAG = "SpotListActivity";
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_spot);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        Utils.initSpotList(SpotListActivity.this);

        SpotListWithHeaderAdapter adapter = new SpotListWithHeaderAdapter();

        if(!Utils.listSpot.isEmpty()){
            adapter.addAll(Utils.listSpot);
        }

        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SpotBase sb = Utils.listSpot.get(position);
                if(sb != null){
                    Intent i = new Intent(SpotListActivity.this,SpotDetailActivity.class);
                    i.putExtra("SpotPos", position);
                    startActivity(i);
                }
            }
        });

        mRecyclerView.setAdapter(adapter);

        //set header
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        mRecyclerView.addItemDecoration(headersDecor);
    }

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

    private class SpotListWithHeaderAdapter extends SpotListAdapter<RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>{

        private RecyclerViewItemClickListener mListener = null;

        private String[] strsStyle = {"自然景观", "文化古迹", "公园/主题公园", "古镇/特色街区","其他"};

        public void setOnItemClickListener(RecyclerViewItemClickListener listener){
            mListener = listener;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.spot_list_item,parent,false);

            return new SpotItemView(view, mListener);
        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SpotItemView itemView = (SpotItemView) holder;

            itemView.getTvSpotName().setText(getItem(position).getStrSpotName());
            String strDes = getItem(position).getStrSpotProvince() + "  |  " + strsStyle[Integer.valueOf(getItem(position).getStrSpotStyle()) - 1 ];
            itemView.getTvSpotDes().setText(strDes);
        }

        @Override
        public long getHeaderId(int position) {
            return getItem(position).getStrSpotNameEn().charAt(0);
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
            HeaderView hv = (HeaderView) holder;
            char ch = (char) (getItem(position).getStrSpotNameEn().charAt(0)-'a'+'A');
            hv.getmTextView().setText(String.valueOf(ch));
        }
    }
}
