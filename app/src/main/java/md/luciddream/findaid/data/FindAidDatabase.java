package md.luciddream.findaid.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.model.Location;

@Database(entities = {Location.class}, version = 1)
public abstract class FindAidDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
