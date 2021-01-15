package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.final_project.cody.CodyFragment;
import com.example.final_project.explain.ExplainFragment;
import com.example.final_project.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    CodyFragment codyFragment;
    ExplainFragment desFragment;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        // 하단 네비게이션 추가
        bottomNavigationView = findViewById(R.id.nav_view);

        // 프래그먼트 관리자 생성
        fragmentManager = getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();
        // 프래그먼트 객체 생성
        homeFragment = new HomeFragment();
        codyFragment = new CodyFragment();
        desFragment = new ExplainFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, homeFragment).commitAllowingStateLoss();

        // 리스너(프래그먼트 스위치) -> 네비게이션 리스너 사용
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, homeFragment, getString(R.string.fragment_home)).commitAllowingStateLoss();
                        ft.addToBackStack(null);
                        return true;

                    case R.id.navigation_cody:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, codyFragment, getString(R.string.fragment_cody)).commitAllowingStateLoss();
                        ft.addToBackStack(null);
                        return true;

                    case R.id.navigation_des:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, desFragment, getString(R.string.fragment_des)).commitAllowingStateLoss();
                        ft.addToBackStack(null);
                        return true;

                    default:
                        return false;
                }
            }
        });

    }
}