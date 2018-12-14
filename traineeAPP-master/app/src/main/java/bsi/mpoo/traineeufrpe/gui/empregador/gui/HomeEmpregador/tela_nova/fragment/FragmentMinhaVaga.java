package bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_nova.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.dominio.Vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.EditarEmpresa.EditarEmpresa;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.PerfilVagaEmp;
import bsi.mpoo.traineeufrpe.gui.extra.VagasListAdapter;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.VagaServices.VagaServices;

public class FragmentMinhaVaga extends Fragment {
    private ListView listView;
    private final String TAG = "listData";
    private VagaServices vagaServices;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_minha_vaga, container, false);
        listView = (ListView)v.findViewById(R.id.lista);
        populate();
        return v;
    }

    private void populate() {
        vagaServices = new VagaServices(getActivity());
        Log.d(TAG, "Populando");
        ArrayList<String> listVagas = new ArrayList<>();
        ArrayList<Vaga> listObjeto = new ArrayList<>();
        listVagas = vagaServices.getVagas();
        listObjeto = vagaServices.getObjetoVaga(listVagas, getActivity());
        final VagasListAdapter adapter = new VagasListAdapter(getActivity(), R.layout.activity_lista_minha_vaga, listObjeto);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vaga id = ((Vaga) adapterView.getItemAtPosition(i));
                SessaoEmpregador.instance.setVaga(id);
                Intent EditVaga = new Intent(getActivity(), PerfilVagaEmp.class);
                startActivity(EditVaga);
            }
        });
    }
}
