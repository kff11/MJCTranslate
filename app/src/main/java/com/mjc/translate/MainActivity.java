package com.mjc.translate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mjc.translate.Latest.LatestTransActivity;
import com.mjc.translate.Setting.SettingActivity;
import com.mjc.translate.config.Papago;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    // 레이아웃
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private Spinner languageSpinner;
    private Spinner toLanguageSpinner;
    private String langouage;
    private String toLanguage;
    private String languageCode;
    private String toLanguageCode;

    private EditText inputText;
    private TextView outputText;
    private BootstrapButton submitButton;
    private String inputString;
    private String outputString;
    private String result;

    private SharedPreference sharedPreference;

    private Intent intent;

    private long initTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.main_drawer_layout);

        spinnerInit();
        layoutInit();
    }

    private String convertToLanguageCode(String language) {
        String result = "";
        switch (language) {
            case "한국어":
                result = "ko";
                break;
            case "영어":
                result = "en";
                break;
            case "일본어":
                result = "ja";
                break;
            case "중국어":
                result = "zh-CN";
                break;
        }
        return result;
    }

    private void spinnerInit() {
        languageSpinner = findViewById(R.id.main_spinner_language);
        toLanguageSpinner = findViewById(R.id.main_spinner_toLanguage);
        toLanguageSpinner.setSelection(1);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langouage = parent.getItemAtPosition(position).toString();
                languageCode = convertToLanguageCode(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toLanguage = parent.getItemAtPosition(position).toString();
                toLanguageCode = convertToLanguageCode(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setFontSize() {
        int fontSize = sharedPreference.getSharedInteger("fontSize");
        inputText.setTextSize(fontSize);
        outputText.setTextSize(fontSize);
    }

    private void layoutInit() {

        inputText = findViewById(R.id.main_input);
        outputText = findViewById(R.id.main_output);
        submitButton = findViewById(R.id.main_submit);

        sharedPreference = new SharedPreference(this);

        // 툴바
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);

        // 네비게이션 드로어
        NavigationView navigationView = findViewById(R.id.navgation_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main_menu_home:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        break;
                    case R.id.main_menu_latestTrans:
                        intent = new Intent(getApplicationContext(), LatestTransActivity.class);
                        break;
                    case R.id.main_menu_setting:
                        intent = new Intent(getApplicationContext(), SettingActivity.class);
                        break;
                }
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                return false;
            }
        });
    }

    public void onClick(View v) {
        if (!languageCode.equals(toLanguageCode)) {
            inputString = inputText.getText().toString();
            new BackgroundTask().execute();
        } else {
            Toast.makeText(this, "번역 언어를 바꿔주세요!", Toast.LENGTH_LONG).show();
        }
    }

    // 백그라운드에서 파파고 API 사용!
    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(Integer... arg0) {
            StringBuilder output = new StringBuilder();
            String clientId = Papago.getClientId(); // 애플리케이션 클라이언트 아이디 값";
            String clientSecret = Papago.getClientSecret(); // 애플리케이션 클라이언트 시크릿 값";
            try {
                // 번역문을 UTF-8으로 인코딩합니다.
                String text = URLEncoder.encode(inputString, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";

                // 파파고 API와 연결을 수행합니다.
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                // 번역할 문장을 파라미터로 전송합니다.
                String postParams = "source=" + languageCode + "&target=" + toLanguageCode + "&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();

                // 번역 결과를 받아옵니다.
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    output.append(inputLine);
                }
                br.close();
            } catch (Exception ex) {
                Log.e("SampleHTTP", "Exception in processing response.", ex);
                ex.printStackTrace();
            }
            result = output.toString();
            return null;
        }

        protected void onPostExecute(Integer a) {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            if (element.getAsJsonObject().get("errorMessage") != null) {
                Log.e("번역 오류", "번역 오류가 발생했습니다. " +
                        "[오류 코드: " + element.getAsJsonObject().get("errorCode").getAsString() + "]");
            } else if (element.getAsJsonObject().get("message") != null) {
                // 번역 결과 출력
                outputString = element.getAsJsonObject().get("message").getAsJsonObject().get("result")
                        .getAsJsonObject().get("translatedText").getAsString();
                outputText.setText(outputString);

                if (sharedPreference.getSharedboolean("autoSave")) {
                    DBHelper.addLatestTrans(inputString, outputString, langouage, toLanguage, getApplicationContext());
                }
            }

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                initTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFontSize();
    }
}
