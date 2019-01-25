package bsi.mpoo.traineeufrpe.negocio;

import android.content.Context;
import android.database.Cursor;
import android.util.Patterns;

import java.util.InputMismatchException;

import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.persistencia.CurriculoDAO;
import bsi.mpoo.traineeufrpe.persistencia.EstagiarioDAO;
import bsi.mpoo.traineeufrpe.persistencia.PessoaDAO;

class EstagiarioServices {

    private final int COLUMN_ID_ESTAGIARIO = 4;

    private final Context mContext;
    private final PessoaDAO pessoaDAO;
    private final EstagiarioDAO estagiarioDAO;
    private final CurriculoDAO curriculoDAO;

    public EstagiarioServices(Context context) {
        mContext = context;
        this.pessoaDAO = new PessoaDAO(context);
        this.estagiarioDAO = new EstagiarioDAO(context);
        this.curriculoDAO = new CurriculoDAO(context);
    }

    public static boolean isEmailValido(String email) {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isSenhaIgual(String senha1, String senha2) {
        return senha1.equals(senha2);
    }

    public static boolean isCPF(String CPF) {
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    public Pessoa getPessoaCompleta(long idPessoa){
        Pessoa pessoa = pessoaDAO.getPessoaById(idPessoa);
        Cursor data = pessoaDAO.getIdEstagiarioByPessoa(idPessoa);
        if (data != null && data.moveToFirst()) {
            long idEstagiario = data.getLong(COLUMN_ID_ESTAGIARIO);
            data.close();
            pessoa.setEstagiario(estagiarioDAO.getEstagiarioById(idEstagiario, mContext));
            pessoa.getEstagiario().setCurriculo(curriculoDAO.getIdCurriculo(idEstagiario, mContext));
            return pessoa;
        } return null;
    }
}

