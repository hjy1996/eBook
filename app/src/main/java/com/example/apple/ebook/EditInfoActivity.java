package com.example.apple.ebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditInfoActivity extends AppCompatActivity {

    public EditText recontact, readdress;
    public Button saveinfo;
    public MyData myData;
    public int userid;
    public String contact, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinfo);

        Intent getdata = getIntent();
        Bundle get_Data = getdata.getExtras();
        userid = get_Data.getInt("userid");
        contact = get_Data.getString("contact");
        address = get_Data.getString("address");

        recontact = (EditText) findViewById(R.id.recontact);
        readdress = (EditText) findViewById(R.id.readdress);
        saveinfo = (Button) findViewById(R.id.btn_save);

        recontact.setText(contact);
        readdress.setText(address);

        myData = new MyData(this);

        saveinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contact = recontact.getText().toString();
                address = readdress.getText().toString();
                boolean result = myData.UpdataUser(userid, contact, address);

                if (result){
                    Toast.makeText(EditInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditInfoActivity.this, FeatureActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("userid", userid);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Toast.makeText(EditInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

    }
}
