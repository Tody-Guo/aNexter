package com.tware.anexter;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
//import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class gyroscope extends Activity implements SensorEventListener{
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	private SensorManager mSensorManager;
	private Sensor mGyro;
	private TextView vX;
	private TextView vY;
	private TextView vZ;
    private static float EPSILON = 0.001f;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private boolean isPass = false;
    private boolean isSet = false;
    private float pre_x;
    private float pre_y;
    private float pre_z;
    private float timestamp;
    private float[] angle = new float[4];  
    private boolean tx = false;
    private boolean ty = false;
    private boolean tz = false;

    public gyroscope()
    {
    	angle[0] = 0;
    	angle[1] = 0;
    	angle[2] = 0;
    	angle[3] = 0;
    	timestamp = 0;
    }
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyro);
        this.setTitle("aNexter - Gyroscope 测试");
        
        vX = (TextView)findViewById(R.id.v_x);
        vY = (TextView)findViewById(R.id.v_y);
        vZ = (TextView)findViewById(R.id.v_z);
        
        vX.setText("");
        vY.setText("");
        vZ.setText("");
        
        bPass = (Button)findViewById(R.id.btn_pass);
        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(gyroscope.this , getResults.class);
        		startActivity(i);
        		gyroscope.this.finish();
        	}
        });
        Button bGyro = (Button)findViewById(R.id.btn_gyro);
//        bGyro.setEnabled(false);
        bGyro.setVisibility(View.INVISIBLE);
        /*        
        bGyro.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
    			try{
    				Intent i = new Intent();
    				i.setComponent(new ComponentName("com.shuttle.tp.activity",
    					"com.shuttle.tp.activity.ManualActivity"));
    				startActivity(i);
    				bPass.setEnabled(true);
    				bFail.setEnabled(true);
    			}catch(ActivityNotFoundException e)
    			{
    				Toast.makeText(getApplicationContext(),
        					"Open Shuttle Test Programme failed!",
        					Toast.LENGTH_SHORT)
        					.show();
        			return ;
    			}			
        	}
        });
*/        
        bFail = (Button)findViewById(R.id.btn_fail);
//        bFail.setEnabled(false);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(gyroscope.this , getResults.class);
        		startActivity(i);
        		gyroscope.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "N/A|");
        		i.setClass(gyroscope.this , getResults.class);
        		startActivity(i);
        		gyroscope.this.finish();
        	}
        });
        
        
        LOG = this.getIntent().getStringExtra("LOG");
//        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
        
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(mGyro == null)
        {
        	vX.setText("No Gyroscope Sensor!");
        }
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mGyro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    
    @Override
	public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0, "打开GyroScope测试");
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
    		case 0:
    			try{
    				Intent i = new Intent();
    				i.setComponent(new ComponentName("com.shuttle.tp.activity",
    					"com.shuttle.tp.activity.ManualActivity"));
    				startActivity(i);
    				bPass.setEnabled(true);
    				bFail.setEnabled(true);
    			}catch(ActivityNotFoundException e)
    			{
    				Toast.makeText(getApplicationContext(),
        					"Open Shuttle Test Programme failed!",
        					Toast.LENGTH_SHORT)
        					.show();
        			return false;
    			}	
    			return true;
        }
        return false;
    }
	
    
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
    	if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)  
        {  
    		return;  
        }
    	
    	if (isPass)
    	{
    		Intent i = new Intent();
    		i.putExtra("LOG", LOG + "PASS|");
    		i.setClass(gyroscope.this , getResults.class);
    		startActivity(i);
    		gyroscope.this.finish();
    		return;
    	}

    	if (timestamp != 0) {
        	final float dT = (event.timestamp - timestamp) * NS2S;
        	float axisX = event.values[0];              
        	float axisY = event.values[1];              
        	float axisZ = event.values[2]; 
        	float omegaMagnitude = (float) Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);
        	if (omegaMagnitude > EPSILON) {
        		axisX /= omegaMagnitude;
        		axisY /= omegaMagnitude;
        		axisZ /= omegaMagnitude;
        	}
        	
        	float thetaOverTwo = omegaMagnitude * dT /1.7f;   /* FIXME: it was correct?*/
        	float sinThetaOverTwo = (float) Math.sin(thetaOverTwo);              
        	float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);              
        	angle[0] += sinThetaOverTwo * axisX*100;              
        	angle[1] += sinThetaOverTwo * axisY*100;              
        	angle[2] += sinThetaOverTwo * axisZ*100;              
        	angle[3] = cosThetaOverTwo;        	
         }
         timestamp = event.timestamp;

         if (!isSet)
         {
        	 pre_x = angle[0];
        	 pre_y = angle[1];
        	 pre_z = angle[2];
        	 isSet = true;
         }
         
         if (Math.abs(angle[0])- Math.abs(pre_x) > 90)
         {
        	 tx = true;
        	 vX.setTextColor(Color.GREEN);
         }

         if (Math.abs(angle[1])- Math.abs(pre_y) > 90)
         {
        	 ty = true;
        	 vY.setTextColor(Color.GREEN);
         }
         
         if (Math.abs(angle[2])- Math.abs(pre_z) > 90)
         {
        	 tz = true;
        	 vZ.setTextColor(Color.GREEN);
         }
         
         vX.setText("X: " + Float.toString(angle[0]));
         vY.setText("Y: " + Float.toString(angle[1]));
         vZ.setText("Z: " + Float.toString(angle[2]));
         if(tx && ty && tz )
         {
            vX.setText("Gyro Test Pass!");
       	 	isPass = true;
         }
	}
}