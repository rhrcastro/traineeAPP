package bsi.mpoo.traineeufrpe.gui.empregador.gui.CadastroEmpregador.fragment_login_cadastro;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.dominio.Empregador.Empregador;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.HomeEmpregador;
import bsi.mpoo.traineeufrpe.infra.Validacao.Validacao;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices.EmpregadorServices;

public class FragmentCadastroEmpregador extends Fragment {
    Button cadastrarEmpregador;
    private EmpregadorServices empregadorServices = new EmpregadorServices(getContext());
    private Validacao validacao = new Validacao();
    private EditText editNomeCadastroEmpregador;
    private EditText editEmailCadastroEmpregador;
    private EditText editCNPJ;
    private EditText editSenhaCadastroEmpregador;
    private EditText editConfirmaSenhaEmpregador;
    private EditText editCidadeEmpregador;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_cadastro_empregador, container, false);
        context = v.getContext();
        this.editNomeCadastroEmpregador = v.findViewById(R.id.editNomeCadastroEmpregador);
        this.editEmailCadastroEmpregador = v.findViewById(R.id.editEmailCadastroEmpregador);
        this.editCNPJ = v.findViewById(R.id.editCNPJ);
        this.editSenhaCadastroEmpregador = v.findViewById(R.id.editSenhaCadastroEmpregador);
        this.editConfirmaSenhaEmpregador = v.findViewById(R.id.editConfirmaSenhaEmpregador);
        this.editCidadeEmpregador = v.findViewById(R.id.editCidadeEmpregador);

        this.cadastrarEmpregador = v.findViewById(R.id.cadastrarEmpregador);
        this.cadastrarEmpregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastroEmpregador();
            }
        });
        return v;
    }
    private boolean verificarCampos() {
        String nome = editNomeCadastroEmpregador.getText().toString().trim();
        String email = editEmailCadastroEmpregador.getText().toString().trim();
        String senha = editSenhaCadastroEmpregador.getText().toString().trim();
        String repetirSenha = editConfirmaSenhaEmpregador.getText().toString().trim();
        String cnpj = editCNPJ.getText().toString().trim();
        String cidade = editCidadeEmpregador.getText().toString().trim();
        if (validacao.verificarCampoVazio(nome)){
            this.editNomeCadastroEmpregador.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoEmail(email)) {
            this.editEmailCadastroEmpregador.setError("Formato de email inválido");
            return false;
        } else if (validacao.verificarCampoVazio(senha)){
            this.editSenhaCadastroEmpregador.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(repetirSenha)) {
            this.editConfirmaSenhaEmpregador.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(cnpj)){
            this.editCNPJ.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(cidade)){
            this.editCNPJ.setError("Campo vazio");
            return false;
        } else {
            return true;
        }
    }
    public void cadastroEmpregador() {
        if (!this.verificarCampos()) {
            return;
        }
        if (this.empregadorServices.cadastrarEmpregador(this.criarEmpregador(), context)) {
            Toast.makeText(getContext(), "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
            Intent abreTelaEmpregador = new Intent(getActivity(), HomeEmpregador.class);
            startActivity(abreTelaEmpregador);
        } else {
            Toast.makeText(getContext(),"Já existe uma conta com este e-mail",Toast.LENGTH_SHORT).show();
        }
    }
    private Empregador criarEmpregador() {
        String nome = editNomeCadastroEmpregador.getText().toString().trim();
        String email = editEmailCadastroEmpregador.getText().toString().trim();
        String senha = editSenhaCadastroEmpregador.getText().toString().trim();
        String cnpj = editCNPJ.getText().toString().trim();
        String cidade = editCidadeEmpregador.getText().toString().trim();
        Empregador empregador = new Empregador();
        empregador.setNome(nome);
        empregador.setEmail(email);
        empregador.setSenha(senha);
        empregador.setCnpj(cnpj);
        empregador.setCidade(cidade);
        return empregador;
    }
}
