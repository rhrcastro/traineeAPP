package com.trainee2.GUI.fragment.estagiario.Cadastro_b_Login_a;

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

import com.trainee2.GUI.estagiario.TelaCadastroCurriculo;
import com.trainee2.R;
import com.trainee2.dominio.Curriculo;
import com.trainee2.dominio.Estagiario;
import com.trainee2.dominio.Pessoa;
import com.trainee2.infra.Sessao;
import com.trainee2.infra.Validacao;
import com.trainee2.negocio.LoginServices;

public class Fragment_b extends Fragment implements AdapterView.OnItemSelectedListener{
    Spinner Segmento;
    Button botCadastroCurriculo;
    EditText instituicaoCadastro, cursoCadastro;
    private Sessao sessao = Sessao.getInstance();
    private Validacao validacao = new Validacao();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_b, container, false);

        instituicaoCadastro = (EditText)v.findViewById(R.id.instituicaoCadastro);
        cursoCadastro = (EditText)v.findViewById(R.id.cursoCadastro);

        Segmento = (Spinner)v.findViewById(R.id.Segmento);
        Segmento.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.fields, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Segmento.setAdapter(adapter);

        botCadastroCurriculo = (Button)v.findViewById(R.id.botCadastroCurriculo);
        botCadastroCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarCurriculo();

            }
        });
        return v;
    }
    public boolean verificarCampos() {
        String curso = cursoCadastro.getText().toString().trim();
        String instituicao = instituicaoCadastro.getText().toString().trim();
        String segmentoArea = Segmento.toString().trim();
        if (validacao.verificarCampoVazio(curso)) {
            this.cursoCadastro.setError("O campo curso não pode ficar vazio");
            return false;
        }
        if (validacao.verificarCampoVazio(instituicao)) {
            this.instituicaoCadastro.setError("O campo instituição não pode ficar vazio");
            return false;
        } else {
            return true;
        }

    }


    public void cadastrarCurriculo() {
        Curriculo curriculo;
        Pessoa daSessao = new Pessoa();
        Estagiario estagiario = new Estagiario();
        daSessao.setEstagiario(estagiario);
        if (!this.verificarCampos()) {
            return;
        }
        LoginServices loginServices = new LoginServices(getContext());
        curriculo = loginServices.cadastrar(criarCurriculo(), getContext());
        if (curriculo instanceof Curriculo) {
            estagiario.setCurriculo(curriculo);
            Sessao.instance.setPessoa(daSessao);
            Toast.makeText(getContext(), "Curriculo cadastrado.", Toast.LENGTH_SHORT).show();
            Intent abreTelaCadastroEstagiario = new Intent(getActivity(), TelaCadastroCurriculo.class);
            startActivity(abreTelaCadastroEstagiario);
        }else{
            Toast.makeText(getContext(), "Curriculo não cadastrado.", Toast.LENGTH_SHORT).show();
        }
    }
    private Curriculo criarCurriculo(){
        String curso = cursoCadastro.getText().toString().trim();
        String instituicao = instituicaoCadastro.getText().toString().trim();
        String areaAtuacao = Segmento.toString();
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
}
