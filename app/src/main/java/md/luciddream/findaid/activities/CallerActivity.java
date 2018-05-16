package md.luciddream.findaid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import md.luciddream.findaid.R;

public class CallerActivity extends AppCompatActivity {
/*todo
First, get the LocationManager.
Then, call LocationManager.getLastKnownPosition.
Then create a GeoCoder and call GeoCoder.getFromLocation.
Do this is in a separate thread!!
This will give you a list of Address objects.
Call Address.getCountryName and you got it.

Keep in mind that the last known position can be a bit stale, so if the user just crossed the border, you may not know about it for a while.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
