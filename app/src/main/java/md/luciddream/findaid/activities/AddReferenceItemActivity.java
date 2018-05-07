package md.luciddream.findaid.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import md.luciddream.findaid.R;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.dao.OrganDao;
import md.luciddream.findaid.data.dao.SeasonDao;
import md.luciddream.findaid.data.dao.SymptomDao;
import md.luciddream.findaid.data.helper.LocationHelper;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Organ;
import md.luciddream.findaid.data.model.Season;
import md.luciddream.findaid.data.model.Symptom;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AddReferenceItemActivity extends AppCompatActivity {
    private TextInputEditText mName;
    private Spinner mLocation, mOrgan, mSeason, mSymptom;
    private EditText mStep;
    private String[] mLocationList, mOrganList, mSeasonList, mSymptomList;
    public AddReferenceItemActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reference_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.item_location_spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Something was selected: " + position ,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Nothing was selected" ,Toast.LENGTH_LONG).show();
            }
        });

        //Creating the ArrayAdapter instance having the country list
        LocationHelper locationHelper = new LocationHelper(Executors.newSingleThreadExecutor(),
                Room.databaseBuilder(getApplicationContext(), FindAidDatabase.class, "database-name")
//                        .addMigrations(FindAidDatabase.INSERT_BASIC_DATA)
                        .build());
        List<Location> all = locationHelper.findAll();
        String[] arr = new String[all.size()];
        for(int i = 0; i < arr.length; i++){
            arr[i] = all.get(0).getName();
        }
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arr);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    public void onSaveClick(View view){
        Snackbar.make(view, R.string.save_str,Snackbar.LENGTH_SHORT).show();

    }

}
