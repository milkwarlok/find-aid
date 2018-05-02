package md.luciddream.findaid.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import md.luciddream.findaid.TestUtil;
import md.luciddream.findaid.data.dao.SeasonDao;
import md.luciddream.findaid.data.model.Organ;
import md.luciddream.findaid.data.model.Season;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SeasonReadWriteTest {

    private SeasonDao mSeasonDao;
    private FindAidDatabase mDb;

    @Before
    public  void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mSeasonDao = mDb.seasonDao();
    }

    @After
    public void closeDB(){
        if(mDb != null) mDb.close();
    }

    @Test
    public void writeSeasonAndReadInList(){
        Season item = TestUtil.createSeason();
        mSeasonDao.insert(item);
        List<Season> seasonTest = mSeasonDao.findByName(TestUtil.TEST_SEASON);
        assertNotNull(seasonTest);
        assertTrue(!seasonTest.isEmpty());
        assertTrue(seasonTest.get(0).getName().equals(item.getName()));
    }

    @Test
    public void deleteSeason(){

        Season item = TestUtil.createSeason();
        mSeasonDao.insert(item);
        mSeasonDao.delete(item);
        List<Season> seasonTest = mSeasonDao.findByName(TestUtil.TEST_SEASON);
        assertNotNull(seasonTest);
        assertTrue(seasonTest.isEmpty());
    }
}
