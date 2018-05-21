package md.luciddream.findaid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import md.luciddream.findaid.R;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.helper.Helper;
import md.luciddream.findaid.data.helper.LocationHelper;
import md.luciddream.findaid.data.model.NamedEntity;
import md.luciddream.findaid.data.specific.SpecificTrauma;
import md.luciddream.findaid.data.specific.SpecificTraumaInflater;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateReferenceItemActivity extends AppCompatActivity {

    private ExecutorService executor;
    private FindAidDatabase findAidDatabase;

    private SpecificTrauma originalSpecificTrauma;
    private SpecificTrauma changedSpecificTrauma;
    private Intent parentIntent;

    private TextView name;
    private Spinner locationSpinner;


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
        Helper<?> locationHelper = new LocationHelper(executor, findAidDatabase.locationDao());

        inflateSpinner(locationSpinner, locationHelper);
//        locationSpinner.selected

    }

    private void inflateSpinner(Spinner spinner, Helper helper) {
        String[] arr = getStrings(helper);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        int defaultPosition = arrayAdapter.getPosition(changedSpecificTrauma.getLocation().getName());
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
                Intent intent = new Intent(this, DetailReferenceItemActivity.class);
                intent.putExtra("t_id", parentIntent.getIntExtra("t_id", 0));
                intent.putExtra("t_name", parentIntent.getStringExtra("t_name"));
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onSymptomsShowHideClick(View view){
        Snackbar.make(view, "onSymptomsShowHideClick was clicked.", Snackbar.LENGTH_SHORT).show();
    }
    public void onStepsShowHideClick(View view){
        Snackbar.make(view, "onStepsShowHideClick was clicked.", Snackbar.LENGTH_SHORT).show();
    }

    public void onSaveClick(View view){
        Snackbar.make(view, "OnSaveClick was clicked.", Snackbar.LENGTH_SHORT).show();
    }
}
