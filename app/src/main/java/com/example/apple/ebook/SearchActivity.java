package com.example.apple.ebook;

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
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    public EditText key;
    public Button sure;
    public MyData searchData;
    public ListView seachlv;
    public List<Map<String, Object>> keysearch;
    public BookAdapter searchAdapter;
    public int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

        Intent getid = getIntent();
        Bundle getdata = getid.getExtras();
        userid = getdata.getInt("userid");

        key = findViewById(R.id.search_name);
        sure = findViewById(R.id.sure);
        searchData = new MyData(SearchActivity.this);
        seachlv = findViewById(R.id.searchlist);
        keysearch = new ArrayList<Map<String, Object>>();
        searchAdapter = new BookAdapter(SearchActivity.this, keysearch);

        seachlv.setAdapter(searchAdapter);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchBook();
            }
        });

        seachlv.setOnItemClickListener(list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return true;
    }

    public void SearchBook(){
        String keyname = key.getText().toString();
        List<Map<String, Object>> data = searchData.SearchBykey(keyname);
        if (data != null && data.size() > 0 ){
            for(int i = data.size() - 1; i >= 0; i-- ){
                Map<String, Object> map = data.get(i);
                keysearch.add(map);
            }
        }
        searchAdapter.resetData(keysearch);
    }

    private AdapterView.OnItemClickListener list = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(SearchActivity.this,MainBookDetailActivity.class);
            Bundle bundle = new Bundle();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) searchAdapter.getItem(position);
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

}
