package bsi.mpoo.traineeufrpe.gui.empregador.home.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.ControladorVaga;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterInscritos;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.InscricaoServices;

public class FragmentCandidato extends ListFragment
        implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private Context mContext;
    private ListView listaVagas;
    private AdapterInscritos adapter;
    private ArrayList<ControladorVaga> inscritos;
    private InscricaoServices inscricaoServices = new InscricaoServices(getContext());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.fragment_candidato, container, false);
        listaVagas = v.findViewById(android.R.id.list);
        populate();
        return v;
    }


    private void populate() {
        inscricaoServices = new InscricaoServices(getActivity());
        inscritos = inscricaoServices.listarInscricoes(SessaoEmpregador.getInstance().getEmpregador(), mContext);
        adapter = new AdapterInscritos(getContext(), inscritos);
        listaVagas.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextChange(String text) {
        /*if (TextUtils.isEmpty(text)) {
            adapter.filtro("");
            listaVagas.clearTextFilter();
        } else {
            adapter.filtro(text);
        }*/
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    public void onStart() {
        super.onStart();
        populate();
    }
}