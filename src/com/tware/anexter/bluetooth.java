package com.tware.anexter;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
//import android.widget.Toast;

public class bluetooth extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private Button bBlue;
	private String LOG;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth);
        this.setTitle("aNexter - 蓝牙测试");
        
        bPass = (Button)findViewById(R.id.btn_pass);     
        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(bluetooth.this , camera.class);
        		startActivity(i);
        		bluetooth.this.finish();
        	}
        });
        
        bBlue = (Button)findViewById(R.id.btn_bluetooth);
        bBlue.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
        		BluetoothAdapter btAdapt= BluetoothAdapter.getDefaultAdapter();
                
        		if (btAdapt == null)
        		{
        			Toast.makeText(getApplicationContext(),
        							"没有蓝牙设备",
        							Toast.LENGTH_SHORT).show();
        			return ;
        		}
        		
        		bBlue.setEnabled(false);	
        		
        		if (!btAdapt.isEnabled())
                {
                	btAdapt.enable();
                }
			
                try{
    				Intent i = new Intent();
    				i.setClassName("com.android.IvtBluetooth", "com.android.IvtBluetooth.IVTBluetooth");
    				startActivity(i);
    				bPass.setEnabled(true);
    				bFail.setEnabled(true);
    			}catch(ActivityNotFoundException e)
    			{
    				Log.d("aNext", "No IVT Devices found, and now starting default Bluetooth Settings");
        			startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
    				bPass.setEnabled(true);
    				bFail.setEnabled(true);
    			} 			
        	}
        });       
        
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setEnabled(false);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(bluetooth.this , getResults.class);
        		startActivity(i);
        		bluetooth.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "N/A|");
        		i.setClass(bluetooth.this , camera.class);
        		startActivity(i);
        		bluetooth.this.finish();
        	}
        });
        
        LOG = this.getIntent().getStringExtra("LOG");
//        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		bBlue.setEnabled(true);
		return super.onKeyDown(keyCode, event);
	}
}