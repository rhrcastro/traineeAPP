package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class CurriculoDAO {
    private final Database bancoDados;

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
        int indexXP = cursor.getColumnIndex("experiencia");
        String xp = cursor.getString(indexXP);
        int indexObj = cursor.getColumnIndex("objetivo");
        String obj = cursor.getString(indexObj);
        int indexRel = cursor.getColumnIndex("relacionamento");
        String relacionamento = cursor.getString(indexRel);
        int indexBasico = cursor.getColumnIndex("basico");
        String basico = cursor.getString(indexBasico);
        int indexForte = cursor.getColumnIndex("especifico");
        String forte = cursor.getString(indexForte);
        int indexDisciplina = cursor.getColumnIndex("disciplina");
        String disciplina = cursor.getString(indexDisciplina);
        int indexLink = cursor.getColumnIndex("link");
        String link = cursor.getString(indexLink);
        int indexSegmento = cursor.getColumnIndex("areaAtuacao");
        String segmento = cursor.getString(indexSegmento);
        Curriculo curriculo = new Curriculo();
        curriculo.setId(id);
        curriculo.setCurso(curso);
        curriculo.setInstituicao(instituicao);
        curriculo.setAreaAtuacao(segmento);
        curriculo.setLink(link);
        curriculo.setExperiencia(xp);
        curriculo.setRelacionamento(relacionamento);
        curriculo.setDisciplinas(disciplina);
        curriculo.setObjetivo(obj);
        curriculo.setConhcimentos_basicos(basico);
        curriculo.setConhecimentos_especificos(forte);
        return curriculo;
    }
    public long inserirCurriculo(Curriculo curriculo) {
        long result;
        SQLiteDatabase escritorBanco =  bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("curso", curriculo.getCurso());
        valores.put("instituicao", curriculo.getInstituicao());
        valores.put("areaAtuacao", curriculo.getAreaAtuacao());
        valores.put("experiencia", curriculo.getExperiencia());
        valores.put("objetivo", curriculo.getObjetivo());
        valores.put("relacionamento", curriculo.getRelacionamento());
        valores.put("basico", curriculo.getConhcimentos_basicos());
        valores.put("especifico", curriculo.getConhecimentos_especificos());
        valores.put("disciplina", curriculo.getDisciplinas());
        valores.put("link", curriculo.getLink());
        result = escritorBanco.insert("curriculo", null, valores);
        escritorBanco.close();
        return result;
    }

    private Curriculo load(String query, String[] args) {
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
        return this.load(query, args);
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

    public void mudarXp(Curriculo curriculo, String xp) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("experiencia", xp);
        db.update("curriculo", valores,"id = ?", new String[]{String.valueOf(curriculo.getId())});
        db.close();
    }

    public void mudarObj(Curriculo curriculo, String obj) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("objetivo", obj);
        db.update("curriculo", valores,"id = ?", new String[]{String.valueOf(curriculo.getId())});
        db.close();
    }

    public void mudarRelacionamento(Curriculo curriculo, String relacionamento) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("relacionamento", relacionamento);
        db.update("curriculo", valores,"id = ?", new String[]{String.valueOf(curriculo.getId())});
        db.close();
    }

    public void mudarBasico(Curriculo curriculo, String basico) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("basico", basico);
        db.update("curriculo", valores,"id = ?", new String[]{String.valueOf(curriculo.getId())});
        db.close();
    }

    public void mudarEspecifico(Curriculo curriculo, String especifico) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("especifico", especifico);
        db.update("curriculo", valores,"id = ?", new String[]{String.valueOf(curriculo.getId())});
        db.close();
    }

    public void mudarDisciplina(Curriculo curriculo, String disciplina) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("disciplina", disciplina);
        db.update("curriculo", valores,"id = ?", new String[]{String.valueOf(curriculo.getId())});
        db.close();
    }

}