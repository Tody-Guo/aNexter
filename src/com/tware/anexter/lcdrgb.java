package com.tware.anexter;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
//import android.widget.Toast;

public class lcdrgb extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private Button bLcdRetest;
	
	private int id = 0;
	private View rgb;
	private ProgressBar bar;
	private String LOG;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
        setContentView(R.layout.lcdrgb);
        this.setTitle("aNexter - LCD RGB Test");

        rgb = findViewById(R.id.rgb_chk);
        rgb.setBackgroundColor(Color.MAGENTA);
        bar = (ProgressBar)findViewById(R.id.progressBar1);
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bNa = (Button)findViewById(R.id.btn_na);
        bPass = (Button)findViewById(R.id.btn_pass);  
        bLcdRetest = (Button)findViewById(R.id.btn_lcdRetest);
        
        bPass.setVisibility(4);
        bNa.setVisibility(4);
        bFail.setVisibility(4);
        bar.setVisibility(4);
        bLcdRetest.setVisibility(View.INVISIBLE);
     
        bLcdRetest.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
        		if ( timer != null )
        		{
        	    	timer.cancel();
        	    	timer = null;
        	    	Log.w("aNexter", "LCD Timer canceled from manual test...........");
        		}

        		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        		rgb.setVisibility(View.VISIBLE);
        		bLcdRetest.setVisibility(View.INVISIBLE);
        		bar.setVisibility(View.INVISIBLE);

        		/* set manual test */
        		id = 0;
        		rgb.setOnClickListener(new OnClickListener(){
                	@Override
                	public void onClick(View v)
                	{
                		nowChangeBg(id);
                		id ++;
                	}
                });
        	}
        });
        
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(lcdrgb.this , sdToPc.class);
        		startActivity(i);
        		lcdrgb.this.finish();
        	}
        });
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(lcdrgb.this , getResults.class);
        		startActivity(i);
        		lcdrgb.this.finish();
        	}
        });
        
        LOG = this.getIntent().getStringExtra("LOG");
//        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
      
        timer.schedule(task, 1000,3000);  // start timer, 3s per time.
    }
    
    Handler uhandle = new Handler();
    Timer timer = new Timer();
    TimerTask task = new TimerTask()
    {
    	@Override
    	public void run()
    	{
    		uhandle.post(new Runnable()
    		{
    			@Override
    			public void run()
    			{
            		nowChangeBg(id);
            		id ++;
    			}
    		});
    	}
    };
    
    public void nowChangeBg(int id)
    {
		TextView v1 = (TextView)rgb;
    	switch(id)
		{
			case 0:
				v1.setText("Red");
				rgb.setBackgroundColor(Color.RED);
				break;
			case 1:
				v1.setText("Green");
				rgb.setBackgroundColor(Color.GREEN);
				break;
			case 2:
				v1.setText("Blue");
				rgb.setBackgroundColor(Color.BLUE);
				break;

			case 3:
				v1.setText("Gray1");
				rgb.setBackgroundColor(Color.DKGRAY);
				break;

			case 4:
				v1.setText("Gray2");
				rgb.setBackgroundColor(Color.GRAY);
				break;

			case 5:
				v1.setText("Gray3");
				rgb.setBackgroundColor(Color.LTGRAY);
				break;
				
			case 6:
				v1.setText("White");
				rgb.setBackgroundColor(Color.WHITE);
				break;
			default:
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);       				
				rgb.setVisibility(View.INVISIBLE);
		        bNa.setVisibility(View.INVISIBLE);
		        bPass.setVisibility(View.VISIBLE);
		        bFail.setVisibility(View.VISIBLE);
		        bar.setVisibility(View.VISIBLE);
		        bLcdRetest.setVisibility(View.VISIBLE);
				id = -1;
				break;
		}
    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
    	
		if ( timer != null )
		{
	    	timer.cancel();
	    	timer = null;
	    	Log.w("aNexter", "LCD Timer canceled from onStop()............");
			return;
		}
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
    