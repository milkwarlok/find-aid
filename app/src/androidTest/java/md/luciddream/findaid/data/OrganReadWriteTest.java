package md.luciddream.findaid.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import md.luciddream.findaid.data.dao.OrganDao;
import md.luciddream.findaid.data.model.Organ;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class OrganReadWriteTest {
    
    private OrganDao mOrganDao;
    private FindAidDatabase mDb;
    
    @Before
    public  void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mOrganDao = mDb.organDao();
    }

    @After
    public void closeDB(){
        if(mDb != null) mDb.close();
    }
    @Test
    public void writeOrganAndReadInList(){
        Organ organ = new Organ();
        organ.setO_id(1);
        organ.setName("Skin");
        mOrganDao.insert(organ);
        List<Organ> organSkin = mOrganDao.findByName("Skin");
        assertNotNull(organSkin);
        assertTrue(!organSkin.isEmpty());
        assertTrue(organSkin.get(0).getName().equals(organ.getName()));
    }

    @Test
    public void deleteOrgan(){
        Organ organ = new Organ();
        organ.setO_id(1);
        organ.setName("Skin");
        mOrganDao.insert(organ);
        mOrganDao.delete(organ);
        List<Organ> skinOrgan = mOrganDao.findByName("Skin");
        assertNotNull(skinOrgan);
        assertTrue(skinOrgan.isEmpty());
    }
}
