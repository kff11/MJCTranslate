package com.mjc.translate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mjc.translate.engTrans.EngToJpnFragment;
import com.mjc.translate.engTrans.EngToKorFragment;
import com.mjc.translate.engTrans.EngToZhFragment;
import com.mjc.translate.korTrans.KorToEngFragment;
import com.mjc.translate.korTrans.KorToJpnFragment;
import com.mjc.translate.korTrans.KorToZhFragment;

public class EngTransActivity extends AppCompatActivity {

    // 레이아웃
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eng_trans);

        layoutInit();
        bottomNavInit();
    }

    private void layoutInit() {
        // 툴바
        toolbar = findViewById(R.id.eng_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);

    }

    private void bottomNavInit() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        BottomNavigationView bottomNavigationView = findViewById(R.id.eng_bottom_navigation_view);

        final EngToKorFragment engToKorFragment = new EngToKorFragment();
        final EngToJpnFragment engToJpnFragment = new EngToJpnFragment();
        final EngToZhFragment engToZhFragment = new EngToZhFragment();

        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.eng_framelayout, engToKorFragment).commitAllowingStateLoss();

        // 하단 바 이벤트
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_engToKor: {
                        transaction.replace(R.id.eng_framelayout, engToKorFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_engToJpn: {
                        transaction.replace(R.id.eng_framelayout, engToJpnFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_engToZh: {
                        transaction.replace(R.id.eng_framelayout, engToZhFragment).commitAllowingStateLoss();
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
