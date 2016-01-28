package fr.upmfgrenoble.wicproject.pdr;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Sonin on 10.12.2015.
 */
public class StepDetectionHandler implements SensorEventListener{
    private SensorManager sm;
    private Sensor mSensor;
    private int pas;
    private float prec;
    private float curr;
    private float [] tabZ;
    private int cptTab;
    private float sommeZ;

    public StepDetectionHandler(SensorManager sma){
        prec=0;
        curr=0;
        pas = 0;
        sm = sma;
        mSensor = sma.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        tabZ =  new float[] {0,0,0,0,0};
        cptTab=0;
        sommeZ=0;
    }

    public void start(){
        sm.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stop(){
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
         prec = curr;

        // --------- calcul de la moyenne des 5 dernieres valeures d'acceleration----------
        tabZ[cptTab] = event.values[0]; // on rajoute la valeur courante dans les 5 dernières valeures.
        cptTab++;
        for (int i=0; i<5; i++){
            sommeZ = sommeZ + tabZ[i];
        }
        curr = sommeZ/5;
        sommeZ=0;
        if (cptTab == 4){
            cptTab=0;
        }
        // ------------ fin du calcul de moyenne-----------

        if (curr < 0.6 && prec > 0.6){
            sListener.newStep((double) 0.8);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private StepDetectionListener sListener;

    public void setStepDetectionListener (StepDetectionListener l){
        this.sListener = l;
    }

    public interface StepDetectionListener{
        public void newStep(double stepSize);
    }
}
