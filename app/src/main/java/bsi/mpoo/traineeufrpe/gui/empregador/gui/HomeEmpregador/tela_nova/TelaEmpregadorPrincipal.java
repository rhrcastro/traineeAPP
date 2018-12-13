package bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_nova;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.gui.empregador.gui.CadastroEmpregador.CadastroEmpregador;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.EditarEmpresa.EditarEmpresa;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.PerfilEmpregador.PerfilEmpregador;
import bsi.mpoo.traineeufrpe.gui.extra.MyFragmentPagerAdapterTelaEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.Sessao.Sessao;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;

public class TelaEmpregadorPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_empregador_principal);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layoutTEMP);
        mViewPager = (ViewPager)findViewById(R.id.view_pagerTEMP);

        mViewPager.setAdapter(new MyFragmentPagerAdapterTelaEmpregadorPrincipal(getSupportFragmentManager(), getResources().getStringArray(R.array.tabsPrincipalEmpregador)));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.trocarImagem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headView  = navigationView.getHeaderView(0);
        TextView nome = headView.findViewById(R.id.nomeEmpregadorDrawer);
        TextView email = headView.findViewById(R.id.emailEmpregadorDrawer);
        nome.setText(SessaoEmpregador.instance.getEmpregador().getNome());
        email.setText(SessaoEmpregador.instance.getEmpregador().getEmail());


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exibirConfirmacaoSair();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tela_empregador_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            exibirPerfil();
        } else if (id == R.id.nav_slideshow) {
            exibirConfiguracoes();
        } else if (id == R.id.nav_manage) {
            exibirConfirmacaoSair();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void exibirConfirmacaoSair() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setTitle("Sair");
        msgBox.setMessage("Deseja mesmo sair?");
        setBtnPositivoSair(msgBox);
        setBtnNegativoSair(msgBox);
        msgBox.show();
    }
    public void setBtnPositivoSair(AlertDialog.Builder msgBox){
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Sessao.getInstance().reset();
                exibirTelaLogin();
                finish();
            }
        });

    }
    public void setBtnNegativoSair(AlertDialog.Builder msgBox){
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }
    public void exibirTelaLogin(){
        Intent intent = new Intent(this, CadastroEmpregador.class);
        startActivity(intent);
    }

    private void exibirPerfil() {
        Intent intent = new Intent(getBaseContext(), PerfilEmpregador.class);
        startActivity(intent);
    }
    private void exibirConfiguracoes() {
        Intent intent = new Intent(getBaseContext(), EditarEmpresa.class);
        startActivity(intent);
    }
}
