package md.luciddream.findaid.data.join;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import md.luciddream.findaid.TestUtil;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.dao.TraumaDao;
import md.luciddream.findaid.data.dao.join.TraumaLocationDao;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaLocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static md.luciddream.findaid.TestUtil.TEST_LOCATION;
import static md.luciddream.findaid.TestUtil.TEST_TRAUMA;

public class TraumaLocationReadWriteTest {
    private TraumaLocationDao mDao;
    private LocationDao mLocationDao;
    private TraumaDao mTraumaDao;
    private FindAidDatabase mDb;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mDao = mDb.traumaLocationDao();
        mLocationDao = mDb.locationDao();
        mTraumaDao = mDb.traumaDao();
    }

    @After
    public void closeDb(){
        mDb.close();
    }

    @Test(expected = android.database.sqlite.SQLiteConstraintException.class)
    public void write_TraumaLocation_AndGetException(){
        TraumaLocation item = new TraumaLocation();
        item.setL_id(5);
        item.setT_id(5);
        mDao.insert(item);
    }

    @Test
    public void write_TraumaLocation_AndReadInList(){
        Location location = new Location(null, TEST_LOCATION);
        Trauma trauma = new Trauma(null, TEST_TRAUMA, 3);
        mLocationDao.insert(location);
        mTraumaDao.insert(trauma);

        TraumaLocation item = new TraumaLocation();
        item.setT_id(1);
        item.setL_id(1);
        mDao.insert(item);
        List<TraumaLocation> all = mDao.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertTrue(all.get(0).getL_id() == item.getL_id() && all.get(0).getT_id() == item.getT_id());
    }

    @Test
    public void write_TraumaLocation_AndReadByLocationId(){
        Location firstLocation = new Location(null, TEST_LOCATION);
        Location secondLocation = new Location(null, TEST_LOCATION);
        mLocationDao.insert(firstLocation, secondLocation);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaLocation firstTraumaLocation = new TraumaLocation(1,1); // should find
        TraumaLocation secondTraumaLocation = new TraumaLocation(1,2); // should find
        TraumaLocation thirdTraumaLocation = new TraumaLocation(2,1); // should not find
        mDao.insert(firstTraumaLocation, secondTraumaLocation, thirdTraumaLocation);
        List<Trauma> traumasByLocationId = mDao.getTraumasByLocationId(1);
        for(Trauma trauma: traumasByLocationId){
            Log.d("TraumaLocationReadWriteTest.java:write_TraumaLocation_AndReadByLocationId.test",
                    trauma.getT_id() + ". " + trauma.getName());
        }
        assertNotNull(traumasByLocationId);
        assertFalse(traumasByLocationId.isEmpty());
        assertTrue(traumasByLocationId.size() == 2);// found exactly two
        assertTrue(traumasByLocationId.get(0).getT_id() == 1);//with exact values
        assertTrue(traumasByLocationId.get(1).getT_id() == 2);
    }

    @Test
    public void write_TraumaLocation_AndReadByLocationName(){
        Location firstLocation = new Location(null, TEST_LOCATION);
        Location secondLocation = new Location(null, TEST_LOCATION + "Something");
        mLocationDao.insert(firstLocation, secondLocation);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaLocation firstTraumaLocation = new TraumaLocation(1,1); // should find
        TraumaLocation secondTraumaLocation = new TraumaLocation(2,1); // should not find(because l_id = 2 -> different Location name
        TraumaLocation thirdTraumaLocation = new TraumaLocation(2,2); // should not find
        mDao.insert(firstTraumaLocation, secondTraumaLocation, thirdTraumaLocation);
        List<Trauma> traumasByLocationId = mDao.getTraumasByLocationName(TEST_LOCATION);
        for(Trauma trauma: traumasByLocationId){
            Log.d("TraumaLocationReadWriteTest.java:write_TraumaLocation_AndReadByLocationName.test",
                    trauma.getT_id() + ". " + trauma.getName());
        }
        assertNotNull(traumasByLocationId);
        assertFalse(traumasByLocationId.isEmpty());
        assertTrue(traumasByLocationId.size() == 1);// found exactly one
    }

    @Test
    public void write_TraumaLocation_AndReadByTraumaId(){
        Location firstLocation = new Location(null, TEST_LOCATION);
        Location secondLocation = new Location(null, TEST_LOCATION);
        mLocationDao.insert(firstLocation, secondLocation);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaLocation firstTraumaLocation = new TraumaLocation(1,1); // should not find
        TraumaLocation secondTraumaLocation = new TraumaLocation(1,2); // should find
        mDao.insert(firstTraumaLocation, secondTraumaLocation);
        List<Location> locationByTraumaId = mDao.getLocationByTraumaId(2);
        for(Location location: locationByTraumaId){
            Log.d("TraumaLocationReadWriteTest.java:write_TraumaLocation_AndReadByTraumaId.test",
                    location.getL_id() + ". " + location.getName());
        }
        assertNotNull(locationByTraumaId);
        assertFalse(locationByTraumaId.isEmpty());
        assertTrue(locationByTraumaId.size() == 1);// found exactly two
        assertTrue(locationByTraumaId.get(0).getL_id() == 1);//with exact values
    }
    @Test
    public void write_TraumaLocation_AndReadByTraumaName(){
        Location firstLocation = new Location(null, TEST_LOCATION);
        Location secondLocation = new Location(null, TEST_LOCATION );
        mLocationDao.insert(firstLocation, secondLocation);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA+"Something", 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaLocation firstTraumaLocation = new TraumaLocation(1,1); // should find
        TraumaLocation secondTraumaLocation = new TraumaLocation(2,2); // should not find(because l_id = 2 -> different Location name
        mDao.insert(firstTraumaLocation, secondTraumaLocation);
        List<Location> locationByTraumaName = mDao.getLocationByTraumaName(TEST_TRAUMA);
        for(Location location: locationByTraumaName){
            Log.d("TraumaLocationReadWriteTest.java:write_TraumaLocation_AndReadByTraumaName.test",
                    location.getL_id() + ". " + location.getName());
        }
        assertNotNull(locationByTraumaName);
        assertFalse(locationByTraumaName.isEmpty());
        assertTrue(locationByTraumaName.size() == 1);// found exactly one
    }
}
