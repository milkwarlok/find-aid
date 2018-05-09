package md.luciddream.findaid.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
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
import md.luciddream.findaid.data.helper.LocationHelper;
import md.luciddream.findaid.data.model.Location;

import java.util.List;
import java.util.concurrent.*;

public class ReferenceActivity extends AppCompatActivity {

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
    }


    public void onShowClick(View view) {
        TextView textView = findViewById(R.id.refence_select_label);
        LocationHelper locationHelper = new LocationHelper(Executors.newSingleThreadExecutor(), db.locationDao());
            List<Location> locations = locationHelper.findAll();
            if(locations == null)
                textView.setText("It is null");
            if(locations.size() == 0)
                textView.setText("It is empty");
            for(Location location: locations) {
                textView.append(location.getName() + "\n");
            }
    }
}
