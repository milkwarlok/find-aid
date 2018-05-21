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
import md.luciddream.findaid.R;

public class UpdateReferenceItemActivity extends AppCompatActivity {

    private Intent parentIntent;

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
