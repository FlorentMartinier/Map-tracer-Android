package fr.upmfgrenoble.wicproject.pdr;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by Sonin on 18.12.2015.
 */
public class DeviceAttitudeHandler implements SensorEventListener {


    private SensorManager sm;
    private Sensor mSensor;
    private float yaw;
    //private StepDetectionListener sListener;

    public DeviceAttitudeHandler(SensorManager sma){
        sm = sma;
        mSensor = sma.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void start(){
        sm.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if( event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR )
         {
             float[]rotMat=new float[9];
             float[]values=new float[3] ;
            
             SensorManager.getRotationMatrixFromVector(rotMat, event.values);
             SensorManager.getOrientation(rotMat, values);
            
             yaw = values[0];
          }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public  float getYaw(){
        return yaw;
    }
}
