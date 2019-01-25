package bsi.mpoo.traineeufrpe.gui.empregador.home.vaga;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.Notificacao;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNotificacaoEmpregador;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.NotificacaoServices;

public class ActNotificacoesEmpregador extends AppCompatActivity {

    private ListView listaNotificacoes;
    private NotificacaoServices notificationServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_notificacoes_emp);
        listaNotificacoes = findViewById(R.id.listNotifEmpregador);
        populate();
    }

    private void populate() {
        ArrayList<Notificacao> lista = new ArrayList<>();
        notificationServices = new NotificacaoServices(this);
        lista = notificationServices.exibirNotificacoes4Empregador(SessaoEmpregador.getInstance().getEmpregador());
        final AdapterNotificacaoEmpregador adapter = new AdapterNotificacaoEmpregador(this, lista);
        listaNotificacoes.setAdapter(adapter);
    }

}
