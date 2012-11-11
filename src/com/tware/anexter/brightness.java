package com.tware.anexter;


import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class brightness extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private TextView vTips;
	private int num = 0;
	private String LOG;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brightness);
        this.setTitle("aNexter - LCD Brightness Test");
        timer.schedule(task, 1000, 1000);
        
        vTips = (TextView)findViewById(R.id.v_bright_tips);
        
        bPass = (Button)findViewById(R.id.btn_pass);       
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
				if (timer != null)
				{
	        		timer.cancel();
	        		timer = null;
				}
        		setBrightness(255);
	    		Intent i = new Intent();
	    		i.putExtra("LOG", LOG + "PASS|");
	    		i.setClass(brightness.this , wifiBtMac.class);
	    		startActivity(i);       		
	    		brightness.this.finish();
        	}
        });
        
        SeekBar setBright = (SeekBar)findViewById(R.id.seekBar);
        setBright.setMax(255);
        setBright.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (progress >= 20)
				{
					setBrightness(progress);
				}	
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				int progress = seekBar.getProgress();
				if (timer != null)
				{
	        		timer.cancel();
	        		timer = null;
	        		vTips.setText("Manual Adjust Brightness Test...");
				}
				if (progress >= 20)
				{
					setBrightness(progress);
				}					
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int progress = seekBar.getProgress();
				if (progress >= 20)
				{
					setBrightness(progress);
				}else if(progress < 20)
				{
					setBrightness(20);					
				}
			}
        });
/*        
        Button brightset = (Button)findViewById(R.id.btn_brightness);
        brightset.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
        		startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
        	}
        });
*/
        
        LOG = this.getIntent().getStringExtra("LOG");
//        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
				if (timer != null)
				{
	        		timer.cancel();
	        		timer = null;
				}
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(brightness.this , getResults.class);
        		startActivity(i);
        		brightness.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);

    }
    
    public void setBrightness(int bright)
    {
		Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, bright);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.screenBrightness = (float) (((float) bright) / 255.0);
		getWindow().setAttributes(lp);
    }
    
   
	private Handler uHandler = new Handler();
	private Timer timer = new Timer();
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			uHandler.post(new Runnable() {
				@Override
				public void run() {
					int bright = 255;
					if (num == 0) {
						bright = 200;
						num = 1;
					} else {
						bright = 20;
						num = 0;
					}					
					Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, bright);
					WindowManager.LayoutParams lp = getWindow().getAttributes();
					lp.screenBrightness = (float) (((float) bright) / 255.0);
					getWindow().setAttributes(lp);
				}

			});
		}
	};
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		setBrightness(255);
		Log.w("aNexter", "Brightness Destroy()");
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0, "Open Display Setting");
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
    		case 0:
    			startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
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