package fr.upmfgrenoble.wicproject.ui;

import android.content.Context;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import fr.upmfgrenoble.wicproject.R;
import fr.upmfgrenoble.wicproject.pdr.DeviceAttitudeHandler;
import fr.upmfgrenoble.wicproject.pdr.StepDetectionHandler;
import fr.upmfgrenoble.wicproject.pdr.StepPositioningHandler;
import viewer.GoogleMapTracer;

public class PDRActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, StepDetectionHandler.StepDetectionListener {

    private GoogleMap mMap;
    private SensorManager sm;
    private StepDetectionHandler sdh;
    private StepPositioningHandler sph;
    private DeviceAttitudeHandler dah;
    private GoogleMapTracer tracer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdr);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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

        mMap.setOnMapClickListener(this);
        tracer = new GoogleMapTracer(mMap);

    }

    @Override
    public void onMapClick(LatLng latLng) {

        sph = new StepPositioningHandler(latLng);
        Log.v("LatLngCourrent", sph.getCurrentPosition().toString());
        Log.v("LatLngCourrent", latLng.toString());

        sdh = new StepDetectionHandler(sm);
        dah = new DeviceAttitudeHandler(sm);
        sdh.start();
        dah.start();
        //sph.setCurrentPosition(latLng);
        sdh.setStepDetectionListener(this);

        tracer.newSegment();
        tracer.newPoint(latLng);
    }

    @Override
        public void newStep(double stepSize) {
        Log.v("step1", sph.getCurrentPosition().toString());
        LatLng latlng = sph.computeNextStep(stepSize, (double)dah.getYaw());
        Log.v("step", latlng.toString());
        tracer.newPoint(latlng);
        sph.setCurrentPosition(latlng);
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        sdh.stop();
        dah.stop();
    }
}
