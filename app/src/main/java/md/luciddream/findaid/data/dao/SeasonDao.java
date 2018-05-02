package md.luciddream.findaid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.model.Season;

import java.util.List;

@Dao
public interface SeasonDao extends Insertable<Season>, Deletable<Season>{
    @Query("SELECT * FROM Season")
    List<Season> findAll();

    @Query("SELECT * FROM Season WHERE Season.sn_id in (:id)")
    List<Season> findByIds(int[] id);

    @Query("SELECT * FROM Season WHERE Season.name = :name")
    List<Season> findByName(String name);
}
