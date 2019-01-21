package bsi.mpoo.traineeufrpe.infra.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class PopularBanco {

    EmpregadorServices empregadorServices;
    VagaServices vagaServices;
    Context mContext;


    public PopularBanco(Context context){
        empregadorServices = new EmpregadorServices(context);
        vagaServices = new VagaServices(context);
        mContext = context;

        if (!bancoIsPopulado()){
            popularEmpregadores();
            popularVagas();
        }

    }

    private void popularEmpregadores() {
        criarEmpregador("Alphabet", "aaa@teste.com", "122333455", "123", "Recife");
        criarEmpregador("Banco Pernambucano", "bbb@teste.com", "122343455", "123", "Olinda");
        criarEmpregador("Carrefive", "ccc@teste.com", "122358455", "123", "Paulista");
        criarEmpregador("Diamonds", "ddd@teste.com", "902333455", "123", "Abreu e Lima");
        criarEmpregador("Estelitta", "eee@teste.com", "122333421", "123", "Jaboatão dos Guararapes");
    }

    private void popularVagas() {
        criarVaga("Analista de Sotware",
                "Teste",
                "R$ 1200",
                "Tecnologia",
                "Testando",
                1,
                "08:00 às 12:00");

        criarVaga("Cientista de Dados",
                "Teste",
                "R$ 1800",
                "Tecnologia",
                "Testando",
                1,
                "13:00 às 18:00");

        criarVaga("Contador",
                "Teste",
                "R$ 1350",
                "Negócios",
                "Testando",
                2,
                "13:00 às 19:00");

        criarVaga("Designer Gráfico",
                "Teste",
                "R$ 800",
                "Artes e Design",
                "Testando",
                3,
                "08:00 às 12:00");

        criarVaga("Auxiliar Administrativo",
                "Teste",
                "R$ 650",
                "Negócios",
                "Testando",
                5,
                "Não especificado");

        criarVaga("Auxiliar em Vendas",
                "Teste",
                "R$ 900",
                "Comunicação",
                "Testando",
                4,
                "13:00 às 18:00");

        criarVaga("Recepcionista",
                "Teste",
                "R$ 1000",
                "Comunicação",
                "Testando",
                4,
                "13:00 às 18:00");
    }

    private boolean bancoIsPopulado() {
        String query = "SELECT * FROM empregador";
        Cursor cursor = this.execQuery(query);
        boolean populado = false;
        if (cursor.moveToFirst()) {
            populado = true;
        }
        return populado;
    }
    private Cursor execQuery(String query) {
        Database bancoDados;
        bancoDados = new Database(mContext);
        SQLiteDatabase leitorBanco = bancoDados.getReadableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, null);
        return cursor;
    }


    public void criarEmpregador(String nome, String email, String cnpj, String senha, String cidade){
        Empregador empregador = new Empregador();
        empregador.setNome(nome);
        empregador.setEmail(email);
        empregador.setCnpj(cnpj);
        empregador.setSenha(Base64.encodeToString(senha.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)",""));
        empregador.setCidade(cidade);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.profile_empregador);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob);
        byte[] bitmapdata = blob.toByteArray();
        empregador.setFoto(bitmapdata);
        empregadorServices.cadastrarEmpregador(empregador);
    }

    public void criarVaga(String nome, String requisito, String bolsa, String area, String obs,
                          long idEmpregador, String horario){
        Vaga vaga = new Vaga();
        vaga.setNome(nome);
        vaga.setRequisito(requisito);
        vaga.setBolsa(bolsa);
        vaga.setArea(area);
        vaga.setObs(obs);
        vaga.setDataCriacao(System.currentTimeMillis());
        vaga.setHorario(horario);
        vaga.setEmpregador(empregadorServices.getEmpregadorById(idEmpregador));
        vagaServices.cadastrarVaga(vaga);
    }

}
