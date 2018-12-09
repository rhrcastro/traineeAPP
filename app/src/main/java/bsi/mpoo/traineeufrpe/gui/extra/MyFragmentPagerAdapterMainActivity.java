package bsi.mpoo.traineeufrpe.gui.extra;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bsi.mpoo.traineeufrpe.gui.estagiario.gui.Cadastro.fragment_cadastro_e_login.FragmentCadastroEstagiario;
import bsi.mpoo.traineeufrpe.gui.estagiario.gui.Cadastro.fragment_cadastro_e_login.FragmentLoginEstagiario;

public class MyFragmentPagerAdapterMainActivity extends FragmentStatePagerAdapter {

    private String[] mTabTitles;
    public MyFragmentPagerAdapterMainActivity(FragmentManager fm, String[] TabTitles){
        super(fm);
        this.mTabTitles = TabTitles;

    }

    @Override
    public Fragment getItem(int i) {

        switch(i){
            case 0:
                return new FragmentLoginEstagiario();
            case 1:
                return new FragmentCadastroEstagiario();
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

