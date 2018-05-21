package md.luciddream.findaid.data.specific;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.helper.*;
import md.luciddream.findaid.data.helper.join.*;

import java.util.concurrent.ExecutorService;

public abstract class SpecificHelper {
    protected ExecutorService executorService;
    protected SpecificTrauma specificTrauma;
    protected FindAidDatabase findAidDatabase;

    protected TraumaHelper traumaHelper;
    protected LocationHelper locationHelper;
    protected OrganHelper organHelper;
    protected SeasonHelper seasonHelper;
    protected SymptomHelper symptomHelper;
    protected StepHelper stepHelper;

    protected TraumaLocationHelper traumaLocationHelper;
    protected TraumaOrganHelper traumaOrganHelper;
    protected TraumaSeasonHelper traumaSeasonHelper;
    protected TraumaSymptomHelper traumaSymptomHelper;
    protected TraumaStepHelper traumaStepHelper;

    SpecificHelper(ExecutorService executorService, FindAidDatabase findAidDatabase, SpecificTrauma specificTrauma) {
        this.executorService = executorService;
        this.specificTrauma = specificTrauma;
        this.findAidDatabase = findAidDatabase;
        setUp();
    }
    SpecificHelper(ExecutorService executorService, FindAidDatabase findAidDatabase) {
        this.executorService = executorService;
        this.findAidDatabase = findAidDatabase;
        setUp();
    }
    private void setUp() {
        this.traumaHelper = new TraumaHelper(executorService,findAidDatabase.traumaDao());
        this.locationHelper = new LocationHelper(executorService, findAidDatabase.locationDao());
        this.organHelper = new OrganHelper(executorService, findAidDatabase.organDao());
        this.seasonHelper = new SeasonHelper(executorService, findAidDatabase.seasonDao());
        this.symptomHelper = new SymptomHelper(executorService, findAidDatabase.symptomDao());
        this.stepHelper = new StepHelper(executorService, findAidDatabase.stepDao());

        this.traumaLocationHelper = new TraumaLocationHelper(executorService, findAidDatabase.traumaLocationDao());
        this.traumaOrganHelper = new TraumaOrganHelper(executorService, findAidDatabase.traumaOrganDao());
        this.traumaSeasonHelper = new TraumaSeasonHelper(executorService, findAidDatabase.traumaSeasonDao());
        this.traumaSymptomHelper = new TraumaSymptomHelper(executorService, findAidDatabase.traumaSymptomDao());
        this.traumaStepHelper = new TraumaStepHelper(executorService, findAidDatabase.traumaStepDao());
    }
}
