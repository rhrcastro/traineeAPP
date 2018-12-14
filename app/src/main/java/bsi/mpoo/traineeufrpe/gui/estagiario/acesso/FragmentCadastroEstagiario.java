package bsi.mpoo.traineeufrpe.gui.estagiario.acesso;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.gui.estagiario.curriculo.ActCurriculo;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.infra.Validacao.Validacao;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class FragmentCadastroEstagiario extends Fragment implements AdapterView.OnItemSelectedListener{
    EditText edtCurso;
    EditText edtInstituicao;
    Spinner edtSegmento;
    private Validacao validacao = new Validacao();
    Button cadastrar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_cadastro_estagiario, container, false);
        this.edtCurso = v.findViewById(R.id.cursoCadastro);
        this.edtInstituicao = v.findViewById(R.id.instituicaoCadastro);
        edtSegmento = (Spinner)v.findViewById(R.id.Segmento);
        edtSegmento.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.fields, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtSegmento.setAdapter(adapter);
        cadastrar = (Button)v.findViewById(R.id.botCadastroCurriculo);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarCurriculo();
            }
        });

        return v;
    }
    public boolean verificarCampos() {
        String curso = edtCurso.getText().toString().trim();
        String instituicao = edtInstituicao.getText().toString().trim();
        if (validacao.verificarCampoVazio(curso)) {
            this.edtCurso.setError("O campo curso não pode ficar vazio");
            return false;
        }
        if (validacao.verificarCampoVazio(instituicao)) {
            this.edtInstituicao.setError("O campo instituição não pode ficar vazio");
            return false;
        } else {
            return true;
        }

    }
    public void cadastrarCurriculo() {
        if (!this.verificarCampos()) {
            return;
        }
        Curriculo curriculo = new Curriculo();
        LoginServices loginServices = new LoginServices(getContext());
        curriculo = loginServices.cadastrar(criarCurriculo(), getContext());
        if (curriculo instanceof Curriculo) {
            SessaoEstagiario.instance.setCurriculo(curriculo);
            Toast.makeText(getContext(), "Curriculo cadastrado.", Toast.LENGTH_SHORT).show();
            Intent abreTelaCadastroEstagiario = new Intent(getActivity(), ActCurriculo.class);
            startActivity(abreTelaCadastroEstagiario);
            finishActivity();
        }else{
            Toast.makeText(getContext(), "Curriculo não cadastrado.", Toast.LENGTH_SHORT).show();
        }
    }
    private Curriculo criarCurriculo(){
        String curso = edtCurso.getText().toString().trim();
        String instituicao = edtInstituicao.getText().toString().trim();
        String areaAtuacao = edtSegmento.toString().trim();
        Curriculo curriculo = new Curriculo();
        curriculo.setCurso(curso);
        curriculo.setAreaAtuacao(areaAtuacao);
        curriculo.setInstituicao(instituicao);
        return curriculo;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }
}