package com.hackasia.smart_trolley;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import util.SensorListener;

public class AutoNavControlFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.auto_nav_state,container,false);



/***
        //setting onclick listener for the start button
        v.findViewById(R.id.startbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView speed = (TextView)v.findViewById(R.id.speedvaluetxt);

            }
        });
***/
        return v;
    }
}
