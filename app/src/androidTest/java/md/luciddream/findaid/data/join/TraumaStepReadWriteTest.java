package md.luciddream.findaid.data.join;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.StepDao;
import md.luciddream.findaid.data.dao.TraumaDao;
import md.luciddream.findaid.data.dao.join.TraumaStepDao;
import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaStep;
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
public class TraumaStepReadWriteTest {
    private TraumaStepDao mDao;
    private StepDao mStepDao;
    private TraumaDao mTraumaDao;
    private FindAidDatabase mDb;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, FindAidDatabase.class).build();
        mDao = mDb.traumaStepDao();
        mStepDao = mDb.stepDao();
        mTraumaDao = mDb.traumaDao();
    }

    @After
    public void closeDb(){
        mDb.close();
    }

    @Test(expected = android.database.sqlite.SQLiteConstraintException.class)
    public void write_TraumaStep_AndGetException(){
        TraumaStep item = new TraumaStep();
        item.setSp_id(5);
        item.setT_id(5);
        mDao.insert(item);
    }

    @Test
    public void write_TraumaStep_AndReadInList(){
        Step step = new Step(null, TEST_ORGAN);
        Trauma trauma = new Trauma(null, TEST_TRAUMA, 3);
        mStepDao.insert(step);
        mTraumaDao.insert(trauma);

        TraumaStep item = new TraumaStep();
        item.setT_id(1);
        item.setSp_id(1);
        mDao.insert(item);
        List<TraumaStep> all = mDao.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertTrue(all.get(0).getSp_id() == item.getSp_id() && all.get(0).getT_id() == item.getT_id());
    }

    @Test
    public void write_TraumaStep_AndReadByStepId(){
        Step firstStep = new Step(null, TEST_ORGAN);
        Step secondStep = new Step(null, TEST_ORGAN);
        mStepDao.insert(firstStep, secondStep);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaStep firstTraumaStep = new TraumaStep(1,1); // should find
        TraumaStep secondTraumaStep = new TraumaStep(1,2); // should find
        TraumaStep thirdTraumaStep = new TraumaStep(2,1); // should not find
        mDao.insert(firstTraumaStep, secondTraumaStep, thirdTraumaStep);
        List<Trauma> traumasByStepId = mDao.getTraumasByStepId(1);
        for(Trauma trauma: traumasByStepId){
            Log.d("TraumaStepReadWriteTest.java:write_TraumaStep_AndReadByStepId.test",
                    trauma.getT_id() + ". " + trauma.getName());
        }
        assertNotNull(traumasByStepId);
        assertFalse(traumasByStepId.isEmpty());
        assertTrue(traumasByStepId.size() == 2);// found exactly two
        assertTrue(traumasByStepId.get(0).getT_id() == 1);//with exact values
        assertTrue(traumasByStepId.get(1).getT_id() == 2);
    }

    @Test
    public void write_TraumaStep_AndReadByStepName(){
        Step firstStep = new Step(null, TEST_ORGAN);
        Step secondStep = new Step(null, TEST_ORGAN + "Something");
        mStepDao.insert(firstStep, secondStep);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaStep firstTraumaStep = new TraumaStep(1,1); // should find
        TraumaStep secondTraumaStep = new TraumaStep(2,1); // should not find(because l_id = 2 -> different Step name
        TraumaStep thirdTraumaStep = new TraumaStep(2,2); // should not find
        mDao.insert(firstTraumaStep, secondTraumaStep, thirdTraumaStep);
        List<Trauma> traumasByStepId = mDao.getTraumasByStepName(TEST_ORGAN);
        for(Trauma trauma: traumasByStepId){
            Log.d("TraumaStepReadWriteTest.java:write_TraumaStep_AndReadByStepName.test",
                    trauma.getT_id() + ". " + trauma.getName());
        }
        assertNotNull(traumasByStepId);
        assertFalse(traumasByStepId.isEmpty());
        assertTrue(traumasByStepId.size() == 1);// found exactly one
    }
    //
    @Test
    public void write_TraumaStep_AndReadByTraumaId(){
        Step firstStep = new Step(null, TEST_ORGAN);
        Step secondStep = new Step(null, TEST_ORGAN);
        mStepDao.insert(firstStep, secondStep);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA, 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaStep firstTraumaStep = new TraumaStep(1,1); // should not find
        TraumaStep secondTraumaStep = new TraumaStep(1,2); // should find
        mDao.insert(firstTraumaStep, secondTraumaStep);
        List<Step> stepByTraumaId = mDao.getStepByTraumaId(2);
        for(Step step: stepByTraumaId){
            Log.d("TraumaStepReadWriteTest.java:write_TraumaStep_AndReadByTraumaId.test",
                    step.getSp_id() + ". " + step.getName());
        }
        assertNotNull(stepByTraumaId);
        assertFalse(stepByTraumaId.isEmpty());
        assertTrue(stepByTraumaId.size() == 1);// found exactly two
        assertTrue(stepByTraumaId.get(0).getSp_id() == 1);//with exact values
    }
    @Test
    public void write_TraumaStep_AndReadByTraumaName(){
        Step firstStep = new Step(null, TEST_ORGAN);
        Step secondStep = new Step(null, TEST_ORGAN );
        mStepDao.insert(firstStep, secondStep);

        Trauma firstTrauma = new Trauma(null, TEST_TRAUMA, 1);
        Trauma secondTrauma = new Trauma(null, TEST_TRAUMA+"Something", 2);

        mTraumaDao.insert(firstTrauma, secondTrauma);

        TraumaStep firstTraumaStep = new TraumaStep(1,1); // should find
        TraumaStep secondTraumaStep = new TraumaStep(2,2); // should not find(because l_id = 2 -> different Step name
        mDao.insert(firstTraumaStep, secondTraumaStep);
        List<Step> stepByTraumaName = mDao.getStepByTraumaName(TEST_TRAUMA);
        for(Step step: stepByTraumaName){
            Log.d("TraumaStepReadWriteTest.java:write_TraumaStep_AndReadByTraumaName.test",
                    step.getSp_id() + ". " + step.getName());
        }
        assertNotNull(stepByTraumaName);
        assertFalse(stepByTraumaName.isEmpty());
        assertTrue(stepByTraumaName.size() == 1);// found exactly one
    }

    @Test
    public void write_TraumaStep_AndReadOrdinallyInList_ByTraumaId(){
        Step firstStep  = new Step(null, "1. This step importance will be 9");
        Step secondStep = new Step(null, "2. This step importance will be 0");
        Step thirdStep  = new Step(null, "3. This step importance will be 2");
        Step fourthStep = new Step(null, "4. This step importance will be 5");
        mStepDao.insert(firstStep, secondStep, thirdStep, fourthStep);

        Trauma trauma = new Trauma(null, "SomeTrauma",3);
        mTraumaDao.insert(trauma);

        TraumaStep first = new TraumaStep(1, 1, 9);
        TraumaStep second = new TraumaStep(2, 1, 0);
        TraumaStep third = new TraumaStep(3, 1, 2);
        TraumaStep fourth = new TraumaStep(4, 1, 5);
        mDao.insert(first, second, third, fourth);

        List<Step> orderedStepByTraumaId = mDao.getOrderedStepByTraumaId(1);
        assertNotNull(orderedStepByTraumaId);
        assertTrue(orderedStepByTraumaId.size() == 4);
        for(Step step : orderedStepByTraumaId){
            Log.w("TraumaStepReadWriteTest.java:write_TraumaStep_AndWriteOrdinallyInList_ByTraumaId.test", step.getName());
        }
        int stepNameLength = firstStep.getName().length();
        assertTrue(orderedStepByTraumaId.get(0).getName().charAt(stepNameLength - 1) == '0');
        assertTrue(orderedStepByTraumaId.get(1).getName().charAt(stepNameLength - 1) == '2');
        assertTrue(orderedStepByTraumaId.get(2).getName().charAt(stepNameLength - 1) == '5');
        assertTrue(orderedStepByTraumaId.get(3).getName().charAt(stepNameLength - 1) == '9');

    }

    @Test
    public void write_TraumaStep_AndReadOrdinallyInList_ByTraumaName(){
        Step firstStep  = new Step(null, "1. This step importance will be 8");
        Step secondStep = new Step(null, "2. This step importance will be 1");
        Step thirdStep  = new Step(null, "3. This step importance will be 3");
        Step fourthStep = new Step(null, "4. This step importance will be 6");
        mStepDao.insert(firstStep, secondStep, thirdStep, fourthStep);

        Trauma trauma = new Trauma(null, "SomeTrauma",3);
        mTraumaDao.insert(trauma);

        TraumaStep first = new TraumaStep(1, 1, 8);
        TraumaStep second = new TraumaStep(2, 1, 1);
        TraumaStep third = new TraumaStep(3, 1, 3);
        TraumaStep fourth = new TraumaStep(4, 1, 6);
        mDao.insert(first, second, third, fourth);

        List<Step> orderedStepByTraumaId = mDao.getOrderedStepByTraumaName("SomeTrauma");
        assertNotNull(orderedStepByTraumaId);
        assertTrue(orderedStepByTraumaId.size() != 0);
        assertTrue(orderedStepByTraumaId.size() == 4);
        for(Step step : orderedStepByTraumaId){
            Log.w("TraumaStepReadWriteTest.java:write_TraumaStep_AndWriteOrdinallyInList_ByTraumaName.test", step.getName());
        }
        int stepNameLength = firstStep.getName().length();
        assertTrue(orderedStepByTraumaId.get(0).getName().charAt(stepNameLength - 1) == '1');
        assertTrue(orderedStepByTraumaId.get(1).getName().charAt(stepNameLength - 1) == '3');
        assertTrue(orderedStepByTraumaId.get(2).getName().charAt(stepNameLength - 1) == '6');
        assertTrue(orderedStepByTraumaId.get(3).getName().charAt(stepNameLength - 1) == '8');
    }
}
