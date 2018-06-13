package md.luciddream.findaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import md.luciddream.findaid.activities.CallerActivity;
import md.luciddream.findaid.activities.ReferenceActivity;
import md.luciddream.findaid.custom.adapter.ReferenceItemArrayAdapter;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.helper.TraumaHelper;
import md.luciddream.findaid.data.helper.join.TraumaSymptomHelper;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.specific.SpecificTrauma;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ReferenceItemArrayAdapter symptomAdapter;
    private ListView symptomListView;

    private ExecutorService executorService;

    private FindAidDatabase db;
//todo: поменять картинки на кнопки с зеленым фоном - темой приложения.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FindAidDatabase.getInstance(getApplicationContext());
        symptomListView = findViewById(R.id.main_activity_list_view);
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
    private List<Trauma> getTraumas(){
        TraumaHelper traumaHelper = new TraumaHelper(executorService, db.traumaDao());
        return traumaHelper.findFirstMostRelevant(3);
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

    public void onCallerClick(View view){
        Intent intent = new Intent(view.getContext(), CallerActivity.class);
        startActivity(intent);
    }

    public void onReferenceClick(View view){
        Intent intent = new Intent(view.getContext(), ReferenceActivity.class);
        startActivity(intent);
    }


}

