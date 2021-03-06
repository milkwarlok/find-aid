package md.luciddream.findaid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Symptom;

import java.util.List;

@Dao
public interface SymptomDao extends Insertable<Symptom>, Deletable<Symptom>, Existable{
    @Query("SELECT * FROM Symptom")
    List<Symptom> findAll();

    @Query("SELECT * FROM Symptom WHERE Symptom.sm_id in (:id)")
    List<Symptom> findByIds(int[] id);

    @Query("SELECT * FROM Symptom WHERE Symptom.sm_id  = :id")
    Symptom findById(int id);

    @Query("SELECT * FROM Symptom WHERE Symptom.name = :name")
    List<Symptom> findByName(String name);

    @Query("SELECT * FROM Symptom WHERE Symptom.name in (:names)")
    List<Symptom> findByNames(String[] names);

    @Override
    @Query("SELECT EXISTS(SELECT 1 FROM Symptom WHERE Symptom.name =:name)")
    Boolean exists(String name);
}
