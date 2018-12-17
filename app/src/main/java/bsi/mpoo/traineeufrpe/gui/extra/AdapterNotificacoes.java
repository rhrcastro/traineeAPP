package bsi.mpoo.traineeufrpe.gui.extra;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.Notifications;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;

public class AdapterNotificacoes extends BaseAdapter {

    private final ArrayList<Notifications> listaNotifications;
    private Activity activity;

    public AdapterNotificacoes(Activity activity, ArrayList<Notifications> listaNotifications) {
        super();
        this.listaNotifications = listaNotifications;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaNotifications.size();
    }

    @Override
    public Object getItem(int position) {
        return listaNotifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater()
                    .inflate(R.layout.lista_notificacoes, parent, false);
        }
        Notifications notifications = listaNotifications.get(position);

        TextView titulo = (TextView) convertView.findViewById(R.id.nome_candidato);
        TextView mensagem = (TextView) convertView.findViewById(R.id.mensagem);
        TextView nomeVaga = (TextView) convertView.findViewById(R.id.notif_nome_vaga);
        ImageView image = convertView.findViewById(R.id.imagemNotif);
        byte[] fotoEstagiario = notifications.getFotoEstagiario();
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoEstagiario, 0, fotoEstagiario.length);
        image.setImageBitmap(bitmap);

        titulo.setText(notifications.getNomeRemetente());
        mensagem.setText(notifications.getMensagem());
        nomeVaga.setText(notifications.getNomeDestinatario());
        return convertView;
    }
}
