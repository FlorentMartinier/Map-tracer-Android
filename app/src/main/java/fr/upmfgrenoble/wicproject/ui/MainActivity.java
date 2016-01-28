package fr.upmfgrenoble.wicproject.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import fr.upmfgrenoble.wicproject.model.GPX;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            InputStream is = getAssets().open("simple.gpx");
             GPX fgpx = GPX.parse(is);
            Iterator i = fgpx.listIterator();
            while (i.hasNext()){
                Object x = i.next();
                Log.v("GPX:",""+x);

            }
            Log.v("MainActivity","ouhooo");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
