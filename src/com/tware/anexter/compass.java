package com.tware.anexter;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class compass extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private TextView vCompass;
	private String LOG;
	
	private SensorManager mSensorManager;
	private Sensor magnetometer;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass);
        this.setTitle("aNexter - 指南针测试");
        
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        vCompass = (TextView)findViewById(R.id.view_compass);
        vCompass.setText("");
        
        bPass = (Button)findViewById(R.id.btn_pass);
        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(compass.this , gyroscope.class);
        		startActivity(i);
        		compass.this.finish();
        	}
        });
        
        Button compass1 = (Button)findViewById(R.id.btn_compass);
        compass1.setText("Retest");
        compass1.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
        		if (magnetometer != null){
        			try{
        				startActivity(new Intent(compass.this, compass1.class));
        				bPass.setEnabled(true);
        				bFail.setEnabled(true);
        			}catch(ActivityNotFoundException e){
        				Toast.makeText(getApplicationContext(),
        					"Open Internal Compass Fail!",
        					Toast.LENGTH_SHORT)
        					.show();
    				return ;
        			}
        		}else{
        			Toast.makeText(getApplicationContext(), "No Compass Sensor", Toast.LENGTH_LONG).show();
        		}
        	}
        });    	
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(compass.this , getResults.class);
        		startActivity(i);
        		compass.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "N/A|");
        		i.setClass(compass.this , gyroscope.class);
        		startActivity(i);
        		compass.this.finish();
        	}
        });
        
        LOG = this.getIntent().getStringExtra("LOG");
        Toast.makeText(getApplicationContext(), "测试指南针需要进行画8字校验！", Toast.LENGTH_SHORT).show();
        
        if (magnetometer != null){        
        	try{
        		startActivity(new Intent(compass.this, compass1.class));
        		bPass.setEnabled(true);
        		bFail.setEnabled(true);
        	}catch(ActivityNotFoundException e){
        		Toast.makeText(getApplicationContext(),
					"Open Internal Compass Fail!",
					Toast.LENGTH_SHORT)
					.show();
        		return ;
        	} 
        }else{
        	vCompass.setTextColor(Color.RED);
        	vCompass.setText("No Compass Sensor!");
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0, "打开指南针");
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
    				i.setClassName("com.netpatia.android.filteredcompass", 
							"com.netpatia.android.filteredcompass.FilteredCompassActivity");
        		    startActivity(i);
        		    bPass.setEnabled(true);
        		    bFail.setEnabled(true);
    			}catch(ActivityNotFoundException e)
    			{
    				Toast.makeText(getApplicationContext(),
        					"Open Compass Failed!",
        					Toast.LENGTH_SHORT)
        					.show();
    				return false;
    			}  
    			return true;
        }
        return false;
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}