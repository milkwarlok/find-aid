package md.luciddream.findaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMapClick(View view){
        Toast.makeText(view.getContext(), "HI", Toast.LENGTH_SHORT).show();
    }

    public void onHelpClick(View view){

    }

    public void onReferenceClick(View view){
//        Intent intent = new Intent(view.getContext(), ReferenceActivity.class);
//        startActivity(intent);
    }
}
