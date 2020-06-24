package com.mjc.translate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mjc.translate.jpnTrans.JpnToEngFragment;
import com.mjc.translate.jpnTrans.JpnToKorFragment;
import com.mjc.translate.jpnTrans.JpnToZhFragment;
import com.mjc.translate.zhTrans.ZhToEngFragment;
import com.mjc.translate.zhTrans.ZhToJpnFragment;
import com.mjc.translate.zhTrans.ZhToKorFragment;

public class ZhTransActivity extends AppCompatActivity {

    // 레이아웃
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zh_trans);

        layoutInit();
        bottomNavInit();
    }

    private void layoutInit() {
        // 툴바
        toolbar = findViewById(R.id.zh_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);

    }

    private void bottomNavInit() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        BottomNavigationView bottomNavigationView = findViewById(R.id.zh_bottom_navigation_view);

        final ZhToKorFragment zhToKorFragment = new ZhToKorFragment();
        final ZhToEngFragment zhToEngFragment = new ZhToEngFragment();
        final ZhToJpnFragment zhToJpnFragment = new ZhToJpnFragment();

        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.zh_framelayout, zhToKorFragment).commitAllowingStateLoss();

        // 하단 바 이벤트
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_zhToKor: {
                        transaction.replace(R.id.zh_framelayout, zhToKorFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_zhToEng: {
                        transaction.replace(R.id.zh_framelayout, zhToEngFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_zhToJpn: {
                        transaction.replace(R.id.zh_framelayout, zhToJpnFragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
