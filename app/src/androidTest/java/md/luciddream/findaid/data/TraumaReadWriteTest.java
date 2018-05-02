package md.luciddream.findaid.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import md.luciddream.findaid.TestUtil;
import md.luciddream.findaid.data.dao.TraumaDao;
import md.luciddream.findaid.data.model.Trauma;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TraumaReadWriteTest {

    private TraumaDao mTraumaDao;
    private FindAidDatabase mDb;

    @Before
    public  void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mTraumaDao = mDb.traumaDao();
    }

    @After
    public void closeDB(){
        if(mDb != null) mDb.close();
    }

    @Test
    public void writeTraumaAndReadInList(){
        Trauma item = TestUtil.createTrauma();
        mTraumaDao.insert(item);
        List<Trauma> items = mTraumaDao.findByName(TestUtil.TEST_TRAUMA);
        assertNotNull(items);
        assertTrue(!items.isEmpty());
        assertTrue(items.get(0).getName().equals(item.getName()));
    }

    @Test
    public void deleteTrauma(){

        Trauma item = TestUtil.createTrauma();
        mTraumaDao.insert(item);
        mTraumaDao.delete(item);
        List<Trauma> items = mTraumaDao.findByName(TestUtil.TEST_TRAUMA);
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }
}

