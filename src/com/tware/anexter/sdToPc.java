package com.tware.anexter;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class sdToPc extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdpc);
        this.setTitle("aNexter - 底座USB 及  连接电脑测试");
        
        bPass = (Button)findViewById(R.id.btn_pass);
//        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(sdToPc.this , bluetooth.class);
        		startActivity(i);
        		sdToPc.this.finish();
        	}
        });
        
        
        
        Button iDockUsb = (Button)findViewById(R.id.btn_dockingusb);
        iDockUsb.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
    			try{
    				Intent i = new Intent();
   					i.setClassName("com.oem.iFileManager", "com.oem.iFileManager.iFileManager");
    		    	startActivity(i);
    			}catch(ActivityNotFoundException e)
    			{
    				Toast.makeText(getApplicationContext(),
        					"Open Filemanager Failed!",
        					Toast.LENGTH_SHORT)
        					.show();
    				return ;
    			}    			
        	}
        });
        
        Button sdToPc1 = (Button)findViewById(R.id.btn_sdtopc);
        sdToPc1.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{       		
        		Toast.makeText(getApplicationContext(),
        						"请插入MicroUSB线连接至电脑，检查电脑中是否能查看SD卡中的内容", 
        						Toast.LENGTH_SHORT).show();
        	}
        }); 
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(sdToPc.this , getResults.class);
        		startActivity(i);
        		sdToPc.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "N/A|");
        		i.setClass(sdToPc.this , bluetooth.class);
        		startActivity(i);
        		sdToPc.this.finish();
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
		return super.onKeyDown(keyCode, event);
	}
}