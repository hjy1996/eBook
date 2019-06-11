package com.example.apple.ebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2018/5/18.
 */

public class BookAdapter extends BaseAdapter {

    private List<Map<String,Object>> data;
    private Context context;

    public BookAdapter(Context context, List<Map<String,Object>> data){
        super();
        this.context = context;
        this.data = data;
    }

    public void resetData( List<Map<String,Object>> data){
        this.data = data;
        notifyDataSetChanged();
        //更新ListView中的数据
    }

    @Override
    public int getCount(){
        return this.data.size();
        //获取item的个数
    }

    @Override
    public Object getItem(int arg0){
        return this.data.get(arg0);
        //获取item位置

    }

    @Override
    public long getItemId(int arg0){
        return arg0;
        //获取item的ID

    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2){
        arg1 = LayoutInflater.from(context).inflate(R.layout.listitem, null);
        //获取ListView中各个item的布局文件

        Map<String,Object> map = (Map<String, Object>) getItem(arg0);
        ((TextView) arg1.findViewById(R.id.bookname) ).setText( (String) map.get(MyData.PvtBookName));
        ((TextView) arg1.findViewById(R.id.author) ).setText( (String) map.get(MyData.PvtBookInfo));

        return arg1;
    }
}
