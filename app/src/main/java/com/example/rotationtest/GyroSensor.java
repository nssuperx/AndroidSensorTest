package com.example.rotationtest;

import android.hardware.SensorEvent;
import android.widget.TextView;

import java.util.Locale;

public class GyroSensor {
    private static long preTimeStamp = 0;

    public static void viewValue(SensorEvent event, TextView textView){
        float sensorX = event.values[0];
        float sensorY = event.values[1];
        float sensorZ = event.values[2];

        long timeStamp = event.timestamp;
        float dt = (float) (timeStamp - preTimeStamp) * (float) Math.pow(10, -9);
        preTimeStamp = timeStamp;

        String strSensorValue = String.format(Locale.US, "gyroscope:\n" + "X: %f\nY: %f\nZ: %f\ntimestamp: %d\ndt: %f\n", sensorX, sensorY, sensorZ, timeStamp, dt);
        textView.setText(strSensorValue);

    }
}