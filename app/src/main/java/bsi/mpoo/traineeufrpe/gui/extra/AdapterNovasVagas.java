package bsi.mpoo.traineeufrpe.gui.extra;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;

public class AdapterNovasVagas extends BaseAdapter {

    private final ArrayList<Vaga> listaVagas;
    private Activity activity;

    public AdapterNovasVagas(Activity activity, ArrayList<Vaga> listaVagas) {
        super();
        this.listaVagas = listaVagas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaVagas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaVagas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater()
                    .inflate(R.layout.lista_vagasnovas, parent, false);
        }
        Vaga vaga = listaVagas.get(position);

        TextView titulo = (TextView) convertView.findViewById(R.id.nome_candidato);
        TextView nomeEmpresa = (TextView) convertView.findViewById(R.id.mensagem);
        TextView valorBolsa = (TextView) convertView.findViewById(R.id.bolsa_vaga);
        TextView dataVaga = (TextView) convertView.findViewById(R.id.data_vaga);

        titulo.setText(vaga.getNome());
        nomeEmpresa.setText(vaga.getEmpregador().getNome());
        valorBolsa.setText(vaga.getBolsa());
        dataVaga.setText(vaga.getDataCriacao());
        return convertView;
    }
}
