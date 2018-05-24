package md.luciddream.findaid.validator;

import android.content.Context;
import md.luciddream.findaid.R;
import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.specific.SpecificTrauma;

import java.util.HashSet;
import java.util.Set;

public class SpecificTraumaValidator {
    private SpecificTrauma specificTrauma;
    private ValidityMessages message;
    Context context;
    public SpecificTraumaValidator(SpecificTrauma specificTrauma, Context context) {
        this.specificTrauma = specificTrauma;
        message = ValidityMessages.IS_OK;
        this.context = context;
    }

    public boolean isValid(){
        boolean isValid = true;
        isValid = isTraumaValid(specificTrauma.getTrauma());
        if(!isValid){
            message = ValidityMessages.INVALID_NAME;
            return isValid;

        }
        isValid = areSymptomsValid(specificTrauma.getSymptoms());
        if(!isValid){
            message = ValidityMessages.INVALID_SYMPTOMS;
            return isValid;
        }
        isValid = areStepsValid(specificTrauma.getSteps());
        if(!isValid){
            message = ValidityMessages.INVALID_STEPS;
            return isValid;
        }
        return isValid;
    }

    private boolean isTraumaValid(Trauma trauma){
        boolean isNameValid = trauma.getName().matches(context.getString(R.string.trauma_name_regex));
        return isNameValid ;
    }

    private boolean areStepsValid(Step[] steps){
        for(int i = 0; i < steps.length; i++){
            if(!isStepValid(steps[i])) return false;
        }
//        Set<Step> stepSet = new HashSet<>();
//        for(int i = 0; i < steps.length; i++){
//            int sizeBefore = stepSet.size();
//            stepSet.add(steps[i]);
//            int sizeAfter = stepSet.size();
//            if(sizeAfter == sizeBefore) return false;
//        }
        return true;
    }

    private boolean isStepValid(Step step) {
        boolean isNameValid = step.getName().matches(context.getResources().getString(R.string.valid_symptom_step_regex));
        return isNameValid;
    }

    private boolean areSymptomsValid(Symptom[] symptoms){
        boolean areSymptomsValid = true;
        for(int i = 0; i < symptoms.length; i++){
            areSymptomsValid = areSymptomsValid && isSymptomValid(symptoms[i]);
        }
        return areSymptomsValid;
    }

    private boolean isSymptomValid(Symptom symptom) {
        boolean isNameValid = symptom.getName().matches(context.getResources().getString(R.string.valid_symptom_step_regex));
        return isNameValid;
    }

    public ValidityMessages getMessage() {
        return message;
    }
}
