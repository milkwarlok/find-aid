package md.luciddream.findaid.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.ListView;
import android.widget.TextView;
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

        List<Trauma> traumas = getTraumas();
        List<String> traumaNames = getTraumaNames(traumas);
        List<List<String>> symptomsList = getSymptomList(traumas);
        symptomAdapter = new ReferenceItemArrayAdapter(this,traumaNames,symptomsList);
        symptomListView.setAdapter(symptomAdapter);

    }

    List<Trauma> getTraumas(){
        TraumaHelper traumaHelper = new TraumaHelper(executorService, db.traumaDao());
        return traumaHelper.findAll();
    }

    List<String> getTraumaNames(List<Trauma> traumas){
        List<String> toReturn = new ArrayList<>(traumas.size());

        for(int i = 0; i < traumas.size(); i++){
            toReturn.add(traumas.get(i).getName());
        }
        return toReturn;
    }

    List<List<String>> getSymptomList(List<Trauma> traumas){
        TraumaSymptomHelper traumaSymptomHelper = new TraumaSymptomHelper(executorService, db.traumaSymptomDao());
        List<List<String>> toReturn = new ArrayList<>(traumas.size());
        for(int i = 0; i < traumas.size(); i++){
            List<Symptom> symptomList = traumaSymptomHelper.getSecondByFirstId(traumas.get(i).getT_id());
            List<String> toAdd = new ArrayList<>(symptomList.size());
            for(int j = 0; j < symptomList.size(); j++){
                toAdd.add(symptomList.get(j).getName());
            }
            toReturn.add(toAdd);
        }
        return toReturn;
    }



}
