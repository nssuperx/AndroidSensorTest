package com.example.rotationtest;

import android.hardware.SensorEvent;
import android.widget.TextView;

import java.util.Locale;

public class GravitySensor {
    public static void viewValue(SensorEvent event, TextView textView){
        StringBuffer sensorValue = new StringBuffer();
        sensorValue.append(ShowSensorInfo.showInfo(event));
        float sensorX = event.values[0];
        float sensorY = event.values[1];
        float sensorZ = event.values[2];

        sensorValue.append(String.format(Locale.US, "gravity:\n" + "X: %f\nY: %f\nZ: %f\n", sensorX, sensorY, sensorZ));

        textView.setText(sensorValue);
    }
}
