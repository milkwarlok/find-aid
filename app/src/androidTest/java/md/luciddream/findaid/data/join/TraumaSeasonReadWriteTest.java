package md.luciddream.findaid.data.join;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.SeasonDao;
import md.luciddream.findaid.data.dao.TraumaDao;
import md.luciddream.findaid.data.dao.join.TraumaSeasonDao;
import md.luciddream.findaid.data.model.Season;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaSeason;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static md.luciddream.findaid.TestUtil.TEST_ORGAN;
import static md.luciddream.findaid.TestUtil.TEST_TRAUMA;

public class TraumaSeasonReadWriteTest {
    private TraumaSeasonDao mDao;
    private SeasonDao mSeasonDao;
    private TraumaDao mTraumaDao;
    private FindAidDatabase mDb;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mDao = mDb.traumaSeasonDao();
        mSeasonDao = mDb.seasonDao();
        mTraumaDao = mDb.traumaDao();
    }

    @After
    public void closeDb(){
        mDb.close();
    }

    @Test(expected = android.database.sqlite.SQLiteConstraintException.class)
    public void write_TraumaSeason_AndGetException(){
        TraumaSeason item = new TraumaSeason();
        item.setSn_id(5);
        item.setT_id(5);
        mDao.insert(item);
    }

    @Test
    public void write_TraumaSeason_AndReadInList(){
        Season season = new Season(null, TEST_ORGAN);
        Trauma trauma = new Trauma(null, TEST_TRAUMA, 3);
        mSeasonDao.insert(season);
        mTraumaDao.insert(trauma);

        TraumaSeason item = new TraumaSeason();
        item.setT_id(1);
        item.setSn_id(1);
        mDao.insert(item);
        List<TraumaSeason> all = mDao.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertTrue(all.get(0).getSn_id() == item.getSn_id() && all.get(0).getT_id() == item.getT_id());
    }

    @Test
    public void write_TraumaSeason_AndReadBySeasonId(){
        Season firstSeason = new Season(null, TEST_ORGAN);
        Season secondSeason = new Season(null, TEST_ORGAN);
        mSeasonDao.insert(firstSeason, secondSeason);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaSeason firstTraumaSeason = new TraumaSeason(1,1); // should find
        TraumaSeason secondTraumaSeason = new TraumaSeason(1,2); // should find
        TraumaSeason thirdTraumaSeason = new TraumaSeason(2,1); // should not find
        mDao.insert(firstTraumaSeason, secondTraumaSeason, thirdTraumaSeason);
        List<Trauma> traumasBySeasonId = mDao.getTraumasBySeasonId(1);
        for(Trauma trauma: traumasBySeasonId){
            Log.d("TraumaSeasonReadWriteTest.java:write_TraumaSeason_AndReadBySeasonId.test",
                    trauma.getT_id() + ". " + trauma.getName());
        }
        assertNotNull(traumasBySeasonId);
        assertFalse(traumasBySeasonId.isEmpty());
        assertTrue(traumasBySeasonId.size() == 2);// found exactly two
        assertTrue(traumasBySeasonId.get(0).getT_id() == 1);//with exact values
        assertTrue(traumasBySeasonId.get(1).getT_id() == 2);
    }
    @Test
    public void write_TraumaSeason_AndReadBySeasonName(){
        Season firstSeason = new Season(null, TEST_ORGAN);
        Season secondSeason = new Season(null, TEST_ORGAN + "Something");
        mSeasonDao.insert(firstSeason, secondSeason);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaSeason firstTraumaSeason = new TraumaSeason(1,1); // should find
        TraumaSeason secondTraumaSeason = new TraumaSeason(2,1); // should not find(because l_id = 2 -> different Season name
        TraumaSeason thirdTraumaSeason = new TraumaSeason(2,2); // should not find
        mDao.insert(firstTraumaSeason, secondTraumaSeason, thirdTraumaSeason);
        List<Trauma> traumasBySeasonId = mDao.getTraumasBySeasonName(TEST_ORGAN);
        for(Trauma trauma: traumasBySeasonId){
            Log.d("TraumaSeasonReadWriteTest.java:write_TraumaSeason_AndReadBySeasonName.test",
                    trauma.getT_id() + ". " + trauma.getName());
        }
        assertNotNull(traumasBySeasonId);
        assertFalse(traumasBySeasonId.isEmpty());
        assertTrue(traumasBySeasonId.size() == 1);// found exactly one
    }
    @Test
    public void write_TraumaSeason_AndReadByTraumaId(){
        Season firstSeason = new Season(null, TEST_ORGAN);
        Season secondSeason = new Season(null, TEST_ORGAN);
        mSeasonDao.insert(firstSeason, secondSeason);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaSeason firstTraumaSeason = new TraumaSeason(1,1); // should not find
        TraumaSeason secondTraumaSeason = new TraumaSeason(1,2); // should find
        mDao.insert(firstTraumaSeason, secondTraumaSeason);
        List<Season> seasonByTraumaId = mDao.getSeasonByTraumaId(2);
        for(Season season: seasonByTraumaId){
            Log.d("TraumaSeasonReadWriteTest.java:write_TraumaSeason_AndReadByTraumaId.test",
                    season.getSn_id() + ". " + season.getName());
        }
        assertNotNull(seasonByTraumaId);
        assertFalse(seasonByTraumaId.isEmpty());
        assertTrue(seasonByTraumaId.size() == 1);// found exactly two
        assertTrue(seasonByTraumaId.get(0).getSn_id() == 1);//with exact values
    }
    @Test
    public void write_TraumaSeason_AndReadByTraumaName(){
        Season firstSeason = new Season(null, TEST_ORGAN);
        Season secondSeason = new Season(null, TEST_ORGAN );
        mSeasonDao.insert(firstSeason, secondSeason);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA+"Something", 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaSeason firstTraumaSeason = new TraumaSeason(1,1); // should find
        TraumaSeason secondTraumaSeason = new TraumaSeason(2,2); // should not find(because l_id = 2 -> different Season name
        mDao.insert(firstTraumaSeason, secondTraumaSeason);
        List<Season> seasonByTraumaName = mDao.getSeasonByTraumaName(TEST_TRAUMA);
        for(Season season: seasonByTraumaName){
            Log.d("TraumaSeasonReadWriteTest.java:write_TraumaSeason_AndReadByTraumaName.test",
                    season.getSn_id() + ". " + season.getName());
        }
        assertNotNull(seasonByTraumaName);
        assertFalse(seasonByTraumaName.isEmpty());
        assertTrue(seasonByTraumaName.size() == 1);// found exactly one
    }
}
