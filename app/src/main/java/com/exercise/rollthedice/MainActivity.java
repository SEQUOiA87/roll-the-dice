package com.exercise.rollthedice;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DiceRoller diceRoller;
    private ImageView imageViewDice;
    private Random randomNumberGenerator;
    private ShakeDetector shakeDetector;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageViewDice = findViewById(R.id.iamge_view_dice);
        randomNumberGenerator = new Random();
        diceRoller = new DiceRoller();
        shakeDetector = new ShakeDetector();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        imageViewDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRollingDice();
            }
        });

        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                animateRollingDice();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    private void animateRollingDice() {
        final Handler handler = new Handler();

        final Runnable animation = new Runnable() {
            @Override
            public void run() {
                int randomNumber = randomNumberGenerator.nextInt(6) + 1;
                diceRoller.rollDice(randomNumber, imageViewDice);
                handler.postDelayed(this, 200);
            }
        };
        handler.post(animation);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(animation);
            }
        }, 3000);
    }
}
