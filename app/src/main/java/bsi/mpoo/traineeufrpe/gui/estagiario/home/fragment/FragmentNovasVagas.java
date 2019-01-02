package bsi.mpoo.traineeufrpe.gui.estagiario.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
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
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNovasVagas;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class FragmentNovasVagas extends ListFragment
        implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    Context mContext;
    ListView listaVagas;
    AdapterNovasVagas adapter;
    ArrayList<Vaga> vagas;
    VagaServices vagaServices = new VagaServices(getContext());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_novas_vagas, container, false);
        listaVagas = (ListView) layout.findViewById(android.R.id.list);
        populate();
        return layout;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void populate() {
        vagaServices = new VagaServices(getActivity());
        vagas = vagaServices.getVagasPorData(getActivity());
        adapter = new AdapterNovasVagas(getContext(), vagas);
        listaVagas.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tela_estagiario_principal, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_settings);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("em novas vagas");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
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
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }
}
