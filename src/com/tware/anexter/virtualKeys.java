package com.tware.anexter;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

public class virtualKeys extends Activity{
    /** Called when the activity is first created. */
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	private CheckBox cBack;
	private CheckBox cHome;
	private CheckBox cMenu;
	private CheckBox cVolP;
	private CheckBox cVolM;
	private int totalKeys = 0x0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virtualkey);
        this.setTitle("aNexter - 虚拟按键功能测试");
        
        bPass = (Button)findViewById(R.id.btn_pass);       
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		if (cVolP.isChecked() && cVolM.isChecked())
        		{
        			Intent i = new Intent();
        			i.putExtra("LOG", LOG + "PASS|");
        			i.setClass(virtualKeys.this , dateTime.class);
        			timer.cancel();
        			startActivity(i);
        			virtualKeys.this.finish();
        		}
        	}
        });
        
        cBack = (CheckBox)findViewById(R.id.chk_back);
        cHome = (CheckBox)findViewById(R.id.chk_home);
        cMenu = (CheckBox)findViewById(R.id.chk_menu);
        cVolP = (CheckBox)findViewById(R.id.chk_volplus);
        cVolM = (CheckBox)findViewById(R.id.chk_volminus);
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(virtualKeys.this , getResults.class);
        		startActivity(i);
        		timer.cancel();
        		virtualKeys.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);
        
        LOG = this.getIntent().getStringExtra("LOG");
//        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
    	
        timer.schedule(task, 1000, 1000);
    }

    
    private Handler uHandler = new Handler();
	private Timer timer = new Timer();
	private TimerTask task = new TimerTask() {
		
	   @Override
	   public void run() {
		   uHandler.post(new Runnable() {
				@Override
				public void run() {
					/*  Remove HOME Key detect */
					if(totalKeys == 0x1f || totalKeys == (0x01 | 0x04 | 0x08 | 0x10))
					{
		        		Intent i = new Intent();
		        		i.putExtra("LOG", LOG + "PASS|");
		        		i.setClass(virtualKeys.this , dateTime.class);
		        		timer.cancel();
		        		timer = null;
		        		startActivity(i);
		        		virtualKeys.this.finish();
					}
				}
		   });
	   }	   
	};
	
	
	/* FIXME:  How does it works within Android 4.0? */
	@Override
    public void onAttachedToWindow() {
    	this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
    	super.onAttachedToWindow();
    } 
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			cBack.setChecked(true);
			totalKeys |=0x01;
			return true;
		}
		if(keyCode == KeyEvent.KEYCODE_HOME)
		{
			cHome.setChecked(true);
			totalKeys |=0x02;
			return true;
		}
		if(keyCode == KeyEvent.KEYCODE_MENU)
		{
			cMenu.setChecked(true);
			totalKeys |=0x04;
			return true;
		}
		if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
		{
			cVolM.setChecked(true);
			totalKeys |=0x08;
			return true;
		}

		if(keyCode == KeyEvent.KEYCODE_VOLUME_UP)
		{
			cVolP.setChecked(true);
			totalKeys |=0x10;
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}