package com.example.apple.ebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class BorrowAdapter extends BaseAdapter{
    private List<Map<String,Object>> borrowdata;
    private Context borrowcontext;

    public BorrowAdapter(Context context, List<Map<String, Object>> data){
        super();
        this.borrowcontext = context;
        this.borrowdata = data;
    }

    public void resetData(List<Map<String, Object>> data){
        this.borrowdata = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.borrowdata.size();
    }

    @Override
    public Object getItem(int position) {
        return this.borrowdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(borrowcontext).inflate(R.layout.borrowitem, null);

        Map<String, Object> map = (Map<String, Object>) getItem(position);

        ((TextView) convertView.findViewById(R.id.borrowbook)).setText( (String) map.get(MyData.LeaseBook));

        return convertView;
    }
}
