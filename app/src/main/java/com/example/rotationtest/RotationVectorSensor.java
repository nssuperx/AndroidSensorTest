package com.example.rotationtest;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.Locale;

public class RotationVectorSensor {
    private static float rad2degree = (float) (180.0/Math.PI);
    public static void viewValue(SensorEvent event, TextView textView){
        float sensorX = event.values[0];
        float sensorY = event.values[1];
        float sensorZ = event.values[2];
        float sensorW = event.values[3];

//        float[] quat = {sensorX, sensorY, sensorZ, sensorW};
//        float[] rm = new float[9];
//        float[] euler = new float[3];
//        SensorManager.getRotationMatrixFromVector(rm, quat);
//        SensorManager.getOrientation(rm, euler);

        StringBuffer strSensorValue = new StringBuffer();
        strSensorValue.append(String.format(Locale.US, "rotation_vector:\n" + "X: %f\nY: %f\nZ: %f\nS: %f\n", sensorX, sensorY, sensorZ, sensorW));
//        strSensorValue.append("\n");
//        strSensorValue.append(String.format(Locale.US, "euler:\n" + "X: %f\nY: %f\nZ: %f\n", euler[0] * rad2degree, euler[1] * rad2degree, euler[2] * rad2degree));

        textView.setText(strSensorValue.toString());
    }
}
