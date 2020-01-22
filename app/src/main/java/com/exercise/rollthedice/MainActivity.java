package com.exercise.rollthedice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView imageViewDice;
    private Random randomNumberGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageViewDice = findViewById(R.id.iamge_view_dice);
        randomNumberGenerator = new Random();

        imageViewDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRollingDice();
            }
        });
    }

    private void animateRollingDice() {
        final Handler handler = new Handler();

        final Runnable animation = new Runnable() {
            @Override
            public void run() {
                rollDice();
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

    private void rollDice() {
        int randomNumber = randomNumberGenerator.nextInt(6) + 1;

        switch (randomNumber) {
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
            default:
                break;
        }
    }
}
