package md.luciddream.findaid.data.specific;

import md.luciddream.findaid.data.FindAidDatabase;

import java.util.concurrent.ExecutorService;

public class SpecificDeleter extends SpecificHelper {
    public SpecificDeleter(ExecutorService executorService, FindAidDatabase findAidDatabase, SpecificTrauma specificTrauma) {
        super(executorService, findAidDatabase, specificTrauma);
    }

    public void delete(){
        if(traumaHelper.exists(specificTrauma.getTrauma().getName()))
            if(specificTrauma.getTrauma().getT_id() == null || specificTrauma.getTrauma().getT_id() == 0) throw new RuntimeException("Must specify trauma_id before deleting.");
            traumaHelper.delete(specificTrauma.trauma);
        }
}
