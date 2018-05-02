package md.luciddream.findaid.data.dao.join;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.dao.Deletable;
import md.luciddream.findaid.data.dao.Insertable;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaLocation;

import java.util.List;

@Dao
public interface TraumaLocationDao extends Insertable<TraumaLocation>, Deletable<TraumaLocation> {

    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in(" +
            "SELECT Trauma_Location.t_id FROM Trauma_Location WHERE Trauma_Location.l_id = :locationId)")
    List<Trauma> getTraumasByLocationId(int locationId);


    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in (" +
            "SELECT Trauma_Location.t_id FROM Trauma_Location WHERE Trauma_Location.l_id in (" +
            "SELECT Location.l_id FROM Location WHERE Location.name = :locationName))")
    List<Trauma> getTraumasByLocationName(String locationName);

    @Query("SELECT * FROM Location WHERE Location.l_id in(" +
            "SELECT Trauma_Location.l_id FROM Trauma_Location WHERE Trauma_Location.t_id = :traumaId)")
    List<Location> getLocationByTraumaId(int traumaId);

    @Query("SELECT * FROM Location WHERE Location.l_id in (" +
            "SELECT Trauma_Location.l_id FROM Trauma_Location WHERE Trauma_Location.t_id in (" +
            "SELECT Trauma.t_id FROM Trauma WHERE Trauma.name = :traumaName))")
    List<Location> getLocationByTraumaName(String traumaName);

    @Query("SELECT * FROM Trauma_Location")
    List<TraumaLocation> findAll();



}

