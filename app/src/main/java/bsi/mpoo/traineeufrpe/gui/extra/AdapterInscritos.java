package bsi.mpoo.traineeufrpe.gui.extra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.ControladorVaga;

public class AdapterInscritos extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    ArrayList<ControladorVaga> listaInscritos = new ArrayList<>();
    private ArrayList<ControladorVaga> arrayVagas = new ArrayList<>();

    public AdapterInscritos(Context context, ArrayList<ControladorVaga> arrayControladorVagas) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arrayVagas.addAll(arrayControladorVagas);
        this.listaInscritos.addAll(arrayControladorVagas);
    }

    public class ViewHolder{
        TextView mVaga, mNome, mCurso, mInstituicao, mCidade;
        ImageView mImg;
    }

    @Override
    public int getCount() {
        return listaInscritos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaInscritos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        AdapterInscritos.ViewHolder holder;
        if (view == null) {
            holder = new AdapterInscritos.ViewHolder();
            view = inflater.inflate(R.layout.adapter_inscritos, null);
            holder.mNome = view.findViewById(R.id.campo_nome);
            holder.mCurso = view.findViewById(R.id.campo_curso);
            holder.mInstituicao = view.findViewById(R.id.campo_instituicao);
            holder.mCidade = view.findViewById(R.id.campo_cidade);
            holder.mImg = view.findViewById(R.id.photo_profile);
            holder.mVaga = view.findViewById(R.id.campo_vaga);
            view.setTag(holder);
        } else {
            holder = (AdapterInscritos.ViewHolder)view.getTag();
        }
        holder.mNome.setText(listaInscritos.get(position).getPessoa().getNome());
        holder.mCurso.setText(listaInscritos.get(position).getPessoa().getEstagiario()
                .getCurriculo().getCurso());
        holder.mInstituicao.setText(listaInscritos.get(position).getPessoa().getEstagiario()
                .getCurriculo().getInstituicao());
        holder.mCidade.setText(listaInscritos.get(position).getPessoa().getCidade());
        holder.mVaga.setText(listaInscritos.get(position).getVaga().getNome());
        byte[] fotoEstagiario = listaInscritos.get(position).getPessoa().getEstagiario().getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoEstagiario, 0, fotoEstagiario.length);
        holder.mImg.setImageBitmap(bitmap);

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pessoa pessoa = ((Pessoa) listaInscritos.get(position));
                Intent it = new Intent(mContext, PerfilVagaEstagiario.class);
                PerfilVagaEstagiario.vaga = vaga;
                mContext.startActivity(it);
            }
        });*/
        return view;
    }

    /*public void filtro(String chartext){
        chartext = chartext.toLowerCase(Locale.getDefault());
        listaInscritos.clear();
        if (chartext.length() == 0){
            listaInscritos.addAll(arrayVagas);
        } else {
            for (Vaga vaga : arrayVagas){
                if (vaga.getNome().toLowerCase(Locale.getDefault()).contains(chartext)){
                    listaInscritos.add(vaga);
                } else if (vaga.getEmpregador().getNome().toLowerCase(Locale.getDefault()).contains(chartext)){
                    listaInscritos.add(vaga);
                }
            }
        } notifyDataSetChanged();
    }*/
}

