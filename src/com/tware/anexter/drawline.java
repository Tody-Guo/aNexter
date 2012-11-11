package com.tware.anexter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class drawline extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	private int plugged;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawline);
        this.setTitle("aNexter - Touchpanel Drawing Line Test");
        
        bPass = (Button)findViewById(R.id.btn_pass);       
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(drawline.this , inputchar.class);
        		startActivity(i);
        		drawline.this.finish();
        	}
        });
        
        Button draw = (Button)findViewById(R.id.btn_draw);
        draw.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
        		startActivity(new Intent(drawline.this, PointerLocation.class));
        		bPass.setEnabled(true);
        	}
        });
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(drawline.this , getResults.class);
        		startActivity(i);
        		drawline.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);
        
        LOG = this.getIntent().getStringExtra("LOG");
        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
        
        new AlertDialog.Builder(this)
        	.setIcon(R.drawable.icon)
        	.setTitle("Warning")
        	.setMessage("Please remove AD adapter before perform this test!")
        	.setPositiveButton("Start", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					 startActivity(new Intent(drawline.this, PointerLocation.class));
				}
        	}).show();
        
    }
    
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context arg0, Intent intent) {
			String action = intent.getAction();
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)){
				plugged = intent.getIntExtra("plugged", 0);	
				
				if(plugged == BatteryManager.BATTERY_PLUGGED_AC)
				{
					bPass.setEnabled(false);
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