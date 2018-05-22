package md.luciddream.findaid.data.dao.join;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.dao.Deletable;
import md.luciddream.findaid.data.dao.Insertable;
import md.luciddream.findaid.data.model.Organ;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaOrgan;
import md.luciddream.findaid.data.model.join.TraumaOrgan;

import java.util.List;

@Dao
public interface TraumaOrganDao extends Insertable<TraumaOrgan>, Deletable<TraumaOrgan> {

    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in(" +
            "SELECT Trauma_Organ.t_id FROM Trauma_Organ WHERE Trauma_Organ.o_id = :organId)")
    List<Trauma> getTraumasByOrganId(int organId);


    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in (" +
            "SELECT Trauma_Organ.t_id FROM Trauma_Organ WHERE Trauma_Organ.o_id in (" +
            "SELECT Organ.o_id FROM Organ WHERE Organ.name = :organName))")
    List<Trauma> getTraumasByOrganName(String organName);

    @Query("SELECT * FROM Organ WHERE Organ.o_id in(" +
            "SELECT Trauma_Organ.o_id FROM Trauma_Organ WHERE Trauma_Organ.t_id = :traumaId)")
    List<Organ> getOrganByTraumaId(int traumaId);

    @Query("SELECT * FROM Organ WHERE Organ.o_id in (" +
            "SELECT Trauma_Organ.o_id FROM Trauma_Organ WHERE Trauma_Organ.t_id in (" +
            "SELECT Trauma.t_id FROM Trauma WHERE Trauma.name = :traumaName))")
    List<Organ> getOrganByTraumaName(String traumaName);

    @Query("UPDATE Trauma_Organ SET o_id = :secondId WHERE t_id = :firstId;")
    void updateSecondByFirstId(int firstId, int secondId);

    @Query("SELECT * FROM Trauma_Organ")
    List<TraumaOrgan> findAll();
}
