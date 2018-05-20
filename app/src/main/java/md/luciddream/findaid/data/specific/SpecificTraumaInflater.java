package md.luciddream.findaid.data.specific;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.helper.LocationHelper;
import md.luciddream.findaid.data.model.*;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class SpecificTraumaInflater extends SpecificHelper{
    Integer traumaId;
    String traumaName;
    public SpecificTraumaInflater(ExecutorService executorService, FindAidDatabase findAidDatabase, Integer traumaId, String traumaName) {
        super(executorService, findAidDatabase);
        this.traumaId = traumaId;
        this.traumaName = traumaName;
    }

    public SpecificTrauma inflate(){
        if(traumaId != null && traumaId != 0){
            return inflateById();
        }
        else if(traumaName != null && traumaName.length() != 0){
            return inflateByName();
        }
        else throw new RuntimeException("Trauma id or name should be provided.");
    }

    private SpecificTrauma inflateById() {
        Trauma trauma = traumaHelper.findById(traumaId);
        if(trauma == null) throw new RuntimeException("Trauma with id " + traumaId + " was not found.");
        SpecificTrauma specificTrauma = new SpecificTrauma();
        specificTrauma.setTrauma(trauma);

        List<Location> locationByTraumaId = traumaLocationHelper.getSecondByFirstId(traumaId);
        specificTrauma.setLocation(locationByTraumaId == null || locationByTraumaId.size() == 0? new Location(): locationByTraumaId.get(0));
        List<Organ> organByTraumaId = traumaOrganHelper.getSecondByFirstId(traumaId);
        specificTrauma.setOrgan(organByTraumaId == null || organByTraumaId.size() == 0? new Organ(): organByTraumaId.get(0));
        List<Season> seasonByTraumaId = traumaSeasonHelper.getSecondByFirstId(traumaId);
        specificTrauma.setSeason(seasonByTraumaId == null || seasonByTraumaId.size() == 0? new Season(): seasonByTraumaId.get(0));
        List<Symptom> symptomList = traumaSymptomHelper.getSecondByFirstId(traumaId);
        Symptom[] symptomArray = new Symptom[symptomList.size()];
        for(int i = 0; i < symptomList.size(); i++){
            symptomArray[i] = symptomList.get(i);
        }
        specificTrauma.setSymptoms(symptomArray);
        List<Step> stepList = traumaStepHelper.getSecondByFirstId(traumaId);
        Step[] stepArray = new Step[stepList.size()];
        for(int i = 0; i < stepList.size(); i++){
            stepArray[i] = stepList.get(i);
        }
        specificTrauma.setSteps(stepArray);

        return specificTrauma;
    }

    private SpecificTrauma inflateByName() {
        throw new UnsupportedOperationException();
//      return null;
    }

}
