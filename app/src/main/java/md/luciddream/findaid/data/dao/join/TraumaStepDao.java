package md.luciddream.findaid.data.dao.join;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import md.luciddream.findaid.data.dao.Deletable;
import md.luciddream.findaid.data.dao.Insertable;
import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaStep;
import md.luciddream.findaid.data.model.join.TraumaSymptom;

import java.util.List;

@Dao
public interface TraumaStepDao extends Insertable<TraumaStep>, Deletable<TraumaStep> {
    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in(" +
            "SELECT Trauma_Step.t_id FROM Trauma_Step WHERE Trauma_Step.sp_id = :stepId)")
    List<Trauma> getTraumasByStepId(int stepId);


    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in (" +
            "SELECT Trauma_Step.t_id FROM Trauma_Step WHERE Trauma_Step.sp_id in (" +
            "SELECT Step.sp_id FROM Step WHERE Step.name = :stepName))")
    List<Trauma> getTraumasByStepName(String stepName);

    @Query("SELECT * FROM Step WHERE Step.sp_id in(" +
            "SELECT Trauma_Step.sp_id FROM Trauma_Step WHERE Trauma_Step.t_id = :traumaId)")
    List<Step> getStepByTraumaId(int traumaId);

    @Query("SELECT * FROM Step WHERE Step.sp_id in (" +
            "SELECT Trauma_Step.sp_id FROM Trauma_Step WHERE Trauma_Step.t_id in (" +
            "SELECT Trauma.t_id FROM Trauma WHERE Trauma.name = :traumaName))")
    List<Step> getStepByTraumaName(String traumaName);

    @Query("SELECT * FROM Step NATURAL JOIN Trauma_Step WHERE t_id = :traumaId ORDER BY ord_num")
    List<Step> getOrderedStepByTraumaId(int traumaId);


    @Query("SELECT * FROM Step NATURAL JOIN Trauma_Step WHERE t_id in(" +
            "SELECT Trauma.t_id FROM Trauma WHERE Trauma.name = :traumaName) ORDER BY ord_num")
    List<Step> getOrderedStepByTraumaName(String traumaName);

    @Query("SELECT * FROM Trauma_Step")
    List<TraumaStep> findAll();

    @Update
    public void update(TraumaStep item);

}
