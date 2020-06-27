package com.hackasia.smart_trolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import util.SensorListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    Fragment googleMapFragment = new GoogleMapFragment();
    Fragment autoNavFragment = new AutoNavControlFragment();
    private static final String TAG = "MainActivity";
    private SensorManager sensorManager;
    Sensor accelerometer;
    Sensor directionsensor;
    Sensor gravitysensor;
    SensorListener sensorlistener = new SensorListener();
    double speed = 0;
    double direction = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create for the autoNav fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container,autoNavFragment);
        transaction.commit();


        //register the sensor service and bind to the main activity
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        directionsensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gravitysensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        sensorManager.registerListener((SensorEventListener) MainActivity.this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) MainActivity.this,directionsensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) MainActivity.this,gravitysensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            speed = sensorlistener.linearAccChange(sensorEvent);
            Log.d(TAG, Double.toString(speed));
        }
        if(sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY)
            sensorlistener.gravityChange(sensorEvent);
        if(sensorEvent.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
            sensorlistener.mFieldChange(sensorEvent);
        if(sensorlistener.getGravity()!=null && sensorlistener.getMfield()!=null){
            direction = sensorlistener.directionCalculation();
            //Log.d(TAG, Double.toString(direction));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
/***
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        sensorlistener.onSensorChangedListener(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    ***/
}