package com.example.apple.ebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.apple.ebook.R.id.btn_login;

public class LogInActivity extends AppCompatActivity{

    private Button log_in ;
    private TextView sign_in ;
    private EditText get_name ;
    private EditText get_pw;

    public int userid;
    public MyData myData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        log_in = (Button) findViewById(btn_login);
        sign_in = (TextView) findViewById(R.id.sign_in);
        get_name = (EditText) findViewById(R.id.addname);
        get_pw = (EditText) findViewById(R.id.addpw);
        myData = new MyData(LogInActivity.this);

        sign_in.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        log_in.setOnClickListener(l);
        sign_in.setOnClickListener(l);

    }

    public int doLogin(){
        String get_user_name = get_name.getText().toString();
        String get_user_pw = get_pw.getText().toString();
        if(get_user_name.equals("") || get_user_pw.equals("")) {
            Toast.makeText(LogInActivity.this, "请输入用户名密码", Toast.LENGTH_LONG).show();
            return 0;
        }else{
            userid = myData.login(get_user_name, get_user_pw);
            if (userid == 0){
                Toast.makeText(LogInActivity.this,"用户名密码错误，请重新输入",
                        Toast.LENGTH_LONG).show();
                get_name.setText("");
                get_pw.setText("");
                return 0;
            }else {
               // SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
               // SharedPreferences.Editor editor = sp.edit();
                Toast.makeText(LogInActivity.this,"输入正确",Toast.LENGTH_SHORT).show();
               // editor.putString("user_name",get_user_name);
               // editor.putString("user_pw",get_user_pw);
                //editor.commit();
                return userid;
            }
        }
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    doLogin();
                    if (userid != 0) {
                        Intent intent = new Intent(LogInActivity.this, FeatureActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("userid", userid);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case R.id.sign_in:
                    Intent intent1 = new Intent(LogInActivity.this, Sign_inActivity.class);
                    startActivity(intent1);
            }
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (myData != null){myData.close();}
    }

}

