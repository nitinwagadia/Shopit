package com.cloudcomputing.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cloudcomputing.shopit.FragmentTab;

import java.util.ArrayList;

/**
 * Created by Nitin on 12/11/2015.
 */
public class MainScreenPagerAdapter extends FragmentPagerAdapter {

    ArrayList<String> mTitle;

    public MainScreenPagerAdapter(FragmentManager fm, ArrayList<String> list) {
        super(fm);
        mTitle = list;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment temp = new FragmentTab();
        Bundle b = new Bundle();
        b.putString("text", mTitle.get(position));
        temp.setArguments(b);
        return new FragmentTab();
    }

    @Override
    public int getCount() {
        return mTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTitle.get(position);
    }
}
