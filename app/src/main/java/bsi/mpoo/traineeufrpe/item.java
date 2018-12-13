package bsi.mpoo.traineeufrpe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thal3.trainee.R;

import java.util.ArrayList;

public class item extends BaseAdapter {
    private Context context;
    private ArrayList<String> lista;

    public item (Context context, ArrayList<String> lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String nome = lista.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.item, null);

        TextView name = (TextView)layout.findViewById(R.id.nome);
        name.setText(nome);

        return layout;
    }
}
