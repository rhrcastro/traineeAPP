package bsi.mpoo.traineeufrpe.gui.estagiario.perfil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.VerCurriculo;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.ActEstagiarioPrincipal;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;

public class ActExibirPerfilEstagiario extends AppCompatActivity {
    private TextView curso, instituicao, area, email, cidade, curriculoperfil;
    private ImageView imagem;
    Toolbar toolbar;
    private String strCurso, strInstituicao, strArea;
    CardView cardView1, cardView2, cardView3, cardView4;
    FloatingActionButton fab_edit;

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
        Constroi();
        Bitmap bitmap = getImagem();
        Set(bitmap);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditarPerfilEstagiario.class);
                startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getLink().equals("")){
                    MostrarUrl(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getLink());
                }else{
                    Snackbar snackbar;
                    snackbar = Snackbar.make(v, "Cadastre um link para ser exibido.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            }
        });

        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditarPerfilEstagiario.class);
                startActivity(intent);
                finish();
            }
        });


        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getExperiencia().equals("")){
                    Intent intent = new Intent(getBaseContext(), VerCurriculo.class);
                    startActivity(intent);
                }else{
                    Snackbar snackbar;
                    snackbar = Snackbar.make(v, "Cadastre um curriculo para ser exibido.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            }
        });


    }

    private void Set(Bitmap bitmap) {
        curso.setText(strCurso);
        instituicao.setText(strInstituicao);
        area.setText(strArea);
        email.setText(SessaoEstagiario.getInstance().getPessoa().getEstagiario().getEmail());
        cidade.setText(SessaoEstagiario.getInstance().getPessoa().getCidade());
        imagem.setContentDescription(SessaoEstagiario.getInstance().getPessoa().getNome());
        imagem.setImageBitmap(bitmap);
        toolbar.setTitle(SessaoEstagiario.getInstance().getPessoa().getNome());
        setSupportActionBar(toolbar);
        if (SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getLink() != null){
            curriculoperfil.setText("Clique aqui para exibir seu curriculo(Link).");
        }
    }

    private void Constroi() {
        curriculoperfil = findViewById(R.id.campo_curriculo);
        curso =  findViewById(R.id.campo_curso);
        instituicao =  findViewById(R.id.campo_instituicao);
        area =  findViewById(R.id.campo_area);
        email = findViewById(R.id.campo_email);
        cidade =  findViewById(R.id.campo_local);
        imagem =  findViewById(R.id.campo_imagem);
        toolbar =  findViewById(R.id.toolbar);
        cardView1 = findViewById(R.id.cardViewInfo);
        cardView2 = findViewById(R.id.cardViewLink);
        cardView3 = findViewById(R.id.cardViewCurriculo);
        cardView4 = findViewById(R.id.cardViewEdit);
        fab_edit =  findViewById(R.id.fab_edit);
        fab_edit.show();
    }

    private void MostrarUrl(String url) {
        Intent site = new Intent(Intent.ACTION_VIEW);
        site.setData(Uri.parse(url));
        startActivity(site);
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
