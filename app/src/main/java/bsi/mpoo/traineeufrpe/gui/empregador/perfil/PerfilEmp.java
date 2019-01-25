package bsi.mpoo.traineeufrpe.gui.empregador.perfil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.empregador.edit.ActEditarEmpregador;
import bsi.mpoo.traineeufrpe.gui.empregador.home.ActEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;

public class PerfilEmp extends AppCompatActivity {

    private TextView Nome;
    private TextView email;
    private TextView cidade;
    private ImageView imagem;
    private Toolbar toolbar;
    private final String strNome;
    private final String strEmail;
    private final String strCidade;
    private FloatingActionButton fab_edit;

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

