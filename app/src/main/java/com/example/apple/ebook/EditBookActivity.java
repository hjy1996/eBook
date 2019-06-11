package com.example.apple.ebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditBookActivity extends AppCompatActivity {

    public EditText bookname, bookauth, bookinfo;
    public Button savebook;
    public String bookname_detail, bookauth_detail, bookinfo_detail;
    public int bookid, userid;

    private MyData myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        Intent getdata = getIntent();
        Bundle get_Data = getdata.getExtras();

        bookid = get_Data.getInt("bookid");
        userid = get_Data.getInt("userid");
        bookauth_detail = get_Data.getString("bookauthor");
        bookinfo_detail = get_Data.getString("bookinfo");
        bookname_detail = get_Data.getString("bookname");
        savebook = (Button) findViewById(R.id.btn_save_edit);
        bookname = (EditText) findViewById(R.id.rebookname);
        bookauth = (EditText) findViewById(R.id.reauthor);
        bookinfo = (EditText) findViewById(R.id.reinfo);

        bookname.setText(bookname_detail);
        bookauth.setText(bookauth_detail);
        bookinfo.setText(bookinfo_detail);

        savebook.setOnClickListener(saveClicListener);
    }

    View.OnClickListener saveClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myData = new MyData(EditBookActivity.this);
            boolean result = myData.UpdataBook(bookid, bookname.getText().toString(), bookauth.getText()
                    .toString(), bookinfo.getText().toString());
            if (result) {
                Toast.makeText(EditBookActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditBookActivity.this, FeatureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("userid", userid);
                intent.putExtras(bundle);
                startActivity(intent);
                EditBookActivity.this.finish();
            }else {
                Toast.makeText(EditBookActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                EditBookActivity.this.finish();
            }
        }
    };


}
