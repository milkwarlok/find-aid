package md.luciddream.findaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import md.luciddream.findaid.R;
import md.luciddream.findaid.custom.adapter.AddableItemArrayAdapter;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.helper.Helper;
import md.luciddream.findaid.data.helper.LocationHelper;
import md.luciddream.findaid.data.helper.OrganHelper;
import md.luciddream.findaid.data.helper.SeasonHelper;
import md.luciddream.findaid.data.model.*;
import md.luciddream.findaid.data.specific.SpecificTrauma;
import md.luciddream.findaid.data.specific.SpecificTraumaInflater;
import md.luciddream.findaid.data.specific.SpecificUpdater;
import md.luciddream.findaid.validator.SpecificTraumaValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateReferenceItemActivity extends AppCompatActivity {

    private ExecutorService executor;
    private FindAidDatabase findAidDatabase;

    private ArrayList<String> symptomHints;
    private ArrayList<String> symptomValues;
    private ArrayList<String> stepHints;
    private ArrayList<String> stepValues;

    private SpecificTrauma originalSpecificTrauma;
    private SpecificTrauma changedSpecificTrauma;
    private Intent parentIntent;

    private TextView name;
    private Spinner locationSpinner, organSpinner, seasonSpinner;
    private ListView symptomListView, stepListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reference_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        parentIntent = getIntent();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, parentIntent.getStringExtra("t_name"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        executor = Executors.newSingleThreadExecutor();
        findAidDatabase = FindAidDatabase.getInstance(this);

        SpecificTraumaInflater traumaInflater = new SpecificTraumaInflater(executor, findAidDatabase,
                parentIntent.getIntExtra("t_id", 0), parentIntent.getStringExtra("t_name"));
        originalSpecificTrauma = traumaInflater.inflate();
        changedSpecificTrauma = traumaInflater.inflate();

        name = (TextView) findViewById(R.id.update_item_name_text_view);
        name.setText(changedSpecificTrauma.getTrauma().getName());

        locationSpinner = (Spinner) findViewById(R.id.update_item_location_spinner);
        inflateSpinner(locationSpinner, new LocationHelper(executor, findAidDatabase.locationDao()),changedSpecificTrauma.getLocation().getName());

        organSpinner = (Spinner) findViewById(R.id.update_item_organ_spinner);
        inflateSpinner(organSpinner, new OrganHelper(executor,findAidDatabase.organDao()),changedSpecificTrauma.getOrgan().getName());

        seasonSpinner = (Spinner) findViewById(R.id.update_item_season_spinner);
        inflateSpinner(seasonSpinner, new SeasonHelper(executor, findAidDatabase.seasonDao()),changedSpecificTrauma.getSeason().getName());

        Symptom[] symptoms = changedSpecificTrauma.getSymptoms();
        symptomHints = new ArrayList<>(symptoms.length);
        symptomValues = new ArrayList<>(symptoms.length);
        for(int i = 0; i < symptoms.length; i++){
            symptomHints.add("Введите симптом");
            symptomValues.add(symptoms[i].getName());
        }
        ArrayAdapter symptomAdapter = new AddableItemArrayAdapter(this, symptomHints, symptomValues, "Введите симптом");

        symptomListView = (ListView) findViewById(R.id.update_item_symptom_listview);
        symptomListView.setAdapter(symptomAdapter);


        Step[] steps = changedSpecificTrauma.getSteps();
        stepHints = new ArrayList<>(steps.length);
        stepValues = new ArrayList<>(steps.length);
        for(int i = 0; i < steps.length; i++){
            stepHints.add("Введите симптом");
            stepValues.add(steps[i].getName());
        }
        ArrayAdapter stepAdapter = new AddableItemArrayAdapter(this, stepHints, stepValues, "Введите симптом");

        stepListView = (ListView) findViewById(R.id.update_item_step_listview);
        stepListView.setAdapter(stepAdapter);
    }

    private void inflateSpinner(Spinner spinner, Helper helper, String defaultItem) {
        String[] arr = getStrings(helper);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        int defaultPosition = arrayAdapter.getPosition(defaultItem);
        spinner.setSelection(defaultPosition);
    }

    private String[] getStrings(Helper helper) {
        List<? extends NamedEntity> all = helper.findAll();
        String[] arr = new String[all.size()];
        for(int i = 0; i < arr.length; i++){
            arr[i] = all.get(i).getName();
        }
        return arr;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                startParentActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startParentActivity() {
        Intent intent = new Intent(this, DetailReferenceItemActivity.class);
        intent.putExtra("t_id", parentIntent.getIntExtra("t_id", 0));
        intent.putExtra("t_name", parentIntent.getStringExtra("t_name"));
        startActivity(intent);
        finish();
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
        String locationSpinnerValue = locationSpinner.getSelectedItem().toString();
        changedSpecificTrauma.setLocation(new Location(null, locationSpinnerValue));

        String organSpinnerValue = organSpinner.getSelectedItem().toString();
        changedSpecificTrauma.setOrgan(new Organ(null, organSpinnerValue));

        String seasonSpinnerValue = seasonSpinner.getSelectedItem().toString();
        changedSpecificTrauma.setSeason(new Season(null, seasonSpinnerValue));

        Symptom[] symptoms = new Symptom[symptomValues.size()];
        for(int i = 0; i < symptomValues.size(); i++){
            symptoms[i] = new Symptom(null, symptomValues.get(i));
        }
        changedSpecificTrauma.setSymptoms(symptoms);

        Step[] steps = new Step[stepValues.size()];
        Map<String, Integer> stepOrder = new ConcurrentHashMap<>();
        for(int i = 0 ; i < stepValues.size(); i++){
            steps[i] = new Step(null, stepValues.get(i));
            stepOrder.put(stepValues.get(i), i + 1);
        }
        changedSpecificTrauma.setSteps(steps);
        changedSpecificTrauma.setStepOrder(stepOrder);

        SpecificTraumaValidator specificTraumaValidator = new SpecificTraumaValidator(changedSpecificTrauma);
        if(!specificTraumaValidator.isValid()) {
           return;
        }
        for(int i = 0; i < stepOrder.size(); i++){
            Log.d("MAP_VALUES", stepValues.get(i) + ": " +stepOrder.get(stepValues.get(i)));
        }
        SpecificUpdater updater = new SpecificUpdater(executor, findAidDatabase, changedSpecificTrauma);
        updater.update();

        Snackbar.make(view, "OnSaveClick was clicked." + changedSpecificTrauma.getLocation().getName() +
                changedSpecificTrauma.getOrgan().getName(), Snackbar.LENGTH_SHORT).show();
        finish();
        startParentActivity();
    }
}
