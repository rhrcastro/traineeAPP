package bsi.mpoo.traineeufrpe.gui.empregador.acesso;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.extra.MyFragmentPagerAdapterMainActivityEmpregador;

public class ActCadastroLoginEmpregador extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_acesso_empregador);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layoutCEMP);
        mViewPager = (ViewPager) findViewById(R.id.view_pagerCEMP);

        mViewPager.setAdapter(new MyFragmentPagerAdapterMainActivityEmpregador(getSupportFragmentManager(), getResources().getStringArray(R.array.tabs)));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.White));
    }
}