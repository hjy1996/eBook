package com.example.apple.ebook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public TextView addbook;
    public int userid;
    public ListView listView;
    public List<Map<String, Object>> all_book;
    public BookAdapter bookAdapter;
    public MyData myData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        Intent get_id = getIntent();
        Bundle getID = get_id.getExtras();
        userid = getID.getInt("userid");

        addbook = (TextView) findViewById(R.id.addbook);
        listView = (ListView) findViewById(R.id.Booklist);

        all_book = new ArrayList<Map<String, Object>>();
        bookAdapter = new BookAdapter(MainActivity.this, all_book);
        myData = new MyData(MainActivity.this);

        addbook.setOnClickListener(l);
        listView.setOnItemClickListener(list);

        listView.setAdapter(bookAdapter);
        showBook();

    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                    Intent intent3 = new Intent(MainActivity.this, AddBookActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("userid",userid);
                    intent3.putExtras(bundle);
                    startActivity(intent3);
        }
    };

    private AdapterView.OnItemClickListener list = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this,MainBookDetailActivity.class);
            Bundle bundle = new Bundle();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) bookAdapter.getItem(position);
            bundle.putString("bookname", (String) map.get(MyData.PvtBookName));
            bundle.putString("bookauth", (String) map.get(MyData.PvtBookAuth));
            bundle.putString("bookinfo", (String) map.get(MyData.PvtBookInfo));
            bundle.putInt("bookid", (int) map.get(MyData.PvtBookID));
            bundle.putInt("hostid", (int) map.get(MyData.PvtID));
            bundle.putInt("bookqueue", (int) map.get(MyData.BookQueue));
            bundle.putInt("userid", userid);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    };

    public void showBook(){
        List<Map<String,Object>> data = myData.ShowBook();
        if (data != null && data.size() > 0 ){
            for(int i = data.size() - 1; i >= 0; i-- ){
                Map<String, Object> map = data.get(i);
                all_book.add(map);
            }
        }
        bookAdapter.resetData(all_book);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userid", userid);
        intent.putExtras(bundle);
        startActivity(intent);
        this.finish();
        return true;
    }

    }

