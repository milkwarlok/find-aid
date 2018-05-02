package md.luciddream.findaid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.model.Step;

import java.util.List;

@Dao
public interface StepDao extends Insertable<Step>, Deletable<Step>{
    @Query("SELECT * FROM Step")
    List<Step> findAll();

    @Query("SELECT * FROM Step WHERE Step.sp_id in (:id)")
    List<Step> findByIds(int[] id);

    @Query("SELECT * FROM Step WHERE Step.name = :name")
    List<Step> findByName(String name);
}
