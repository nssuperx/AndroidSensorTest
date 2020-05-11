package com.example.rotationtest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// 参考ページ: https://akira-watson.com/android/gyroscope.html

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView textViewGyro, textViewAccelerometer, textViewLiner, textViewRotVec, textViewMagnetic, textViewGravity;

    ViewSensorValue gyroValue, accelerometerValue, liner_accelerationValue, rot_vecValue, magneticValue, gravityValue;

    // onCreate　Activity生成時 初期化
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // インスタンス取得
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        textViewGyro = findViewById(R.id.text_gyro);
        textViewAccelerometer = findViewById(R.id.text_accel);
        textViewLiner = findViewById(R.id.text_liner_accel);
        textViewRotVec = findViewById(R.id.text_vector);
        textViewMagnetic = findViewById(R.id.text_magnetic);
        textViewGravity = findViewById(R.id.text_gravity);
    }

    // onResume Activity表示時
    @Override
    protected void onResume() {
        super.onResume();

        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor liner_acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        Sensor rot_vec = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        // gs03 u9200 no support
        //Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        //Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);

        // ジャイロスコープなかった時の処理
        if(gyro != null){
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
            gyroValue = new ViewSensorValue();
        }else{
            textViewGyro.setText("No support");
        }

        if(accelerometer != null){
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            accelerometerValue = new ViewSensorValue();
        }else{
            textViewAccelerometer.setText("No support");
        }

        if(liner_acceleration != null){
            sensorManager.registerListener(this, liner_acceleration, SensorManager.SENSOR_DELAY_NORMAL);
            liner_accelerationValue = new ViewSensorValue();
        }else{
            textViewLiner.setText("No support");
        }

        if(rot_vec != null){
            sensorManager.registerListener(this, rot_vec, SensorManager.SENSOR_DELAY_NORMAL);
            rot_vecValue = new ViewSensorValue();
        }else{
            textViewRotVec.setText("No support");
        }

        if(magnetic != null){
            sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
            magneticValue = new ViewSensorValue();
        }else{
            textViewMagnetic.setText("No support");
        }

        if(gravity != null){
            sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
            gravityValue = new ViewSensorValue();
        }else{
            textViewMagnetic.setText("No support");
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
            gyroValue.viewValue(event, textViewGyro);
        }

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelerometerValue.viewValue(event, textViewAccelerometer);
        }

        if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            liner_accelerationValue.viewValue(event, textViewLiner);
        }

        if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            rot_vecValue.viewValue(event, textViewRotVec);
        }

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magneticValue.viewValue(event, textViewMagnetic);
        }

        if(event.sensor.getType() == Sensor.TYPE_GRAVITY){
            gravityValue.viewValue(event, textViewGravity);
        }


        // 参考ページ:https://developer.android.com/reference/android/hardware/SensorManager#getRotationMatrix(float[],%20float[],%20float[],%20float[])
        // ここの計算はパソコン側にやらせる。
        /*
        if(magnetic != null && accelerometer != null){
            SensorManager.getRotationMatrix(rotationMatrix, null, accelerometer, magnetic);
            SensorManager.getOrientation(rotationMatrix, degree);
            String strDegreeValue = String.format(Locale.US, "accelerometer_degree:\n" + "X: %f\nY: %f\nZ: %f\n", degree[0] * (float) (180/Math.PI), degree[1] * (float) (180/Math.PI), degree[2] * (float) (180/Math.PI));
            textViewDegAccMag.setText(strDegreeValue);
        }

        if(magnetic != null && gravity != null){
            SensorManager.getRotationMatrix(rotationMatrix, null, gravity, magnetic);
            SensorManager.getOrientation(rotationMatrix, degree);
            String strDegreeValue = String.format(Locale.US, "gravity_degree:\n" + "X: %f\nY: %f\nZ: %f\n", degree[0] * (float) (180/Math.PI), degree[1] * (float) (180/Math.PI), degree[2] * (float) (180/Math.PI));
            textViewDegGraMag.setText(strDegreeValue);
        }

         */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
