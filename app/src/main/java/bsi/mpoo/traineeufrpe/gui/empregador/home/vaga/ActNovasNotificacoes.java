package bsi.mpoo.traineeufrpe.gui.empregador.home.vaga;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.Notifications;
import bsi.mpoo.traineeufrpe.dominio.NovaNofificacoes;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNotificacoes;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNovasNotificacoes;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.NotificationServices;
import bsi.mpoo.traineeufrpe.negocio.NovaNotificacoesServices;

public class ActNovasNotificacoes extends AppCompatActivity {

    ListView listaNotificacoes;
    NovaNotificacoesServices notificationServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_notificacoes_emp);
        listaNotificacoes = findViewById(R.id.listNotifEmpregador);
        populate();
    }

    private void populate() {
        ArrayList<NovaNofificacoes> lista = new ArrayList<>();
        notificationServices = new NovaNotificacoesServices(this);
        lista = notificationServices.exibirNotificacoes4Empregador(SessaoEmpregador.getInstance().getEmpregador());
        final AdapterNovasNotificacoes adapter = new AdapterNovasNotificacoes(this, lista);
        listaNotificacoes.setAdapter(adapter);
    }

}
