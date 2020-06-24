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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mjc.translate.Latest.LatestTransActivity;
import com.mjc.translate.korTrans.KorToEngFragment;
import com.mjc.translate.korTrans.KorToJpnFragment;
import com.mjc.translate.korTrans.KorToZhFragment;

public class MainActivity extends AppCompatActivity {

    // 레이아웃
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private Intent intent;

    private long initTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.main_drawer_layout);

        layoutInit();
        bottomNavInit();
    }

    private void layoutInit() {
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
                    case R.id.main_menu_engTrans:
                        intent = new Intent(getApplicationContext(), EngTransActivity.class);
                        break;
                    case R.id.main_menu_jpnTrans:
                        intent = new Intent(getApplicationContext(), JpnTransActivity.class);
                        break;
                    case R.id.main_menu_zhTrans:
                        intent = new Intent(getApplicationContext(), ZhTransActivity.class);
                        break;
                    case R.id.main_menu_latestTrans:
                        intent = new Intent(getApplicationContext(), LatestTransActivity.class);
                        break;
                }
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                return false;
            }
        });
    }

    private void bottomNavInit() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        final KorToEngFragment korToEngFragment = new KorToEngFragment();
        final KorToJpnFragment korToJpnFragment = new KorToJpnFragment();
        final KorToZhFragment korToZhFragment = new KorToZhFragment();

        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_framelayout, korToEngFragment).commitAllowingStateLoss();

        // 하단 바 이벤트
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_korToEng: {
                        transaction.replace(R.id.main_framelayout, korToEngFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_korToJpn: {
                        transaction.replace(R.id.main_framelayout, korToJpnFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_korToZh: {
                        transaction.replace(R.id.main_framelayout, korToZhFragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });

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
}
