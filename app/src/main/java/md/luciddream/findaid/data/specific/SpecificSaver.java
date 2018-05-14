package md.luciddream.findaid.data.specific;

import android.arch.persistence.room.Transaction;
import android.util.Log;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.dao.TraumaDao;
import md.luciddream.findaid.data.helper.LocationHelper;
import md.luciddream.findaid.data.helper.TraumaHelper;
import md.luciddream.findaid.data.helper.join.TraumaLocationHelper;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaLocation;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpecificSaver {
    ExecutorService executorService;
    SpecificTrauma specificTrauma;
    FindAidDatabase findAidDatabase;

    private TraumaHelper traumaHelper;
    private LocationHelper locationHelper;

    private TraumaLocationHelper traumaLocationHelper;

    public SpecificSaver(ExecutorService executorService, SpecificTrauma specificTrauma, FindAidDatabase findAidDatabase) {
        this.executorService = executorService;
        this.specificTrauma = specificTrauma;
        this.findAidDatabase = findAidDatabase;
        setUp();
    }

    private void setUp() {
        this.traumaHelper = new TraumaHelper(executorService,findAidDatabase.traumaDao());
        this.locationHelper = new LocationHelper(executorService, findAidDatabase.locationDao());
        this.traumaLocationHelper = new TraumaLocationHelper(executorService, findAidDatabase.traumaLocationDao());
    }

//    @Transaction
    public void save(){
        saveTraumaName();
        int traumaId = getTraumaId();
        int locationId = getLocationId();
        saveTraumaLocationByIds(traumaId, locationId);
    }

    private void saveTraumaName() {
        List<Trauma> traumaCheckList = traumaHelper.findByName(specificTrauma.getTrauma().getName());
        if(traumaCheckList.isEmpty()){
            traumaHelper.insert(specificTrauma.getTrauma());
        }
    }

    private int getTraumaId(){
        List<Trauma> traumaList = traumaHelper.findByName(specificTrauma.getTrauma().getName());
        int t_id;
        if(traumaList!= null && !traumaList.isEmpty())
            t_id = traumaList.get(0).getT_id();
        else
            t_id = -1;
        return t_id;
    }

    private int getLocationId(){
        List<Location> locationList = locationHelper.findByName(specificTrauma.getLocation().getName());
        int l_id;
        if(locationList!= null && !locationList.isEmpty())
            l_id = locationList.get(0).getL_id();
        else
            l_id = -1;
        return l_id;
    }

    private void saveTraumaLocationByIds(int traumaId, int locationId){
        Log.d("Ids: ", "traumaId: "+ traumaId);
        Log.d("Ids: ", "locationId: "+ locationId);
        traumaLocationHelper.insert(new TraumaLocation(locationId, traumaId));
    }


}
