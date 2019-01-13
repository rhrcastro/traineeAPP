package bsi.mpoo.traineeufrpe.gui.extra;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bsi.mpoo.traineeufrpe.gui.estagiario.acesso.fragment.FragmentCadastroAcademico;
import bsi.mpoo.traineeufrpe.gui.estagiario.acesso.fragment.FragmentLoginEstagiario;

public class MyFragmentPagerAdapterFragMainActivity extends FragmentStatePagerAdapter {

    private String[] mTabTitles;
    public MyFragmentPagerAdapterFragMainActivity(FragmentManager fm, String[] TabTitles){
        super(fm);
        this.mTabTitles = TabTitles;

    }

    @Override
    public Fragment getItem(int i) {

        switch(i){
            case 0:
                return new FragmentLoginEstagiario();
            case 1:
                return new FragmentCadastroAcademico();
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

