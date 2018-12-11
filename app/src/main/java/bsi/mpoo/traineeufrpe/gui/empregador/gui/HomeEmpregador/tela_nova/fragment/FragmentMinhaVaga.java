package bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_nova.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.gui.empregador.gui.EditarEmpresa.EditarEmpresa;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices.EmpregadorServices;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices.VagaServices;

public class FragmentMinhaVaga extends Fragment {
    private ListView listView;
    private final String TAG = "listData";
    private VagaServices vagaServices;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_minha_vaga, container, false);
        listView = (ListView)v.findViewById(R.id.listViewmenu);
        vagaServices = new VagaServices(getActivity());
        populate();
        return v;
    }

    private void populate() {
        Log.d(TAG, "Populando");
        ArrayList<String> listD = new ArrayList<>();
        listD = vagaServices.getListaNomeVagas();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listD);
        listView.setAdapter(adapter);

    }
}
