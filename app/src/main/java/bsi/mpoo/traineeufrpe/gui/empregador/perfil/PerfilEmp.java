package bsi.mpoo.traineeufrpe.gui.empregador.perfil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.empregador.home.ActEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;

public class PerfilEmp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String localizacao;
    private long id;
    private TextView edtLocalizacao;
    private EmpregadorServices empregadorServices = new EmpregadorServices(this);
    private ImageView imgEmpresa;
    private final int newImage = 1;
    private static final int PERMISSION_REQUEST = 0;


    public PerfilEmp() {
        this.localizacao = SessaoEmpregador.getInstance().getEmpregador().getCidade();
        this.id = SessaoEmpregador.getInstance().getEmpregador().getId();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_tela_emp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgEmpresa = findViewById(R.id.imageViewPEmp);

        FloatingActionButton fab =  findViewById(R.id.trocarFotoEmp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissaoGravarLerArquivos();
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        TextView nome = findViewById(R.id.NomePerfilEmp);
        TextView email = findViewById(R.id.emailPerfilEmp);
        ImageView imagem = findViewById(R.id.imageViewPEmp);
        nome.setText(SessaoEmpregador.instance.getEmpregador().getNome());
        email.setText(SessaoEmpregador.instance.getEmpregador().getEmail());
        byte[] foto = SessaoEmpregador.getInstance().getEmpregador().getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
        imagem.setImageBitmap(bitmap);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headView  = navigationView.getHeaderView(0);
        TextView nome1 = headView.findViewById(R.id.NomePerfilEmp);
        TextView email1 = headView.findViewById(R.id.emailPerfilEmp);
        ImageView imagem1 = headView.findViewById(R.id.imageViewPEmp);
        nome1.setText(SessaoEmpregador.instance.getEmpregador().getNome());
        email1.setText(SessaoEmpregador.instance.getEmpregador().getEmail());
        byte[] foto1 = SessaoEmpregador.getInstance().getEmpregador().getFoto();
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(foto1, 0, foto.length);
        imagem1.setImageBitmap(bitmap1);
        edtLocalizacao = findViewById(R.id.localizacaoEmpresa);
        edtLocalizacao.setText(this.localizacao);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.perfilemp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            exibirTelaP();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == newImage) {
            try {
                Uri imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                setFotoEmpregador(bitmap);
                editarImagemObjeto();
                Toast.makeText(this, "Imagem trocada com sucesso", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setFotoEmpregador(Bitmap bitmap)
    {
        this.imgEmpresa.setImageBitmap(bitmap);
    }

    private byte[] conveterImageViewToByte() {
        Bitmap bitmap = ((BitmapDrawable) imgEmpresa.getDrawable()).getBitmap();
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, saida);
        return saida.toByteArray();
    }

    public void editarImagemObjeto() {
        byte[] foto = conveterImageViewToByte();
        SessaoEmpregador.getInstance().getEmpregador().setFoto(foto);
        empregadorServices.alterarFotoEmpregador(SessaoEmpregador.getInstance().getEmpregador());
    }

    private void escolherFoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione a foto"), newImage);
    }

    private void permissaoGravarLerArquivos() {
        int permissionCheckRead = ContextCompat.checkSelfPermission(PerfilEmp.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PerfilEmp.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(PerfilEmp.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        } else {
            escolherFoto();
        }
    }

    private void exibirTelaP() {
        Intent intent = new Intent(PerfilEmp.this, ActEmpregadorPrincipal.class);
        startActivity(intent);
    }
}
