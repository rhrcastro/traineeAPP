package com.trainee2.GUI.empregador;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.trainee2.R;
import com.trainee2.extras.MyFragmentPagerAdapter;
import com.trainee2.extras.MyFragmentPagerAdapterEMP;

public class TelaCadastroEmpregador extends AppCompatActivity {

    private TabLayout mTabLayout2;
    private ViewPager mViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_empregador);

        mTabLayout2 = (TabLayout)findViewById(R.id.tab_layoutEMP);
        mViewPager2 = (ViewPager)findViewById(R.id.view_pagerEMP);

        mViewPager2.setAdapter(new MyFragmentPagerAdapterEMP(getSupportFragmentManager(), getResources().getStringArray(R.array.tabs)));
        mTabLayout2.setupWithViewPager(mViewPager2);
        mTabLayout2.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
    }
}
