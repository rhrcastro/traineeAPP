package bsi.mpoo.traineeufrpe.gui.estagiario.perfil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.ActEstagiarioPrincipal;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class ActExibirPerfilEstagiario extends AppCompatActivity {

    private TextView curso, instituicao, area, email, cidade;
    private ImageView imagem;
    private LoginServices loginServices = new LoginServices(this);
    Toolbar toolbar;

    private String strCurso, strInstituicao, strArea, strEmail, strCidade;
    FloatingActionButton fab_edit;
    private String strNome;

    public ActExibirPerfilEstagiario(){
        Curriculo curriculo = getCurriculo();
        strCurso = curriculo.getCurso();
        strInstituicao = curriculo.getInstituicao();
        strArea = curriculo.getAreaAtuacao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_estagiario);
        curso =  findViewById(R.id.campo_curso);
        instituicao =  findViewById(R.id.campo_instituicao);
        area =  findViewById(R.id.campo_area);
        email = findViewById(R.id.campo_email);
        cidade =  findViewById(R.id.campo_local);
        imagem =  findViewById(R.id.campo_imagem);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab_edit =  findViewById(R.id.fab_edit);
        fab_edit.show();
        Bitmap bitmap = getImagem();
        Curriculo curriculo = getCurriculo();
        curso.setText(strCurso);
        instituicao.setText(strInstituicao);
        area.setText(strArea);
        email.setText(SessaoEstagiario.getInstance().getPessoa().getEstagiario().getEmail());
        cidade.setText(SessaoEstagiario.getInstance().getPessoa().getCidade());
        imagem.setContentDescription(SessaoEstagiario.getInstance().getPessoa().getNome());
        imagem.setImageBitmap(bitmap);
        toolbar.setTitle(SessaoEstagiario.getInstance().getPessoa().getNome());
        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditarPerfilEstagiario.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Bitmap getImagem(){
        byte[] fotoEstagiario = SessaoEstagiario.getInstance().getPessoa().getEstagiario().getFoto();
        return BitmapFactory.decodeByteArray(fotoEstagiario, 0, fotoEstagiario.length);
    }

    private Curriculo getCurriculo(){
        return SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ActEstagiarioPrincipal.class));
        finish();
    }
}
