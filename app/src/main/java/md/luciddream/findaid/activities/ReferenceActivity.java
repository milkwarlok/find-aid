package md.luciddream.findaid.activities;

import android.app.SearchManager;
import android.arch.persistence.room.Room;
import android.content.Context;
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

import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import md.luciddream.findaid.R;
import md.luciddream.findaid.custom.adapter.ReferenceItemArrayAdapter;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.helper.LocationHelper;
import md.luciddream.findaid.data.helper.TraumaHelper;
import md.luciddream.findaid.data.helper.join.TraumaSymptomHelper;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaSymptom;
import md.luciddream.findaid.data.specific.SpecificTrauma;

import java.util.*;
import java.util.concurrent.*;

public class ReferenceActivity extends AppCompatActivity {
    private ReferenceItemArrayAdapter symptomAdapter;
    private ListView symptomListView;

    private ExecutorService executorService;

    private FindAidDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddReferenceItemActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


         db = FindAidDatabase.getInstance(getApplicationContext());
         symptomListView = findViewById(R.id.content_reference_list_view);
         executorService = Executors.newSingleThreadExecutor();



    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Trauma> traumas = getTraumas();
        List<List<Symptom>> symptomsList = getSymptomList(traumas);
        List<SpecificTrauma> specificTraumas = new ArrayList<>(traumas.size());
        assert(traumas.size() == symptomsList.size());
        for(int i = 0; i < traumas.size(); i++){
            if(traumas.size() == 0) continue;
            SpecificTrauma temp = new SpecificTrauma();
            temp.setTrauma(traumas.get(i));
            Symptom[] symptoms = getSymptoms(symptomsList, i);
            temp.setSymptoms(symptoms);
            specificTraumas.add(temp);
        }
        symptomAdapter = new ReferenceItemArrayAdapter(this,specificTraumas);
        symptomListView.setAdapter(symptomAdapter);
    }

    private Symptom[] getSymptoms(List<List<Symptom>> symptomsList, int i) {
        Symptom[] symptoms = new Symptom[symptomsList.get(i).size()];
        for(int j = 0; j < symptoms.length; j++){
            symptoms[j] = symptomsList.get(i).get(j);
        }
        return symptoms;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                symptomAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }
    private List<Trauma> getTraumas(){
        TraumaHelper traumaHelper = new TraumaHelper(executorService, db.traumaDao());
        return traumaHelper.findAll();
    }


    private List<List<Symptom>> getSymptomList(List<Trauma> traumas){
        TraumaSymptomHelper traumaSymptomHelper = new TraumaSymptomHelper(executorService, db.traumaSymptomDao());
        List<List<Symptom>> toReturn = new ArrayList<>(traumas.size());
        for(int i = 0; i < traumas.size(); i++){
            List<Symptom> symptomList = traumaSymptomHelper.getSecondByFirstId(traumas.get(i).getT_id());
            List<Symptom> toAdd = new ArrayList<>(symptomList.size());
            toAdd.addAll(symptomList);
            toReturn.add(toAdd);
        }
        return toReturn;
    }


}
