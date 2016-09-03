package com.jason.listviewtest.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jason.listviewtest.helper.Utils;
import com.jason.listviewtest.R;
import com.jason.listviewtest.activity.SpotDetailActivity;
import com.jason.listviewtest.activity.SpotDetailScrollActivity;
import com.jason.listviewtest.adapter.SpotListAdapter;
import com.jason.listviewtest.model.RecyclerViewItemClickListener;
import com.jason.listviewtest.model.beans.SpotBase;
import com.jason.listviewtest.view.HeaderView;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpotListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpotListFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View mFragView;
    private RecyclerView mRecyclerView;


    public SpotListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpotListFragment.
     */
    public static SpotListFragment newInstance(String param1, String param2) {
        SpotListFragment fragment = new SpotListFragment();
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
        mFragView = inflater.inflate(R.layout.activity_spot_list, container, false);
        mRecyclerView = (RecyclerView) mFragView.findViewById(R.id.recyclerView_spot);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        initRecyclerView();
        return mFragView;
    }

    private void initRecyclerView() {
        SpotListWithHeaderAdapter adapter = new SpotListWithHeaderAdapter();

        if(!Utils.listSpot.isEmpty()){
            adapter.addAll(Utils.listSpot);
        }

        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SpotBase sb = Utils.listSpot.get(position);
                if(sb != null){
                    if(Utils.isSpotStyleCollpase) {
                        Intent i = new Intent(getContext(), SpotDetailScrollActivity.class);
                        i.putExtra("SpotPos", position);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(getContext(), SpotDetailActivity.class);
                        i.putExtra("SpotPos", position);
                        startActivity(i);
                    }
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
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

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
