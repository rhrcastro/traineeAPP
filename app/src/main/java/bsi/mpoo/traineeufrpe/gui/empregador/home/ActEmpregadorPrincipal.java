package bsi.mpoo.traineeufrpe.gui.empregador.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import bsi.mpoo.traineeufrpe.gui.empregador.home.vaga.ActNotificacoesEmpregador;
import bsi.mpoo.traineeufrpe.gui.empregador.home.vaga.CadastrarVaga;
import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.empregador.acesso.ActCadastroLoginEmpregador;
import bsi.mpoo.traineeufrpe.gui.empregador.perfil.PerfilEmp;
import bsi.mpoo.traineeufrpe.gui.extra.MyFragmentPagerAdapterTelaEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.gui.main.ActContato;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import de.hdodenhof.circleimageview.CircleImageView;

public class ActEmpregadorPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_empregador_principal);

        mTabLayout = findViewById(R.id.tab_layoutTEMP);
        mViewPager = findViewById(R.id.view_pagerTEMP);

        mViewPager.setAdapter(new MyFragmentPagerAdapterTelaEmpregadorPrincipal(getSupportFragmentManager(), getResources().getStringArray(R.array.tabsPrincipalEmpregador)));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.White));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =  findViewById(R.id.cadastrarVaga);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreCadastroVaga = new Intent(ActEmpregadorPrincipal.this, CadastrarVaga.class);
                startActivity(abreCadastroVaga);
                finish();
            }
        });

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headView  = navigationView.getHeaderView(0);
        TextView nome = headView.findViewById(R.id.nomeEmpregadorDrawer);
        TextView email = headView.findViewById(R.id.emailEmpregadorDrawer);
        CircleImageView imagem = headView.findViewById(R.id.imagemEmpregadorDrawer);
        nome.setText(SessaoEmpregador.instance.getEmpregador().getNome());
        email.setText(SessaoEmpregador.instance.getEmpregador().getEmail());
        byte[] foto = SessaoEmpregador.getInstance().getEmpregador().getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
        imagem.setImageBitmap(bitmap);


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
        } else if (id == R.id.nav_gallery) {
            exibirNotificacoes();
        } else if (id == R.id.nav_manage) {
            exibirConfirmacaoSair();
        } else if (id == R.id.nav_send) {
            openContato();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openContato() {
        Intent intent = new Intent(this, ActContato.class);
        startActivity(intent);
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
                SessaoEmpregador.getInstance().resetEmpregador();
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
        Intent intent = new Intent(this, ActCadastroLoginEmpregador.class);
        startActivity(intent);
    }

    private void exibirPerfil() {
        Intent intent = new Intent(getBaseContext(), PerfilEmp.class);
        startActivity(intent);
        finish();
    }

    private void exibirNotificacoes() {
        Intent intent = new Intent(getBaseContext(), ActNotificacoesEmpregador.class);
        startActivity(intent);
    }

}
