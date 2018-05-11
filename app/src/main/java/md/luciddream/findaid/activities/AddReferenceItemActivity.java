package md.luciddream.findaid.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import md.luciddream.findaid.R;
import md.luciddream.findaid.custom.adapter.EditTextArrayAdapter;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.helper.*;
import md.luciddream.findaid.data.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddReferenceItemActivity extends AppCompatActivity {
    ExecutorService executor;
    FindAidDatabase findAidDatabase;

    ArrayList<String> symptomHints;
    ArrayList<String> symptomValues;
    ArrayList<String> stepHints;
    ArrayList<String> stepValues;

    ArrayAdapter<String> stepAdapter;
    ArrayAdapter<String> symptomAdapter;

    private TextInputEditText name;
    private Spinner locationSpinner;
    private Spinner organSpinner;
    private Spinner seasonSpinner;
    private ListView symptomListView;
    private ListView stepListView;
//todo: onStepListViewClick{ show or hide list view}
    //todo: same for Symptoms
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reference_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        executor = Executors.newSingleThreadExecutor();
        findAidDatabase = FindAidDatabase.getInstance(getApplicationContext());

        name = (TextInputEditText) findViewById(R.id.item_name_textinputedittext);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        locationSpinner = (Spinner) findViewById(R.id.item_location_spinner);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Something was selected: " + position ,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Helper<?> locationHelper = new LocationHelper(executor, findAidDatabase.locationDao());

        inflateSpinner(locationSpinner, locationHelper);

        organSpinner = (Spinner) findViewById(R.id.item_organ_spinner);
        organSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Something was selected: " + position ,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        inflateSpinner(organSpinner, new OrganHelper(executor,findAidDatabase.organDao()));

        seasonSpinner = (Spinner) findViewById(R.id.item_season_spinner);
        seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Something was selected: " + position ,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        inflateSpinner(seasonSpinner, new SeasonHelper(executor, findAidDatabase.seasonDao()));

        symptomListView = (ListView) findViewById(R.id.item_symptom_listview);
        symptomHints = new ArrayList<>(Arrays.asList(new String("Введите симптом")));
        symptomValues = new ArrayList<>(Arrays.asList(new String("")));
        symptomAdapter = new EditTextArrayAdapter(this, symptomHints, symptomValues, "Введите симптом");
        symptomListView.setAdapter(symptomAdapter);

        stepListView = (ListView) findViewById(R.id.item_step_listview);
        stepHints = new ArrayList<>(Arrays.asList(new String("Введите шаг")));
        stepValues = new ArrayList<>(Arrays.asList(new String("")));


        stepAdapter = new EditTextArrayAdapter(this, stepHints, stepValues, "Введите шаг");
        stepListView.setAdapter(stepAdapter);

    }

    private void inflateSpinner(Spinner spinner, Helper helper) {
        String[] arr = getStrings(helper);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arr);
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

    public void onSaveClick(View view){
        Snackbar.make(view, R.string.save_str,Snackbar.LENGTH_SHORT).show();

    }


}
