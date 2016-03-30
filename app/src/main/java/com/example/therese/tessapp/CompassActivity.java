package com.example.therese.tessapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



public class CompassActivity extends Activity implements SensorEventListener {
            // define the display assembly compass picture
    TextView tvHeading;
            // record the compass picture angle turned
    private ImageView image;
            // device sensor manager
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    Button button;

    /** Called when the user clicks the StartActivity button */
    public void startCompass(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        image = (ImageView) findViewById(R.id.imageViewCompass);
        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }



    @Override
    protected void onResume() {
        super.onResume();
        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }



    @Override
    protected void onPause() {
        super.onPause();
        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        // how long the animation will take place
        ra.setDuration(210);
        // set the animation after the end of the reservation status
        ra.setFillAfter(true);
        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}

