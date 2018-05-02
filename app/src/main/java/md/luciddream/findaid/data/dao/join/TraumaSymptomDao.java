package md.luciddream.findaid.data.dao.join;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.dao.Deletable;
import md.luciddream.findaid.data.dao.Insertable;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaSymptom;

import java.util.List;

@Dao
public interface TraumaSymptomDao extends Insertable<TraumaSymptom>, Deletable<TraumaSymptom> {
    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in(" +
            "SELECT Trauma_Symptom.t_id FROM Trauma_Symptom WHERE Trauma_Symptom.sm_id = :symptomId)")
    List<Trauma> getTraumasBySymptomId(int symptomId);


    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in (" +
            "SELECT Trauma_Symptom.t_id FROM Trauma_Symptom WHERE Trauma_Symptom.sm_id in (" +
            "SELECT Symptom.sm_id FROM Symptom WHERE Symptom.name = :symptomName))")
    List<Trauma> getTraumasBySymptomName(String symptomName);

    @Query("SELECT * FROM Symptom WHERE Symptom.sm_id in(" +
            "SELECT Trauma_Symptom.sm_id FROM Trauma_Symptom WHERE Trauma_Symptom.t_id = :traumaId)")
    List<Symptom> getSymptomByTraumaId(int traumaId);

    @Query("SELECT * FROM Symptom WHERE Symptom.sm_id in (" +
            "SELECT Trauma_Symptom.sm_id FROM Trauma_Symptom WHERE Trauma_Symptom.t_id in (" +
            "SELECT Trauma.t_id FROM Trauma WHERE Trauma.name = :traumaName))")
    List<Symptom> getSymptomByTraumaName(String traumaName);

    @Query("SELECT * FROM Trauma_Symptom")
    List<TraumaSymptom> findAll();
}
