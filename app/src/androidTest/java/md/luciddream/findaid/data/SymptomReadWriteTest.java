package md.luciddream.findaid.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import md.luciddream.findaid.TestUtil;
import md.luciddream.findaid.data.dao.SymptomDao;
import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Symptom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SymptomReadWriteTest {

    private SymptomDao mSymptomDao;
    private FindAidDatabase mDb;

    @Before
    public  void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mSymptomDao = mDb.symptomDao();
    }

    @After
    public void closeDB(){
        if(mDb != null) mDb.close();
    }

    @Test
    public void writeSymptomAndReadInList(){
        Symptom item = TestUtil.createSymptom();
        mSymptomDao.insert(item);
        List<Symptom> items = mSymptomDao.findByName(TestUtil.TEST_SYMPTOM);
        assertNotNull(items);
        assertTrue(!items.isEmpty());
        assertTrue(items.get(0).getName().equals(item.getName()));
    }

    @Test
    public void deleteSymptom(){

        Symptom item = TestUtil.createSymptom();
        mSymptomDao.insert(item);
        mSymptomDao.delete(item);
        List<Symptom> items = mSymptomDao.findByName(TestUtil.TEST_SYMPTOM);
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }
}
