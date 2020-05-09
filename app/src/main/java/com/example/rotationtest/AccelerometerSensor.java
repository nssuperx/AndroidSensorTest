package com.example.rotationtest;

import android.hardware.SensorEvent;
import android.widget.TextView;

import java.util.Locale;

public class AccelerometerSensor {
    public static void viewValue(SensorEvent event, TextView textView){
        float sensorX = event.values[0];
        float sensorY = event.values[1];
        float sensorZ = event.values[2];

        String strSensorValue = String.format(Locale.US, "accelerometer:\n" + "X: %f\nY: %f\nZ: %f\n", sensorX, sensorY, sensorZ);

        textView.setText(strSensorValue);
    }
}
