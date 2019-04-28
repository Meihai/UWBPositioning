package com.onlylemi.mapview.agorithm.pdr;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Vector;

/**
 * Created by admin on 2017/12/16.
 */

public class StepDetectionHandler implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Vector<Float> CalculeMoyenne = new Vector<Float>();
    private StepDetectionListener listnerStemps ;
    private float DistanceParcourue ;
    private float[] acceleration = new float[3];

    //Listner
    public interface StepDetectionListener {
        public void onNewStep( float stepSize);
    }

    public StepDetectionHandler(SensorManager sensorM ){
        this.mSensorManager = sensorM ;
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    public void setSterpDetectionListner( StepDetectionListener ls ){
        listnerStemps  = ls ;
    }

    public void start(){
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        mSensorManager.unregisterListener(this);
    }

    /* à chaque mouvement on récupère la valeur de Y et on l'evalue pour savoir si l'utilisateur s'est déplacé ou pas*/
    @Override
    public void onSensorChanged(SensorEvent event) {

        /* La valeur de Y */
        float postitionY = event.values[1];

        /* calcule de la moyenne */
        float moyenne    = CalculeMoyenne( postitionY ) ;

        /* si la valeur de la moyenne est suppérieur à 1.1 , la valeur observée lors des test , alors on considère qu'il s'agit d'un déplacement */
        if ( moyenne > 1.2 ){

            listnerStemps.onNewStep((float) 0.8);
        }

    }

    /* Calcule de la moyenne des 5 dernières valeurs */
    public float CalculeMoyenne( float valeur ){

        /* somme des 5 dernières valeurs*/
        float tmpValeur = 0 ;
        /* valeur de la moyenne */
        float moyenne   = 0 ;
        CalculeMoyenne.add(valeur);
        if( CalculeMoyenne.size() >= 4 ){
            CalculeMoyenne.removeElementAt(0);

        }

        for(float derniereValeur : CalculeMoyenne) {
            tmpValeur += derniereValeur;
        }

        moyenne = tmpValeur / CalculeMoyenne.size() ;
        return moyenne ;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
}
