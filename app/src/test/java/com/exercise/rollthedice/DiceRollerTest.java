package com.exercise.rollthedice;

import android.media.Image;
import android.widget.ImageView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DiceRollerTest
{
    DiceRoller diceRoller;
    ImageView imageView;

    @Before
    public void setUp() throws Exception
    {
        diceRoller = new DiceRoller();
        imageView = mock(ImageView.class);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void doNotRollDice()
    {
        diceRoller.rollDice(0, imageView);
        verify(imageView, never()).setImageResource(anyInt());
    }

    @Test
    public void rollDice1()
    {
        diceRoller.rollDice(1, imageView);
        verify(imageView).setImageResource(R.drawable.dice1);
    }

    @Test
    public void rollDice2()
    {
        diceRoller.rollDice(2, imageView);
        verify(imageView).setImageResource(R.drawable.dice2);
    }

    @Test
    public void rollDice3()
    {
        diceRoller.rollDice(3, imageView);
        verify(imageView).setImageResource(R.drawable.dice3);
    }

    @Test
    public void rollDice4()
    {
        diceRoller.rollDice(4, imageView);
        verify(imageView).setImageResource(R.drawable.dice4);
    }

    @Test
    public void rollDice5()
    {
        diceRoller.rollDice(5, imageView);
        verify(imageView).setImageResource(R.drawable.dice5);
    }

    @Test
    public void rollDice6()
    {
        diceRoller.rollDice(6, imageView);
        verify(imageView).setImageResource(R.drawable.dice6);
    }
}