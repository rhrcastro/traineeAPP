package bsi.mpoo.traineeufrpe.gui.estagiario.gui.Home.tela_nova.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thal3.trainee.R;

public class FragmentVagasEmAberto extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        return Inflater.inflate(R.layout.activity_fragment_vagas_em_aberto, container, false);

    }
}
