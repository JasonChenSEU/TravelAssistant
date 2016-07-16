package com.jason.listviewtest.adapter;

import android.support.v7.widget.RecyclerView;

import com.jason.listviewtest.model.SpotBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jason on 2016/7/10.
 */
public abstract class SpotListAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private List<SpotBase> items = new ArrayList<>();

    public void add(SpotBase spotBase){
        items.add(spotBase);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends SpotBase> collection){
        if(collection != null){
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(SpotBase...items){
        addAll(Arrays.asList(items));
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String strSpotName){
        Iterator<SpotBase> it = items.iterator();
        while(it.hasNext()){
            SpotBase sb = it.next();
            if(sb.getStrSpotName().equals(strSpotName))
                it.remove();
        }
    }

    public SpotBase getItem(int position){
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
