package com.example.apple.ebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyBorrowActivity extends AppCompatActivity {

    public int userid;
    public ListView list;
    public BorrowAdapter borrowAdapter;
    public List<Map<String,Object>> borrow;
    public MyData myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lease);

        Intent getdata = getIntent();
        Bundle get_data = getdata.getExtras();
        userid = get_data.getInt("userid");

        list = (ListView) findViewById(R.id.leaselist);
        borrow = new ArrayList<Map<String, Object>>();
        borrowAdapter = new BorrowAdapter(MyBorrowActivity.this, borrow);
        myData = new MyData(MyBorrowActivity.this);

        list.setAdapter(borrowAdapter);
        showborrower();
    }

    public void showborrower(){
        List<Map<String, Object>> data = myData.ShowBorrowerByID(userid);
        if (data != null && data.size() > 0 ){

            for(int i = data.size() - 1; i >= 0; i-- ){
                Map<String, Object> map = data.get(i);
                borrow.add(map);
            }
        }
        borrowAdapter.resetData(borrow);

    }
}
