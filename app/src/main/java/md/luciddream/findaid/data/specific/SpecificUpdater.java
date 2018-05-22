package md.luciddream.findaid.data.specific;

import android.util.Log;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.helper.LocationHelper;
import md.luciddream.findaid.data.model.*;
import md.luciddream.findaid.data.model.join.TraumaLocation;
import md.luciddream.findaid.data.model.join.TraumaStep;
import md.luciddream.findaid.data.model.join.TraumaSymptom;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class SpecificUpdater extends SpecificHelper {
    public SpecificUpdater(ExecutorService executorService, FindAidDatabase findAidDatabase, SpecificTrauma specificTrauma) {
        super(executorService, findAidDatabase, specificTrauma);
    }
//todo refactor this method
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
        //todo::----------------------symptomUpdate
        Symptom[] originalSymptoms = originalTrauma.getSymptoms();
        Symptom[] currentSymptoms = specificTrauma.getSymptoms();
        String[] originalSymptomNames = new String[originalSymptoms.length];
        String[] currentSymptomNames = new String[currentSymptoms.length];

        for(int i = 0; i < originalSymptoms.length; i++){
            originalSymptomNames[i] = originalSymptoms[i].getName();
        }
        for(int i = 0; i < currentSymptoms.length; i++){
            currentSymptomNames[i] = currentSymptoms[i].getName();
        }
        List<String> symptomIntersection = new ArrayList<>(Arrays.asList(originalSymptomNames));
        symptomIntersection.retainAll(Arrays.asList(currentSymptomNames));

        List<String> symptomNamesToDelete = new ArrayList<>(Arrays.asList(originalSymptomNames));
        symptomNamesToDelete.removeAll(symptomIntersection);

        List<String> symptomNamesToAdd = new ArrayList<>(Arrays.asList(currentSymptomNames));
        symptomNamesToAdd.removeAll(symptomIntersection);
        loggingLists(symptomNamesToDelete, symptomNamesToAdd);

        for(int i = 0; i < symptomNamesToDelete.size(); i++){
            List<Symptom> temp = symptomHelper.findByName(symptomNamesToDelete.get(i));
            if(temp != null && temp.size() != 0){
                traumaSymptomHelper.delete(new TraumaSymptom(
                        temp.get(0).getSm_id(),
                        specificTrauma.getTrauma().getT_id()
                        ));
            }
        }

        for(int i = 0; i < symptomNamesToAdd.size(); i++){
            List<Symptom> temp = symptomHelper.findByName(symptomNamesToAdd.get(i));
            if(temp != null && temp.size() != 0){
                traumaSymptomHelper.insert(new TraumaSymptom(
                        temp.get(0).getSm_id(),
                        specificTrauma.getTrauma().getT_id()
                ));
            }
            else{
                symptomHelper.insert(new Symptom(null, symptomNamesToAdd.get(i)));
                temp = symptomHelper.findByName(symptomNamesToAdd.get(i));
                if(temp != null && temp.size() != 0) {
                    traumaSymptomHelper.insert(new TraumaSymptom(
                            temp.get(0).getSm_id(),
                            specificTrauma.getTrauma().getT_id()
                    ));
                }
            }
        }
        //todo::----------------------steps Update
        Step[] originalSteps = originalTrauma.getSteps();
        Step[] currentSteps = specificTrauma.getSteps();
        String[] originalStepNames = new String[originalSteps.length];
        String[] currentStepNames = new String[currentSteps.length];

        for(int i = 0; i < originalSteps.length; i++){
            originalStepNames[i] = originalSteps[i].getName();
        }
        for(int i = 0; i < currentSteps.length; i++){
            currentStepNames[i] = currentSteps[i].getName();
        }
        List<String> stepIntersection = new ArrayList<>(Arrays.asList(originalStepNames));
        stepIntersection.retainAll(Arrays.asList(currentStepNames));

        List<String> stepNamesToDelete = new ArrayList<>(Arrays.asList(originalStepNames));
        stepNamesToDelete.removeAll(stepIntersection);

        List<String> stepNamesToAdd = new ArrayList<>(Arrays.asList(currentStepNames));
        stepNamesToAdd.removeAll(stepIntersection);
        loggingLists(stepNamesToDelete, stepNamesToAdd);

        for(int i = 0; i < stepNamesToDelete.size(); i++){
            List<Step> temp = stepHelper.findByName(stepNamesToDelete.get(i));
            if(temp != null && temp.size() != 0){
                traumaStepHelper.delete(new TraumaStep(
                        temp.get(0).getSp_id(),
                        specificTrauma.getTrauma().getT_id()
                ));
            }
        }

        for(int i = 0; i < stepNamesToAdd.size(); i++){
            List<Step> temp = stepHelper.findByName(stepNamesToAdd.get(i));
            if(temp != null && temp.size() != 0){
                traumaStepHelper.insert(new TraumaStep(
                        temp.get(0).getSp_id(),
                        specificTrauma.getTrauma().getT_id(),
                        specificTrauma.getStepOrder().get(stepNamesToAdd.get(i))
                ));
            }
            else{
                stepHelper.insert(new Step(null, stepNamesToAdd.get(i)));
                temp = stepHelper.findByName(stepNamesToAdd.get(i));
                if(temp != null && temp.size() != 0) {
                    traumaStepHelper.insert(new TraumaStep(
                            temp.get(0).getSp_id(),
                            specificTrauma.getTrauma().getT_id(),
                            specificTrauma.getStepOrder().get(stepNamesToAdd.get(i))
                    ));
                }
            }
        }
        List<Step> allSteps = traumaStepHelper.getSecondByFirstId(specificTrauma.getTrauma().getT_id());
        for(int i = 0; i < allSteps.size(); i++){
            traumaStepHelper.update(new TraumaStep(
                    allSteps.get(i).getSp_id(),
                    specificTrauma.getTrauma().getT_id(),
                    specificTrauma.getStepOrder().get(allSteps.get(i).getName())
            ));
        }

    }

    private void loggingLists(List<String> symptomNamesToDelete, List<String> symptomNamesToAdd) {
        if(symptomNamesToDelete.size() == 0) Log.d("toDelete", "NOTHING");
        for(int i = 0; i < symptomNamesToDelete.size(); i++){
            Log.d("toDelete", symptomNamesToDelete.get(i));
        }

        if(symptomNamesToAdd.size() == 0) Log.d("toAdd", "NOTHING");
        for(int i = 0; i < symptomNamesToAdd.size(); i++){
            Log.d("toAdd", symptomNamesToAdd.get(i));
        }
    }
}
