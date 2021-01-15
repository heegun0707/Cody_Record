package com.example.final_project.cody;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return new CodyListFragment();
            case 1:
                return new CodyGridFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


}

