package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.database.Database;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;

public class VagaDAO {
    private final Database bancoDados;
    private final EmpregadorServices empregadorServices;

    public VagaDAO(Context context) {
        bancoDados = new Database(context);
        empregadorServices = new EmpregadorServices(context);
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
        int indexEmpregador = cursor.getColumnIndex("id_empregador");
        long idEmpregador = cursor.getLong(indexEmpregador);
        Vaga vaga = new Vaga();
        vaga.setId(id);
        vaga.setNome(nome);
        vaga.setRequisito(req);
        vaga.setBolsa(bolsa);
        vaga.setArea(area);
        vaga.setObs(obs);
        vaga.setDataCriacao(data);
        vaga.setHorario(horario);
        vaga.setEmpregador(empregadorServices.getEmpregadorById(idEmpregador));
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

    public void inserirNotaVaga(long pessoa, long vaga, float nota) {
        SQLiteDatabase escreverBanco = bancoDados.getWritableDatabase();
        if (getNotaVaga(pessoa, vaga) == null){
            ContentValues valores = new ContentValues();
            valores.put("idestagiarioavaliador", pessoa);
            valores.put("idvagavaliada", vaga);
            valores.put("notaAvaliacao", nota);
            long resultado = escreverBanco.insert("avaliacoesVagas", null, valores);
            escreverBanco.close();
        } else {
            ContentValues values = new ContentValues();
            values.put("idestagiarioavaliador", pessoa);
            values.put("idvagavaliada", vaga);
            values.put("notaAvaliacao", nota);
            escreverBanco.update("avaliacoesVagas", values, "idestagiarioavaliador = ? AND idvagavaliada = ?",
                    new String[]{String.valueOf(pessoa), String.valueOf(vaga)});
        }
    }

    public Cursor getDatabyEmpregador(long id) {
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM vaga " +
                "WHERE id_empregador = ?";
        String[] args = {String.valueOf(id)};
        return db.rawQuery(query, args);
    }

    public Cursor getAllDataOrderByDate() {
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM vaga ORDER BY data_criacao DESC";
        return db.rawQuery(query, null);
    }

    public Cursor getAllDataOrderByName() {
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM vaga ORDER BY nome ASC";
        return db.rawQuery(query, null);
    }

    public Cursor getAllDataByArea(String area) {
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM vaga" +
                " WHERE area = ?";
        String[] args = {String.valueOf(area)};
        return db.rawQuery(query, args);
    }

    public Cursor getId(String name) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        String query = "SELECT  id FROM vaga " +
                "WHERE nome = ?";
        String[] args = {String.valueOf(name)};
        return db.rawQuery(query, args);
    }

    public Vaga getVagaById(long id) {
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

    public Double getNotaVaga(long estagiario, long vaga) {
        String query = "SELECT * FROM avaliacoesVagas " +
                "WHERE idvagavaliada = ? " +
                "AND idestagiarioavaliador = ? ";
        String idEstagiario = String.valueOf(estagiario);
        String idVaga = String.valueOf(vaga);
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
        String query =  "SELECT * FROM avaliacoesVagas " +
                "WHERE idestagiarioavaliador = ?";
        String[] args = {String.valueOf(estagiario.getId())};
        return this.loadIdVagaAvaliada(query, args);
    }
    private  HashMap<String, Double> loadIdVagaAvaliada(String query, String[] args) {
        HashMap<java.lang.String, java.lang.Double> avaliacaoEstagiario = new HashMap<>();
        SQLiteDatabase leitorBanco = bancoDados.getWritableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int indexNota = cursor.getColumnIndex("notaAvaliacao");
                Double nota = cursor.getDouble(indexNota);
                int indexIdVaga = cursor.getColumnIndex("idvagavaliada");
                String idVaga = cursor.getString(indexIdVaga);
                avaliacaoEstagiario.put(idVaga, nota);
            } while (cursor.moveToNext());
        }
        return avaliacaoEstagiario;
    }
}
