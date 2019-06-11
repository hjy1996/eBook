package com.example.apple.ebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BookDetailActivity extends AppCompatActivity {

    public String bookname, bookauthor, bookinfo;
    public int bookid, userid, bookqueue;
    public TextView bookname_detail, bookauthor_detail, bookinfo_detail;
    public Button btn_edit, btn_delete;
    public MyData myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__detail);

        bookname_detail = (TextView) findViewById(R.id.bookname_detail);
        bookauthor_detail = (TextView) findViewById(R.id.author_detail);
        bookinfo_detail = (TextView) findViewById(R.id.info_detail);
        btn_delete = (Button) findViewById(R.id.btn_deletbook);
        btn_edit = (Button) findViewById(R.id.btn_editbook);
        myData = new MyData(this);

        Intent getData =  getIntent();
        Bundle getdata = getData.getExtras();

        bookname = getdata.getString("bookname");
        bookauthor = getdata.getString("bookauthor");
        bookinfo = getdata.getString("bookinfo");
        bookid = getdata.getInt("bookid");
        userid = getdata.getInt("userid");
        bookqueue = getdata.getInt("bookqueue");

        bookname_detail.setText(bookname);
        bookauthor_detail.setText(bookauthor);
        bookinfo_detail.setText(bookinfo);

        btn_edit.setOnClickListener(l);
        btn_delete.setOnClickListener(l);

    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){

                case R.id.btn_editbook:
                    Intent intent = new Intent(BookDetailActivity.this, EditBookActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("bookid",bookid);
                    bundle.putString("bookname",bookname);
                    bundle.putString("bookauthor",bookauthor);
                    bundle.putString("bookinfo",bookinfo);
                    bundle.putInt("userid", userid);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    BookDetailActivity.this.finish();
                    break;
                case R.id.btn_deletbook:
                    if (bookqueue > 0){
                        Toast.makeText(BookDetailActivity.this,"本书正在租赁中",Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        boolean result = myData.DeleteBook(bookid);
                        if (result) {
                            Toast.makeText(BookDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            BookDetailActivity.this.finish();
                        } else {
                            Toast.makeText(BookDetailActivity.this, "删除失败", Toast.LENGTH_LONG).show();

                        }
                    }

            }
        }
    };

}
