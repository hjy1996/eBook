package com.example.apple.ebook;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public int userID;
    public MyData admindata;
    public List<Map<String,Object>> admin;
    public AdminAdapter adminAdapter;
    public ListView adminlv;
    public Button deletelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent id = getIntent();
        Bundle getid = id.getExtras();
        userID = getid.getInt("userid");

        admindata = new MyData(AdminActivity.this);
        admin = new ArrayList<Map<String, Object>>();
        adminAdapter = new AdminAdapter(AdminActivity.this, admin);
        adminlv = findViewById(R.id.adminlist);
        deletelease = findViewById(R.id.deletelease);
        deletelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = admindata.Delete();
                if (result){
                    Toast.makeText(AdminActivity.this, "清空成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AdminActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adminlv.setOnItemClickListener(this);

        adminlv.setAdapter(adminAdapter);
        showuser();

    }

    public void showuser(){
        List<Map<String, Object>> data = admindata.ShowUser();
        if ( data.size()>0 && data != null ){
            for (int i = data.size() - 1; i >= 0; i--){
                Map<String,Object> map = data.get(i);
                admin.add(map);
            }
        }
        adminAdapter.resetData(admin);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog));
        final AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        builder.setView(layout);
        builder.setCancelable(false);
        builder.show();
        Button yes = (Button) layout.findViewById(R.id.yes);
        Button no = (Button) layout.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = (Map<String, Object>) adminAdapter.getItem(position);
                int chooseid = (int) map.get(MyData.User_ID);
                if (chooseid > 2) {
                    boolean result = admindata.DeleteUser(chooseid);
                    if (result){
                        Toast.makeText(AdminActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        Intent intent_admin = new Intent(AdminActivity.this, FeatureActivity.class);
                        Bundle bundle_admin = new Bundle();
                        bundle_admin.putInt("userid", userID);
                        intent_admin.putExtras(bundle_admin);
                        startActivity(intent_admin);
                    }else {
                        Toast.makeText(AdminActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(AdminActivity.this, "您不能删除管理员", Toast.LENGTH_SHORT).show();
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
}
