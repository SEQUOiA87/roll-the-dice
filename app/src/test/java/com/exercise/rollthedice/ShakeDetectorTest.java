package com.exercise.rollthedice;

import java.lang.reflect.Field;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ShakeDetectorTest
{

    private ShakeDetector shakeDetector;
    private float tempShakeThresholdGravity;
    private Field shakeThresholdGravityField;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException
    {
        shakeDetector = new ShakeDetector();

        shakeThresholdGravityField = ShakeDetector.class.getDeclaredField("SHAKE_THRESHOLD_GRAVITY");
        shakeThresholdGravityField.setAccessible(true);
        tempShakeThresholdGravity = (float) shakeThresholdGravityField.get(shakeDetector);
        shakeThresholdGravityField.setFloat(shakeDetector, Float.MIN_VALUE);
    }

    @After
    public void tearDown() throws IllegalAccessException
    {
        shakeThresholdGravityField.setFloat(shakeDetector, tempShakeThresholdGravity);
    }

    @Test
    public void setOnShakeListener() throws NoSuchFieldException, IllegalAccessException, InterruptedException
    {
        ShakeDetector.OnShakeListener onShakeListener = mock(ShakeDetector.OnShakeListener.class);
        shakeDetector.setOnShakeListener(onShakeListener);

        SensorEvent sensorEvent = mock(SensorEvent.class);
        final Field valuesField = SensorEvent.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        valuesField.set(sensorEvent, new float[] {100f, 100f, 100f});

        shakeDetector.onSensorChanged(sensorEvent);
        verify(onShakeListener).onShake(anyInt());

        reset(onShakeListener);
        shakeDetector.onSensorChanged(sensorEvent);
        verify(onShakeListener, never()).onShake(anyInt());

        reset(onShakeListener);
        synchronized (this)
        {
            wait(1500);
        }
        shakeDetector.onSensorChanged(sensorEvent);
        verify(onShakeListener).onShake(anyInt());

        reset(onShakeListener);
        shakeThresholdGravityField.setFloat(shakeDetector, Float.MAX_VALUE);
        shakeDetector.onSensorChanged(sensorEvent);
        verifyNoMoreInteractions(onShakeListener);

        shakeDetector.onAccuracyChanged(mock(Sensor.class), 0);
        verifyNoMoreInteractions(onShakeListener);

        shakeDetector.setOnShakeListener(null);
        shakeDetector.onSensorChanged(sensorEvent);
    }
}