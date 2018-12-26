package bsi.mpoo.traineeufrpe.gui.extra;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;

public class AdapterCardsVagas extends BaseAdapter {

    private final ArrayList<Vaga> listaVagas;
    private Activity activity;

    public AdapterCardsVagas(Activity activity, ArrayList<Vaga> listaVagas) {
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
                    .inflate(R.layout.adapter_vagas_card, parent, false);
        }
        Vaga vaga = listaVagas.get(position);

        TextView titulo = (TextView) convertView.findViewById(R.id.titulo_vaga);
        TextView nomeEmpresa = (TextView) convertView.findViewById(R.id.campo_empresa);
        TextView valorBolsa = (TextView) convertView.findViewById(R.id.campo_bolsa);
        TextView campoHorario = (TextView) convertView.findViewById(R.id.campo_horario);
        ImageView status = (ImageView) convertView.findViewById(R.id.status_vaga);

        titulo.setText(vaga.getNome());
        nomeEmpresa.setText(vaga.getEmpregador().getNome());
        valorBolsa.setText(vaga.getBolsa());
        return convertView;
    }
}
