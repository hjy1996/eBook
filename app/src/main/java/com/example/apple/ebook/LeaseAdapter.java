package com.example.apple.ebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class LeaseAdapter extends BaseAdapter{

    private List<Map<String,Object>> datas;
    private Context contexts;

    public LeaseAdapter(Context context, List<Map<String,Object>> data){
        super();
        this.contexts = context;
        this.datas = data;
    }

    public void resetData( List<Map<String,Object>> data){
        this.datas = data;
        notifyDataSetChanged();
        //更新ListView中的数据
    }

    @Override
    public int getCount(){
        return this.datas.size();
        //获取item的个数
    }

    @Override
    public Object getItem(int item){
        return this.datas.get(item);
        //获取item位置

    }

    @Override
    public long getItemId(int item){
        return item;
        //获取item的ID

    }

    @Override
    public View getView(int item, View view, ViewGroup arg2){
        view = LayoutInflater.from(contexts).inflate(R.layout.leaseitem, null);

        Map<String,Object> maplease = (Map<String, Object>) getItem(item);

        ((TextView) view.findViewById(R.id.leasebook)).setText((String) maplease.get(MyData.LeaseBook));
        ((TextView) view.findViewById(R.id.leaserid)).setText((String.valueOf(maplease.get(MyData.LeaserID))));
        ((TextView) view.findViewById(R.id.leaser)).setText((String) maplease.get(MyData.LeaserName));
        ((TextView) view.findViewById(R.id.leasercontact)).setText((String) maplease.get(MyData.LeaserContact));
        ((TextView) view.findViewById(R.id.leaseraddress)).setText((String) maplease.get(MyData.LeaserAddress));
        return view;
    }
}
