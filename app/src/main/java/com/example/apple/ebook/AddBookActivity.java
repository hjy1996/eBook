package com.example.apple.ebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookActivity extends AppCompatActivity {

    public EditText bookname, bookauth, bookinfo;
    public Button savebook;
    public MyData myData;
    public int user_prvid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        bookname = (EditText) findViewById(R.id.add_bookname);
        bookauth = (EditText) findViewById(R.id.add_author);
        bookinfo = (EditText) findViewById(R.id.add_info);
        savebook = findViewById(R.id.btn_savebook);

        myData = new MyData(this);

        Intent get_id = getIntent();
        Bundle get_ID = get_id.getExtras();
        user_prvid = get_ID.getInt("userid");

        savebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSave();
            }
        });
    }

    public void doSave(){

        String get_bookname = bookname.getText().toString();
        String get_bookauth = bookauth.getText().toString();
        String get_bookinfo = bookinfo.getText().toString();

        if (get_bookname.equals("")){
            Toast.makeText(AddBookActivity.this, "请输入书名", Toast.LENGTH_LONG).show();
            return;
        }
        if (get_bookauth.equals("")){
            Toast.makeText(AddBookActivity.this, "请输入作者", Toast.LENGTH_LONG).show();
            return;
        }
        if (get_bookinfo.equals("")){
            Toast.makeText(AddBookActivity.this, "请输入详细信息", Toast.LENGTH_LONG).show();
            return;
        }

        int bookid = myData.insertBook(get_bookname, get_bookauth, get_bookinfo, user_prvid);

        if (bookid == -1){
            Toast.makeText(AddBookActivity.this,"保存失败",Toast.LENGTH_LONG).show();
            return;
        }else {
            Toast.makeText(AddBookActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(AddBookActivity.this, MainActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putInt("userid", user_prvid);
            intent1.putExtras(bundle1);
            startActivity(intent1);
            this.finish();
        }

    }

}
