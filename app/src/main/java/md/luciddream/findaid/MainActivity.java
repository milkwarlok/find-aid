package md.luciddream.findaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import md.luciddream.findaid.activities.CallerActivity;
import md.luciddream.findaid.activities.ReferenceActivity;

public class MainActivity extends AppCompatActivity {
//todo: поменять картинки на кнопки с зеленым фоном - темой приложения.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCallerClick(View view){
        Intent intent = new Intent(view.getContext(), CallerActivity.class);
        startActivity(intent);
    }

    public void onReferenceClick(View view){
        Intent intent = new Intent(view.getContext(), ReferenceActivity.class);
        startActivity(intent);
    }
}

