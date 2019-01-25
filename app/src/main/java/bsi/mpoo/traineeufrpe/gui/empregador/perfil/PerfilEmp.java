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

import com.github.barteksc.pdfviewer.PDFView;

import java.io.ByteArrayOutputStream;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.empregador.edit.ActEditarEmpregador;
import bsi.mpoo.traineeufrpe.gui.empregador.home.ActEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.gui.estagiario.perfil.EditarPerfilEstagiario;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class PerfilEmp extends AppCompatActivity {

    TextView Nome, email, cidade;
    ImageView imagem;
    Toolbar toolbar;
    private String strNome, strEmail, strCidade;
    FloatingActionButton fab_edit;

    public PerfilEmp() {
        this.strNome = SessaoEmpregador.instance.getEmpregador().getNome();
        this.strEmail = SessaoEmpregador.instance.getEmpregador().getEmail();
        this.strCidade = SessaoEmpregador.instance.getEmpregador().getCidade();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_tela_emp);
        Nome = findViewById(R.id.campo_curso);
        email = findViewById(R.id.campo_email);
        cidade = findViewById(R.id.campo_local);
        imagem =  findViewById(R.id.campo_imagemEmpregador);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab_edit = findViewById(R.id.fab_edit);
        Bitmap bitmap = getImagem();
        Nome.setText(strNome);
        email.setText(strEmail);
        cidade.setText(strCidade);
        imagem.setContentDescription(strNome);
        imagem.setImageBitmap(bitmap);
        toolbar.setTitle(strNome);
        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ActEditarEmpregador.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Bitmap getImagem(){
        byte[] fotoEmp = SessaoEmpregador.instance.getEmpregador().getFoto();
        return BitmapFactory.decodeByteArray(fotoEmp, 0, fotoEmp.length);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ActEmpregadorPrincipal.class));
        finish();
    }
}

