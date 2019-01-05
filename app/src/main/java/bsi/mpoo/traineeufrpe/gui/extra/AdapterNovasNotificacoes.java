package bsi.mpoo.traineeufrpe.gui.extra;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.NovaNofificacoes;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.vaga.ControladorVaga;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.empregador.home.vaga.ActPerfilEstagiario4Empregador;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.PerfilVagaEstagiario;
import bsi.mpoo.traineeufrpe.negocio.InscricaoServices;
import bsi.mpoo.traineeufrpe.negocio.NovaNotificacoesServices;
import de.hdodenhof.circleimageview.CircleImageView;

import static bsi.mpoo.traineeufrpe.gui.estagiario.home.PerfilVagaEstagiario.vaga;

public class AdapterNovasNotificacoes extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    ArrayList<NovaNofificacoes> listaNotificacoes = new ArrayList<>();
    private ArrayList<NovaNofificacoes> arrayNotificacoes = new ArrayList<>();
    NovaNotificacoesServices novaNotificacoesServices;
    InscricaoServices inscricaoServices;
    Pessoa pessoa;

    public AdapterNovasNotificacoes(Context context, ArrayList<NovaNofificacoes> arrayNotificacoes) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arrayNotificacoes.addAll(arrayNotificacoes);
        this.listaNotificacoes.addAll(arrayNotificacoes);
        novaNotificacoesServices = new NovaNotificacoesServices(context);
        inscricaoServices = new InscricaoServices(context);
    }

    public class ViewHolder{
        TextView mNome, mMensagem;
        CircleImageView mImagem;
    }

    @Override
    public int getCount() {
        return listaNotificacoes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaNotificacoes.get(position);
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
            view = inflater.inflate(R.layout.adapter_notificacao, null);
            holder.mNome = (TextView) view.findViewById(R.id.nomeEstagiario);
            holder.mMensagem = (TextView) view.findViewById(R.id.mensagemEstagiario);
            holder.mImagem = (CircleImageView) view.findViewById(R.id.fotoEstagiario);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        holder.mNome.setText(listaNotificacoes.get(position).getPessoaEnvia().getNome());
        holder.mMensagem.setText(listaNotificacoes.get(position).getMensagem());
        byte[] fotoEstagiario = listaNotificacoes.get(position).getPessoaEnvia().getEstagiario().getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoEstagiario, 0, fotoEstagiario.length);
        holder.mImagem.setImageBitmap(bitmap);
        /*TODO view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaNotificacoes.get(position).getVagaRelacionada() != null){
                    ControladorVaga controladorVaga = inscricaoServices
                            .getInscricaoByPessoaAndVaga(listaNotificacoes.get(position).getPessoaEnvia(),
                                    listaNotificacoes.get(position).getVagaRelacionada());
                    Intent it = new Intent(mContext, ActPerfilEstagiario4Empregador.class);
                    ActPerfilEstagiario4Empregador.controladorVaga = controladorVaga;
                    mContext.startActivity(it);
                }
            }
        });*/
        return view;

    }

    /*public void filtro(String chartext){
        chartext = chartext.toLowerCase(Locale.getDefault());
        listaNotificacoes.clear();
        if (chartext.length() == 0){
            listaNotificacoes.addAll(arrayNoficacoes);
        } else {
            for (Vaga vaga : arrayNoficacoes){
                if (vaga.getNome().toLowerCase(Locale.getDefault()).contains(chartext)){
                    listaNotificacoes.add(vaga);
                } else if (vaga.getEmpregador().getNome().toLowerCase(Locale.getDefault()).contains(chartext)){
                    listaNotificacoes.add(vaga);
                }
            }
        } notifyDataSetChanged();
    }*/
}
