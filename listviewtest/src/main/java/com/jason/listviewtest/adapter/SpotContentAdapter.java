package com.jason.listviewtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jason.listviewtest.R;
import com.jason.listviewtest.view.SpotContentView;
import com.jason.listviewtest.activity.SpotDetailActivity;
import com.jason.listviewtest.model.Spot;
import com.jason.listviewtest.model.SpotContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016/7/13.
 */
public class SpotContentAdapter extends RecyclerView.Adapter {

    private List<SpotContent> items = new ArrayList<>();

    public void initList(Spot spot) {
        if (spot.getStrAbstract() != null)
            items.add(new SpotContent("印 象", "    " + spot.getStrAbstract()));
        if (spot.getStrAddress() != null)
            items.add(new SpotContent("地 址", "    " + spot.getStrAddress()));
        if (spot.getStrTel() != null)
            items.add(new SpotContent("电 话", "    " + spot.getStrTel()));
        if (spot.getStrSpotSeasonRecmd() != null) {
            String content = "    " + spot.getStrSpotSeasonRecmd().replaceAll("@", "\n    ");
            items.add(new SpotContent("推荐游览时间", content));
        }
        if (spot.getStrOpenTime() != null)
            items.add(new SpotContent("开放时间", "    " + spot.getStrOpenTime()));
        if (spot.getStrTicketInfo() != null)
            items.add(new SpotContent("票务信息", "    " + spot.getStrTicketInfo()));
        if (spot.getStrSpotTravelTips() != null) {
            String content = "    " + spot.getStrSpotTravelTips().replaceAll("@", "\n    ");
            items.add(new SpotContent("贴 士", content));
        }
        if (spot.getStrDescription() != null)
            items.add(new SpotContent("简 介", "    " + spot.getStrDescription()));
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spot_content_item, parent, false);

        return new SpotContentView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SpotContentView view = (SpotContentView) holder;

        SpotContent sc = items.get(position);
        view.getTvTitle().setText(sc.getStrTitle());
        view.getTvDes().setText(sc.getStrDes());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
