package md.luciddream.findaid.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import md.luciddream.findaid.R;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.dao.OrganDao;
import md.luciddream.findaid.data.dao.SeasonDao;
import md.luciddream.findaid.data.dao.SymptomDao;
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
    }

    public void onSaveClick(View view){
        Snackbar.make(view, R.string.save_str,Snackbar.LENGTH_SHORT).show();
    }






}
