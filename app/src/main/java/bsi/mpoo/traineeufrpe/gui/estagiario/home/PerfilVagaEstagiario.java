package bsi.mpoo.traineeufrpe.gui.estagiario.home;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    TextView nomeVaga, BolsaPerfilEst, AreaPerfilEst, ObsPerfilEst, reqPerfilEst;

    Button queroCandidatar;

    InscricaoServices inscricaoServices;

    NotificationServices notificationServices;

    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vaga_estagiario);
        nomeVaga = findViewById(R.id.NomeVagaPerfilEst);
        BolsaPerfilEst = findViewById(R.id.BolsaPerfilVagaEst);
        AreaPerfilEst = findViewById(R.id.AreaPerfilVagaEst);
        ObsPerfilEst = findViewById(R.id.ObsPerfilVagaEst);
        reqPerfilEst = findViewById(R.id.ReqPerfilVagaEst);
        queroCandidatar = findViewById(R.id.quero_candidatar);
        popular();
        mudaBotao(verificaStatus());
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

        nomeVaga.setText(nome);
        BolsaPerfilEst.setText(bolsa);
        AreaPerfilEst.setText(area);
        ObsPerfilEst.setText(obs);
        reqPerfilEst.setText(req);
    }

    private void criarInscricao(){
        inscricaoServices = new InscricaoServices(this);
        if (status) {
            Pessoa remetente = SessaoEstagiario.instance.getPessoa();
            inscricaoServices.delInscricao(vaga.getId(), remetente.getId());
            notificationServices = new NotificationServices(this);
            notificationServices.delNotificacao(vaga.getId(), remetente.getId(), this);


        } else {
            ControladorVaga inscricao = new ControladorVaga();
            inscricao.setVaga(vaga);
            inscricao.setEmpregador(vaga.getEmpregador());
            inscricao.setEstagiario(SessaoEstagiario.instance.getPessoa().getEstagiario());
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
        notifications.setIsEmpregador(true);
        return notifications;
    }

    private boolean mudaBotao(boolean jaCadastrado){
        if (!jaCadastrado) {
            queroCandidatar.setText("CANCELAR INSCRIÇÃO");
            queroCandidatar.setBackgroundColor(Color.RED);
            return true;
        } else {
            queroCandidatar.setText("QUERO ME CANDIDATAR");
            queroCandidatar.setBackgroundColor(Color.parseColor("#194879"));
            return false;
        }
    }

    private boolean verificaStatus() {
        InscricaoServices inscricaoServices = new InscricaoServices(this);
        this.status = inscricaoServices.isInscrito(SessaoEstagiario.instance.getPessoa().getId(), vaga.getId());
        return status;
    }
}
