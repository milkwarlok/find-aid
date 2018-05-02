package md.luciddream.findaid.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import md.luciddream.findaid.data.dao.*;
import md.luciddream.findaid.data.dao.join.TraumaLocationDao;
import md.luciddream.findaid.data.model.*;
import md.luciddream.findaid.data.model.join.TraumaLocation;

@Database(entities = {Location.class, Organ.class, Season.class, Step.class, Symptom.class, Trauma.class,
        TraumaLocation.class},
        version = 1)
public abstract class FindAidDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
    public abstract OrganDao organDao();
    public abstract SeasonDao seasonDao();
    public abstract StepDao stepDao();
    public abstract SymptomDao symptomDao();
    public abstract TraumaDao traumaDao();

    public abstract TraumaLocationDao traumaLocationDao();

}
