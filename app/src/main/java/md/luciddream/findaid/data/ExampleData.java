package md.luciddream.findaid.data;

import android.util.Pair;
import md.luciddream.findaid.data.model.*;
import md.luciddream.findaid.data.model.join.*;

import java.util.Arrays;
import java.util.List;

public class ExampleData {
    Location[] locations;
    Organ[] organs;
    Season[] seasons;
    Step[] steps;
    Symptom[] symptoms;
    Trauma[] traumas;
    TraumaLocation[] traumaLocations;
    TraumaOrgan[] traumaOrgans;
    TraumaSeason[] traumaSeasons;
    TraumaStep[] traumaSteps;
    TraumaSymptom[] traumaSymptoms;

    ExampleData() {
        locations = new Location[]{new Location(null, "Mountains"),
                new Location(null, "Seashore"),
                new Location(null, "Sea"),
                new Location(null, "Urban")};

        organs = new Organ[]{new Organ(null, "Skin"),
                new Organ(null, "Head"),
                new Organ(null, "Heart"),
                new Organ(null, "Body"),
                new Organ(null, "Arm"),
                new Organ(null, "Leg")};

        seasons = new Season[]{new Season(null, "Winter"),
                new Season(null, "Spring"),
                new Season(null, "Summer"),
                new Season(null, "Autumn")};

        steps = new Step[]{new Step(null, "Call ambulance"),
                new Step(null, "Find water"),
                new Step(null, "Give water"),
                new Step(null, "Find bandage"),
                new Step(null, "Use bandage"),
                new Step(null, "The the person to the shade")};

        symptoms = new Symptom[]{new Symptom(null, "Headache"),
                new Symptom(null, "Dizziness"),
                new Symptom(null, "Red skin"),
                new Symptom(null, "Red eyes"),
                new Symptom(null, "Sharp ache in limb or joint"),
                new Symptom(null, "Nausea")};

        traumas = new Trauma[]{new Trauma(null, "Sunburn", 10),
                new Trauma(null, "Sunstroke", 10),
                new Trauma(null, "Luxation", 5),
                new Trauma(null, "Burn", 10)};
        traumaLocations = new TraumaLocation[]{
                new TraumaLocation(3, 1),
                new TraumaLocation(4, 2),
                new TraumaLocation(1, 3),
                new TraumaLocation(2, 4)
        };
        traumaOrgans = new TraumaOrgan[]{
                new TraumaOrgan(1, 1),
                new TraumaOrgan(2, 2),
                new TraumaOrgan(4, 3),
                new TraumaOrgan(1, 4)
        };
        traumaSeasons = new TraumaSeason[]{
                new TraumaSeason(3, 1),
                new TraumaSeason(3, 2),
                new TraumaSeason(1, 3),
                new TraumaSeason(3, 4)
        };
        traumaSteps = new TraumaStep[]{
                new TraumaStep(1, 1, 1),
                new TraumaStep(2, 1,2 ),
                new TraumaStep(3, 1, 3),

                new TraumaStep(1, 2,1 ),
                new TraumaStep(6, 2, 2),

                new TraumaStep(2, 3, 1),
                new TraumaStep(5, 3, 2),

                new TraumaStep(1, 4, 1),
                new TraumaStep(4, 4, 2),
                new TraumaStep(5, 4, 3)
        };

        traumaSymptoms = new TraumaSymptom[]{
                new TraumaSymptom(1, 1),
                new TraumaSymptom(2, 1),
                new TraumaSymptom(3, 1),

                new TraumaSymptom(1, 2),
                new TraumaSymptom(2, 2),
                new TraumaSymptom(6, 2),

                new TraumaSymptom(1, 3),
                new TraumaSymptom(4, 3),
                new TraumaSymptom(5, 3),

                new TraumaSymptom(1, 4),
                new TraumaSymptom(2, 4),
                new TraumaSymptom(3, 4)
        };


    }

    public Location[] getLocations() {
        return locations;
    }

    public Organ[] getOrgans() {
        return organs;
    }

    public Season[] getSeasons() {
        return seasons;
    }

    public Step[] getSteps() {
        return steps;
    }

    public Symptom[] getSymptoms() {
        return symptoms;
    }

    public Trauma[] getTraumas() {
        return traumas;
    }

    public TraumaLocation[] getTraumaLocations() {
        return traumaLocations;
    }

    public TraumaOrgan[] getTraumaOrgans() {
        return traumaOrgans;
    }

    public TraumaSeason[] getTraumaSeasons() {
        return traumaSeasons;
    }

    public TraumaStep[] getTraumaSteps() {
        return traumaSteps;
    }

    public TraumaSymptom[] getTraumaSymptoms() {
        return traumaSymptoms;
    }
}
