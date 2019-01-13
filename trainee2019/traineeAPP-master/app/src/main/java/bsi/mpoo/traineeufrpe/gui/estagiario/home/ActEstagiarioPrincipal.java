package bsi.mpoo.traineeufrpe.gui.estagiario.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.estagiario.perfil.ActExibirPerfilEstagiario;
import bsi.mpoo.traineeufrpe.gui.estagiario.acesso.ActCadastroLoginEstagiario;

import bsi.mpoo.traineeufrpe.gui.estagiario.perfil.EditarPerfilEstagiario;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNovasVagas;
import bsi.mpoo.traineeufrpe.gui.extra.MyFragmentPagerAdapterTelaEstagiarioPrincipal;
import bsi.mpoo.traineeufrpe.gui.main.ActContato;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import de.hdodenhof.circleimageview.CircleImageView;

public class ActEstagiarioPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    static int tabSelect = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_estagiario_principal);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layoutTEP);
        mViewPager = (ViewPager)findViewById(R.id.view_pagerTEP);
        mTabLayout.getTabAt(tabSelect);
        mViewPager.setAdapter(new MyFragmentPagerAdapterTelaEstagiarioPrincipal(getSupportFragmentManager(), getResources().getStringArray(R.array.tabsPrincipal)));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.White));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headView  = navigationView.getHeaderView(0);
        TextView nome = headView.findViewById(R.id.nomeDrawerEstagiario);
        TextView email = headView.findViewById(R.id.emailDrawerEstagiario);
        CircleImageView imagem = headView.findViewById(R.id.imagemDrawerEstagiario);
        byte[] foto = SessaoEstagiario.getInstance().getPessoa().getEstagiario().getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
        imagem.setImageBitmap(bitmap);
        nome.setText(SessaoEstagiario.getInstance().getPessoa().getNome());
        email.setText(SessaoEstagiario.getInstance().getPessoa().getEstagiario().getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exibirConfirmacaoSair();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.tela_estagiario_principal, menu);
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
            exibirPerfilEstagiario();
        } else if (id == R.id.nav_gallery) {
            openNotificacoesEstagiario();
            return true;
        } else if (id == R.id.nav_slideshow) {
            openConfiguracoesEstagiario();
        } else if (id == R.id.nav_manage) {
            exibirConfirmacaoSair();
        } else if (id == R.id.nav_share) {
            openPerfilEstagiario();
        } else if (id == R.id.nav_send) {
            openContato();
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
                SessaoEstagiario.getInstance().reset();
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
        Intent intent = new Intent(this, ActCadastroLoginEstagiario.class);
        startActivity(intent);
    }
    public void openContato() {
        Intent intent = new Intent(this, ActContato.class);
        startActivity(intent);
    }
    public void exibirPerfilEstagiario(){
        Intent intent = new Intent(this, ActExibirPerfilEstagiario.class);
        startActivity(intent);
        finish();
    }
    public void openPerfilEstagiario() {
        Intent intent = new Intent(this, ActExibirPerfilEstagiario.class);
        startActivity(intent);
        finish();
    }
    private void openConfiguracoesEstagiario() {
        return;
    }
    private void openNotificacoesEstagiario() {
        Intent intent = new Intent(this, ActNotificacoesEstagiario.class);
        startActivity(intent);
    }
}
