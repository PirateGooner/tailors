package com.project.android.tailor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class DesignsPagerAdapter extends FragmentPagerAdapter {

    public DesignsPagerAdapter(FragmentManager fm){
        super(fm);
    }


/*
    public DesignsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
*/
    public Fragment getItem(int i){
        switch(i){
            case 0:
                return new NewDesignsFragment();
            case 1:
                return new TestFragment();
        }
        return null;
    }

    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "New Designs";
            case 1:
                return "Tab 2";
            default:
                return null;
        }
    }

    public int getCount(){
        return 2;
    }
}
