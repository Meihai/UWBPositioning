package com.onlylemi.mapview.agorithm.pdr;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by admin on 2017/12/16.
 */

public class DeviceAttitudeHandle implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor orientSensor;
    private float[] mRotationMatrixFromVector = new float[16] ;
    private float[] mRotationMatrix = new float[16];
    private float[] orientationVals = new float[3];

    public DeviceAttitudeHandle(SensorManager sensorM){
        mSensorManager=sensorM;
        orientSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void start(){
        mSensorManager.registerListener(this,orientSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType()==Sensor.TYPE_ROTATION_VECTOR){
            mSensorManager.getRotationMatrixFromVector(mRotationMatrixFromVector,event.values);
            mSensorManager.remapCoordinateSystem(mRotationMatrixFromVector,SensorManager.AXIS_X,SensorManager.AXIS_Z,mRotationMatrix);
            mSensorManager.getOrientation(mRotationMatrix,orientationVals);
        }

        orientationVals[0] = (float) Math.toDegrees(orientationVals[0]);
        orientationVals[1] = (float) Math.toDegrees(orientationVals[1]);
        orientationVals[2] = (float) Math.toDegrees(orientationVals[2]);
    }
    //getter yaw
    public float getOrientationYaw(){
        return orientationVals[0];
    }
    // getter pitch
    public float getOrientationPitch(){
        return orientationVals[1];
    }

    //getter roll
    public float getOrientationRoll(){
        return orientationVals[2];
    }
    // get Yaw,Pitch,Roll
     public float[] getOrientations(){
         return orientationVals;
     }

     @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){

     }
}
