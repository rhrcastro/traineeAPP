package bsi.mpoo.traineeufrpe.gui.extra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.PerfilVagaEstagiario;

public class AdapterNovasVagas extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    ArrayList<Vaga> listaVagas = new ArrayList<>();
    private ArrayList<Vaga> arrayVagas = new ArrayList<>();

    public AdapterNovasVagas(Context context, ArrayList<Vaga> arrayVagas) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arrayVagas.addAll(arrayVagas);
        this.listaVagas.addAll(arrayVagas);
    }

    public class ViewHolder{
        TextView mTitulo, mNomeEmpresa, mValorBolsa, mDataVaga;
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.lista_vagasnovas, null);
            holder.mTitulo = view.findViewById(R.id.nome_candidato);
            holder.mNomeEmpresa = view.findViewById(R.id.txtNomeEmpresa);
            holder.mValorBolsa = view.findViewById(R.id.bolsa_vaga);
            holder.mDataVaga = view.findViewById(R.id.data_vaga);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        holder.mTitulo.setText(listaVagas.get(position).getNome());
        holder.mNomeEmpresa.setText(listaVagas.get(position).getEmpregador().getNome());
        holder.mValorBolsa.setText(listaVagas.get(position).getBolsa());
        holder.mDataVaga.setText(listaVagas.get(position).getDataCriacao());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vaga vaga = ((Vaga) listaVagas.get(position));
                Intent it = new Intent(mContext, PerfilVagaEstagiario.class);
                PerfilVagaEstagiario.vaga = vaga;
                mContext.startActivity(it);
            }
        });
        return view;
    }

    public void filtro(String chartext){
        chartext = chartext.toLowerCase(Locale.getDefault());
        listaVagas.clear();
        if (chartext.length() == 0){
            listaVagas.addAll(arrayVagas);
        } else {
            for (Vaga vaga : arrayVagas){
                if (vaga.getNome().toLowerCase(Locale.getDefault()).contains(chartext)){
                    listaVagas.add(vaga);
                } else if (vaga.getEmpregador().getNome().toLowerCase(Locale.getDefault()).contains(chartext)){
                    listaVagas.add(vaga);
                }
            }
        } notifyDataSetChanged();
    }
}
