package com.tware.anexter;


import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.Toast;

public class wifiTest extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private Button bWifi;
	private WifiManager mWifi;
	private WifiInfo wifiInfo;
	private String LOG;
	private ConnectivityManager con;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi);
        this.setTitle("aNexter - 无线连接测试");

        mWifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);

        timer.schedule(task, 2500, 1000);

        bPass = (Button)findViewById(R.id.btn_pass);       
        bPass.setEnabled(false);
        /*        
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		startActivity(new Intent(wifiTest.this , inputchar.class));
        		wifiTest.this.finish();
        	}
        });
*/        
        bWifi = (Button)findViewById(R.id.btn_wifi);
        bWifi.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{	
        		bWifi.setEnabled(false);
        		startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        	}
        });
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
	        	timer.cancel();
        		timer = null;
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(wifiTest.this , getResults.class);
        		startActivity(i);
        		wifiTest.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);
        
        LOG = this.getIntent().getStringExtra("LOG");
//        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
        
    }
    
	private Handler uHandler = new Handler();
	private Timer timer = new Timer();
	private TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			uHandler.post(new Runnable() {
				@Override
				public void run() {
		        	wifiInfo = mWifi.getConnectionInfo();
		        	if (!mWifi.isWifiEnabled())
		        	{
		        		mWifi.setWifiEnabled(true);
		        	}
					if(con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected())
					{
			        	timer.cancel();
		        		timer = null;
		        		Intent i = new Intent();
		        		i.putExtra("LOG", LOG + wifiInfo.getMacAddress().toUpperCase() + "|");
		        		i.setClass(wifiTest.this , vibrator.class);
		        		startActivity(i);
		        		wifiTest.this.finish();
			        }
				}
			});
		}

	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		bWifi.setEnabled(true);
		return super.onKeyDown(keyCode, event);
	}
    
}