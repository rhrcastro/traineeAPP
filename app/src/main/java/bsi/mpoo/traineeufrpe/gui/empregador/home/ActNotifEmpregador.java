package bsi.mpoo.traineeufrpe.gui.empregador.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNotificacoes;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.dominio.Notifications;
import bsi.mpoo.traineeufrpe.negocio.NotificationServices;

public class ActNotifEmpregador extends AppCompatActivity {

    ListView listaNotificacoes;
    NotificationServices notificationServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_notif_empregador);
        listaNotificacoes = findViewById(R.id.listNotifEmpregador);
        populate();
    }

    private void populate() {
        ArrayList<Notifications> lista = new ArrayList<>();
        notificationServices = new NotificationServices(this);
        lista = notificationServices.listaNotificacoes(SessaoEmpregador.getInstance().getEmpregador(), this);
        final AdapterNotificacoes adapter = new AdapterNotificacoes(this, lista);
        listaNotificacoes.setAdapter(adapter);
        //TODO onClick();
    }

    /*private void onClick() {
        listaNotificacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notifications notif = ((Notifications) parent.getItemAtPosition(position));
                Intent it = new Intent(getActivity(), PerfilVagaEstagiario.class);
                PerfilVagaEstagiario.vaga = vaga;
                startActivity(it);
            }
        });
    }*/
}
