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
    private TextView textViewGyro, textViewAccel, textViewDegree, textViewDistance, textViewRotVec,
            textViewEuler, textViewMagnetic, textViewDegAccMag,textViewGravity, textViewDegGraMag;
    private float degreeX, degreeY, degreeZ, distanceX, distanceY, distanceZ;
    private long preTimeStamp = 0;
    float[] rotationMatrix = new float[9];
    float[] acceler = new float[3];
    float[] magnetic = new float[3];
    float[] gravi = new float[3];
    float[] degree = new float[3];

    // onCreate　Activity生成時 初期化
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // インスタンス取得
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        textViewGyro = findViewById(R.id.text_gyro);
        textViewAccel = findViewById(R.id.text_accel);
        textViewDegree = findViewById(R.id.text_degree);
        textViewDistance = findViewById(R.id.text_distance);
        textViewRotVec = findViewById(R.id.text_vector);
        textViewEuler = findViewById(R.id.text_euler);
        textViewMagnetic = findViewById(R.id.text_magnetic);
        textViewDegAccMag = findViewById(R.id.text_deg_acc_mag);
        textViewGravity = findViewById(R.id.text_gravity);
        textViewDegGraMag = findViewById(R.id.text_deg_gra_mag);
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
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        Sensor rot_vec = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

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

        if(gravity != null){
            sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_UI);
        }else{
            String ns = "No Support";
            textViewMagnetic.setText(ns);
        }
    }

    // onPause
    protected void onPause() {

        super.onPause();
        // 解除
        sensorStop();
    }

    protected void sensorStop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("debug","onSensorChanged");

        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){

            GyroSensor.viewValue(event, textViewGyro);
        }

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            AccelerometerSensor.viewValue(event, textViewAccel);
            acceler = event.values.clone();
        }

        if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            RotationVectorSensor.viewValue(event, textViewRotVec);
        }

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            MagneticFieldSensor.viewValue(event, textViewMagnetic);
            magnetic = event.values.clone();
        }

        if(event.sensor.getType() == Sensor.TYPE_GRAVITY){
            GravitySensor.viewValue(event, textViewGravity);
            gravi = event.values.clone();
        }


        // 参考ページ:https://developer.android.com/reference/android/hardware/SensorManager#getRotationMatrix(float[],%20float[],%20float[],%20float[])
        // ここの計算はパソコン側にやらせる。
        /*
        if(magnetic != null && acceler != null){
            SensorManager.getRotationMatrix(rotationMatrix, null, acceler, magnetic);
            SensorManager.getOrientation(rotationMatrix, degree);
            String strDegreeValue = String.format(Locale.US, "accelerometer_degree:\n" + "X: %f\nY: %f\nZ: %f\n", degree[0] * (float) (180/Math.PI), degree[1] * (float) (180/Math.PI), degree[2] * (float) (180/Math.PI));
            textViewDegAccMag.setText(strDegreeValue);
        }

        if(magnetic != null && gravi != null){
            SensorManager.getRotationMatrix(rotationMatrix, null, gravi, magnetic);
            SensorManager.getOrientation(rotationMatrix, degree);
            String strDegreeValue = String.format(Locale.US, "gravity_degree:\n" + "X: %f\nY: %f\nZ: %f\n", degree[0] * (float) (180/Math.PI), degree[1] * (float) (180/Math.PI), degree[2] * (float) (180/Math.PI));
            textViewDegGraMag.setText(strDegreeValue);
        }

         */
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
