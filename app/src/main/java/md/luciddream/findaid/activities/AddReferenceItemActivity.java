package md.luciddream.findaid.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import md.luciddream.findaid.R;
import md.luciddream.findaid.custom.adapter.AddableItemArrayAdapter;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.helper.*;
import md.luciddream.findaid.data.model.*;
import md.luciddream.findaid.data.specific.SpecificSaver;
import md.luciddream.findaid.data.specific.SpecificTrauma;
import md.luciddream.findaid.validator.SpecificTraumaValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddReferenceItemActivity extends AppCompatActivity {


    private ExecutorService executor;
    private FindAidDatabase findAidDatabase;

    private ArrayList<String> symptomHints;
    private ArrayList<String> symptomValues;
    private ArrayList<String> stepHints;
    private ArrayList<String> stepValues;

    private ArrayAdapter<String> stepAdapter;
    private ArrayAdapter<String> symptomAdapter;

    private TextInputEditText name;
    private Spinner locationSpinner;
    private Spinner organSpinner;
    private Spinner seasonSpinner;
    private ListView symptomListView;
    private ListView stepListView;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reference_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        executor = Executors.newSingleThreadExecutor();
        findAidDatabase = FindAidDatabase.getInstance(getApplicationContext());
        saveButton = (Button) findViewById(R.id.save_reference_item);

        name = (TextInputEditText) findViewById(R.id.item_name_textinputedittext);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        locationSpinner = (Spinner) findViewById(R.id.item_location_spinner);
        Helper<?> locationHelper = new LocationHelper(executor, findAidDatabase.locationDao());

        inflateSpinner(locationSpinner, locationHelper);

        organSpinner = (Spinner) findViewById(R.id.item_organ_spinner);

        inflateSpinner(organSpinner, new OrganHelper(executor,findAidDatabase.organDao()));

        seasonSpinner = (Spinner) findViewById(R.id.item_season_spinner);

        inflateSpinner(seasonSpinner, new SeasonHelper(executor, findAidDatabase.seasonDao()));

        symptomListView = (ListView) findViewById(R.id.item_symptom_listview);
        symptomHints = new ArrayList<>(Arrays.asList("Введите симптом"));
        symptomValues = new ArrayList<>(Arrays.asList(""));
        symptomAdapter = new AddableItemArrayAdapter(this, symptomHints, symptomValues, "Введите симптом");
        symptomListView.setAdapter(symptomAdapter);

        stepListView = (ListView) findViewById(R.id.item_step_listview);
        stepHints = new ArrayList<>(Arrays.asList("Введите шаг"));
        stepValues = new ArrayList<>(Arrays.asList(""));


        stepAdapter = new AddableItemArrayAdapter(this, stepHints, stepValues, "Введите шаг");
        stepListView.setAdapter(stepAdapter);
    }

    private void inflateSpinner(Spinner spinner, Helper helper) {
        String[] arr = getStrings(helper);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    private String[] getStrings(Helper helper) {
        List<? extends NamedEntity> all = helper.findAll();
        String[] arr = new String[all.size()];
        for(int i = 0; i < arr.length; i++){
            arr[i] = all.get(i).getName();
        }
        return arr;
    }


    public void onSymptomsShowHideClick(View view){
        if(symptomListView.getVisibility() == View.VISIBLE)
            symptomListView.setVisibility(View.GONE);
        else {
            symptomListView.setVisibility(View.VISIBLE);
        }
    }
    public void onStepsShowHideClick(View view){
        if(stepListView.getVisibility() == View.VISIBLE)
            stepListView.setVisibility(View.GONE);
        else
            stepListView.setVisibility(View.VISIBLE);
    }

    public void onSaveClick(View view){

        SpecificTrauma specificTrauma = new SpecificTrauma();
        specificTrauma.setTrauma(new Trauma(null, name.getText().toString(), 0));
        specificTrauma.setLocation(new Location(null, locationSpinner.getSelectedItem().toString()));
        specificTrauma.setOrgan(new Organ(null, organSpinner.getSelectedItem().toString()));
        specificTrauma.setSeason(new Season(null, seasonSpinner.getSelectedItem().toString()));

        Symptom[] symptoms = new Symptom[symptomValues.size()];
        for(int i = 0; i < symptomValues.size(); i++){
            symptoms[i] = new Symptom(null ,symptomValues.get(i));
        }
        specificTrauma.setSymptoms(symptoms);

        Step[] steps = new Step[stepValues.size()];
        Map<String, Integer> stepOrder = new ConcurrentHashMap<>();
        for(int i = 0 ; i < stepValues.size(); i++){
            steps[i] = new Step(null, stepValues.get(i));
            stepOrder.put(stepValues.get(i), i + 1);
        }
        specificTrauma.setSteps(steps);
        specificTrauma.setStepOrder(stepOrder);

        SpecificTraumaValidator specificTraumaValidator = new SpecificTraumaValidator(specificTrauma);
        if(!specificTraumaValidator.isValid()) {
            switch (specificTraumaValidator.getMessage()) {
                case IS_OK:
                    break;
                case INVALID_NAME:
                    name.setError("Имя должно быть не пустым и содержать только буквы");
                    return;
                case INVALID_SYMPTOMS:
                    return;
                case INVALID_STEPS:
                    return;
            }
        }
        SpecificSaver specificSaver = new SpecificSaver(executor, findAidDatabase, specificTrauma);
        specificSaver.save();

        Toast.makeText(view.getContext(), R.string.saved_str, Toast.LENGTH_SHORT).show();
        finish();

    }

    /*fixme:if are added 4 steps and click - "save" and then delete step, and click save - than this 4th step is still in DB...
    fixme: can be fixed by validating input, look forward in sprint 4.
    */

}
