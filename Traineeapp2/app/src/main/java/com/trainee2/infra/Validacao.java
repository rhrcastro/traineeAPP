package com.trainee2.infra;
import android.util.Patterns;
public class Validacao {
    public static boolean verificarCampoVazio(String campo) {
        if (campo.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean verificarCampoEmail(String email){
        if(verificarCampoVazio(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        } else {
            return false;
        }
    }
}
