package bsi.mpoo.traineeufrpe.gui.empregador.home.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bsi.mpoo.traineeufrpe.R;

public class FragmentCandidato extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_candidato, container, false);

        return v;
    }
}
