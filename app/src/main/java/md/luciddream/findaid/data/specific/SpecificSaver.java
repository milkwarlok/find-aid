package md.luciddream.findaid.data.specific;

import android.arch.persistence.room.Transaction;
import android.util.Log;
import junit.framework.Assert;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.model.*;
import md.luciddream.findaid.data.model.join.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class SpecificSaver extends SpecificHelper {

    public SpecificSaver(ExecutorService executorService, SpecificTrauma specificTrauma, FindAidDatabase findAidDatabase) {
        super(executorService, specificTrauma, findAidDatabase);
    }

    @Transaction
    public void save(){
        if(traumaHelper.exists(specificTrauma.getTrauma().getName())){
            return;
        }
        saveTraumaName();
        int traumaId = getTraumaId();
        int locationId = getLocationId();
        saveTraumaLocationByIds(locationId, traumaId);
        int organId = getOrganId();
        saveTraumaOrganByIds(organId, traumaId);
        int seasonId = getSeasonId();
        saveTraumaSeasonByIds(seasonId, traumaId);

        saveSymptoms();
        int[] symptomIds = getSymptomsIds();
        saveTraumaSymptomsByIds(symptomIds, traumaId);

        saveSteps();
        List<Step> steps = getSteps();
        saveTraumaSteps(steps, traumaId);
    }



    private void saveTraumaName() {
        if(!traumaHelper.exists(specificTrauma.getTrauma().getName())){
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

    private void saveTraumaLocationByIds(int locationId, int traumaId){
        Log.d("Ids: ", "traumaId: "+ traumaId);
        Log.d("Ids: ", "locationId: "+ locationId);
        traumaLocationHelper.insert(new TraumaLocation(locationId, traumaId));
    }
    private int getOrganId() {
        List<Organ> organList = organHelper.findByName(specificTrauma.getOrgan().getName());
        int o_id;
        if(organList!= null && !organList.isEmpty())
            o_id = organList.get(0).getO_id();
        else
            o_id = -1;
        return o_id;
    }

    private void saveTraumaOrganByIds(int organId, int traumaId) {
        Log.d("Ids: ", "traumaId: "+ traumaId);
        Log.d("Ids: ", "organId: "+ organId);
        traumaOrganHelper.insert(new TraumaOrgan(organId, traumaId));
    }


    private int getSeasonId() {
        List<Season> seasonList = seasonHelper.findByName(specificTrauma.getSeason().getName());
        int sn_id;
        if(seasonList!= null && !seasonList.isEmpty())
            sn_id = seasonList.get(0).getSn_id();
        else
            sn_id = -1;
        return sn_id;
    }

    private void saveTraumaSeasonByIds(int seasonId, int traumaId) {
        Log.d("Ids: ", "traumaId: "+ traumaId);
        Log.d("Ids: ", "seasonId: "+ seasonId);
        traumaSeasonHelper.insert(new TraumaSeason(seasonId, traumaId));
    }

    private void saveSymptoms(){
        Symptom[] symptoms = specificTrauma.getSymptoms();
        for(int i = 0; i < symptoms.length; i++){
            if(!symptomHelper.exists(symptoms[i].getName())){
                symptomHelper.insert(symptoms[i]);
            }
        }
    }

    private int[] getSymptomsIds(){
        Symptom[] symptoms = specificTrauma.getSymptoms();
        int[] ids = new int[symptoms.length];
        String[] symptomStrings = new String[symptoms.length];
        for(int i = 0; i < symptoms.length; i++){
            symptomStrings[i] = symptoms[i].getName();
        }
        List<Symptom> symptomList = symptomHelper.findByNames(symptomStrings);
        Assert.assertEquals(symptomList.size(), ids.length);
        for(int i = 0;i < ids.length; i++){
            ids[i] = symptomList.get(i).getSm_id();
        }
        return ids;
    }

    private void saveTraumaSymptomsByIds(int[] symptomIds, int traumaId){
        for(int i = 0; i < symptomIds.length; i++){
            traumaSymptomHelper.insert(new TraumaSymptom(symptomIds[i], traumaId));
        }
    }


    private void saveSteps(){
        Step[] steps = specificTrauma.getSteps();
        for(int i = 0; i < steps.length; i++){
            if(!stepHelper.exists(steps[i].getName())){
                stepHelper.insert(steps[i]);
            }
        }
    }

    private List<Step> getSteps(){
        Step[] steps = specificTrauma.getSteps();
        int[] ids = new int[steps.length];
        String[] stepStrings = new String[steps.length];
        for(int i = 0; i < steps.length; i++){
            stepStrings[i] = steps[i].getName();
        }
        List<Step> stepList = stepHelper.findByNames(stepStrings);
        Assert.assertEquals(stepList.size(), ids.length);
        return stepList;
    }

    private void saveTraumaSteps(List<Step> step, int traumaId){
        Map<String, Integer> stepOrder = specificTrauma.getStepOrder();
        for(int i = 0; i < step.size(); i++){
            traumaStepHelper.insert(new TraumaStep(step.get(i).getSp_id(),
                    traumaId, stepOrder.get(step.get(i).getName())));
        }
    }
}
