package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class CurriculoDAO {
    private Database bancoDados;

    public CurriculoDAO(Context context) {
        bancoDados = new Database(context);
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
        valores.put("link", curriculo.getLink());
        result = escritorBanco.insert("curriculo", null, valores);
        escritorBanco.close();
        return result;
    }

    private Curriculo load(String query, String[] args,Context context) {
        SQLiteDatabase leitorBanco = bancoDados.getReadableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Curriculo curriculo = null;
        if (cursor.moveToNext()) {
            curriculo = criarCurriculo(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return curriculo;
    }


    public Curriculo getIdCurriculo(long id, Context context) {
        String query = "SELECT * FROM curriculo " +
                "WHERE id = ?";
        String[] args = {String.valueOf(id)};
        return this.load(query, args, context);
    }

    public void mudarCurso(Curriculo curriculo) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("curso", curriculo.getCurso());
        db.update("curriculo", valores,"id = ?", new String[]{String.valueOf(curriculo.getId())});
        db.close();
    }

    public void mudarInstituicao(Curriculo curriculo) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("instituicao", curriculo.getInstituicao());
        db.update("curriculo", valores,"id = ?", new String[]{String.valueOf(curriculo.getId())});
        db.close();
    }

    public void mudarArea(Curriculo curriculo) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("areaAtuacao", curriculo.getAreaAtuacao());
        db.update("curriculo", valores,"id = ?", new String[]{String.valueOf(curriculo.getId())});
        db.close();
    }

    public void mudarLink(Curriculo curriculo) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("link", curriculo.getLink());
        db.update("curriculo", valores,"id = ?", new String[]{String.valueOf(curriculo.getId())});
        db.close();
    }

}