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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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


    public void onShowClick(View view) {
        TextView textView = findViewById(R.id.refence_select_label);
        Callable<List<Location>> task = new Callable<List<Location>>(){
            @Override
            public List<Location> call() throws Exception {
                LocationDao locationDao = db.locationDao();
                List<Location> all = locationDao.findAll();
                return all;
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        try {
            Future<List<Location>> all = executor.submit(task);
            executor.shutdown();
            List<Location> locations = all.get();
            if(locations == null)
                textView.setText("It is null");
            if(locations.size() == 0)
                textView.setText("It is empty");
            for(Location location: locations){
                textView.append(location.getName() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
