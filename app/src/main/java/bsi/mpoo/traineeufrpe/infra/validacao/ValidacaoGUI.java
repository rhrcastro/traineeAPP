package bsi.mpoo.traineeufrpe.infra.validacao;

import android.util.Patterns;

public class ValidacaoGUI {

    public static boolean isCampoVazio(String campo) {
        return campo.isEmpty();
    }

    public static boolean isEmailValido(String email){
        return isCampoVazio(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isSenhasIguais(String senha1 , String senha2){
        return senha1.equals(senha2);
    }


}
