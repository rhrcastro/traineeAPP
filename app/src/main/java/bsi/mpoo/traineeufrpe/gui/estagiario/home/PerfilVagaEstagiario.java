package bsi.mpoo.traineeufrpe.gui.estagiario.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.vaga.ControladorVaga;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.InscricaoServices;
import bsi.mpoo.traineeufrpe.dominio.Notifications;
import bsi.mpoo.traineeufrpe.negocio.NotificationServices;

public class PerfilVagaEstagiario extends AppCompatActivity {

    public static Vaga vaga;
    private TextView txtNomeVaga;
    private TextView txtCampoEmpresa;
    private TextView txtArea;
    private TextView txtRequisitos;
    private TextView txtObservacoes;
    private TextView txtValorBolsa;
    private ImageView imgEmpresa;
    Button queroCandidatar;

    InscricaoServices inscricaoServices;
    NotificationServices notificationServices;

    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vaga_estagiario2);
        txtNomeVaga = findViewById(R.id.txtTituloVaga);
        txtCampoEmpresa = findViewById(R.id.campo_empresa);
        txtArea = findViewById(R.id.txt_area_vaga);
        txtRequisitos = findViewById(R.id.txtRequisitos);
        txtObservacoes = findViewById(R.id.txtObservacoes);
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
    }

    private void criarInscricao(){
        inscricaoServices = new InscricaoServices(this);
        if (status) {
            Pessoa remetente = SessaoEstagiario.instance.getPessoa();
            inscricaoServices.delInscricao(vaga.getId(), remetente.getId());
            notificationServices = new NotificationServices(this);
            notificationServices.delNotificacao(vaga.getId(), remetente.getId(), this);
            Toast.makeText(getBaseContext(), "Inscrição cancelada com sucesso", Toast.LENGTH_SHORT).show();
            this.status = mudaBotao(status);
        } else {
            ControladorVaga inscricao = new ControladorVaga();
            inscricao.setVaga(vaga);
            inscricao.setEmpregador(vaga.getEmpregador());
            inscricao.setPessoa(SessaoEstagiario.instance.getPessoa());
            inscricao.setStatus("concorrendo");
            inscricaoServices.cadastrarInscricao(inscricao, this);
            enviarNotificacao(criarNotificacao(inscricao));
        }
    }

    private void enviarNotificacao(Notifications notification) {
        notificationServices = new NotificationServices(this);
        notificationServices.enviar(notification);
        this.status = mudaBotao(status);
        Toast.makeText(getBaseContext(), "Inscrição realizada com sucesso", Toast.LENGTH_SHORT).show();
    }

    private Notifications criarNotificacao(ControladorVaga inscricao){
        Notifications notifications = new Notifications();
        Pessoa remetente = new Pessoa();
        Empregador destinatario = new Empregador();
        destinatario = inscricao.getEmpregador();
        remetente = SessaoEstagiario.instance.getPessoa();
        notifications.setIdRemetente(remetente.getEstagiario().getId());
        notifications.setIdVaga(vaga.getId());
        notifications.setIdDestinatario(destinatario.getId());
        notifications.setMensagem(" se inscreveu na sua vaga:  ");
        notifications.setNomeRemetente(remetente.getNome());
        notifications.setNomeDestinatario(destinatario.getNome());
        notifications.setFotoEstagiario(remetente.getEstagiario().getFoto());
        notifications.setIsEmpregador(true);
        return notifications;
    }

    private boolean mudaBotao(boolean jaCadastrado){
        if (!jaCadastrado) {
            queroCandidatar.setText("CANCELAR INSCRIÇÃO");
            queroCandidatar.setBackgroundResource(R.drawable.rounded_button_red);
            return true;
        } else {
            queroCandidatar.setText("QUERO ME CANDIDATAR");
            queroCandidatar.setBackgroundResource(R.drawable.rounded_button_green);
            return false;
        }
    }

    private boolean verificaStatus() {
        InscricaoServices inscricaoServices = new InscricaoServices(this);
        this.status = inscricaoServices.isInscrito(SessaoEstagiario.instance.getPessoa().getId(), vaga.getId());
        if (status){
            mudaBotao(false);
        }
        return status;
    }

}
