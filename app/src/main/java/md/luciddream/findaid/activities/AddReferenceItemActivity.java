package md.luciddream.findaid.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import md.luciddream.findaid.R;
import md.luciddream.findaid.custom.adapter.EditTextArrayAdapter;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.dao.OrganDao;
import md.luciddream.findaid.data.dao.SeasonDao;
import md.luciddream.findaid.data.dao.SymptomDao;
import md.luciddream.findaid.data.helper.*;
import md.luciddream.findaid.data.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class AddReferenceItemActivity extends AppCompatActivity {
    ExecutorService executor;
    FindAidDatabase findAidDatabase;


    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reference_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        executor = Executors.newSingleThreadExecutor();
        findAidDatabase = FindAidDatabase.getInstance(getApplicationContext());

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner locationSpinner = (Spinner) findViewById(R.id.item_location_spinner);
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
        Spinner organSpinner = (Spinner) findViewById(R.id.item_organ_spinner);
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

        Spinner seasonSpinner = (Spinner) findViewById(R.id.item_season_spinner);
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

        Spinner symptomSpinner = (Spinner) findViewById(R.id.item_symptom_spinner);
        symptomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Something was selected: " + position ,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        inflateSpinner(symptomSpinner, new SymptomHelper(executor, findAidDatabase.symptomDao()));

        final ListView listview = (ListView) findViewById(R.id.listview);
        list = new ArrayList<>();

        adapter = new EditTextArrayAdapter(this, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.set(position, "");
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });
    }

    private void inflateSpinner(Spinner spinner, Helper helper) {
        //Creating the ArrayAdapter instance having the country list
        String[] arr = getStrings(helper);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
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

    public void onAddItemClick(View view){
        list.add("Введите шаг....");
        adapter.notifyDataSetChanged();

    }
    public void onRemoveItemClick(View view){
        if(list != null && !list.isEmpty())
            list.remove(list.size() - 1);
        adapter.notifyDataSetChanged();

    }


}
