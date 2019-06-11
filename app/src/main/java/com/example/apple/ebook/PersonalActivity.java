package com.example.apple.ebook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PersonalActivity extends AppCompatActivity implements View.OnClickListener{

    public Button btn_editinfo;
    public int userid;
    public String nickname, realname, contact, address;
    public TextView nick_name, user_id,real_name, Contact, Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        nick_name = (TextView) findViewById(R.id.nickname_detail);
        user_id = (TextView) findViewById(R.id.id_detail);
        real_name = (TextView) findViewById(R.id.name_detail);
        Contact = (TextView) findViewById(R.id.contact_detail);
        Address = (TextView) findViewById(R.id.address_detail);
        btn_editinfo = (Button) findViewById(R.id.btn_edit);

        Intent get_id = getIntent();
        Bundle getID = get_id.getExtras();
        userid = getID.getInt("userid");

        MyData myData = new MyData(PersonalActivity.this);


        nickname = myData.getUsername(userid);
        realname = myData.getUserrealname(userid);
        contact = myData.getUsercontact(userid);
        address = myData.getUseraddress(userid);


        nick_name.setText(nickname);
        user_id.setText(String.valueOf(userid));
        real_name.setText(realname);
        Contact.setText(contact);
        Address.setText(address);


        btn_editinfo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this,EditInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("userid", userid);
                bundle.putString("contact", contact);
                bundle.putString("address", address);
                intent.putExtras(bundle);
                startActivity(intent);
    }



}
