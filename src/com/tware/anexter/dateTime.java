package com.tware.anexter;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class dateTime extends Activity{
    /** Called when the activity is first created. */
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datetime);
        this.setTitle("aNexter - 日期时间检查");
        timer.schedule(task, 1000, 1000);
        
        bPass = (Button)findViewById(R.id.btn_pass);       
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(dateTime.this , batinfo.class);
        		startActivity(i);
        		timer.cancel();
        		timer = null;
        		dateTime.this.finish();
        	}
        });
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(dateTime.this , getResults.class);
        		startActivity(i);
        		timer.cancel();
        		timer = null;
        		dateTime.this.finish();
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
					TextView timeV = (TextView)findViewById(R.id.view_time);
					Time tm = new Time();

					tm.setToNow();  //Get local time

					int year = tm.year;
					int mon = tm.month + 1;
					int day = tm.monthDay;
					int hour = tm.hour;
					int min = tm.minute;
					int sec = tm.second;
	        
					timeV.setTextSize(39);
					timeV.setTextColor(Color.YELLOW);
					timeV.setText("当前时间: \n    "+ year + "-" + mon + "-" 
	        				+ day + " " + hour + ":" + min + ":" + sec);
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
		return super.onKeyDown(keyCode, event);
	}
}