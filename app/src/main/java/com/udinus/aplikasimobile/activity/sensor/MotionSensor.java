package com.udinus.aplikasimobile.activity.sensor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.udinus.aplikasimobile.R;

public class MotionSensor extends AppCompatActivity {
    private Accelerometer accelerometer;
    private Gyroscope gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_sensor);

        accelerometer = new Accelerometer(this);
        gyroscope = new Gyroscope(this);

        accelerometer.setListener((tx, ty, tz) -> {
            if (tx > 1.0f) {
                getWindow().getDecorView().setBackground(AppCompatResources.getDrawable(this, R.color.red));
            } else if (tx < -1.0f) {
                getWindow().getDecorView().setBackground(AppCompatResources.getDrawable(this, R.color.blue));
            }
        });

        gyroscope.setListener((rx, ry, rz) -> {
            if (rz > 1.0f) {
                getWindow().getDecorView().setBackground(AppCompatResources.getDrawable(this, R.color.green));
            } else if (rz < -1.0f) {
                getWindow().getDecorView().setBackground(AppCompatResources.getDrawable(this, R.color.yellow));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.register();
        gyroscope.register();
    }

    @Override
    protected void onPause() {
        super.onPause();

        accelerometer.unregister();
        gyroscope.unregister();
    }
}