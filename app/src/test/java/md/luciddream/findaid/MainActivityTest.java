package md.luciddream.findaid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import md.luciddream.findaid.activities.CallerActivity;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {
    private MainActivity mainActivity;
    private View view;

    @Before
    public void setUp(){
        mainActivity = Mockito.mock(MainActivity.class);
        view = Mockito.mock(ImageView.class);

    }
    @Test
    public void onCallerClickTest(){
        Mockito.doNothing().when(mainActivity).startActivity(Mockito.any(Intent.class));
//        mainActivity.onCallerClick(view);
//        Mockito.verify(mainActivity).onCallerClick(view);
       // mainActivity.onCreate(Mockito.mock(Bundle.class));
       // mainActivity.onCallerClick(view);
       // Mockito.verify(mainActivity).startActivity(Mockito.any(Intent.class));
    }
}
