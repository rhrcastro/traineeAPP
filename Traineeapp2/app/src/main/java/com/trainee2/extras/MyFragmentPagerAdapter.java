package com.trainee2.extras;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.trainee2.GUI.fragment.estagiario.Cadastro_b_Login_a.Fragment_a;
import com.trainee2.GUI.fragment.estagiario.Cadastro_b_Login_a.Fragment_b;

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mTabTitles;
    public MyFragmentPagerAdapter(FragmentManager fm, String[] TabTitles){
        super(fm);
        this.mTabTitles = TabTitles;

    }

    @Override
    public Fragment getItem(int i) {

        switch(i){
            case 0:
                return new Fragment_a();
            case 1:
                return new Fragment_b();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int i){
        return this.mTabTitles[i];

    }
}
