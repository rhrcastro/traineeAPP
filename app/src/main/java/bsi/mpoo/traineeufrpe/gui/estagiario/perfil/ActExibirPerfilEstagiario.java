package bsi.mpoo.traineeufrpe.gui.estagiario.perfil;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.gui.estagiario.curriculo.CadastrarCurriculo2;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.ActEstagiarioPrincipal;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.PdfViewer;

public class ActExibirPerfilEstagiario extends AppCompatActivity {
    private TextView curso, instituicao, area, email, cidade, curriculoperfil;
    private ImageView imagem;
    private Toolbar toolbar;
    private final String strCurso;
    private final String strInstituicao;
    private final String strArea;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    private FloatingActionButton fab_edit;
    private PdfViewer pdfViewer;

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
                final String[] opcoes = {"Mudar Link", "Mudar Curriculo"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ActExibirPerfilEstagiario.this);
                builder.setTitle("Editar");
                builder.setItems(opcoes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("Mudar Link".equals(opcoes[which])){
                            Intent intent = new Intent(ActExibirPerfilEstagiario.this, EditarPerfilEstagiario.class);
                            startActivity(intent);
                        }
                        else if ("Mudar Curriculo".equals(opcoes[which])){
                            Intent intent = new Intent(ActExibirPerfilEstagiario.this, CadastrarCurriculo2.class);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
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
                    PreparaCurriculo();
                    pdfViewer.ViewPdf();
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

    private void PreparaCurriculo() {
        pdfViewer = new PdfViewer(this);
        pdfViewer.open_document();
        pdfViewer.addMetaData("Nome", "Vaga", "Eu");
        pdfViewer.addTitle(SessaoEstagiario.instance.getPessoa().getNome(),
                SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getInstituicao()
                        + " Sede em " + SessaoEstagiario.instance.getPessoa().getCidade(),
                "Estágio na área de " + SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getAreaAtuacao());
        pdfViewer.addText("_________________________________________________________");
        pdfViewer.addParagraph("Sumário");
        pdfViewer.addText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getExperiencia());
        pdfViewer.addText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getRelacionamento());
        pdfViewer.addText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getObjetivo());
        pdfViewer.addText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getConhcimentos_basicos());
        pdfViewer.addParagraph("Experiência");
        pdfViewer.addText("_________________________________________________________");
        pdfViewer.addText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getCurso());
        pdfViewer.addText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getConhecimentos_especificos());
        pdfViewer.addText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getDisciplinas());

        pdfViewer.close_doc();
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
