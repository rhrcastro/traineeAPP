package bsi.mpoo.traineeufrpe.gui.estagiario.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.Notificacao;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.vaga.ControladorVaga;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.InscricaoServices;
import bsi.mpoo.traineeufrpe.negocio.NotificacaoServices;

public class PerfilVagaEstagiario extends AppCompatActivity {

    public static Vaga vaga;
    private TextView txtNomeVaga;
    private TextView txtCampoEmpresa;
    private TextView txtArea;
    private TextView txtRequisitos;
    private TextView txtObservacoes;
    private TextView txtValorBolsa;
    private TextView txtCampoTurno;
    private ImageView imgEmpresa;
    Button queroCandidatar;

    InscricaoServices inscricaoServices;
    NotificacaoServices notificationServices;

    boolean isInscrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vaga_estagiario2);
        txtNomeVaga = findViewById(R.id.txtTituloVaga);
        txtCampoEmpresa = findViewById(R.id.campo_empresa);
        txtArea = findViewById(R.id.txt_area_vaga);
        txtRequisitos = findViewById(R.id.txtRequisitos);
        txtObservacoes = findViewById(R.id.txtObservacoes);
        txtCampoTurno = findViewById(R.id.campo_turno);
        txtValorBolsa = findViewById(R.id.txtValorBolsa);
        imgEmpresa = findViewById(R.id.imgEmpresa);
        queroCandidatar = findViewById(R.id.btnQueroCandidatar);
        popular();
        verificaStatus();
        queroCandidatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarInscricao();
            }
        });

    }

    private void popular(){
        String nome = vaga.getNome();
        String bolsa = vaga.getBolsa();
        String area = vaga.getArea();
        String obs = vaga.getObs();
        String req = vaga.getRequisito();
        String horario = vaga.getHorario();
        String nome_empregador = vaga.getEmpregador().getNome();
        byte[] imgBits = vaga.getEmpregador().getFoto();
        Bitmap imagem = BitmapFactory.decodeByteArray(imgBits, 0, imgBits.length);

        txtNomeVaga.setText(nome);
        txtCampoEmpresa.setText(nome_empregador);
        imgEmpresa.setImageBitmap(imagem);
        txtArea.setText(area);
        txtRequisitos.setText(req);
        txtObservacoes.setText(obs);
        txtValorBolsa.setText(bolsa);
        txtCampoTurno.setText(horario);
    }

    private void criarInscricao(){
        inscricaoServices = new InscricaoServices(this);
        notificationServices = new NotificacaoServices(this);
        if (isInscrito) {
            Pessoa remetente = SessaoEstagiario.instance.getPessoa();
            inscricaoServices.delInscricao(vaga.getId(), remetente.getId());
            notificationServices.delNotificacoesVagaParaEmpregador(vaga.getId(), remetente.getId());
            Toast.makeText(getBaseContext(), "Inscrição cancelada com sucesso", Toast.LENGTH_SHORT).show();
            this.isInscrito = mudaBotao(isInscrito);
        } else {
            ControladorVaga inscricao = new ControladorVaga();
            inscricao.setVaga(vaga);
            inscricao.setEmpregador(vaga.getEmpregador());
            inscricao.setPessoa(SessaoEstagiario.instance.getPessoa());
            inscricao.setStatus("concorrendo");
            inscricaoServices.cadastrarInscricao(inscricao, this);
            enviarNotificacao4Empregador(inscricao);
            this.isInscrito = mudaBotao(isInscrito);
        }
    }

    private void enviarNotificacao4Empregador(ControladorVaga inscricao){
        Notificacao novaNotificacao = new Notificacao();
        novaNotificacao.setPessoaEnvia(inscricao.getPessoa());
        novaNotificacao.setEmpregadorRecebe(inscricao.getEmpregador());
        String nomeVaga = inscricao.getVaga().getNome();
        novaNotificacao.setMensagem("está inscrito em sua vaga " + nomeVaga + ".");
        novaNotificacao.setVagaRelacionada(inscricao.getVaga());
        notificationServices.enviar4Empregador(novaNotificacao);
    }


    private boolean mudaBotao(boolean jaCadastrado){
        if (!jaCadastrado) {
            queroCandidatar.setText(getString(R.string.cancelar_inscricao));
            queroCandidatar.setBackgroundResource(R.drawable.rounded_button_red);
            return true;
        } else {
            queroCandidatar.setText(getString(R.string.candidatar));
            queroCandidatar.setBackgroundResource(R.drawable.rounded_button_green);
            return false;
        }
    }

    private boolean verificaStatus() {
        InscricaoServices inscricaoServices = new InscricaoServices(this);
        this.isInscrito = inscricaoServices.isInscrito(SessaoEstagiario.instance.getPessoa().getId(), vaga.getId());
        if (isInscrito){
            String status = inscricaoServices.getStatusInscricaoByEstagiarioAndVaga(SessaoEstagiario.instance.getPessoa(), vaga);            mudaBotao(false);
            if (status.equals("selecionado")){
                queroCandidatar.setVisibility(View.INVISIBLE);
            } else if (status.equals("dispensado")){
                queroCandidatar.setVisibility(View.INVISIBLE);
            }
        }
        return isInscrito;
    }

}
