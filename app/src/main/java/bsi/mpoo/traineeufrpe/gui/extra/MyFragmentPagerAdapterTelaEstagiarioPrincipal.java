package bsi.mpoo.traineeufrpe.gui.extra;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bsi.mpoo.traineeufrpe.gui.estagiario.gui.Home.tela_nova.fragment.FragmentNvagas;
import bsi.mpoo.traineeufrpe.gui.estagiario.gui.Home.tela_nova.fragment.FragmentVagasRecomendadas;
import bsi.mpoo.traineeufrpe.gui.estagiario.gui.Home.tela_nova.fragment.FragmentVagasEmAberto;


public class MyFragmentPagerAdapterTelaEstagiarioPrincipal extends FragmentStatePagerAdapter {

    private String[] mTabTitles;
    public MyFragmentPagerAdapterTelaEstagiarioPrincipal(FragmentManager fm, String[] TabTitles){
        super(fm);
        this.mTabTitles = TabTitles;

    }

    @Override
    public Fragment getItem(int i) {

        switch(i){
            case 0:
                return new FragmentNvagas();
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

