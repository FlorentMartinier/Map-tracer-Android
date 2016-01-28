package fr.upmfgrenoble.wicproject.pdr;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sonin on 18.12.2015.
 */
public class StepPositioningHandler {
    private LatLng mCurrentLocation;

    public StepPositioningHandler(LatLng l){
        mCurrentLocation = l;
    }
    public void setCurrentPosition(LatLng cp){
        mCurrentLocation = cp;
    }
    public LatLng getCurrentPosition(){
        return mCurrentLocation;
    }

    public LatLng computeNextStep(double stepSize, double bearing){
        stepSize = stepSize/1000; // conversion en metres.

        double x = Math.asin(Math.sin(Math.toRadians(mCurrentLocation.latitude))*Math.cos(stepSize/ 6378.0)+
                   Math.cos(Math.toRadians(mCurrentLocation.latitude))*Math.sin(stepSize / 6378.0)*Math.cos(bearing));
        double y = (Math.toRadians(mCurrentLocation.longitude)) + Math.atan2(Math.sin(bearing) * Math.sin(stepSize / 6378.0) * Math.cos(Math.toRadians(mCurrentLocation.latitude)),
                Math.cos(stepSize / 6378.0) - Math.sin(Math.toRadians(mCurrentLocation.latitude)) * Math.sin(x));
        Log.v("LatLngCourrent", Math.toDegrees(x)+" xxxxxxxxx");
        Log.v("LatLngCourrent", Math.toDegrees(y)+" yyyyyyyyyy");

        return new LatLng(Math.toDegrees(x),Math.toDegrees(y));
    }
}
