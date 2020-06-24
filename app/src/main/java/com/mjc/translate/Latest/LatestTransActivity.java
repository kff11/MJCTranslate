package com.mjc.translate.Latest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.mjc.translate.DBHelper;
import com.mjc.translate.R;

import java.util.ArrayList;

public class LatestTransActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView countTextView;
    private LatestRecyclerViewAdapter adapter;
    private Cursor cursor;
    private DBHelper dbHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_trans);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        layoutInit();
        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.latest_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LatestRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        getItem();
    }

    private void getItem() {
        db.beginTransaction();
        cursor = db.rawQuery("SELECT * FROM latestTrans", null);

        try {
            cursor.moveToFirst();
            countTextView.setText(cursor.getCount() + "개의 기록이 있습니다.");
            ArrayList<LatestRecyclerViewItem> items = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                LatestRecyclerViewItem item = new LatestRecyclerViewItem();
                item.setId(cursor.getInt(0));
                item.setInput(cursor.getString(1));
                item.setOutput(cursor.getString(2));
                item.setLanguage(cursor.getString(3));
                item.setToLanguage(cursor.getString(4));
                items.add(item);
                cursor.moveToNext();
            }
            for (int i = items.size() - 1; i >= 0; i--) {
                adapter.addItem(items.get(i));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor != null) {
                cursor.close();
                db.endTransaction();
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void layoutInit() {
        // 툴바
        toolbar = findViewById(R.id.latest_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        countTextView = findViewById(R.id.latest_count);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }
}
