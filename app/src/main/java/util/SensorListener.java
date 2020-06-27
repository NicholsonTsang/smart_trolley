package util;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;


public class SensorListener {

    private SensorManager sensorManager;
    private int sample_period = 40; //the interval between two acceleration value measure should > 40 ms
    private long last_acc_measure_time = System.currentTimeMillis();
    private long current_time;
    private float[] gravity = null; //sensor x y z value for the gravity
    private float[] mfield = null;
    double a; //acceleration

   public double linearAccChange(SensorEvent sensorEvent) {

       current_time = System.currentTimeMillis();
       if ((current_time - last_acc_measure_time) >= sample_period) {
           //Log.d("test",Long.toString(last_acc_measure_time)+ "  "+Long.toString(current_time));
           a = Math.sqrt(Math.pow(sensorEvent.values[0], 2) + Math.pow(sensorEvent.values[1], 2));

           if (Math.abs(a) < 0.25)
               a = 0;
           double speed = a * (current_time - last_acc_measure_time);
           //Log.d("test",Double.toString(a));
           last_acc_measure_time = current_time;
           //Log.d("test",Long.toString(current_time - last_acc_measure_time) + Long.toString(current_time)+ "    "+Long.toString(last_acc_measure_time));
       }

       return a;
   }


   public float[] gravityChange(SensorEvent sensorEvent){
       gravity = sensorEvent.values;
       return gravity;
   }


   public float[] mFieldChange(SensorEvent sensorEvent){
       mfield = sensorEvent.values;
       return mfield;
   }


   public float directionCalculation(){

       if(gravity !=null && mfield !=null){
           float R[] = new float[9];
           float I[] = new float[9];
           boolean success = SensorManager.getRotationMatrix(R, I, gravity, mfield);

           if(success){
               float orientation[] = new float[3];
               SensorManager.getOrientation(R,orientation);
               float azimuth = orientation[0];
               float azimuthInDegree  = (float)(Math.toDegrees(azimuth)+360)%360;
               //Log.d("azimuth", Double.toString(azimuthInDegree));
               return azimuthInDegree;
           }
       }
       return 0;
   }

    public float[] getGravity() {
        return gravity;
    }

    public float[] getMfield() {
        return mfield;
    }
}

