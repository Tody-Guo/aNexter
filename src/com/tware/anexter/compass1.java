package com.tware.anexter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class compass1 extends Activity implements SensorEventListener {
 
  Float azimut;  // View to draw a compass
 
  public class CustomDrawableView extends View {
    Paint paint = new Paint();
    public CustomDrawableView(Context context) {
      super(context);
      paint.setColor(Color.GREEN);
      paint.setStyle(Style.STROKE);
      paint.setStrokeWidth(2);
      paint.setAntiAlias(true);
    };
 
    protected void onDraw(Canvas canvas) {
      int width = getWidth();
      int height = getHeight();
      int centerx = width/2;
      int centery = height/2;
      canvas.drawLine(centerx, 0, centerx, height, paint);
      canvas.drawLine(0, centery, width, centery, paint);
      // Rotate the canvas with the azimut     
      if (azimut != null)
        canvas.rotate(-azimut*360/(2*3.14159f), centerx, centery);
      paint.setColor(Color.BLUE);
      canvas.drawLine(centerx, -1000, centerx, +1000, paint);
      canvas.drawLine(-1000, centery, 1000, centery, paint);
      paint.setColor(Color.RED);
      canvas.drawText("N", centerx+5, centery-60, paint);
      canvas.drawText("S", centerx-10, centery+65, paint);
      paint.setColor(Color.GREEN);
    }
  }
 
  CustomDrawableView mCustomDrawableView;
  private SensorManager mSensorManager;
  Sensor accelerometer;
  Sensor magnetometer;
 
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setTitle("aNexter - 指南针测试");
    mCustomDrawableView = new CustomDrawableView(this);
    setContentView(mCustomDrawableView);    // Register the sensor listeners
    mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
      accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
  }
 
  protected void onResume() {
    super.onResume();
    mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
  }
 
  protected void onPause() {
    super.onPause();
    mSensorManager.unregisterListener(this);
  }
 
  public void onAccuracyChanged(Sensor sensor, int accuracy) {  }
 
  float[] mGravity;
  float[] mGeomagnetic;
  public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
      mGravity = event.values;
    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
      mGeomagnetic = event.values;
    if (mGravity != null && mGeomagnetic != null) {
      float R[] = new float[9];
      float I[] = new float[9];
      boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
      if (success) {
        float orientation[] = new float[3];
        SensorManager.getOrientation(R, orientation);
        azimut = orientation[0]; // orientation contains: azimut, pitch and roll
      }
    }
    mCustomDrawableView.invalidate();
  }
}