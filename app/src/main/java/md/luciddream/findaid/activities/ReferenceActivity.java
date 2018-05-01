package md.luciddream.findaid.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.TextView;
import md.luciddream.findaid.R;
import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.model.Location;

import java.util.List;

public class ReferenceActivity extends AppCompatActivity {

    private FindAidDatabase db;
    private boolean b;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


         db = Room.databaseBuilder(getApplicationContext(),
                FindAidDatabase.class, "database-name").build();
    }


    public void onInsertClick(View view){
        LocationDao locationDao = db.locationDao();
        Location location = new Location();
        Thread thread = new Thread(){
            @Override
            public void run() {
                location.setL_id(null);
                location.setName("Mountains");
                locationDao.insertAll(location);
            }
        };
        thread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView textView = (TextView) findViewById(R.id.reference_insert_label);


        thread = new Thread(){
            @Override
            public void run() {
                final List<Location> all;
                all = locationDao.findAll();
                b = all.isEmpty();
            }
        };
        thread.start();

        textView.setText(b?"Not inserted":"Inserted");
    }

}
