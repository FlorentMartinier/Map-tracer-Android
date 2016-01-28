package viewer;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import fr.upmfgrenoble.wicproject.R;

/**
 * Created by Sonin on 20.12.2015.
 */
public class GoogleMapTracer {
    private GoogleMap map;
    private LatLng prec;
    private PolylineOptions p;
    private Marker m;

    public GoogleMapTracer(GoogleMap map){
        this.map = map;
    }

    public void newSegment (){
        map.clear();
        prec = null;
        p = new PolylineOptions().width(5).color(Color.RED);

       //map.addPolyline(new PolylineOptions().width(5).color(Color.RED));
    }

    public void newPoint(LatLng point){
        if (prec == null){
            prec = point;
            map.addMarker(new MarkerOptions().position(prec).title("Votre point depart").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            m = map.addMarker(new MarkerOptions().position(point).title("Votre point d'arrivee").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        }
        m.setPosition(point);

        p.add(prec, point);
        map.addPolyline(p);
        prec = point;

    }

}
