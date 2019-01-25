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
import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.negocio.Criptografia;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;
import bsi.mpoo.traineeufrpe.persistencia.CurriculoDAO;
import bsi.mpoo.traineeufrpe.persistencia.EstagiarioDAO;
import bsi.mpoo.traineeufrpe.persistencia.PessoaDAO;
import bsi.mpoo.traineeufrpe.persistencia.VagaDAO;

public class PopularBanco {


    PessoaDAO pessoaDAO;
    EstagiarioDAO estagiarioDAO;
    CurriculoDAO curriculoDAO;
    VagaDAO vagaDAO;
    EmpregadorServices empregadorServices;
    VagaServices vagaServices;
    Context mContext;


    public PopularBanco(Context context) {
        pessoaDAO = new PessoaDAO(context);
        estagiarioDAO = new EstagiarioDAO(context);
        curriculoDAO = new CurriculoDAO(context);
        vagaDAO = new VagaDAO(context);
        empregadorServices = new EmpregadorServices(context);
        vagaServices = new VagaServices(context);

        mContext = context;

        if (!bancoIsPopulado()) {
            popularEmpregadores();
            popularVagas();
        }

        if (!bancoIsPopuladoPessoas()){
            popularEstagiarios();
        }

    }

    private void popularEmpregadores() {
        criarEmpregador("Alphabet", "aaa@teste.com", "122333455", "123", "Recife");
        criarEmpregador("Banco Pernambucano", "bbb@teste.com", "122343455", "123", "Olinda");
        criarEmpregador("Carrefive", "ccc@teste.com", "122358455", "123", "Paulista");
        criarEmpregador("Diamonds", "ddd@teste.com", "902333455", "123", "Abreu e Lima");
        criarEmpregador("Estelitta", "eee@teste.com", "122333421", "123", "Jaboatão dos Guararapes");
        criarEmpregador("Donimed", "fff@teste.com", "122312121", "123", "São Paulo");
        criarEmpregador("Rádio CVM", "ggg@teste.com", "122100421", "123", "Salvador");
        criarEmpregador("Consultoria AGP", "hhh@teste.com", "122333300", "123", "São Vicente");

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
                2,
                "13:00 às 18:00");

        criarVaga("Desenvolvedor Python",
                "Teste",
                "R$ 1350",
                "Tecnologia",
                "Testando",
                1,
                "13:00 às 19:00");

        criarVaga("Desenvolvedor Front-End",
                "Teste",
                "R$ 800",
                "Artes e Design",
                "Testando",
                2,
                "08:00 às 12:00");

        criarVaga("Auxiliar Administrativo",
                "Teste",
                "R$ 650",
                "Negócios",
                "Testando",
                6,
                "Não especificado");

        criarVaga("Auxiliar em RH",
                "Teste",
                "R$ 900",
                "Comunicação",
                "Testando",
                4,
                "13:00 às 18:00");

        criarVaga("Estágio em Logística",
                "Teste",
                "R$ 1000",
                "Comunicação",
                "Testando",
                3,
                "13:00 às 18:00");

        criarVaga("Estágio em Contabilidade",
                "Teste",
                "R$ 1700",
                "Saúde",
                "Testando",
                8,
                "13:00 às 18:00");
    }

    public void popularEstagiarios(){
        cadastrarPessoa("Henrique César", "hcesarjs@gmail.com",
                "122454541121", "123", "Paulista",
                "Sistemas de Informação", "UFRPE", "Tecnologia",
                "Sem experiência", "Estágio na Área de TI", "Bom",
                "Teste", "Android Studio", "Teste",
                "http://www.traineeufrpe.github.io");

        cadastrarPessoa("Thales Gabriel", "thalesg88@gmail.com",
                "1777754541121", "123", "Paulista",
                "Sistemas de Informação", "UFRPE", "Tecnologia",
                "Sem experiência", "Estágio na Área de TI", "Bom",
                "Teste", "Android Studio", "Teste",
                "http://www.traineeufrpe.github.io");

        cadastrarPessoa("Yasmmin Monteiro", "ymclaudino@gmail.com",
                "122454599991", "123", "Olinda",
                "Sistemas de Informação", "UFRPE", "Tecnologia",
                "Sem experiência", "Estágio na Área de TI", "Bom",
                "Teste", "Android Studio", "Teste",
                "http://www.traineeufrpe.github.io");

        cadastrarPessoa("André França", "est5@gmail.com",
                "002454599991", "123", "Olinda",
                "Economia", "UFRPE", "Negocios",
                "Sem experiência", "Estágio em Contabilidade", "Bom",
                "Teste", "Android Studio", "Teste",
                "http://www.traineeufrpe.github.io");

        cadastrarPessoa("Willian Zonner", "est1@gmail.com",
                "122450000091", "123", "São Paulo",
                "Jornalismo", "USP", "Comunicação",
                "Sem experiência", "Estágio em Rádio e Jornais", "Bom",
                "Teste", "Android Studio", "Teste",
                "http://www.traineeufrpe.github.io");

        cadastrarPessoa("Julia Servero", "est2@gmail.com",
                "54575599991", "123", "Contagem",
                "Medicina", "UFMG", "Tecnologia",
                "Sem experiência", "Estágio em clínicas", "Bom",
                "Teste", "Android Studio", "Teste",
                "http://www.traineeufrpe.github.io");

        cadastrarPessoa("Laura Oliveira", "est3@gmail.com",
                "111114599991", "123", "Belo Horizonte",
                "Gastronomia", "Faculdade Promove", "Ciências Humanas",
                "Sem experiência", "Estágio em Bares e/ou Restaurantes", "Bom",
                "Teste", "Android Studio", "Teste",
                "http://www.traineeufrpe.github.io");

        cadastrarPessoa("Évilla Santos", "evilla@gmail.com",
                "122454599991", "123", "São Luís",
                "Psicologia", "CEUMA", "Saúde",
                "Sem experiência", "Estágio em Consultórios", "Bom",
                "Teste", "Android Studio", "Teste",
                "http://www.traineeufrpe.github.io");

        cadastrarPessoa("Vinicius Alves", "vinicius@gmail.com",
                "888854541121", "123", "Recife",
                "Sistemas de Informação", "UFRPE", "Tecnologia",
                "Sem experiência", "Estágio na Área de TI", "Bom",
                "Teste", "Android Studio", "Teste",
                "http://www.traineeufrpe.github.io");

        cadastrarPessoa("Ricardo Henrique", "rhcastro@outlook.com",
                "122451234521", "123", "Recife",
                "Sistemas de Informação", "UFRPE", "Tecnologia",
                "Sem experiência", "Estágio na Área de TI", "Bom",
                "Teste", "Android Studio", "Teste",
                "http://www.traineeufrpe.github.io");


        criarAvaliacao(1,1, (float)5.0);
        criarAvaliacao(1,2, (float)4.0);
        criarAvaliacao(1,3, (float)5.0);
        criarAvaliacao(1,4, (float)5.0);
        criarAvaliacao(1,5, (float)1.0);
        criarAvaliacao(1,6, (float)2.0);
        criarAvaliacao(1,7, (float)1.0);
        criarAvaliacao(1,8, (float)1.0);

        criarAvaliacao(2,1, (float)5.0);
        criarAvaliacao(2,2, (float)5.0);
        criarAvaliacao(2,3, (float)5.0);
        criarAvaliacao(2,4, (float)5.0);
        criarAvaliacao(2,5, (float)2.0);
        criarAvaliacao(2,6, (float)1.0);
        criarAvaliacao(2,7, (float)1.0);
        criarAvaliacao(2,8, (float)2.0);

        criarAvaliacao(3,1, (float)4.0);
        criarAvaliacao(3,2, (float)5.0);
        criarAvaliacao(3,3, (float)4.0);
        criarAvaliacao(3,4, (float)4.0);
        criarAvaliacao(3,5, (float)1.0);
        criarAvaliacao(3,6, (float)2.0);
        criarAvaliacao(3,7, (float)1.0);
        criarAvaliacao(3,8, (float)2.0);

        criarAvaliacao(4,1, (float)3.0);
        criarAvaliacao(4,2, (float)5.0);
        criarAvaliacao(4,3, (float)5.0);
        criarAvaliacao(4,4, (float)4.0);
        criarAvaliacao(4,5, (float)2.0);
        criarAvaliacao(4,6, (float)1.0);
        criarAvaliacao(4,7, (float)2.0);
        criarAvaliacao(4,8, (float)1.0);

        criarAvaliacao(5,1, (float)1.0);
        criarAvaliacao(5,2, (float)2.0);
        criarAvaliacao(5,3, (float)2.0);
        criarAvaliacao(5,4, (float)1.0);
        criarAvaliacao(5,5, (float)3.0);
        criarAvaliacao(5,6, (float)5.0);
        criarAvaliacao(5,7, (float)4.0);
        criarAvaliacao(5,8, (float)5.0);

        criarAvaliacao(6,1, (float)2.0);
        criarAvaliacao(6,2, (float)1.0);
        criarAvaliacao(6,3, (float)1.0);
        criarAvaliacao(6,4, (float)1.0);
        criarAvaliacao(6,5, (float)5.0);
        criarAvaliacao(6,6, (float)4.0);
        criarAvaliacao(6,7, (float)5.0);
        criarAvaliacao(6,8, (float)4.0);

        criarAvaliacao(7,1, (float)1.0);
        criarAvaliacao(7,2, (float)1.0);
        criarAvaliacao(7,3, (float)1.0);
        criarAvaliacao(7,4, (float)2.0);
        criarAvaliacao(7,5, (float)5.0);
        criarAvaliacao(7,6, (float)5.0);
        criarAvaliacao(7,7, (float)5.0);
        criarAvaliacao(7,8, (float)5.0);

        criarAvaliacao(8,1, (float)2.0);
        criarAvaliacao(8,2, (float)1.0);
        criarAvaliacao(8,3, (float)2.0);
        criarAvaliacao(8,4, (float)1.0);
        criarAvaliacao(8,5, (float)4.0);
        criarAvaliacao(8,6, (float)5.0);
        criarAvaliacao(8,7, (float)3.0);
        criarAvaliacao(8,8, (float)5.0);

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

    private boolean bancoIsPopuladoPessoas() {
        String query = "SELECT * FROM pessoa";
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


    private void criarEmpregador(String nome, String email, String cnpj, String senha, String cidade) {
        Empregador empregador = new Empregador();
        empregador.setNome(nome);
        empregador.setEmail(email);
        empregador.setCnpj(cnpj);
        empregador.setSenha(Criptografia.criptografar(senha));
        empregador.setCidade(cidade);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.profile_empregador);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob);
        byte[] bitmapdata = blob.toByteArray();
        empregador.setFoto(bitmapdata);
        empregadorServices.cadastrarEmpregador(empregador);
    }

    private void criarVaga(String nome, String requisito, String bolsa, String area, String obs,
                           long idEmpregador, String horario) {
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

    private void criarPessoa(String nome, String cpf, String cidade, Estagiario estagiario) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setCpf(cpf);
        pessoa.setCidade(cidade);
        pessoa.setEstagiario(estagiario);
        pessoaDAO.inserirPessoa(pessoa);
    }

    private Estagiario criarEstagiario(String email, String senha, Curriculo curriculo) {
        Estagiario estagiario = new Estagiario();
        estagiario.setEmail(email);
        estagiario.setSenha(Criptografia.criptografar(senha));
        estagiario.setCurriculo(curriculo);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.user_profile);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob);
        byte[] bitmapdata = blob.toByteArray();
        estagiario.setFoto(bitmapdata);
        estagiario.setId(estagiarioDAO.inserirEstagiario(estagiario));
        return estagiario;
    }

    private Curriculo criarCurriculo(String curso, String instituicao, String areaAtuacao,
                                     String exp, String obj, String relacionamento,
                                     String basico, String esp, String disciplina, String link) {
        Curriculo curriculo = new Curriculo();
        curriculo.setCurso(curso);
        curriculo.setInstituicao(instituicao);
        curriculo.setAreaAtuacao(areaAtuacao);
        curriculo.setExperiencia(exp);
        curriculo.setObjetivo(obj);
        curriculo.setRelacionamento(relacionamento);
        curriculo.setConhcimentos_basicos(basico);
        curriculo.setConhecimentos_especificos(esp);
        curriculo.setDisciplinas(disciplina);
        curriculo.setLink(link);
        curriculo.setId(curriculoDAO.inserirCurriculo(curriculo));
        return curriculo;
    }

    private void cadastrarPessoa(String nome, String email, String cpf, String senha, String cidade,
                                 String curso, String instituicao, String areaAtuacao,
                                 String exp, String obj, String relacionamento,
                                 String basico, String esp, String disciplina, String link) {
        Curriculo curriculo = this.criarCurriculo(curso, instituicao, areaAtuacao,
                exp, obj, relacionamento, basico, esp, disciplina, link);
        Estagiario estagiario = this.criarEstagiario(email, senha, curriculo);
        this.criarPessoa(nome, cpf, cidade, estagiario);
    }

    private void criarAvaliacao(long idEstagiario, long idVaga, float notaAvaliacao){
        vagaDAO.inserirNotaVaga(idEstagiario, idVaga, notaAvaliacao);
    }

}

