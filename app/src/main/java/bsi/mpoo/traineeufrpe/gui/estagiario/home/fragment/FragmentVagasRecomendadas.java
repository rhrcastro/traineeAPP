package bsi.mpoo.traineeufrpe.gui.estagiario.home.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bsi.mpoo.traineeufrpe.R;

public class FragmentVagasRecomendadas extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        return Inflater.inflate(R.layout.fragment_vagas_recomendadas, container, false);

    }
}
