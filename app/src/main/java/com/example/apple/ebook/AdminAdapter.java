package com.example.apple.ebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class AdminAdapter extends BaseAdapter{

    private List<Map<String,Object>> admindata;
    private Context admincontext;

    public AdminAdapter(Context context, List<Map<String, Object>> data){
        super();
        this.admincontext = context;
        this.admindata = data;
    }

    public void resetData(List<Map<String, Object>> data){
        this.admindata = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.admindata.size();
    }

    @Override
    public Object getItem(int position) {
        return this.admindata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(admincontext).inflate(R.layout.adminitem, null);

        Map<String, Object> adminmap = (Map<String, Object>) getItem(position);
        String username = (String) adminmap.get(MyData.User_Name);
        ((TextView) convertView.findViewById(R.id.adminid)).setText(String.valueOf(adminmap.get(MyData.User_ID)));
        ((TextView) convertView.findViewById(R.id.adminname)).setText(username);

        return convertView;
    }
}
