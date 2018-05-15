package md.luciddream.findaid.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;
import md.luciddream.findaid.data.dao.*;
import md.luciddream.findaid.data.dao.join.*;
import md.luciddream.findaid.data.model.*;
import md.luciddream.findaid.data.model.join.*;

import javax.inject.Inject;
import java.util.List;
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

    private static FindAidDatabase INSTANCE;

    public synchronized static FindAidDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }
    private static FindAidDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                FindAidDatabase.class,
                "database-name")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                ExampleData exampleData = new ExampleData();
                                FindAidDatabase database = getInstance(context);
                                database.locationDao().insert(exampleData.getLocations());
                                database.organDao().insert(exampleData.getOrgans());
                                database.seasonDao().insert(exampleData.getSeasons());
                                database.stepDao().insert(exampleData.getSteps());
                                database.symptomDao().insert(exampleData.getSymptoms());
                                database.traumaDao().insert(exampleData.getTraumas());
                                database.traumaLocationDao().insert(exampleData.getTraumaLocations());
                                database.traumaOrganDao().insert(exampleData.getTraumaOrgans());
                                database.traumaSeasonDao().insert(exampleData.getTraumaSeasons());
                                database.traumaStepDao().insert(exampleData.getTraumaSteps());
                                database.traumaSymptomDao().insert(exampleData.getTraumaSymptoms());
                            }
                        });
                    }
                })
                .build();
    }
}
