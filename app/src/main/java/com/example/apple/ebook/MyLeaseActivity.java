package com.example.apple.ebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyLeaseActivity extends AppCompatActivity {

    public int userid;
    public ListView list;
    public LeaseAdapter leaseAdapter;
    public List<Map<String, Object>> leaser;
    public MyData myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lease);

        Intent getdata = getIntent();
        Bundle get_data = getdata.getExtras();
        userid = get_data.getInt("userid");

        list = (ListView) findViewById(R.id.leaselist);
        leaser = new ArrayList<Map<String, Object>>();
        leaseAdapter = new LeaseAdapter(MyLeaseActivity.this, leaser);
        myData = new MyData(MyLeaseActivity.this);

        list.setOnItemClickListener(l);

        list.setAdapter(leaseAdapter);
        showleaser();
    }

    public void showleaser() {
        List<Map<String, Object>> data = myData.ShowLeaserByID(userid);
        if (data != null && data.size() > 0) {

            for (int i = data.size() - 1; i >= 0; i--) {
                Map<String, Object> map = data.get(i);
                leaser.add(map);
            }
        }
        leaseAdapter.resetData(leaser);

    }

    AdapterView.OnItemClickListener l = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog));
            final AlertDialog.Builder builder = new AlertDialog.Builder(MyLeaseActivity.this);
            builder.setView(layout);
            builder.setCancelable(false);
            builder.show();
            Button yes = (Button) layout.findViewById(R.id.yes);
            Button no = (Button) layout.findViewById(R.id.no);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> map = (Map<String, Object>) leaseAdapter.getItem(position);
                    //int leasenum = (int) map.get(MyData.LeaseNum);
                    int leaseid = (int) map.get(MyData.LeaseID);
                    int hostid = (int) map.get(MyData.HostID);
                    int bookid = (int) map.get(MyData.LeaseBookID);
                    boolean result1 =  myData.DeleteLease(leaseid);
                    boolean result2 =  myData.UpdataLeaseNum(hostid);
                    boolean result3 =  myData.UpdataBookqueue(bookid);
                    if (result1 && result2 && result3) {
                        Intent intent = new Intent(MyLeaseActivity.this, MyLeaseActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("userid", userid);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(MyLeaseActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }


    };
}
