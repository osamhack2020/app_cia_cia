package com.dygames.cia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private com.dygames.cia.MainFragment fragmentMain = new com.dygames.cia.MainFragment();
    private com.dygames.cia.SearchFragment fragmentSearch = new com.dygames.cia.SearchFragment();
    private com.dygames.cia.CourseFragment fragmentCourse = new com.dygames.cia.CourseFragment();
    private com.dygames.cia.InfoFragment fragmentInfo = new com.dygames.cia.InfoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentMain).commitAllowingStateLoss();

        ((BottomNavigationView) findViewById(R.id.navigationView)).setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                ((BottomNavigationView) findViewById(R.id.navigationView)).getMenu().setGroupCheckable(0, true, true);
                switch (menuItem.getItemId()) {
                    case R.id.bottom_main:
                        transaction.replace(R.id.frameLayout, fragmentMain).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_search:
                        transaction.replace(R.id.frameLayout, fragmentSearch).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_course:
                        transaction.replace(R.id.frameLayout, fragmentCourse).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_info:
                        transaction.replace(R.id.frameLayout, fragmentInfo).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });

    }

    //@Override
    //public void onBackPressed() {
    //}
}