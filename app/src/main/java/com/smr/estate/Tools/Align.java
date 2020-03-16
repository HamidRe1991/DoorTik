package com.smr.estate.Tools;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.smr.estate.R;

public class Align extends AppCompatActivity implements SensorEventListener
{
    private Sensor sensor;
    private SensorManager sensorManager;
    private ImageView imgHorizontal, imgVertical, imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_align);

        imgVertical = findViewById(R.id.imgVertical);
        imgHorizontal = findViewById(R.id.imgHorizontal);
        imgBack = findViewById(R.id.imgAlignBack);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        imgHorizontal.setTranslationY(event.values[1] * 30);
        imgVertical.setTranslationX(event.values[0] * 30);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
