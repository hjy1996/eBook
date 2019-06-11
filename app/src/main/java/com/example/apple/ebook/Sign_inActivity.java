package com.example.apple.ebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sign_inActivity extends AppCompatActivity {

    public EditText addinname, addinpw, addinrealname, addincontact, addinaddress;
    public Button btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        addinname = (EditText) findViewById(R.id.addinname);
        addinpw = (EditText) findViewById(R.id.addinpw);
        addinrealname = (EditText) findViewById(R.id.add_realname);
        addincontact = (EditText) findViewById(R.id.add_contact);
        addinaddress = (EditText) findViewById(R.id.add_address);
        btn_signin = (Button) findViewById(R.id.btn_signin);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormatCheck();
            }
        });
    }


    public boolean NameCheck(String name){
        //限制用户名

        String namecheck = "^.{2,8}$";
        //通过正则表达式限制用户名在2~8个字符

        return name.matches(namecheck);
    }
    public void FormatCheck(){
        //检查格式是否正确

        String get_name = addinname.getText().toString();
        String get_pw = addinpw.getText().toString();
        String get_realname = addinrealname.getText().toString();
        String get_contact = addincontact.getText().toString();
        String get_address = addinaddress.getText().toString();

        if (TextUtils.isEmpty(get_name) || !NameCheck(get_name) ){
            Toast.makeText(Sign_inActivity.this,"请输入正确的用户名",Toast.LENGTH_LONG).show();
            return;
        }
        if (get_pw.equals("")){
            Toast.makeText(Sign_inActivity.this,"请输入密码",Toast.LENGTH_LONG).show();
            return;
        }
        if (get_realname.equals("")){
            Toast.makeText(Sign_inActivity.this,"请输入姓名",Toast.LENGTH_LONG).show();
            return;
        }
        if (get_contact.equals("")){
            Toast.makeText(Sign_inActivity.this,"请输入联系方式",Toast.LENGTH_LONG).show();
            return;
        }
        if (get_address.equals("")){
            Toast.makeText(Sign_inActivity.this,"请输入地址",Toast.LENGTH_LONG).show();
            return;
        }
        MyData myData = new MyData(Sign_inActivity.this);

        int rename = myData.SearchRename(get_name);
        if (rename == 0){
            Toast.makeText(Sign_inActivity.this,"昵称重复",Toast.LENGTH_LONG).show();
            addinname.setText("");
            return;
        }else {
            int id = myData.insertUser(get_name, get_pw, get_realname, get_contact, get_address);
            if (id == -1) {
                Toast.makeText(Sign_inActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                return;
            } else {
                Toast.makeText(Sign_inActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Sign_inActivity.this, LogInActivity.class);
                // Bundle bundle = new Bundle();
                //bundle.putLong("userid",id);
                startActivity(intent);
                Sign_inActivity.this.finish();
            }
        }
    }
}
