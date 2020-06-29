package com.mjc.translate.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.mjc.translate.R;
import com.mjc.translate.SharedPreference;

public class SettingActivity extends AppCompatActivity {

    private SharedPreference sharedPreference;
    private Switch autoSwitch;
    private Toolbar toolbar;
    private TextView fontSize;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        layoutInit();
        switchInit();
        switchLisnter();
    }

    private void layoutInit() {
        sharedPreference = new SharedPreference(this);
        fontSize = findViewById(R.id.setting_fontSize_result);

        // 툴바
        toolbar = findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void switchInit() {
        autoSwitch = findViewById(R.id.setting_autoCheck);
        autoSwitch.setChecked(sharedPreference.getSharedboolean("autoSave"));
    }

    private void switchLisnter() {
        autoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // 스위치 클릭 시 작은 진동
                vibrator.vibrate(100);
                if (isChecked) {
                    sharedPreference.setSharedboolean("autoSave", true);
                } else {
                    sharedPreference.setSharedboolean("autoSave", false);
                }
            }
        });
    }

    private void setFontSizeResult() {
        if (sharedPreference.getSharedInteger("fontSize") == 15) {
            fontSize.setText("작게");
        } else if (sharedPreference.getSharedInteger("fontSize") == 20) {
            fontSize.setText("보통 (기본값)");
        } else if (sharedPreference.getSharedInteger("fontSize") == 25) {
            fontSize.setText("크게");
        } else if (sharedPreference.getSharedInteger("fontSize") == 30) {
            fontSize.setText("최대");
        }
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_fontSize:
                intent = new Intent(this, FontSizeActivity.class);
                break;
        }

        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFontSizeResult();
    }
}
