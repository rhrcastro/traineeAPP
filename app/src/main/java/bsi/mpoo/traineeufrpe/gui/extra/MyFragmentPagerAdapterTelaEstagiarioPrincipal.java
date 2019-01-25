package bsi.mpoo.traineeufrpe.gui.extra;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bsi.mpoo.traineeufrpe.gui.estagiario.home.fragment.FragmentNovasVagas;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.fragment.FragmentVagasRecomendadas;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.fragment.FragmentVagasEmAberto;

public class MyFragmentPagerAdapterTelaEstagiarioPrincipal extends FragmentStatePagerAdapter {

    private final String[] mTabTitles;
    public MyFragmentPagerAdapterTelaEstagiarioPrincipal(FragmentManager fm, String[] TabTitles){
        super(fm);
        this.mTabTitles = TabTitles;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new FragmentNovasVagas();
            case 1:
                return new FragmentVagasEmAberto();
            case 2:
                return new FragmentVagasRecomendadas();
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