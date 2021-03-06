package md.luciddream.findaid;

import android.support.annotation.NonNull;
import md.luciddream.findaid.data.model.*;

public class TestUtil {

    public static final String TEST_LOCATION = "Море";
    public static final String TEST_ORGAN = "Тело";
    public static final String TEST_SEASON = "Лето";
    public static final String TEST_STEP = "Позвоните в скорую";
    public static final String TEST_SYMPTOM = "Головокружение";
    public static final String TEST_TRAUMA = "Солнечный удар";
    //never set TEST_ID to null, it will lead to difference insert and select values in tests.
    public static final Integer TEST_ID = 1;
    public static final Integer TEST_RELEVANCE = 2;

    public static Location createLocation(){
        Location item = new Location();
        item.setL_id(TEST_ID);
        item.setName(TEST_LOCATION);
        return item;
    }
    public static Organ createOrgan(){
        Organ item = new Organ();
        item.setO_id(TEST_ID);
        item.setName(TEST_ORGAN);
        return item;
    }
    public static Season createSeason(){
        Season item = new Season();
        item.setSn_id(TEST_ID);
        item.setName(TEST_SEASON);
        return item;
    }
    public static Step createStep(){
        Step item = new Step();
        item.setSp_id(TEST_ID);
        item.setName(TEST_STEP);
        return item;
    }
    public static Symptom createSymptom(){
        Symptom item = new Symptom();
        item.setSm_id(TEST_ID);
        item.setName(TEST_SYMPTOM);
        return item;
    }
    public static Trauma createTrauma(){
        Trauma item = new Trauma();
        item.setT_id(TEST_ID);
        item.setName(TEST_TRAUMA);
        item.setRelevance(TEST_RELEVANCE);
        return item;
    }
}
