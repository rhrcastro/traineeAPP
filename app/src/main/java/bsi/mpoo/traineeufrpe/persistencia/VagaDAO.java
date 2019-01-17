package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.database.Database;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.SlopeOne;

public class VagaDAO {
    private Database bancoDados;

    public VagaDAO(Context context) {
        bancoDados = new Database(context);
    }

    private Vaga criarVaga(Cursor cursor) {
        int indexId = cursor.getColumnIndex("id");
        long id = cursor.getLong(indexId);
        int indexNome = cursor.getColumnIndex("nome");
        String nome = cursor.getString(indexNome);
        int indexRequisito = cursor.getColumnIndex("requisito");
        String req = cursor.getString(indexRequisito);
        int indexBolsa = cursor.getColumnIndex("bolsa");
        String bolsa = cursor.getString(indexBolsa);
        int indexArea = cursor.getColumnIndex("area");
        String area = cursor.getString(indexArea);
        int indexObs = cursor.getColumnIndex("obs");
        String obs = cursor.getString(indexObs);
        int indexData = cursor.getColumnIndex("data_criacao");
        long data = cursor.getLong(indexData);
        int indexHorario = cursor.getColumnIndex("horario");
        String horario = cursor.getString(indexHorario);
        Vaga vaga = new Vaga();
        vaga.setId(id);
        vaga.setNome(nome);
        vaga.setRequisito(req);
        vaga.setBolsa(bolsa);
        vaga.setArea(area);
        vaga.setObs(obs);
        vaga.setDataCriacao(data);
        vaga.setHorario(horario);
        return vaga;
    }

    public long inserirVaga(Vaga vaga) {
        SQLiteDatabase escreverBanco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", vaga.getNome());
        valores.put("requisito", vaga.getRequisito());
        valores.put("area", vaga.getArea());
        valores.put("bolsa", vaga.getBolsa());
        valores.put("obs", vaga.getObs());
        valores.put("id_empregador", vaga.getEmpregador().getId());
        valores.put("data_criacao", System.currentTimeMillis());
        valores.put("horario", vaga.getHorario());
        long resultado = escreverBanco.insert("vaga", null, valores);
        escreverBanco.close();
        return resultado;
    }

    public Cursor getDatabyEmpregador(long id) {
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM vaga " +
                "WHERE id_empregador = ?";
        String[] args = {String.valueOf(id)};
        Cursor data = db.rawQuery(query, args);
        return data;
    }

    public Cursor getAllDataOrderByDate() {
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM vaga ORDER BY data_criacao DESC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAllDataOrderByName() {
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM vaga ORDER BY nome ASC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAllDataByArea(String area) {
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM vaga" +
                " WHERE area = ?";
        String[] args = {String.valueOf(area)};
        Cursor data = db.rawQuery(query, args);
        return data;
    }

    public Cursor getId(String name) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        String query = "SELECT  id FROM vaga " +
                "WHERE nome = ?";
        String[] args = {String.valueOf(name)};
        Cursor data = db.rawQuery(query, args);
        return data;
    }

    public Vaga getVagaById(int id) {
        String query = "SELECT * FROM vaga " +
                "WHERE id = ?";
        String[] args = {String.valueOf(id)};
        return this.carregarObjeto(query, args);
    }

    private Vaga carregarObjeto(String query, String[] args) {
        SQLiteDatabase leitorBanco = bancoDados.getReadableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Vaga vaga = null;
        if (cursor.moveToNext()) {
            vaga = criarVaga(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return vaga;
    }

    public void mudarNomeVaga(Vaga vaga) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", vaga.getNome());
        db.update("vaga", values, "id = ?", new String[]{String.valueOf(vaga.getId())});
    }

    public void mudarAreaVaga(Vaga vaga) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("area", vaga.getNome());
        db.update("vaga", values, "id = ?", new String[]{String.valueOf(vaga.getId())});
    }

    public void mudarBolsaVaga(Vaga vaga) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bolsa", vaga.getBolsa());
        db.update("vaga", values, "id = ?", new String[]{String.valueOf(vaga.getId())});

    }

    public void mudarRequisitoVaga(Vaga vaga) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("requisito", vaga.getRequisito());
        db.update("vaga", values, "id = ?", new String[]{String.valueOf(vaga.getId())});

    }

    public void mudarObsVaga(Vaga vaga) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("obs", vaga.getObs());
        db.update("vaga", values, "id = ?", new String[]{String.valueOf(vaga.getId())});

    }

    public void mudarHorarioVaga(Vaga vaga) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("horario", vaga.getHorario());
        db.update("vaga", values, "id = ?", new String[]{String.valueOf(vaga.getId())});

    }

    public void deletarVaga(long id2) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        String query = "DELETE FROM vaga " +
                "WHERE id = " + id2;
        db.execSQL(query);

    }

    public Double getNotaVaga(Estagiario estagiario, Vaga vaga) {
        String query = "SELECT * FROM avaliacoesVagas " +
                "WHERE idvagavaliada = ? " +
                "AND idestagiarioavaliador = ? ";
        String idEstagiario = String.valueOf(estagiario.getId());
        String idVaga = String.valueOf(vaga.getId());
        String[] args = {idVaga, idEstagiario};
        SQLiteDatabase leitorBanco = bancoDados.getWritableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Double nota = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int indexNota = cursor.getColumnIndex("notaAvaliacao");
            nota = cursor.getDouble(indexNota);
        }
        return nota;
    }
    public HashMap<String, Double> getAvaliacaoVaga(Estagiario estagiario) {
        String query =  "SELECT * FROM avaliacoesVaga " +
                "WHERE idestagiarioavaliador = ?";
        String[] args = {String.valueOf(estagiario.getId())};
        return this.loadIdVagaAvaliada(query, args);
    }
    private  HashMap<String, Double> loadIdVagaAvaliada(String query, String[] args) {
        HashMap<java.lang.String, java.lang.Double> avaliacaoUsuario = new HashMap<>();
        SQLiteDatabase leitorBanco = bancoDados.getWritableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int indexNota = cursor.getColumnIndex("notaAvaliacao");
                java.lang.Double nota = cursor.getDouble(indexNota);
                int indexIdVaga = cursor.getColumnIndex("idVagavaliada");
                java.lang.String idVaga = cursor.getString(indexIdVaga);
                avaliacaoUsuario.put(idVaga, nota);
            } while (cursor.moveToNext());
        }
        return avaliacaoUsuario;
    }
}
