package com.mjc.translate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mjc.translate.engTrans.EngToJpnFragment;
import com.mjc.translate.engTrans.EngToKorFragment;
import com.mjc.translate.engTrans.EngToZhFragment;
import com.mjc.translate.jpnTrans.JpnToEngFragment;
import com.mjc.translate.jpnTrans.JpnToKorFragment;
import com.mjc.translate.jpnTrans.JpnToZhFragment;

public class JpnTransActivity extends AppCompatActivity {

    // 레이아웃
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpn_trans);

        layoutInit();
        bottomNavInit();

    }

    private void layoutInit() {
        // 툴바
        toolbar = findViewById(R.id.jpn_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void bottomNavInit() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        BottomNavigationView bottomNavigationView = findViewById(R.id.jpn_bottom_navigation_view);

        final JpnToKorFragment jpnToKorFragment = new JpnToKorFragment();
        final JpnToEngFragment jpnToEngFragment = new JpnToEngFragment();
        final JpnToZhFragment jpnToZhFragment = new JpnToZhFragment();

        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.jpn_framelayout, jpnToKorFragment).commitAllowingStateLoss();

        // 하단 바 이벤트
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_jpnToKor: {
                        transaction.replace(R.id.jpn_framelayout, jpnToKorFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_jpnToEng: {
                        transaction.replace(R.id.jpn_framelayout, jpnToEngFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_jpnToZh: {
                        transaction.replace(R.id.jpn_framelayout, jpnToZhFragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
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
