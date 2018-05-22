package md.luciddream.findaid.data.dao.join;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.dao.Deletable;
import md.luciddream.findaid.data.dao.Insertable;
import md.luciddream.findaid.data.model.Season;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaSeason;

import java.util.List;

@Dao
public interface TraumaSeasonDao extends Insertable<TraumaSeason>, Deletable<TraumaSeason> {
    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in(" +
            "SELECT Trauma_Season.t_id FROM Trauma_Season WHERE Trauma_Season.sn_id = :seasonId)")
    List<Trauma> getTraumasBySeasonId(int seasonId);


    @Query("SELECT * FROM Trauma WHERE Trauma.t_id in (" +
            "SELECT Trauma_Season.t_id FROM Trauma_Season WHERE Trauma_Season.sn_id in (" +
            "SELECT Season.sn_id FROM Season WHERE Season.name = :seasonName))")
    List<Trauma> getTraumasBySeasonName(String seasonName);

    @Query("SELECT * FROM Season WHERE Season.sn_id in(" +
            "SELECT Trauma_Season.sn_id FROM Trauma_Season WHERE Trauma_Season.t_id = :traumaId)")
    List<Season> getSeasonByTraumaId(int traumaId);

    @Query("SELECT * FROM Season WHERE Season.sn_id in (" +
            "SELECT Trauma_Season.sn_id FROM Trauma_Season WHERE Trauma_Season.t_id in (" +
            "SELECT Trauma.t_id FROM Trauma WHERE Trauma.name = :traumaName))")
    List<Season> getSeasonByTraumaName(String traumaName);

    @Query("UPDATE Trauma_Season SET sn_id = :secondId WHERE t_id = :firstId;")
    void updateSecondByFirstId(int firstId, int secondId);

    @Query("SELECT * FROM Trauma_Season")
    List<TraumaSeason> findAll();
}
