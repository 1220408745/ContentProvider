package com.xue.qin.demo.customer;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by xue.qin on 2017/6/11.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button button;
    private Button button1;
    private Button button2;
    private static final String[] PROJECTION = {"_id", "name", "age", "gender"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        button = (Button) findViewById(R.id.testProvider);
        button1 = (Button) findViewById(R.id.testProvider1);
        button2 = (Button) findViewById(R.id.testProvider2);

        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testProvider:
                getResult(Uri.parse("content://" + "database.qin.xue.com.databasedemo" + "/demobase"));
                break;
            case R.id.testProvider1:
                getResult(Uri.parse("content://" + "database.qin.xue.com.databasedemo" + "/demobase/1"));
                break;
            case R.id.testProvider2:
//                getResult(Uri.parse("content://" + "database.qin.xue.com.databasedemo" + "/demobase/gender/0"));
                getResult(Uri.parse("content://" + "database.qin.xue.com.databasedemo" + "/demobase/age/9"));
                break;
        }
    }

    private void getResult(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, PROJECTION, null, null, null);
        Log.i(TAG, "cursor.getCount() = " + cursor.getCount());
        if (cursor == null || cursor.getCount() == 0) {
            return;
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            int gender = cursor.getInt(3);
            Log.i(TAG, "id = " + id + "  name = " + name + "  age = " + age + "  gender = " + gender);
        }
    }

}
