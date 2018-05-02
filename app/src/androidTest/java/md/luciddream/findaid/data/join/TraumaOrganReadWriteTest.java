package md.luciddream.findaid.data.join;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.OrganDao;
import md.luciddream.findaid.data.dao.TraumaDao;
import md.luciddream.findaid.data.dao.join.TraumaOrganDao;
import md.luciddream.findaid.data.model.Organ;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaOrgan;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static md.luciddream.findaid.TestUtil.TEST_ORGAN;
import static md.luciddream.findaid.TestUtil.TEST_TRAUMA;

@RunWith(AndroidJUnit4.class)
public class TraumaOrganReadWriteTest {
    private TraumaOrganDao mDao;
    private OrganDao mOrganDao;
    private TraumaDao mTraumaDao;
    private FindAidDatabase mDb;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mDao = mDb.traumaOrganDao();
        mOrganDao = mDb.organDao();
        mTraumaDao = mDb.traumaDao();
    }

    @After
    public void closeDb(){
        mDb.close();
    }

    @Test(expected = android.database.sqlite.SQLiteConstraintException.class)
    public void write_TraumaOrgan_AndGetException(){
        TraumaOrgan item = new TraumaOrgan();
        item.setO_id(5);
        item.setT_id(5);
        mDao.insert(item);
    }

    @Test
    public void write_TraumaOrgan_AndReadInList(){
        Organ organ = new Organ(null, TEST_ORGAN);
        Trauma trauma = new Trauma(null, TEST_TRAUMA, 3);
        mOrganDao.insert(organ);
        mTraumaDao.insert(trauma);

        TraumaOrgan item = new TraumaOrgan();
        item.setT_id(1);
        item.setO_id(1);
        mDao.insert(item);
        List<TraumaOrgan> all = mDao.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertTrue(all.get(0).getO_id() == item.getO_id() && all.get(0).getT_id() == item.getT_id());
    }

    @Test
    public void write_TraumaOrgan_AndReadByOrganId(){
        Organ firstOrgan = new Organ(null, TEST_ORGAN);
        Organ secondOrgan = new Organ(null, TEST_ORGAN);
        mOrganDao.insert(firstOrgan, secondOrgan);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaOrgan firstTraumaOrgan = new TraumaOrgan(1,1); // should find
        TraumaOrgan secondTraumaOrgan = new TraumaOrgan(1,2); // should find
        TraumaOrgan thirdTraumaOrgan = new TraumaOrgan(2,1); // should not find
        mDao.insert(firstTraumaOrgan, secondTraumaOrgan, thirdTraumaOrgan);
        List<Trauma> traumasByOrganId = mDao.getTraumasByOrganId(1);
        for(Trauma trauma: traumasByOrganId){
            Log.d("TraumaOrganReadWriteTest.java:write_TraumaOrgan_AndReadByOrganId.test",
                    trauma.getT_id() + ". " + trauma.getName());
        }
        assertNotNull(traumasByOrganId);
        assertFalse(traumasByOrganId.isEmpty());
        assertTrue(traumasByOrganId.size() == 2);// found exactly two
        assertTrue(traumasByOrganId.get(0).getT_id() == 1);//with exact values
        assertTrue(traumasByOrganId.get(1).getT_id() == 2);
    }

    @Test
    public void write_TraumaOrgan_AndReadByOrganName(){
        Organ firstOrgan = new Organ(null, TEST_ORGAN);
        Organ secondOrgan = new Organ(null, TEST_ORGAN + "Something");
        mOrganDao.insert(firstOrgan, secondOrgan);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaOrgan firstTraumaOrgan = new TraumaOrgan(1,1); // should find
        TraumaOrgan secondTraumaOrgan = new TraumaOrgan(2,1); // should not find(because l_id = 2 -> different Organ name
        TraumaOrgan thirdTraumaOrgan = new TraumaOrgan(2,2); // should not find
        mDao.insert(firstTraumaOrgan, secondTraumaOrgan, thirdTraumaOrgan);
        List<Trauma> traumasByOrganId = mDao.getTraumasByOrganName(TEST_ORGAN);
        for(Trauma trauma: traumasByOrganId){
            Log.d("TraumaOrganReadWriteTest.java:write_TraumaOrgan_AndReadByOrganName.test",
                    trauma.getT_id() + ". " + trauma.getName());
        }
        assertNotNull(traumasByOrganId);
        assertFalse(traumasByOrganId.isEmpty());
        assertTrue(traumasByOrganId.size() == 1);// found exactly one
    }
//
    @Test
    public void write_TraumaOrgan_AndReadByTraumaId(){
        Organ firstOrgan = new Organ(null, TEST_ORGAN);
        Organ secondOrgan = new Organ(null, TEST_ORGAN);
        mOrganDao.insert(firstOrgan, secondOrgan);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaOrgan firstTraumaOrgan = new TraumaOrgan(1,1); // should not find
        TraumaOrgan secondTraumaOrgan = new TraumaOrgan(1,2); // should find
        mDao.insert(firstTraumaOrgan, secondTraumaOrgan);
        List<Organ> organByTraumaId = mDao.getOrganByTraumaId(2);
        for(Organ organ: organByTraumaId){
            Log.d("TraumaOrganReadWriteTest.java:write_TraumaOrgan_AndReadByTraumaId.test",
                    organ.getO_id() + ". " + organ.getName());
        }
        assertNotNull(organByTraumaId);
        assertFalse(organByTraumaId.isEmpty());
        assertTrue(organByTraumaId.size() == 1);// found exactly two
        assertTrue(organByTraumaId.get(0).getO_id() == 1);//with exact values
    }
    @Test
    public void write_TraumaOrgan_AndReadByTraumaName(){
        Organ firstOrgan = new Organ(null, TEST_ORGAN);
        Organ secondOrgan = new Organ(null, TEST_ORGAN );
        mOrganDao.insert(firstOrgan, secondOrgan);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA+"Something", 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaOrgan firstTraumaOrgan = new TraumaOrgan(1,1); // should find
        TraumaOrgan secondTraumaOrgan = new TraumaOrgan(2,2); // should not find(because l_id = 2 -> different Organ name
        mDao.insert(firstTraumaOrgan, secondTraumaOrgan);
        List<Organ> organByTraumaName = mDao.getOrganByTraumaName(TEST_TRAUMA);
        for(Organ organ: organByTraumaName){
            Log.d("TraumaOrganReadWriteTest.java:write_TraumaOrgan_AndReadByTraumaName.test",
                    organ.getO_id() + ". " + organ.getName());
        }
        assertNotNull(organByTraumaName);
        assertFalse(organByTraumaName.isEmpty());
        assertTrue(organByTraumaName.size() == 1);// found exactly one
    }
}
