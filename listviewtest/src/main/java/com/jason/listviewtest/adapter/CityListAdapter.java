package com.jason.listviewtest.adapter;

import android.support.v7.widget.RecyclerView;

import com.jason.listviewtest.model.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jason on 2016/6/27.
 */
public abstract class CityListAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private List<City> items = new ArrayList<>();

    public void add(City city){
        items.add(city);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends City> collection){
        if(collection != null){
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(City...items){
        addAll(Arrays.asList(items));
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String strProvinceName){
        Iterator<City> it = items.iterator();
        while(it.hasNext()){
            City pro = it.next();
            if(pro.getStrCityName().equals(strProvinceName))
                it.remove();
        }
    }

    public City getItem(int position){
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
