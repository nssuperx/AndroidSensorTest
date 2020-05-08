package com.example.rotationtest;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

// 参考ページ: https://akira-watson.com/android/gyroscope.html

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView textViewGyro, textViewAccel, textInfoGyro, textInfoAccel, textViewDegree;
    private float degreeX, degreeY, degreeZ;
    private long preTimeStamp = 0;

    // onCreate　Activity生成時 初期化
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // インスタンス取得
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        textInfoGyro = findViewById(R.id.text_info_gyro);
        textViewGyro = findViewById(R.id.text_gyro);
        textInfoAccel = findViewById(R.id.text_info_accel);
        textViewAccel = findViewById(R.id.text_accel);
        textViewDegree = findViewById(R.id.text_degree);
    }

    // onResume Activity表示時
    @Override
    protected void onResume() {
        degreeX = 0.0f;
        degreeY = 0.0f;
        degreeZ = 0.0f;

        super.onResume();

        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // ジャイロスコープなかった時の処理
        if(gyro != null){
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_UI);
        }else{
            String ns = "No Support";
            textViewGyro.setText(ns);
        }


        if(accel != null){
            sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            String ns = "No Support";
            textViewAccel.setText(ns);
        }
    }

    // onPause
    protected void onPause() {

        super.onPause();
        // 解除
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("debug","onSensorChanged");

        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            float sensorX = event.values[0];
            float sensorY = event.values[1];
            float sensorZ = event.values[2];
            long timeStamp = event.timestamp;
            float dt = (float) (timeStamp - preTimeStamp) * (float) Math.pow(10, -9);
            preTimeStamp = timeStamp;

            degreeX += sensorX * dt;
            degreeY += sensorY * dt;
            degreeZ += sensorZ * dt;

            String strSensorValue = String.format(Locale.US, "gyroscope:\n" + "X: %f\nY: %f\nZ: %f\n timestamp: %d\n dt: %f\n", sensorX, sensorY, sensorZ, timeStamp, dt);
            String strDegreeValue = String.format(Locale.US, "degree:\n" + "X: %f\nY: %f\nZ: %f\n", degreeX, degreeY, degreeZ);

            textViewGyro.setText(strSensorValue);
            textViewDegree.setText(strDegreeValue);

            showInfo(event);

        }

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float sensorX = event.values[0];
            float sensorY = event.values[1];
            float sensorZ = event.values[2];

            String strSensorValue = String.format(Locale.US, "accelerometer:\n" + "X: %f\nY: %f\nZ: %f\n", sensorX, sensorY, sensorZ);

            textViewAccel.setText(strSensorValue);

            showInfo(event);
        }
    }

    private void showInfo(SensorEvent event){
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
        info.append(String.valueOf(data));
        info.append("\n");

        // require API Level 21
        /*
        data = event.sensor.getMaxDelay();
        info.append("MaxDelay: ");
        info.append(String.valueOf(data));
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
        info.append(String.valueOf(fData));
        info.append("\n");

        info.append("Resolution: ");
        fData = event.sensor.getResolution();
        info.append(String.valueOf(fData));
        info.append(" m/s^2\n");

        info.append("Power: ");
        fData = event.sensor.getPower();
        info.append(String.valueOf(fData));
        info.append(" mA\n");

        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            textInfoGyro.setText(info);
        }

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            textInfoAccel.setText(info);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Hello world!!!
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textview = findViewById(R.id.textView);
        textview.setText(R.string.text);
    }
    */


}
