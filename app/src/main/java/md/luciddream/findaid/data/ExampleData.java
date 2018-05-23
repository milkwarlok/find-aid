package md.luciddream.findaid.data;

import android.content.Context;
import md.luciddream.findaid.R;
import md.luciddream.findaid.data.model.*;
import md.luciddream.findaid.data.model.join.*;


public class ExampleData {
    private Location[] locations;
    private Organ[] organs;
    private Season[] seasons;
    private Step[] steps;
    private Symptom[] symptoms;
    private Trauma[] traumas;
    private TraumaLocation[] traumaLocations;
    private TraumaOrgan[] traumaOrgans;
    private TraumaSeason[] traumaSeasons;
    private TraumaStep[] traumaSteps;
    private TraumaSymptom[] traumaSymptoms;
    private Context context;

    ExampleData(Context newContext) {
        context = newContext;
        locations = new Location[]{
                new Location(null, context.getString(R.string.location_any)),
                new Location(null, context.getString(R.string.location_mountains)),
                new Location(null, context.getString(R.string.location_seashore)),
                new Location(null, context.getString(R.string.location_sea)),
                new Location(null, context.getString(R.string.location_urban)),
                new Location(null, context.getString(R.string.location_village)),
                new Location(null, context.getString(R.string.location_countryside)),
                new Location(null, context.getString(R.string.location_desert)),
                new Location(null, context.getString(R.string.location_river)),
                new Location(null, context.getString(R.string.location_jungle)),
                new Location(null, context.getString(R.string.location_forest)),
                new Location(null, context.getString(R.string.location_poll)),
                new Location(null, context.getString(R.string.location_home)),
                new Location(null, context.getString(R.string.location_fitness)),
                new Location(null, context.getString(R.string.location_school)),
                new Location(null, context.getString(R.string.location_university)),
                new Location(null, context.getString(R.string.location_kindergarten))
        };

        organs = new Organ[]{
                new Organ(null, context.getString(R.string.organ_any)),
                new Organ(null, context.getString(R.string.organ_skin)),
                new Organ(null, context.getString(R.string.organ_head)),
                new Organ(null, context.getString(R.string.organ_heart)),
                new Organ(null, context.getString(R.string.organ_body)),
                new Organ(null, context.getString(R.string.organ_arm)),
                new Organ(null, context.getString(R.string.organ_leg)),
                new Organ(null, context.getString(R.string.organ_brain)),
                new Organ(null, context.getString(R.string.organ_kidneys)),
                new Organ(null, context.getString(R.string.organ_lungs)),
                new Organ(null, context.getString(R.string.organ_stomach)),
                new Organ(null, context.getString(R.string.organ_ear)),
                new Organ(null, context.getString(R.string.organ_iris)),
                new Organ(null, context.getString(R.string.organ_nose)),
                new Organ(null, context.getString(R.string.organ_tongue)),
                new Organ(null, context.getString(R.string.organ_elbow)),
                new Organ(null, context.getString(R.string.organ_finger)),
                new Organ(null, context.getString(R.string.organ_wrist)),
                new Organ(null, context.getString(R.string.organ_foot)),
                new Organ(null, context.getString(R.string.organ_toe)),
                new Organ(null, context.getString(R.string.organ_hip))
        };

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

        traumas = new Trauma[]{new Trauma(null, "Sunburn", 10, false),
                new Trauma(null, "Sunstroke", 10, false),
                new Trauma(null, "Luxation", 5, false),
                new Trauma(null, "Burn", 10, false)};
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
