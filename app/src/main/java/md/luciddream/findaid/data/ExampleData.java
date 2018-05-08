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

        organs = new Organ[]{new Organ(null, "Skin"),
                new Organ(null, "Head"),
                new Organ(null, "Heart"),
                new Organ(null, "Body"),
                new Organ(null, "Arm"),
                new Organ(null, "Leg")};

        seasons = new Season[]{new Season(null , "Winter"),
                new Season(null , "Spring"),
                new Season(null , "Summer"),
                new Season(null , "Autumn")};

        steps = new Step[]{new Step(null, "Call ambulance"),
                new Step(null, "Find water"),
                new Step(null, "Give water"),
                new Step(null, "Find bandage"),
                new Step(null, "Use bandage")};

        symptoms = new Symptom[]{new Symptom(null, "Headache"),
                new Symptom(null, "Dizziness"),
                new Symptom(null, "Red skin"),
                new Symptom(null, "Red eyes")};

        traumas = new Trauma[]{new Trauma(null, "Sunburn", 10),
                new Trauma(null, "Sunstroke", 10),
                new Trauma(null, "Luxation", 5),
                new Trauma(null, "Burn", 10)};
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
