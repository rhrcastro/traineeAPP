package bsi.mpoo.traineeufrpe.gui.empregador.gui.CadastroEmpregador;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.gui.extra.MyFragmentPagerAdapterMainActivityEmpregador;

public class CadastroEmpregador extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tela_cadastro_empregador);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layoutCEMP);
        mViewPager = (ViewPager) findViewById(R.id.view_pagerCEMP);

        mViewPager.setAdapter(new MyFragmentPagerAdapterMainActivityEmpregador(getSupportFragmentManager(), getResources().getStringArray(R.array.tabs)));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));


    }
}