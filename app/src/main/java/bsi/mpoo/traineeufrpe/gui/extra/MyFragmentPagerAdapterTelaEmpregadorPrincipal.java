package bsi.mpoo.traineeufrpe.gui.extra;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bsi.mpoo.traineeufrpe.gui.empregador.home.FragmentCandidato;
import bsi.mpoo.traineeufrpe.gui.empregador.home.FragmentMinhaVaga;

public class MyFragmentPagerAdapterTelaEmpregadorPrincipal extends FragmentStatePagerAdapter {
    private String[] mTabTitles;
    public MyFragmentPagerAdapterTelaEmpregadorPrincipal(FragmentManager fm, String[] TabTitles) {
        super(fm);
        this.mTabTitles = TabTitles;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
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
    public CharSequence getPageTitle(int i){ return this.mTabTitles[i]; }
}
