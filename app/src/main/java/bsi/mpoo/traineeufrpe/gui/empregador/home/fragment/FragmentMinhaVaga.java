package bsi.mpoo.traineeufrpe.gui.empregador.home.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.gui.empregador.home.vaga.PerfilVagaEmpregador;
import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.extra.VagasListAdapter;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class FragmentMinhaVaga extends Fragment {

    private ListView listView;
    private final String TAG = "listData";
    private VagaServices vagaServices = new VagaServices(getContext());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.fragment_minha_vaga, container, false);
        listView = v.findViewById(R.id.lista);
        populate();
        return v;
    }

    private void populate() {
        vagaServices = new VagaServices(getActivity());
        Log.d(TAG, "Populando");
        ArrayList<String> listVagas = new ArrayList<>();
        ArrayList<Vaga> listObjeto = new ArrayList<>();
        listVagas = vagaServices.getVagas();
        listObjeto = vagaServices.getObjetoVaga(listVagas);
        final VagasListAdapter adapter = new VagasListAdapter(getActivity(), R.layout.lista_vaga_empresa, listObjeto);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vaga id = ((Vaga) adapterView.getItemAtPosition(i));
                SessaoEmpregador.instance.setVaga(id);
                Intent EditVaga = new Intent(getActivity(), PerfilVagaEmpregador.class);
                startActivity(EditVaga);
            }
        });
    }
}
