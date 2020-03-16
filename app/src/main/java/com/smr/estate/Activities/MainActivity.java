package com.smr.estate.Activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.smr.estate.Fragment.AdsFragment;

import com.smr.estate.Fragment.ArchiveFragment;
import com.smr.estate.Fragment.ProfileFragment;
import com.smr.estate.Fragment.ToolsFragment;
import com.smr.estate.Interface.BottomNavigation;
import com.smr.estate.R;

//Custom pager handler with bottom navigation

public class MainActivity extends AppCompatActivity
{
    AdsFragment adsFragment = new AdsFragment();
    ArchiveFragment archiveFragment = new ArchiveFragment();
    ToolsFragment toolsFragment = new ToolsFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("tag","-----------------"+"MAin activity"+"--------------------------");

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        viewPager = findViewById(R.id.viewpager);

        BottomNavigation.ViewPagerAdapter adapter = new BottomNavigation.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(profileFragment);
        adapter.addFragment(toolsFragment);
        adapter.addFragment(archiveFragment);
        adapter.addFragment(adsFragment);

        BottomNavigation.setUpViewPager(MainActivity.this, viewPager, adapter, bottomNavigationView, R.menu.bottom_navigation_items, 3, 3);
    }
}
