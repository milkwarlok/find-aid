package md.luciddream.findaid;

import android.arch.persistence.room.Room;
import android.content.Context;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.model.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import java.util.List;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class LocationReadWriteTest {
    private LocationDao mLocationDao;
    private FindAidDatabase mDb;

    @Before
    public void createDB(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mLocationDao = mDb.locationDao();
    }

    @After
    public void closeDB(){
        mDb.close();
    }

    @Test
    public void writeLocationAndReadInList(){
        Location location = new Location();
        location.setL_id(1);
        location.setName("Seashore");
        mLocationDao.insertAll(location);
        List<Location> seashoreLocation = mLocationDao.findByName("Seashore");
        assertNotNull(seashoreLocation);
        assertTrue(location.getName().equals(seashoreLocation.get(0).getName()));
    }
}
