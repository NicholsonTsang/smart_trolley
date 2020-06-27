package com.hackasia.smart_trolley;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import util.SensorListener;

public class AutoNavControlFragment extends Fragment implements SensorEventListener{

    private SensorManager sensorManager;
    Sensor accelerometer;
    Sensor directionsensor;
    Sensor gravitysensor;
    SensorListener sensorlistener = new SensorListener();
    double speed = 0;
    double direction = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.auto_nav_state,container,false);


        //register the sensor service and bind to the main activity
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        directionsensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gravitysensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        //setting onclick listener for the start button
        v.findViewById(R.id.startbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.registerListener((SensorEventListener) AutoNavControlFragment.this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener((SensorEventListener) AutoNavControlFragment.this,directionsensor, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener((SensorEventListener) AutoNavControlFragment.this,gravitysensor, SensorManager.SENSOR_DELAY_NORMAL);


            }
        });

        v.findViewById(R.id.stopbtn).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(AutoNavControlFragment.this);
            }
        });

        return v;
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            speed = sensorlistener.linearAccChange(sensorEvent);
            TextView speedvalue = getView().findViewById(R.id.speedvalue);
            speedvalue.setText(String.format("%.2f",speed));
            //Log.d("test", Double.toString(speed));
        }
        if(sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY)
            sensorlistener.gravityChange(sensorEvent);
        if(sensorEvent.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
            sensorlistener.mFieldChange(sensorEvent);
        if(sensorlistener.getGravity()!=null && sensorlistener.getMfield()!=null){
            direction = sensorlistener.directionCalculation();
            TextView degreevalue = getView().findViewById(R.id.degreevalue);
            degreevalue.setText(String.format("%.1f",direction));
            //Log.d("test", Double.toString(direction));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
