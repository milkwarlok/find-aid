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
import android.widget.Toast;
import md.luciddream.findaid.R;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.specific.SpecificDeleter;
import md.luciddream.findaid.data.specific.SpecificTrauma;
import md.luciddream.findaid.data.specific.SpecificTraumaInflater;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailReferenceItemActivity extends AppCompatActivity {
    private ExecutorService executor;
    private FindAidDatabase findAidDatabase;
    private Intent parentIntent;

    private SpecificTrauma specificTrauma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reference_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        parentIntent = getIntent();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, specificTrauma.getTrauma().getName() + " " + specificTrauma.getSeason().getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        executor = Executors.newSingleThreadExecutor();
        findAidDatabase = FindAidDatabase.getInstance(this);

        SpecificTraumaInflater traumaInflater = new SpecificTraumaInflater(executor, findAidDatabase,
                parentIntent.getIntExtra("t_id", 0),parentIntent.getStringExtra("t_name"));
        specificTrauma = traumaInflater.inflate();
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
        Snackbar.make(view, "onSymptomsShowHideClick was clicked.", Snackbar.LENGTH_SHORT).show();
    }
    public void onStepsShowHideClick(View view){
        Snackbar.make(view, "onStepsShowHideClick was clicked.", Snackbar.LENGTH_SHORT).show();

    }

    public void onSaveClick(View view){
        Snackbar.make(view, "OnSaveClick was clicked.", Snackbar.LENGTH_SHORT).show();
    }

}
