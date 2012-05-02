package com.tware.anexter;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class wifiBtMac extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	
	private String btMac, WifiMac;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifibtmac);
        this.setTitle("aNexter - 无线与蓝牙地址检查");
        
        bPass = (Button)findViewById(R.id.btn_pass);       
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(wifiBtMac.this , imageMcu.class);
        		startActivity(i);
        		wifiBtMac.this.finish();
        	}
        });
        
        TextView mac = (TextView)findViewById(R.id.view_wifi_bt);

        WifiManager mWifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        if (!mWifi.isWifiEnabled())
        {
        	mWifi.setWifiEnabled(true);
        }
        
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        
        BluetoothAdapter bAdapt= BluetoothAdapter.getDefaultAdapter();
       
        if (bAdapt != null)
        {
            if (!bAdapt.isEnabled())
            {
            	bAdapt.enable();
            }
            
            btMac = bAdapt.getAddress();
        }else{
        	btMac = "No Bluetooth Device!";
        }
        
        if((WifiMac = wifiInfo.getMacAddress())== null)
        {
        	WifiMac = "No Wifi Device";
        }
        
        mac.setTextSize(38);
        mac.setText("无线MAC:  "+ WifiMac.toUpperCase() + "\n蓝牙MAC:  " + btMac);
     
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(wifiBtMac.this , getResults.class);
        		startActivity(i);
        		wifiBtMac.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);
        
        LOG = this.getIntent().getStringExtra("LOG");
        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
        
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