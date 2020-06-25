package com.mjc.translate.Latest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.mjc.translate.R;
import com.mjc.translate.SharedPreference;

public class LatestTransSelectActivity extends AppCompatActivity {

    private Intent intent;
    private String input, output, language, toLanguage;
    private TextView inputTextView, outputTextView, languageTextView, toLanguageTextView;
    private Toolbar toolbar;
    private SharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_trans_select);

        valuesInit();
        layoutInit();
    }

    private void valuesInit() {
        intent = getIntent();
        if (intent.getStringArrayExtra("values") != null) {
            String[] values = intent.getStringArrayExtra("values");
            input = values[0];
            output = values[1];
            language = values[2];
            toLanguage = values[3];
        }
    }

    private void layoutInit() {
        inputTextView = findViewById(R.id.latestSelect_input);
        outputTextView = findViewById(R.id.latestSelect_output);
        languageTextView = findViewById(R.id.latestSelect_language);
        toLanguageTextView = findViewById(R.id.latestSelect_toLanguage);

        inputTextView.setText(input);
        outputTextView.setText(output);
        languageTextView.setText(language);
        toLanguageTextView.setText(toLanguage);

        sharedPreference = new SharedPreference(this);
        int fontSize = sharedPreference.getSharedInteger("fontSize");

        inputTextView.setTextSize(fontSize);
        outputTextView.setTextSize(fontSize);

        // 툴바
        toolbar = findViewById(R.id.latestSelect_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
