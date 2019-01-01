package bsi.mpoo.traineeufrpe.gui.estagiario.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Locale;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.PerfilVagaEstagiario;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterVagasAbertas;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class FragmentVagasEmAberto extends ListFragment
        implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    Context mContext;
    ListView listaVagas;
    AdapterVagasAbertas adapter;
    ArrayList<Vaga> vagas;
    VagaServices vagaServices = new VagaServices(getContext());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.fragment_vagas_em_aberto, container, false);
        listaVagas = v.findViewById(android.R.id.list);
        populate();
        return v;
    }


    private void populate() {
        vagaServices = new VagaServices(getActivity());
        vagas = vagaServices.getListaVagas(getActivity());
        adapter = new AdapterVagasAbertas(getContext(), vagas);
        listaVagas.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tela_estagiario_principal, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_settings);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("em vagas abertas");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextChange(String text) {
        if (TextUtils.isEmpty(text)) {
            adapter.filtro("");
            listaVagas.clearTextFilter();
        } else {
            adapter.filtro(text);
        }
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

}

