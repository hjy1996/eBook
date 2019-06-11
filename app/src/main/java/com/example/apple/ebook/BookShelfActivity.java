package com.example.apple.ebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookShelfActivity extends AppCompatActivity{

    private List<Map<String,Object>> prv_book;
    private ListView lv;
    private BookAdapter adapter;
    public int userid;
    public MyData myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);
        lv = (ListView) findViewById(R.id.prv_booklist);

        Intent get_id = getIntent();
        Bundle get_ID = get_id.getExtras();
        userid = get_ID.getInt("userid");


        prv_book = new ArrayList<Map<String,Object>>();
        adapter = new BookAdapter(BookShelfActivity.this,prv_book);
        myData = new MyData(BookShelfActivity.this);

        lv.setOnItemClickListener(myItemClickListener);

        lv.setAdapter(adapter);
        ShowBook();
    }

    public void ShowBook() {

        List<Map<String,Object>> data = myData.SearchByID(userid);
        if (data != null && data.size() > 0 ) {
            for (int i = data.size() - 1; i >= 0; i--) {
                Map<String, Object> map = data.get(i);
                prv_book.add(map);
            }
            adapter.resetData(prv_book);
        }
        else
            return;
    }


    private AdapterView.OnItemClickListener myItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(BookShelfActivity.this,BookDetailActivity.class);
            Bundle bundle = new Bundle();

            Map<String, Object> map = (Map<String, Object>) adapter.getItem(position);
            bundle.putString("bookname", (String) map.get(MyData.PvtBookName));
            bundle.putString("bookauthor", (String) map.get(MyData.PvtBookAuth));
            bundle.putString("bookinfo", (String) map.get(MyData.PvtBookInfo));
            bundle.putInt("bookid", (int) map.get(MyData.PvtBookID));
            bundle.putInt("bookqueue", (Integer) map.get(MyData.BookQueue));
            bundle.putInt("userid", userid);
            intent.putExtras(bundle);

            startActivity(intent);

        }
    };


    /******
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (myData != null){
            myData.close();
        }
    }
    ***/


}
