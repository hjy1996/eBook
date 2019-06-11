package com.example.apple.ebook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.apple.ebook.MyData.BookQueue;
import static com.example.apple.ebook.MyData.User_Address;
import static com.example.apple.ebook.MyData.User_Contact;
import static com.example.apple.ebook.MyData.User_Realname;

public class MainBookDetailActivity extends AppCompatActivity {

   public String  bookauthor, bookinfo, book_name, host, usercontact, useraddress;
   public ImageButton ima_delete;
   public Button btn_lease, btn_order;
   public int bookid, userid, hostid, booknum;
   public TextView booknamedetail, authordetail, infodetail, hostname;
   public MyData myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_book_detail);

        Intent getdata = getIntent();
        Bundle get_Data = getdata.getExtras();
        book_name = get_Data.getString("bookname");
        bookauthor = get_Data.getString("bookauth");
        bookinfo = get_Data.getString("bookinfo");
        bookid = get_Data.getInt("bookid");
        hostid = get_Data.getInt("hostid");
        booknum = get_Data.getInt("bookqueue");
        userid = get_Data.getInt("userid");


        booknamedetail = (TextView) findViewById(R.id.booknamedetail);
        authordetail = (TextView) findViewById(R.id.authordetail);
        infodetail = (TextView) findViewById(R.id.infodetail);
        ima_delete = (ImageButton) findViewById(R.id.imb_delete);
        btn_lease = (Button) findViewById(R.id.btn_lease);
        btn_order = (Button) findViewById(R.id.btn_order);
        hostname = findViewById(R.id.hostname);
        myData = new MyData(this);
        host = myData.Showname(hostid);

        booknamedetail.setText(book_name);
        authordetail.setText(bookauthor);
        infodetail.setText(bookinfo);
        hostname.setText(host);

        ima_delete.setOnClickListener(l);
        btn_lease.setOnClickListener(l);
        btn_order.setOnClickListener(l);

    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_lease:
                    if (userid == hostid){
                        Toast.makeText(MainBookDetailActivity.this, "自己的书，回头看看别的吧",
                                Toast.LENGTH_LONG).show();
                        MainBookDetailActivity.this.finish();
                        return;
                    }
                    if (booknum == 0){
                        dolease();
                         MainBookDetailActivity.this.finish();
                    } else {Toast.makeText(MainBookDetailActivity.this, "此书已出租,请进行预约",
                            Toast.LENGTH_LONG).show();}
                    break;
                case R.id.btn_order:
                    if (booknum < 4) {
                        dolease();
                    }else {Toast.makeText(MainBookDetailActivity.this, "预约人数太多，" +
                                    "请过几天再来",
                            Toast.LENGTH_LONG).show();
                        MainBookDetailActivity.this.finish();}
                    break;
                case R.id.imb_delete:
                    if (userid == 1 || userid == 2){
                        if (booknum == 0) {
                            boolean result = myData.DeleteBook(bookid);
                            if (result) {
                                Toast.makeText(MainBookDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(MainBookDetailActivity.this, FeatureActivity.class);
                                Bundle bundle1 = new Bundle();
                                bundle1.putInt("userid", userid);
                                intent1.putExtras(bundle1);
                                startActivity(intent1);
                                finish();
                            }
                        } else{
                            Toast.makeText(MainBookDetailActivity.this, "本书正在租赁中", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {Toast.makeText(MainBookDetailActivity.this, "您没有权限删除", Toast.LENGTH_LONG).show();}
            }
        }
    } ;

    public void dolease(){
        String name = myData.getUsername(userid);
        String contact = myData.getUsercontact(userid);
        String address = myData.getUseraddress(userid);
        int num = myData.insertLease(userid, hostid, booknum , bookid, name, contact, address, book_name );
        if (num > 0) {
            myData.UpdataBookNum(bookid, num);
            Toast.makeText(MainBookDetailActivity.this, "操作成功,您的信息已发给书主", Toast.LENGTH_SHORT).show();
            MainBookDetailActivity.this.finish();
        }else {
            Toast.makeText(MainBookDetailActivity.this, "程序错误，稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}
