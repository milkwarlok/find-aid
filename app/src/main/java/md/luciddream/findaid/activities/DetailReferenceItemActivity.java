package md.luciddream.findaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import md.luciddream.findaid.R;
import md.luciddream.findaid.custom.adapter.DetailItemArrayAdapter;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.specific.SpecificDeleter;
import md.luciddream.findaid.data.specific.SpecificTrauma;
import md.luciddream.findaid.data.specific.SpecificTraumaInflater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailReferenceItemActivity extends AppCompatActivity {
    private ExecutorService executor;
    private FindAidDatabase findAidDatabase;
    private Intent parentIntent;

    private SpecificTrauma specificTrauma;

    private TextView nameTextView, locationTextView, organTextView,
    seasonTextView;
    private ListView symptomListView, stepListView;

    private ArrayAdapter<String> symptomAdapter, stepAdapter;
    private List<String> symptomStrings, stepStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reference_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        parentIntent = getIntent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        executor = Executors.newSingleThreadExecutor();
        findAidDatabase = FindAidDatabase.getInstance(this);

        SpecificTraumaInflater traumaInflater = new SpecificTraumaInflater(executor, findAidDatabase,
                parentIntent.getIntExtra("t_id", 0),parentIntent.getStringExtra("t_name"));
        specificTrauma = traumaInflater.inflate();

        nameTextView = findViewById(R.id.detail_item_name);
        nameTextView.setText(specificTrauma.getTrauma().getName());

        locationTextView = findViewById(R.id.detail_item_location_textview_2);
        locationTextView.setText(specificTrauma.getLocation().getName());

        organTextView = findViewById(R.id.detail_item_organ_textview_2);
        organTextView.setText(specificTrauma.getOrgan().getName());

        seasonTextView = findViewById(R.id.detail_item_season_textview_2);
        seasonTextView.setText(specificTrauma.getSeason().getName());

        symptomStrings = new ArrayList<>();
        Symptom[] symptoms = specificTrauma.getSymptoms();
        for(int i = 0; i < symptoms.length; i++){
            symptomStrings.add(symptoms[i].getName());
        }
        symptomAdapter = new DetailItemArrayAdapter(this, symptomStrings);
        symptomListView = (ListView) findViewById(R.id.detail_item_symptom_listview);
        symptomListView.setAdapter(symptomAdapter);

        stepStrings = new ArrayList<>();
        Step[] steps = specificTrauma.getSteps();
        for(int i = 0; i < steps.length; i++){
            stepStrings.add(steps[i].getName());
        }
        stepListView = (ListView) findViewById(R.id.detail_item_step_listview);
        stepAdapter = new DetailItemArrayAdapter(this, stepStrings);
        stepListView.setAdapter(stepAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_delete_menu, menu);
        MenuItem updateOption = menu.findItem(R.id.update_menu_item);
        MenuItem deleteOption = menu.findItem(R.id.delete_menu_item);
        updateOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), "Update clicked.", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        deleteOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SpecificTrauma specificTrauma = new SpecificTrauma();
                specificTrauma.setTrauma(new Trauma(parentIntent.getIntExtra("t_id", 0),
                        parentIntent.getStringExtra("t_name"), 1));
                SpecificDeleter specificDeleter = new SpecificDeleter(executor, specificTrauma,findAidDatabase);
                specificDeleter.delete();
                finish();
                Toast.makeText(getApplicationContext(), "Delete clicked.", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
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
        Snackbar.make(view, "OnSaveClick was clicked.", Snackbar.LENGTH_SHORT).show();
    }

}
