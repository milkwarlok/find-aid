package md.luciddream.findaid.data.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Trauma;

import java.util.List;

@Dao
public interface TraumaDao extends Insertable<Trauma>, Deletable<Trauma>, Existable{
    @Query("SELECT * FROM Trauma")
    List<Trauma> findAll();

    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in (:id)")
    List<Trauma> findByIds(int[] id);

    @Query("SELECT * FROM Trauma WHERE Trauma.t_id  = :id")
    Trauma findById(int id);

    @Query("SELECT * FROM Trauma WHERE Trauma.name = :name")
    List<Trauma> findByName(String name);

    @Query("SELECT * FROM Trauma WHERE Trauma.t_id ORDER BY Trauma.relevance DESC LIMIT :howMany;")
    List<Trauma> findFirstMostRelevant(Integer howMany);

    @Override
    @Query("SELECT EXISTS(SELECT 1 FROM Trauma WHERE Trauma.name =:name)")
    Boolean exists(String name);
}
