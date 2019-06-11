package com.example.apple.ebook;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FeatureActivity extends AppCompatActivity implements View.OnClickListener{

    public Button btn_featmain, btn_featbook, btn_featlease,
                  btn_featborrow, btn_featinfo, btn_featadmin;
    public TextView about;
    public int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        btn_featmain = findViewById(R.id.feature_main);
        btn_featbook = findViewById(R.id.feature_bookshelf);
        btn_featlease = findViewById(R.id.feature_mylease);
        btn_featborrow = findViewById(R.id.feature_myborrow);
        btn_featinfo = findViewById(R.id.feature_myinfo);
        btn_featadmin = findViewById(R.id.feature_admin);
        about = findViewById(R.id.feature_about);

        about.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        Intent getid = getIntent();
        Bundle getdata = getid.getExtras();
        userID = getdata.getInt("userid");

        btn_featmain.setOnClickListener(this);
        btn_featbook.setOnClickListener(this);
        btn_featlease.setOnClickListener(this);
        btn_featborrow.setOnClickListener(this);
        btn_featinfo.setOnClickListener(this);
        btn_featadmin.setOnClickListener(this);
        about.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feature_main:
                Intent intent1 = new Intent(FeatureActivity.this, MainActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("userid", userID);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
            case R.id.feature_bookshelf:
                Intent intent_shelf = new Intent(FeatureActivity.this, BookShelfActivity.class);
                Bundle bundle_shelf = new Bundle();
                bundle_shelf.putInt("userid",userID);
                intent_shelf.putExtras(bundle_shelf);
                startActivity(intent_shelf);
                break;
            case R.id.feature_mylease:
                Intent intent_lease = new Intent(FeatureActivity.this, MyLeaseActivity.class);
                Bundle bundle_lease = new Bundle();
                bundle_lease.putInt("userid", userID);
                intent_lease.putExtras(bundle_lease);
                startActivity(intent_lease);
                break;
            case R.id.feature_myborrow:
                Intent intent_borrow = new Intent(FeatureActivity.this, MyBorrowActivity.class);
                Bundle bundle_borrow = new Bundle();
                bundle_borrow.putInt("userid",userID);
                intent_borrow.putExtras(bundle_borrow);
                startActivity(intent_borrow);
                break;
            case R.id.feature_myinfo:
                Intent intent_info = new Intent(FeatureActivity.this, PersonalActivity.class);
                Bundle bundle_info = new Bundle();
                bundle_info.putInt("userid",userID);
                intent_info.putExtras(bundle_info);
                startActivity(intent_info);
                break;
            case R.id.feature_admin:
                if (userID == 1 || userID == 2) {
                    Intent intent_admin = new Intent(FeatureActivity.this, AdminActivity.class);
                    Bundle bundle_admin = new Bundle();
                    bundle_admin.putInt("userid", userID);
                    intent_admin.putExtras(bundle_admin);
                    startActivity(intent_admin);
                }else
                    {Toast.makeText(FeatureActivity.this, "您没有权限", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.feature_about:
                Intent intent = new Intent(FeatureActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(FeatureActivity.this, LogInActivity.class);
        startActivity(intent);
        this.finish();
        return true;
    }

}
