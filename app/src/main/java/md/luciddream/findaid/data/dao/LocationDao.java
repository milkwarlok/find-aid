package md.luciddream.findaid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.model.Location;

import java.util.List;

@Dao
public interface LocationDao extends Insertable<Location>, Deletable<Location> {
    @Query("SELECT * FROM Location")
    List<Location> findAll();

    @Query("SELECT * FROM Location WHERE Location.l_id in (:id)")
    List<Location> findByIds(int[] id);

    @Query("SELECT * FROM Location WHERE Location.name = :name")
    List<Location> findByName(String name);
}
