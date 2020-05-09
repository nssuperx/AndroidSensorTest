package com.example.rotationtest;

import android.hardware.SensorEvent;
import android.widget.TextView;

import java.util.Locale;

public class MagneticFieldSensor {
    public static void viewValue(SensorEvent event, TextView textView){
        float sensorX = event.values[0];
        float sensorY = event.values[1];
        float sensorZ = event.values[2];

        String strSensorValue = String.format(Locale.US, "rotation_vector:\n" + "X: %f\nY: %f\nZ: %f\n", sensorX, sensorY, sensorZ);

        textView.setText(strSensorValue);
    }
}
