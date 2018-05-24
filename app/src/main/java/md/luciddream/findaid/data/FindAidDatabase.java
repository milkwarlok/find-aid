package md.luciddream.findaid.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import md.luciddream.findaid.R;
import md.luciddream.findaid.data.dao.*;
import md.luciddream.findaid.data.dao.join.*;
import md.luciddream.findaid.data.model.*;
import md.luciddream.findaid.data.model.join.*;

import java.util.HashMap;
import java.util.concurrent.Executors;


//todo: !!!PROVIDE SCHEMA MIGRATION
@Database(entities = {Location.class, Organ.class, Season.class, Step.class, Symptom.class, Trauma.class,
        TraumaLocation.class, TraumaOrgan.class, TraumaSeason.class,TraumaStep.class, TraumaSymptom.class},
        version = 1)

public abstract class  FindAidDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
    public abstract OrganDao organDao();
    public abstract SeasonDao seasonDao();
    public abstract StepDao stepDao();
    public abstract SymptomDao symptomDao();
    public abstract TraumaDao traumaDao();

    public abstract TraumaLocationDao traumaLocationDao();
    public abstract TraumaOrganDao traumaOrganDao();
    public abstract TraumaSeasonDao traumaSeasonDao();
    public abstract TraumaStepDao traumaStepDao();
    public abstract TraumaSymptomDao traumaSymptomDao();

    private static HashMap<String, FindAidDatabase> INSTANCE = new HashMap<>();

    public synchronized static FindAidDatabase getInstance(Context context) {
        if(INSTANCE.get(context.getString(R.string.database_name)) == null){
            INSTANCE.put(context.getString(R.string.database_name), buildDatabase(context));
        }
        return INSTANCE.get(context.getString(R.string.database_name));
    }
    private static FindAidDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                FindAidDatabase.class,
                context.getString(R.string.database_name))
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                InitialData initialData = new InitialData(context);
                                FindAidDatabase database = getInstance(context);
                                database.locationDao().insert(initialData.getLocations());
                                database.organDao().insert(initialData.getOrgans());
                                database.seasonDao().insert(initialData.getSeasons());
                                database.stepDao().insert(initialData.getSteps());
                                database.symptomDao().insert(initialData.getSymptoms());
                                database.traumaDao().insert(initialData.getTraumas());
                                database.traumaLocationDao().insert(initialData.getTraumaLocations());
                                database.traumaOrganDao().insert(initialData.getTraumaOrgans());
                                database.traumaSeasonDao().insert(initialData.getTraumaSeasons());
                                database.traumaStepDao().insert(initialData.getTraumaSteps());
                                database.traumaSymptomDao().insert(initialData.getTraumaSymptoms());
                            }
                        });
                    }
                })
                .build();
    }
}
