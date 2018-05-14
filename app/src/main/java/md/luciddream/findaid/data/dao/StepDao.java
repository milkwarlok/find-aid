package md.luciddream.findaid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Symptom;

import java.util.List;

@Dao
public interface StepDao extends Insertable<Step>, Deletable<Step>, Existable{
    @Query("SELECT * FROM Step")
    List<Step> findAll();

    @Query("SELECT * FROM Step WHERE Step.sp_id in (:id)")
    List<Step> findByIds(int[] id);

    @Query("SELECT * FROM Step WHERE Step.name = :name")
    List<Step> findByName(String name);

    @Query("SELECT * FROM Step WHERE Step.name in (:names)")
    List<Step> findByNames(String[] names);

    @Override
    @Query("SELECT EXISTS(SELECT 1 FROM Step WHERE Step.name =:name)")
    Boolean exists(String name);
}
