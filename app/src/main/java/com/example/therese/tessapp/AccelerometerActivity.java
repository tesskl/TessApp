package com.example.therese.tessapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener{

    Sensor accelerometer;
    SensorManager sm;
    TextView view;
    float ALPHA = 0.25f; // if ALPHA = 1 OR 0, no filter applies.
    float[] gravSensorVals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hi there!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        view = (TextView)findViewById(R.id.acceleration);
    }


    protected float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;
        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        gravSensorVals = lowPass(event.values.clone(), gravSensorVals);
        String s = ("X: " + gravSensorVals[0] +
                "\nY: " + gravSensorVals[1] +
                "\nZ: " + gravSensorVals[2]);
        view.setText(s);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
