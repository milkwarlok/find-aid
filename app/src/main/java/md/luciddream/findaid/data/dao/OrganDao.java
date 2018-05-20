package md.luciddream.findaid.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Organ;

import java.util.List;

@Dao
public interface OrganDao extends Insertable<Organ>, Deletable<Organ> {
    @Query("SELECT * FROM Organ")
    List<Organ> findAll();

    @Query("SELECT * FROM Organ WHERE Organ.o_id in (:id)")
    List<Organ> findByIds(int[] id);

    @Query("SELECT * FROM Organ WHERE Organ.o_id  = :id")
    Organ findById(int id);

    @Query("SELECT * FROM Organ WHERE Organ.name = :name")
    List<Organ> findByName(String name);
}
