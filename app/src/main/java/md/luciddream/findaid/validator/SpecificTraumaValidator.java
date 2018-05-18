package md.luciddream.findaid.validator;

import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.specific.SpecificTrauma;

public class SpecificTraumaValidator {
    private SpecificTrauma specificTrauma;
    private ValidityMessages message;

    public SpecificTraumaValidator(SpecificTrauma specificTrauma) {
        this.specificTrauma = specificTrauma;
        message = ValidityMessages.IS_OK;
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
        boolean isNameValid = trauma.getName().matches("([A-Za-zА-Яа-я]{1,20} ?){1,5}");
        return isNameValid ;
    }

    private boolean areStepsValid(Step[] steps){
        for(int i = 0; i < steps.length; i++){
            if(!isStepValid(steps[i])) return false;
        }
        return true;
    }

    private boolean isStepValid(Step step) {
        boolean isNameValid = step.getName().matches("([A-Za-zА-Яа-я1-9]{1,20} ?){1,25}");
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
        boolean isNameValid = symptom.getName().matches("([A-Za-zА-Яа-я1-9]{1,20} ?){1,25}");
        return isNameValid;
    }

    public ValidityMessages getMessage() {
        return message;
    }
}
