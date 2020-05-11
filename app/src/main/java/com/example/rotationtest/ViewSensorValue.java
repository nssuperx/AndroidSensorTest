package com.example.rotationtest;

import android.hardware.SensorEvent;
import android.widget.TextView;

import java.util.Locale;

public class ViewSensorValue {
    public void viewValue(SensorEvent event, TextView textView){
        StringBuffer strSensorValue = new StringBuffer();
        strSensorValue.append(showInfo(event));
        float[] sensorValues = event.values.clone();
        for(int i=0; i<sensorValues.length; i++){
            strSensorValue.append(String.format(Locale.US, "value[%d]:%f\n", i, sensorValues[i]));
        }
        textView.setText(strSensorValue);
    }

    public StringBuffer showInfo(SensorEvent event){
        int data;
        StringBuffer info = new StringBuffer("Name: ");
        info.append(event.sensor.getName());
        info.append("\n");

        info.append("Vendor: ");
        info.append(event.sensor.getVendor());
        info.append("\n");

        info.append("Type: ");
        info.append(event.sensor.getType());
        info.append("\n");

        data = event.sensor.getMinDelay();
        info.append("MinDelay: ");
        info.append(data);
        info.append("\n");

        // require API Level 21
        /*
        data = event.sensor.getMaxDelay();
        info.append("MaxDelay: ");
        info.append(data);
        info.append("\n");
         */


        // require API Level 21
        /*
        data = event.sensor.getReportingMode();
        String stinfo = "unknown";
        if(data == 0){
            stinfo = "REPORTING_MODE_CONTINUOUS";
        }else if(data == 1){
            stinfo = "REPORTING_MODE_ON_CHANGE";
        }else if(data == 2){
            stinfo = "REPORTING_MODE_ONE_SHOT";
        }
        info.append("ReportingMode: ");
        info.append(stinfo);
        info.append("\n");

         */

        info.append("MaxRange: ");
        float fData = event.sensor.getMaximumRange();
        info.append(fData);
        info.append("\n");

        info.append("Resolution: ");
        fData = event.sensor.getResolution();
        info.append(fData);
        info.append(" m/s^2\n");

        info.append("Power: ");
        fData = event.sensor.getPower();
        info.append(fData);
        info.append(" mA\n");

        return info;
    }
}
