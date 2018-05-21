package md.luciddream.findaid.data.specific;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.model.join.TraumaLocation;

import java.util.concurrent.ExecutorService;

public class SpecificUpdater extends SpecificHelper {
    public SpecificUpdater(ExecutorService executorService, FindAidDatabase findAidDatabase, SpecificTrauma specificTrauma) {
        super(executorService, findAidDatabase, specificTrauma);
    }

    public void update(){
        traumaLocationHelper.delete(new TraumaLocation(
                specificTrauma.getTrauma().getT_id(),
                specificTrauma.getLocation().getL_id()
        ));

    }

}
