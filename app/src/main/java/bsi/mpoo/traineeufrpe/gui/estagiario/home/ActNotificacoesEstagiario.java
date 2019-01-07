package bsi.mpoo.traineeufrpe.gui.estagiario.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.NovaNofificacoes;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNovasNotificacoes;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.NovaNotificacoesServices;

public class ActNotificacoesEstagiario extends AppCompatActivity {
    ListView listaNotificacoesEstagiario;
    NovaNotificacoesServices notificationServices;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_notificacoes_estagiario);
        listaNotificacoesEstagiario = findViewById(R.id.listNotifEstagiario);
        populate();
    }
    private void populate() {
        ArrayList<NovaNofificacoes> lista = new ArrayList<>();
        notificationServices = new NovaNotificacoesServices(this);
        lista = notificationServices.exibirNotificacoes4Empregador(SessaoEmpregador.getInstance().getEmpregador());
        final AdapterNovasNotificacoes adapter = new AdapterNovasNotificacoes(this, lista);
        listaNotificacoesEstagiario.setAdapter(adapter);
    }
}
