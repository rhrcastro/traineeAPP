package bsi.mpoo.traineeufrpe.gui.estagiario.home.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.PerfilVagaEstagiario;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNovasVagas;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class FragmentNovasVagas extends Fragment {

    VagaServices vagaServices = new VagaServices(getContext());
    EmpregadorServices empregadorServices = new EmpregadorServices(getContext());
    ListView listaVagas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_novas_vagas, container, false);
        listaVagas = v.findViewById(R.id.lista_novas_vagas);
        populate();
        return v;
    }


    private void populate() {
        ArrayList<Vaga> vagas = new ArrayList<Vaga>();
        vagaServices = new VagaServices(getActivity());
        vagas = vagaServices.getListaVagas(getActivity());
        final AdapterNovasVagas adapter = new AdapterNovasVagas(getActivity(), vagas);
        listaVagas.setAdapter(adapter);
        onClick();
    }

    private void onClick() {
        listaVagas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vaga vaga = ((Vaga) parent.getItemAtPosition(position));
                Intent it = new Intent(getActivity(), PerfilVagaEstagiario.class);
                PerfilVagaEstagiario.vaga = vaga;
                startActivity(it);
            }
        });
    }
}
