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
    private TextView textViewGyro, textViewAccel, textInfoGyro, textInfoAccel, textViewDegree, textViewDistance, textViewRotVec, textInfoRotVec, textViewEuler, textInfoMagnetic, textViewMagnetic, textViewDegAccMag;
    private float degreeX, degreeY, degreeZ, distanceX, distanceY, distanceZ;
    private long preTimeStamp = 0;
    float[] rotationMatrix = new float[9];
    float[] acceler = new float[3];
    float[] magnetic = new float[3];
    float[] degree = new float[3];

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
        textViewDistance = findViewById(R.id.text_distance);
        textInfoRotVec = findViewById(R.id.text_info_vector);
        textViewRotVec = findViewById(R.id.text_vector);
        textViewEuler = findViewById(R.id.text_euler);
        textInfoMagnetic = findViewById(R.id.text_info_magnetic);
        textViewMagnetic = findViewById(R.id.text_magnetic);
        textViewDegAccMag = findViewById(R.id.text_deg_acc_mag);
    }

    // onResume Activity表示時
    @Override
    protected void onResume() {
        degreeX = 0.0f;
        degreeY = 0.0f;
        degreeZ = 0.0f;

        distanceX = 0.0f;
        distanceY = 0.0f;
        distanceZ = 0.0f;

        super.onResume();

        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        Sensor rot_vec = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // gs03 u9200 no support
        //Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        //Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);

        // ジャイロスコープなかった時の処理
        if(gyro != null){
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_UI);
        }else{
            String ns = "No Support";
            textViewGyro.setText(ns);
        }


        if(accel != null){
            sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        }else{
            String ns = "No Support";
            textViewAccel.setText(ns);
        }

        if(rot_vec != null){
            sensorManager.registerListener(this, rot_vec, SensorManager.SENSOR_DELAY_UI);
        }else{
            String ns = "No Support";
            textViewRotVec.setText(ns);
        }

        if(magnetic != null){
            sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_UI);
        }else{
            String ns = "No Support";
            textViewMagnetic.setText(ns);
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

//        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
//            showInfo(event, textInfoGyro);
//            GyroSensor.viewValue(event, textViewGyro);
//        }
//
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            showInfo(event, textInfoAccel);
            AccelerometerSensor.viewValue(event, textViewAccel);
            acceler = event.values.clone();
        }
//
//        if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
//            showInfo(event, textInfoRotVec);
//            RotationVectorSensor.viewValue(event, textViewRotVec);
//        }

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            showInfo(event, textInfoMagnetic);
            MagneticFieldSensor.viewValue(event, textViewMagnetic);
            magnetic = event.values.clone();
        }

        if(magnetic != null && acceler != null){
            SensorManager.getRotationMatrix(rotationMatrix, null, acceler, magnetic);
            SensorManager.getOrientation(rotationMatrix, degree);
            String strDegreeValue = String.format(Locale.US, "degree:\n" + "X: %f\nY: %f\nZ: %f\n", degree[0] * (float) (180/Math.PI), degree[1] * (float) (180/Math.PI), degree[2] * (float) (180/Math.PI));
            textViewDegAccMag.setText(strDegreeValue);
        }
    }

    private void showInfo(SensorEvent event, TextView textView){
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

        textView.setText(info);
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
