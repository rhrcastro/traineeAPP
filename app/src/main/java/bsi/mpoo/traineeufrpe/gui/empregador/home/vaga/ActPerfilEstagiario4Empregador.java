package bsi.mpoo.traineeufrpe.gui.empregador.home.vaga;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.Notificacao;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.vaga.ControladorVaga;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.negocio.InscricaoServices;
import bsi.mpoo.traineeufrpe.negocio.NotificacaoServices;

public class ActPerfilEstagiario4Empregador extends AppCompatActivity {

    TextView curso, instituicao, area, email, cidade;
    CardView cardView;
    LinearLayout lblSim;
    LinearLayout lblNao;
    TextView txtStatus;
    ImageView imagem;
    private InscricaoServices inscricaoServices = new InscricaoServices(this);
    Toolbar toolbar;
    boolean selecionado = false;
    private String strCurso, strInstituicao, strArea, strEmail, strCidade;
    private String strNome;
    public static ControladorVaga controladorVaga;
    private Pessoa pessoa;
    private Vaga vaga;
    NotificacaoServices notificationServices = new NotificacaoServices(this);

    public ActPerfilEstagiario4Empregador(){
        pessoa = controladorVaga.getPessoa();
        strCurso = pessoa.getEstagiario().getCurriculo().getCurso();
        strInstituicao = pessoa.getEstagiario().getCurriculo().getInstituicao();
        strArea = pessoa.getEstagiario().getCurriculo().getAreaAtuacao();
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
        Bitmap bitmap = getImagem();
        curso.setText(strCurso);
        instituicao.setText(strInstituicao);
        area.setText(strArea);
        email.setText(pessoa.getEstagiario().getEmail());
        cidade.setText(pessoa.getCidade());
        imagem.setContentDescription(pessoa.getNome());
        imagem.setImageBitmap(bitmap);
        toolbar.setTitle(pessoa.getNome());
        setSupportActionBar(toolbar);
        cardView = findViewById(R.id.cardViewEmpregador);
        cardView.setVisibility(View.VISIBLE);
        lblSim = findViewById(R.id.selectSim);
        lblNao = findViewById(R.id.selectNao);
        txtStatus = findViewById(R.id.txtStatus);
        lblSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selecionado)
                    inscricaoServices = new InscricaoServices(ActPerfilEstagiario4Empregador.this);
                    controladorVaga.setStatus("selecionado");
                    inscricaoServices.setStatusInscricaoByEstagiarioAndVaga(controladorVaga);
                    lblNao.setBackgroundColor(Color.parseColor("#999999"));
                    txtStatus.setText("Candidato selecionado.");
                    notificacaoSelecionadoEstagiario(controladorVaga);
                    txtStatus.setVisibility(View.VISIBLE);
                    selecionado = true;
            }
        });
        lblNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selecionado)
                    inscricaoServices = new InscricaoServices(ActPerfilEstagiario4Empregador.this);
                    controladorVaga.setStatus("dispensado");
                    inscricaoServices.setStatusInscricaoByEstagiarioAndVaga(controladorVaga);
                    lblSim.setBackgroundColor(Color.parseColor("#999999"));
                    txtStatus.setText("Candidato dispensado.");
                    txtStatus.setVisibility(View.VISIBLE);
                    notificacaoEstagiarioNaoSelecionado(controladorVaga);
                    selecionado = true;
            }
        });
    }

    private Bitmap getImagem(){
        byte[] fotoEstagiario = pessoa.getEstagiario().getFoto();
        return BitmapFactory.decodeByteArray(fotoEstagiario, 0, fotoEstagiario.length);
    }
    private void notificacaoSelecionadoEstagiario(ControladorVaga inscricao){
        Notificacao novaNotificacao = new Notificacao();
        novaNotificacao.setEmpregadorEnvia(inscricao.getEmpregador());
        novaNotificacao.setPessoaRecebe(inscricao.getPessoa());
        String nomeVaga = inscricao.getVaga().getNome();
        novaNotificacao.setMensagem("Você foi selecionado para a vaga " + nomeVaga + ".");
        novaNotificacao.setVagaRelacionada(inscricao.getVaga());

        notificationServices.enviar4Estagiario(novaNotificacao);
    }
    private void notificacaoEstagiarioNaoSelecionado(ControladorVaga inscricao) {
        Notificacao novaNotificacao = new Notificacao();
        novaNotificacao.setEmpregadorEnvia(inscricao.getEmpregador());
        novaNotificacao.setPessoaRecebe(inscricao.getPessoa());
        String nomeVaga = inscricao.getVaga().getNome();
        novaNotificacao.setMensagem("Você não foi selecionado para a vaga " + nomeVaga + ".");
        novaNotificacao.setVagaRelacionada(inscricao.getVaga());
        notificationServices.enviar4Estagiario(novaNotificacao);
    }
}
