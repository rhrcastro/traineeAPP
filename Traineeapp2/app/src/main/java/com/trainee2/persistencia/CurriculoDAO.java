package com.trainee2.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trainee2.dominio.Curriculo;
import com.trainee2.infra.BancoDados;
import com.trainee2.infra.Sessao;

public class CurriculoDAO {
    private BancoDados bancoDados;

    public CurriculoDAO(Context context) {
        bancoDados = new BancoDados(context);
    }
    private Curriculo criarCurriculo(Cursor cursor){
        int indexId = cursor.getColumnIndex("id");
        long id = cursor.getLong(indexId);
        int indexCurso = cursor.getColumnIndex("curso");
        String curso = cursor.getString(indexCurso);
        int indexInstituicao = cursor.getColumnIndex("instituicao");
        String instituicao = cursor.getString(indexInstituicao);
        int indexSegmento = cursor.getColumnIndex("areaAtuacao");
        String areaAtuacao = cursor.getString(indexSegmento);
        Curriculo curriculo = new Curriculo();
        curriculo.setId(id);
        curriculo.setCurso(curso);
        curriculo.setInstituicao(instituicao);
        curriculo.setAreaAtuacao(areaAtuacao);
        return curriculo;
    }
    public long inserirCurriculo(Curriculo curriculo) {
        long result;
        SQLiteDatabase escritorBanco =  bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("curso", curriculo.getCurso());
        valores.put("instituicao", curriculo.getInstituicao());
        valores.put("areaAtuacao", curriculo.getAreaAtuacao());
        result = escritorBanco.insert("curriculo", null, valores);
        escritorBanco.close();
        if (result > -1){
            curriculo.setId(result);
        }
        return result;
    }
}
