package md.luciddream.findaid.data.dao.join;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.dao.Deletable;
import md.luciddream.findaid.data.dao.Insertable;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaLocation;

import java.util.List;

@Dao
public interface TraumaLocationDao extends Insertable<TraumaLocation>, Deletable<TraumaLocation> {

    @Query("SELECT * FROM Trauma WHERE TRAUMA.t_id in(SELECT Trauma_Location.t_id FROM Trauma_Location WHERE Trauma_Location.l_id = :locationId)")
    public List<Trauma> getTraumasByLocationId(int locationId);

    @Query("SELECT * FROM Trauma_Location")
    public List<TraumaLocation> findAll();
//
//    public void getTraumasByLocationName(String locationName);
//
//    public void getLocationByTraumaId(int traumaId);
//
//    public void getLocationByTraumaName(String TraumaName);
}

