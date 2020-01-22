package com.exercise.rollthedice;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
	private ImageView imageViewDice;
	private Random rng = new Random();
	private ShakeDetector shakeDetector;
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private boolean animationRunning;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageViewDice = findViewById(R.id.image_view_dice);
		shakeDetector = new ShakeDetector();
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		imageViewDice.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
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
		sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		sensorManager.unregisterListener(shakeDetector);
		super.onPause();
	}

	private void animateRollingDice()
	{
		if (!animationRunning)
		{
			animationRunning = true;
			final Handler handler = new Handler();
			final Runnable animation = new Runnable()
			{
				@Override
				public void run()
				{
					rollDice();
					handler.postDelayed(this, 400);
				}
			};
			handler.post(animation);


			handler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					animationRunning = false;
					handler.removeCallbacks(animation);
				}
			}, 3000);
		}
		else {
			Toast.makeText(this, "rolling the dice already", Toast.LENGTH_SHORT).show();
		}
	}

	private void rollDice()
	{
		int randomNumber = rng.nextInt(6) + 1;
		switch (randomNumber)
		{
			case 1:
				imageViewDice.setImageResource(R.drawable.dice1);
				break;
			case 2:
				imageViewDice.setImageResource(R.drawable.dice2);
				break;
			case 3:
				imageViewDice.setImageResource(R.drawable.dice3);
				break;
			case 4:
				imageViewDice.setImageResource(R.drawable.dice4);
				break;
			case 5:
				imageViewDice.setImageResource(R.drawable.dice5);
				break;
			case 6:
				imageViewDice.setImageResource(R.drawable.dice6);
				break;
		}
	}
}

