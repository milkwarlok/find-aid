package md.luciddream.findaid.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import md.luciddream.findaid.TestUtil;
import md.luciddream.findaid.data.dao.StepDao;
import md.luciddream.findaid.data.model.Season;
import md.luciddream.findaid.data.model.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class StepReadWriteTest {

    private StepDao mStepDao;
    private FindAidDatabase mDb;

    @Before
    public  void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mStepDao = mDb.stepDao();
    }

    @After
    public void closeDB(){
        if(mDb != null) mDb.close();
    }

    @Test
    public void writeStepAndReadInList(){
        Step item = TestUtil.createStep();
        mStepDao.insert(item);
        List<Step> items = mStepDao.findByName(TestUtil.TEST_STEP);
        assertNotNull(items);
        assertTrue(!items.isEmpty());
        assertTrue(items.get(0).getName().equals(item.getName()));
    }

    @Test
    public void deleteStep(){

        Step item = TestUtil.createStep();
        mStepDao.insert(item);
        mStepDao.delete(item);
        List<Step> items = mStepDao.findByName(TestUtil.TEST_STEP);
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }
}
