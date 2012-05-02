package com.tware.anexter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toast;

public class batinfo extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private TextView batTxt;
	private String LOG;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.batinfo);
        this.setTitle("aNexter - 电池信息");
        batTxt = (TextView)findViewById(R.id.view_batinfo);
        
        bPass = (Button)findViewById(R.id.btn_pass);
//        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(batinfo.this , gsensorTest.class);
        		startActivity(i);
        		batinfo.this.finish();
        	}
        });
        
        LOG = this.getIntent().getStringExtra("LOG");
//        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();

        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(batinfo.this , getResults.class);
        		startActivity(i);
        		batinfo.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);
        
        registerReceiver(mBatInfoReceiver, 
        		new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
 

    }
    
    

    
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context arg0, Intent intent) {
			String action = intent.getAction();
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)){
			
				int status = intent.getIntExtra("status", 0);
				int health = intent.getIntExtra("health", 1);
				int level = intent.getIntExtra("level", 0);
//				int scale = intent.getIntExtra("scale", 0);
				int plugged = intent.getIntExtra("plugged", 0);
				int voltage = intent.getIntExtra("voltage", 0);
//				int temperature = intent.getIntExtra("temperature", 0);
				String technology = intent.getStringExtra("technology");
		      
				String statusString = "unknown";
			          
			switch (status) {
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					statusString = "unknown";
					break;
				case BatteryManager.BATTERY_STATUS_CHARGING:
					statusString = "正在充电";
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					statusString = "正在放电";
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					statusString = "没有正在充电";
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					statusString = "电池已满";
					break;
			}
			        
			String healthString = "unknown";
			        
			switch (health) {
				case BatteryManager.BATTERY_HEALTH_UNKNOWN:
					healthString = "unknown";
					break;
				case BatteryManager.BATTERY_HEALTH_GOOD:
					healthString = "很好";
					break;
				case BatteryManager.BATTERY_HEALTH_OVERHEAT:
					healthString = "overheat";
					break;
				case BatteryManager.BATTERY_HEALTH_DEAD:
					healthString = "dead";
					break;
				case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
					healthString = "voltage";
					break;
				case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
					healthString = "unspecified failure";
					break;
			}
			        
			String acString = "Unknown";
			        
			switch (plugged) {
				case BatteryManager.BATTERY_PLUGGED_AC:
					acString = "AC电源已插入";
					break;
				case BatteryManager.BATTERY_PLUGGED_USB:
					acString = "USB电源已插入";
					break;
			}
			batTxt.setTextColor(Color.BLUE);
			batTxt.setTextSize(38);
			batTxt.setText(//	  "Battery Information:" +
							  "\n状态: " + statusString +
							  "\n电量: " + String.valueOf(level) + "%" +
						      "\n健康: " + healthString +
//			        		  "\nPresent: " + String.valueOf(present)+
//			        		  "\nScale: " + String.valueOf(scale)+
			        		  "\n插拔: " + acString +
			        		  "\n电压: " + String.valueOf(voltage) + "mV" +
//			        		  "\nTemperature: " + String.valueOf(temperature) + "℃" +
			        		  "\n工艺: " + technology);			       
			if (level >= 95) 
			{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(batinfo.this , gsensorTest.class);
        		startActivity(i);
				batinfo.this.finish();
			}
		}
	}
};
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(mBatInfoReceiver, 
    		new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
  }

  @Override
  	protected void onPause() {
    super.onPause();
    unregisterReceiver(mBatInfoReceiver);
  }
  
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}