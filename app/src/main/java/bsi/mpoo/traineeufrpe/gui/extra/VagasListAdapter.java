package bsi.mpoo.traineeufrpe.gui.extra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;

public class VagasListAdapter extends ArrayAdapter<Vaga> {
    private static final String TAG = "Vaga adapter";
    private Context Mcontext;
    private int Mresource;

    public VagasListAdapter(Context context, int resource, ArrayList<Vaga> objects) {
        super(context, resource, objects);
        Mcontext = context;
        Mresource = resource;
    }

    @NonNull
    @Override
    public View getView (int position, View view, ViewGroup parent) {
        String nome = getItem(position).getNome();
        String bolsa = getItem(position).getBolsa();
        long id = getItem(position).getId();

        Vaga vaga = new Vaga();
        vaga.setNome(nome);
        vaga.setBolsa(bolsa);
        vaga.setId(id);

        LayoutInflater inflater = LayoutInflater.from(Mcontext);
        view = inflater.inflate(Mresource, parent, false);

        TextView ShowName = (TextView)view.findViewById(R.id.NomeListaVaga);
        TextView ShowBolsa = (TextView)view.findViewById(R.id.BolsaVagaLista);
        TextView ShowId = (TextView)view.findViewById(R.id.IdVagaLista);

        ShowName.setText(nome);
        ShowBolsa.setText(bolsa);
        ShowId.setText(0 + "");
        return view;
    }
}