package bsi.mpoo.traineeufrpe.gui.estagiario.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.Notificacao;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNotificacaoEstagiario;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.NotificacaoServices;

public class ActNotificacoesEstagiario extends AppCompatActivity {
    ListView listaNotificacoesEstagiario;
    NotificacaoServices notificationServices;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_notificacoes_estagiario);
        listaNotificacoesEstagiario = findViewById(R.id.listNotifEstagiario);
        populate();
    }
    private void populate() {
        ArrayList<Notificacao> lista = new ArrayList<>();
        notificationServices = new NotificacaoServices(this);
        lista = notificationServices.exibirNotificacoes4Estagiario(SessaoEstagiario.getInstance().getPessoa().getEstagiario());
        final AdapterNotificacaoEstagiario adapter = new AdapterNotificacaoEstagiario(this, lista);
        listaNotificacoesEstagiario.setAdapter(adapter);
    }
}
