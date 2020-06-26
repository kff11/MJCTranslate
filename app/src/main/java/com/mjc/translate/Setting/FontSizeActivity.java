package com.mjc.translate.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mjc.translate.R;
import com.mjc.translate.SharedPreference;

public class FontSizeActivity extends AppCompatActivity {

    private SharedPreference sharedPreference;
    private Toolbar toolbar;
    private SeekBar seekBar;
    private TextView previewTextView, resultTextView;
    private int fontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_size);

        layoutInit();
        seekBarInit();
        seekBarLister();
    }

    private void seekBarInit() {
        seekBar = findViewById(R.id.fontSize_seekBar);

        seekBar.setProgress(sharedPreference.getSharedInteger("fontSize") / 5 - 3);
        previewTextView.setTextSize((seekBar.getProgress() + 3) * 5);
        _setResult(sharedPreference.getSharedInteger("fontSize"));
    }

    private void seekBarLister() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // seekBar 움직일 시 작은 진동
                vibrator.vibrate(100);

                fontSize = (progress + 3) * 5;
                previewTextView.setTextSize(fontSize);
                _setResult(fontSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sharedPreference.setSharedInteger("fontSize", fontSize);
            }
        });
    }

    private void layoutInit() {
        sharedPreference = new SharedPreference(this);
        previewTextView = findViewById(R.id.fontSize_preview);
        resultTextView = findViewById(R.id.fontSize_result);

        // 툴바
        toolbar = findViewById(R.id.fontSize_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void _setResult(int progress) {
        switch (progress) {
            case 15:
                resultTextView.setText("작게");
                break;
            case 20:
                resultTextView.setText("보통 (기본값)");
                break;
            case 25:
                resultTextView.setText("크게");
                break;
            case 30:
                resultTextView.setText("최대");
                break;
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
}
