package com.trainee2.extras;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.trainee2.GUI.fragment.empregador.Home.FragmentCandidato;
import com.trainee2.GUI.fragment.empregador.Home.FragmentMinhaVaga;

public class MyFragmentPagerAdapterTEMP extends FragmentStatePagerAdapter {
    private String[] mTabTitles;
    public MyFragmentPagerAdapterTEMP(FragmentManager fm, String[] TabTitles) {
        super(fm);
        this.mTabTitles = TabTitles;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new FragmentMinhaVaga();
            case 1:
                return new FragmentCandidato();
            default:
                return null;
        }
    }

    @Override
    public int getCount() { return this.mTabTitles.length; }

    @Override
    public CharSequence getPageTitle(int i) {
        return this.mTabTitles[i];

    }
}
