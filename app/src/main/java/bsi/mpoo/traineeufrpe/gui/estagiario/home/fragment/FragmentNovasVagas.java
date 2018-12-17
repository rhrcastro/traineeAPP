package bsi.mpoo.traineeufrpe.gui.estagiario.home.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNovasVagas;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class FragmentNovasVagas extends Fragment {

    VagaServices vagaServices = new VagaServices(getContext());
    ListView listaVagas;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_nvagas, null);
        //listaVagas = v.findViewById(R.id.listview_novasvagas);
        //populate();
        button = v.findViewById(R.id.teste);
        return v;
    }

    private void populate() {
        ArrayList<Vaga> vagas = new ArrayList<Vaga>();
        vagaServices = new VagaServices(getActivity());
        //vagas = vagaServices.getListaVagas();
        final AdapterNovasVagas adapter = new AdapterNovasVagas(getActivity(), vagas);
        listaVagas.setAdapter(adapter);
    }
}
