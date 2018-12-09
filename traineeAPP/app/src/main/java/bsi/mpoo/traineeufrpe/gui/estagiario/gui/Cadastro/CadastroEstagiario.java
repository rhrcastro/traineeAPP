package bsi.mpoo.traineeufrpe.gui.estagiario.gui.Cadastro;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.gui.extra.MyFragmentPagerAdapterMainActivity;


public class CadastroEstagiario extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estagiario);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mViewPager.setAdapter(new MyFragmentPagerAdapterMainActivity(getSupportFragmentManager(), getResources().getStringArray(R.array.tabs)));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));


    }
}


