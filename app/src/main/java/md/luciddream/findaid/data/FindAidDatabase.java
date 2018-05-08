package md.luciddream.findaid.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import md.luciddream.findaid.data.dao.*;
import md.luciddream.findaid.data.dao.join.*;
import md.luciddream.findaid.data.model.*;
import md.luciddream.findaid.data.model.join.*;

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
                            }
                        });
                    }
                })
                .build();
    }



//    public static final Migration INSERT_BASIC_DATA = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("insert into locations values(null, \"Mountains\"");
//            database.execSQL("insert into locations values(null, \"Seashore");
//            database.execSQL("insert into locations values(null, \"Sea");
//            database.execSQL("insert into locations values(null, \"Urban");
//            database.execSQL("insert into organ values(null, \"skin\"");
//            database.execSQL("insert into organ values(null, \"head\"");
//            database.execSQL("insert into organ values(null, \"heart\"");
//            database.execSQL("insert into organ values(null, \"body\"");
//            database.execSQL("insert into organ values(null, \"arm\"");
//            database.execSQL("insert into organ values(null, \"leg\"");
//            database.execSQL("insert into season values (null , \"winter\"");
//            database.execSQL("insert into season values (null , \"spring\"");
//            database.execSQL("insert into season values (null , \"summer\"");
//            database.execSQL("insert into season values (null , \"autumn\"");
//            database.execSQL("insert into step values (null, \"Call ambulance\"");
//            database.execSQL("insert into step values (null, \"Find water\"");
//            database.execSQL("insert into step values (null, \"Give water\"");
//            database.execSQL("insert into step values (null, \"Find bandage\"");
//            database.execSQL("insert into step values (null, \"Use bandage\"");
//            database.execSQL("insert into symptom values(null, \"headache\"");
//            database.execSQL("insert into symptom values(null, \"dizziness\"");
//            database.execSQL("insert into symptom values(null, \"red skin\"");
//            database.execSQL("insert into symptom values(null, \"red eyes\"");
//            database.execSQL("insert into trauma values(null, \"sunburn\", 10");
//            database.execSQL("insert into trauma values(null, \"sunstroke\", 10");
//            database.execSQL("insert into trauma values(null, \"luxation\", 5");
//            database.execSQL("insert into trauma values(null, \"burn\", 10");
//
//        }
//    };
}
