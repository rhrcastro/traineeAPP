package bsi.mpoo.traineeufrpe.gui.estagiario.acesso;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.extra.MyFragmentPagerAdapterFragMainActivity;


public class ActCadastroLoginEstagiario extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estagiario);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mViewPager.setAdapter(new MyFragmentPagerAdapterFragMainActivity(getSupportFragmentManager(), getResources().getStringArray(R.array.tabs)));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));


    }
}


