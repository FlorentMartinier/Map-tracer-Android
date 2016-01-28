package fr.upmfgrenoble.wicproject.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.GpsStatus;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Random;


import fr.upmfgrenoble.wicproject.R;
import fr.upmfgrenoble.wicproject.model.GPX;
import fr.upmfgrenoble.wicproject.pdr.StepDetectionHandler;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int PICKFILE_RESULT_CODE = 1;
    String srcGPX = "simple.gpx";
   /** private StepDetectionHandler.StepDetectionListener sListener = new StepDetectionHandler.StepDetectionListener(){
        int nbpas= 0;
        public void newStep(float pas){

            Toast.makeText(getApplicationContext(), nbpas+"", Toast.LENGTH_LONG).show();
            nbpas++;
        }

    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //String srcGPXExtra = getIntent().getStringExtra("srcGPX");
       // Log.v("result", srcGPXExtra);
       //  if(srcGPXExtra != null){
          // srcGPX = srcGPXExtra;
          //  Toast.makeText(this, "dsfsd", Toast.LENGTH_LONG).show();
             // Log.v("result", srcGPXExtra);
       // }

       // Log.v("result", srcGPXExtra);
       // Toast.makeText(this, srcGPXExtra, Toast.LENGTH_LONG).show();


       // getIntent();

       /* StepDetectionHandler sdh = new StepDetectionHandler((SensorManager)getSystemService(Context.SENSOR_SERVICE));
        sdh.setStepDetectionListener(sListener);
        sdh.start();*/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("idi na hui"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

       // LatLng point1 = new LatLng(45.189, 5.704);
       // LatLng point2 = new LatLng(45.188, 5.725);
       // LatLng point3 = new LatLng(45.191, 5.733);
       // LatLng grenoble = new LatLng(45.19, 5.72);

      //  mMap.addMarker(new MarkerOptions().position(point1).title("Flo idi na hui"));
     //   mMap.addMarker(new MarkerOptions().position(point2).title("idi na hui"));
      //  mMap.addMarker(new MarkerOptions().position(point3).title("idi na hui"));
        /*Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(point1, point2, point3)
                .width(5)
                .color(Color.RED));*/
               // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(grenoble, 15));
        //CameraUpdateFactory.newLatLngBounds(new LatLngBounds(new LatLng(latSouthwest, lngSouthwest),new LatLng(latNortheast, lngNortheast)), 100);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try{
          InputStream is = getAssets().open("simple.gpx");

           // Uri u = null;
            //File f = new File(u.toString());
           // storage/emulated/0/Download/simple-1.gpx
           // File f = new File("/storage/emulated/0/Download/simple.gpx");
            //if (f.exists()){
            //    Toast.makeText(this, "existe", Toast.LENGTH_LONG).show();
          //  }
          ///InputStream is = new FileInputStream(f);

            //openFileInput("simple.gpx");
            GPX fgpx = GPX.parse(is);
            LatLngBounds bounds = fgpx.getLatLngBounds(fgpx);
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,500, 500, 10));
           // CameraUpdateFactory.newLatLngBounds(new LatLngBounds(new LatLng(45.180546110495925,5.762595670670271),new LatLng(45.186764393001795,5.7666791416704655)), 100);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.180546110495925,5.762595670670271), 15));



            for(GPX.Track track : fgpx) {
                for (GPX.TrackSegment trackSeg : track) {
                    Random rand = new Random();
                    PolylineOptions polylineOptions = new PolylineOptions().width(5).color(-rand.nextInt(0xFFFFFF));

                    for (GPX.TrackPoint trackPoint : trackSeg) {
                        polylineOptions.add(trackPoint.position);
                    }
                    mMap.addPolyline(polylineOptions);
                }

            }
            Log.v("MainActivity", "ouhooo");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.get_gpx) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("text/gpx");
            startActivityForResult(intent,PICKFILE_RESULT_CODE);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode == RESULT_OK){
                    String FilePath = data.getData().getPath();
                    initMap(FilePath);
                     //Toast.makeText(this, FilePath, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void initMap(String srcGPX){
        Intent intent = getIntent();
        intent.putExtra("srcGPX", srcGPX);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


}
