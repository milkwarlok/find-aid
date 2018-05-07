package md.luciddream.findaid.data;

import md.luciddream.findaid.data.model.*;

public class ExampleData {
    Location[] locations;
    Organ[] organs;
    Season[] seasons;
    Step[] steps;
    Symptom[] symptoms;
    Trauma[] traumas;
    ExampleData(){
        locations = new Location[]{new Location(null, "Mountains"),
                new Location(null, "Seashore"),
                new Location(null, "Sea"),
                new Location(null, "Urban")};

        organs = new Organ[]{new Organ(null, "skin"),
                new Organ(null, "head"),
                new Organ(null, "heart"),
                new Organ(null, "body"),
                new Organ(null, "arm"),
                new Organ(null, "leg")};

        seasons = new Season[]{new Season(null , "winter"),
                new Season(null , "spring"),
                new Season(null , "summer"),
                new Season(null , "autumn")};

        steps = new Step[]{new Step(null, "Call ambulance"),
                new Step(null, "Find water"),
                new Step(null, "Give water"),
                new Step(null, "Find bandage"),
                new Step(null, "Use bandage")};

        symptoms = new Symptom[]{new Symptom(null, "headache"),
                new Symptom(null, "dizziness"),
                new Symptom(null, "red skin"),
                new Symptom(null, "red eyes")};

        traumas = new Trauma[]{new Trauma(null, "sunburn", 10),
                new Trauma(null, "sunstroke", 10),
                new Trauma(null, "luxation", 5),
                new Trauma(null, "burn", 10)};
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
}
