package com.smr.estate.Interface;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigation
{
    public static class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();
        private boolean isTitle = true;

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        public boolean isTitle()
        {
            return this.isTitle;
        }

        public void setTitle(boolean title)
        {
            this.isTitle = title;
        }

        public Fragment getItem(int position)
        {
            return (Fragment) this.mFragmentList.get(position);
        }

        public int getCount()
        {
            return this.mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public void addFragment(Fragment fragment)
        {
            this.mFragmentList.add(fragment);
            this.isTitle = false;
        }

        public CharSequence getPageTitle(int position)
        {
            return this.isTitle ? (CharSequence) this.mFragmentTitleList.get(position) : null;
        }
    }

    public static void setUpViewPager(Activity activity, final ViewPager viewPager, ViewPagerAdapter adapter, final BottomNavigationView navigation, int resMenuId, int OffscreenPageLimit, int defaultItemSelected)
    {
        viewPager.setAdapter(adapter);
        PopupMenu p = new PopupMenu(activity, (View) null);
        Menu menu = p.getMenu();
        activity.getMenuInflater().inflate(resMenuId, menu);
        final int[] ids = new int[menu.size()];

        for (int i = menu.size() - 1; i >= 0; --i)
        {
            ids[i] = menu.getItem(i).getItemId();
        }

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                for (int i = 0; i < ids.length; ++i)
                {
                    if (item.getItemId() == ids[i])
                    {
                        viewPager.setCurrentItem(i);
                    }
                }

                return true;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            public void onPageSelected(int position)
            {
                navigation.setSelectedItemId(ids[position]);
            }

            public void onPageScrollStateChanged(int state)
            {
            }
        });
        viewPager.setOffscreenPageLimit(OffscreenPageLimit);
        viewPager.setCurrentItem(defaultItemSelected);
    }
}
