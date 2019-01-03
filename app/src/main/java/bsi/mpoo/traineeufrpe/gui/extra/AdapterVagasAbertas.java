package bsi.mpoo.traineeufrpe.gui.extra;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.PerfilVagaEstagiario;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.InscricaoServices;

public class AdapterVagasAbertas extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    ArrayList<Vaga> listaVagas = new ArrayList<>();
    private ArrayList<Vaga> arrayVagas = new ArrayList<>();
    InscricaoServices inscricaoServices;
    Pessoa pessoa;

    public AdapterVagasAbertas(Context context, ArrayList<Vaga> arrayVagas) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arrayVagas.addAll(arrayVagas);
        this.listaVagas.addAll(arrayVagas);
        inscricaoServices = new InscricaoServices(context);
        pessoa = SessaoEstagiario.instance.getPessoa();
    }

    public class ViewHolder{
        TextView mTitulo, mNomeEmpresa, mValorBolsa, mTurno;
        ImageView mImagem;
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
            view = inflater.inflate(R.layout.adapter_vagas_card, null);
            holder.mTitulo = (TextView) view.findViewById(R.id.titulo_vaga);
            holder.mNomeEmpresa = (TextView) view.findViewById(R.id.campo_empresa);
            holder.mValorBolsa = (TextView) view.findViewById(R.id.campo_bolsa);
            holder.mTurno = (TextView) view.findViewById(R.id.campo_turno);
            holder.mImagem = (ImageView) view.findViewById(R.id.status_vaga);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        holder.mTitulo.setText(listaVagas.get(position).getNome());
        holder.mNomeEmpresa.setText(listaVagas.get(position).getEmpregador().getNome());
        holder.mValorBolsa.setText(listaVagas.get(position).getBolsa());
        holder.mTurno.setText(listaVagas.get(position).getHorario());
        String status = inscricaoServices.getStatusInscricaoByEstagiarioAndVaga(pessoa,
                listaVagas.get(position));
        mudarFotoStatus(holder, status);
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

    private void mudarFotoStatus(ViewHolder holder, String status) {
        if (!status.equals("aberta")) {
            switch (status) {
                case "concorrendo":
                    holder.mImagem.setImageResource(R.drawable.concorrendo_v);
                    break;
                case "selecionado":
                    holder.mImagem.setImageResource(R.drawable.selecionado_v);
                    break;
                case "dispensado":
                    holder.mImagem.setImageResource(R.drawable.dispensado_v);
                    break;
            }
        }
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
