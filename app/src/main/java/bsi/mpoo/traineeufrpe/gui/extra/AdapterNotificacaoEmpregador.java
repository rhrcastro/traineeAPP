package bsi.mpoo.traineeufrpe.gui.extra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.Notificacao;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.negocio.InscricaoServices;
import bsi.mpoo.traineeufrpe.negocio.NotificacaoServices;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterNotificacaoEmpregador extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    ArrayList<Notificacao> listaNotificacoes = new ArrayList<>();
    private ArrayList<Notificacao> arrayNotificacoes = new ArrayList<>();
    NotificacaoServices notificacaoServices;
    InscricaoServices inscricaoServices;
    Pessoa pessoa;

    public AdapterNotificacaoEmpregador(Context context, ArrayList<Notificacao> arrayNotificacoes) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arrayNotificacoes.addAll(arrayNotificacoes);
        this.listaNotificacoes.addAll(arrayNotificacoes);
        notificacaoServices = new NotificacaoServices(context);
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
        return view;
    }
}
