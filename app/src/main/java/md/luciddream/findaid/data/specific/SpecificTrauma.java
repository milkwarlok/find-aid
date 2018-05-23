package md.luciddream.findaid.data.specific;

import md.luciddream.findaid.data.model.*;

import java.util.Map;

public class SpecificTrauma {
         Location location;
         Organ organ;
         Season season;
         Step[] steps;
         Symptom[] symptoms;
         Trauma trauma;
         Map<String, Integer> stepOrder;

    public SpecificTrauma() {
    }

    public SpecificTrauma(Location location, Organ organ, Season season, Step[] steps, Symptom[] symptoms, Trauma trauma) {
        this.location = location;
        this.organ = organ;
        this.season = season;
        this.steps = steps;
        this.symptoms = symptoms;
        this.trauma = trauma;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Organ getOrgan() {
        return organ;
    }

    public void setOrgan(Organ organ) {
        this.organ = organ;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public Symptom[] getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Symptom[] symptoms) {
        this.symptoms = symptoms;
    }

    public Trauma getTrauma() {
        return trauma;
    }

    public void setTrauma(Trauma trauma) {
        this.trauma = trauma;
    }

    public Map<String, Integer> getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Map<String, Integer> stepOrder) {
        this.stepOrder = stepOrder;
    }
}
