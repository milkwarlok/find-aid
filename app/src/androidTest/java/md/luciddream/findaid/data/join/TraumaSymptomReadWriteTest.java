package md.luciddream.findaid.data.join;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.SymptomDao;
import md.luciddream.findaid.data.dao.TraumaDao;
import md.luciddream.findaid.data.dao.join.TraumaSymptomDao;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaSymptom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static md.luciddream.findaid.TestUtil.TEST_ORGAN;
import static md.luciddream.findaid.TestUtil.TEST_TRAUMA;

public class TraumaSymptomReadWriteTest {
    private TraumaSymptomDao mDao;
    private SymptomDao mSymptomDao;
    private TraumaDao mTraumaDao;
    private FindAidDatabase mDb;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mDao = mDb.traumaSymptomDao();
        mSymptomDao = mDb.symptomDao();
        mTraumaDao = mDb.traumaDao();
    }

    @After
    public void closeDb(){
        mDb.close();
    }

    @Test(expected = android.database.sqlite.SQLiteConstraintException.class)
    public void write_TraumaSymptom_AndGetException(){
        TraumaSymptom item = new TraumaSymptom();
        item.setSm_id(5);
        item.setT_id(5);
        mDao.insert(item);
    }

    @Test
    public void write_TraumaSymptom_AndReadInList(){
        Symptom symptom = new Symptom(null, TEST_ORGAN);
        Trauma trauma = new Trauma(null, TEST_TRAUMA, 3);
        mSymptomDao.insert(symptom);
        mTraumaDao.insert(trauma);

        TraumaSymptom item = new TraumaSymptom();
        item.setT_id(1);
        item.setSm_id(1);
        mDao.insert(item);
        List<TraumaSymptom> all = mDao.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertTrue(all.get(0).getSm_id() == item.getSm_id() && all.get(0).getT_id() == item.getT_id());
    }

    @Test
    public void write_TraumaSymptom_AndReadBySymptomId(){
        Symptom firstSymptom = new Symptom(null, TEST_ORGAN);
        Symptom secondSymptom = new Symptom(null, TEST_ORGAN);
        mSymptomDao.insert(firstSymptom, secondSymptom);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaSymptom firstTraumaSymptom = new TraumaSymptom(1,1); // should find
        TraumaSymptom secondTraumaSymptom = new TraumaSymptom(1,2); // should find
        TraumaSymptom thirdTraumaSymptom = new TraumaSymptom(2,1); // should not find
        mDao.insert(firstTraumaSymptom, secondTraumaSymptom, thirdTraumaSymptom);
        List<Trauma> traumasBySymptomId = mDao.getTraumasBySymptomId(1);
        for(Trauma trauma: traumasBySymptomId){
            Log.d("TraumaSymptomReadWriteTest.java:write_TraumaSymptom_AndReadBySymptomId.test",
                    trauma.getT_id() + ". " + trauma.getName());
        }
        assertNotNull(traumasBySymptomId);
        assertFalse(traumasBySymptomId.isEmpty());
        assertTrue(traumasBySymptomId.size() == 2);// found exactly two
        assertTrue(traumasBySymptomId.get(0).getT_id() == 1);//with exact values
        assertTrue(traumasBySymptomId.get(1).getT_id() == 2);
    }

    @Test
    public void write_TraumaSymptom_AndReadBySymptomName(){
        Symptom firstSymptom = new Symptom(null, TEST_ORGAN);
        Symptom secondSymptom = new Symptom(null, TEST_ORGAN + "Something");
        mSymptomDao.insert(firstSymptom, secondSymptom);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaSymptom firstTraumaSymptom = new TraumaSymptom(1,1); // should find
        TraumaSymptom secondTraumaSymptom = new TraumaSymptom(2,1); // should not find(because l_id = 2 -> different Symptom name
        TraumaSymptom thirdTraumaSymptom = new TraumaSymptom(2,2); // should not find
        mDao.insert(firstTraumaSymptom, secondTraumaSymptom, thirdTraumaSymptom);
        List<Trauma> traumasBySymptomId = mDao.getTraumasBySymptomName(TEST_ORGAN);
        for(Trauma trauma: traumasBySymptomId){
            Log.d("TraumaSymptomReadWriteTest.java:write_TraumaSymptom_AndReadBySymptomName.test",
                    trauma.getT_id() + ". " + trauma.getName());
        }
        assertNotNull(traumasBySymptomId);
        assertFalse(traumasBySymptomId.isEmpty());
        assertTrue(traumasBySymptomId.size() == 1);// found exactly one
    }
    //
    @Test
    public void write_TraumaSymptom_AndReadByTraumaId(){
        Symptom firstSymptom = new Symptom(null, TEST_ORGAN);
        Symptom secondSymptom = new Symptom(null, TEST_ORGAN);
        mSymptomDao.insert(firstSymptom, secondSymptom);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaSymptom firstTraumaSymptom = new TraumaSymptom(1,1); // should not find
        TraumaSymptom secondTraumaSymptom = new TraumaSymptom(1,2); // should find
        mDao.insert(firstTraumaSymptom, secondTraumaSymptom);
        List<Symptom> symptomByTraumaId = mDao.getSymptomByTraumaId(2);
        for(Symptom symptom: symptomByTraumaId){
            Log.d("TraumaSymptomReadWriteTest.java:write_TraumaSymptom_AndReadByTraumaId.test",
                    symptom.getSm_id() + ". " + symptom.getName());
        }
        assertNotNull(symptomByTraumaId);
        assertFalse(symptomByTraumaId.isEmpty());
        assertTrue(symptomByTraumaId.size() == 1);// found exactly two
        assertTrue(symptomByTraumaId.get(0).getSm_id() == 1);//with exact values
    }
    @Test
    public void write_TraumaSymptom_AndReadByTraumaName(){
        Symptom firstSymptom = new Symptom(null, TEST_ORGAN);
        Symptom secondSymptom = new Symptom(null, TEST_ORGAN );
        mSymptomDao.insert(firstSymptom, secondSymptom);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA+"Something", 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaSymptom firstTraumaSymptom = new TraumaSymptom(1,1); // should find
        TraumaSymptom secondTraumaSymptom = new TraumaSymptom(2,2); // should not find(because l_id = 2 -> different Symptom name
        mDao.insert(firstTraumaSymptom, secondTraumaSymptom);
        List<Symptom> symptomByTraumaName = mDao.getSymptomByTraumaName(TEST_TRAUMA);
        for(Symptom symptom: symptomByTraumaName){
            Log.d("TraumaSymptomReadWriteTest.java:write_TraumaSymptom_AndReadByTraumaName.test",
                    symptom.getSm_id() + ". " + symptom.getName());
        }
        assertNotNull(symptomByTraumaName);
        assertFalse(symptomByTraumaName.isEmpty());
        assertTrue(symptomByTraumaName.size() == 1);// found exactly one
    }
}
