package md.luciddream.findaid.data.specific;

import android.util.Log;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.helper.LocationHelper;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Organ;
import md.luciddream.findaid.data.model.Season;
import md.luciddream.findaid.data.model.join.TraumaLocation;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

public class SpecificUpdater extends SpecificHelper {
    public SpecificUpdater(ExecutorService executorService, FindAidDatabase findAidDatabase, SpecificTrauma specificTrauma) {
        super(executorService, findAidDatabase, specificTrauma);
    }

    public void update(){
        SpecificTraumaInflater traumaInflater = new SpecificTraumaInflater(executorService, findAidDatabase,
                specificTrauma.getTrauma().getT_id(), specificTrauma.getTrauma().getName());
        SpecificTrauma originalTrauma = traumaInflater.inflate();
        if(!originalTrauma.getLocation().getName().equals(specificTrauma.getLocation().getName())){
            Location location = locationHelper.findByName(specificTrauma.getLocation().getName()).get(0);
            traumaLocationHelper.updateSecondByFirstId(originalTrauma.getTrauma().getT_id(), location.getL_id());
        }

        if(!originalTrauma.getOrgan().getName().equals(specificTrauma.getOrgan().getName())){
            Organ organ = organHelper.findByName(specificTrauma.getOrgan().getName()).get(0);
            traumaOrganHelper.updateSecondByFirstId(originalTrauma.getTrauma().getT_id(), organ.getO_id());
        }

        if(!originalTrauma.getSeason().getName().equals(specificTrauma.getSeason().getName())){
            Season season = seasonHelper.findByName(specificTrauma.getSeason().getName()).get(0);
            traumaSeasonHelper.updateSecondByFirstId(originalTrauma.getTrauma().getT_id(), season.getSn_id());
        }

    }
}
