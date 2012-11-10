package com.tware.anexter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toast;

public class suspend extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private boolean loaded = false;
	private long beforeSuspend = 0;
	private long afterSuspend = 0;
	private TextView tm1;
	private String LOG;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suspend);
        this.setTitle("aNexter - Suspend Test");
        
        bPass = (Button)findViewById(R.id.btn_pass); 
        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + afterSuspend + "|");
        		i.setClass(suspend.this , getResults.class);
        		startActivity(i);
        		suspend.this.finish();
        	}
        });
        
         tm1 = (TextView)findViewById(R.id.view_suspend);
         
         
	        bFail = (Button)findViewById(R.id.btn_fail);
	        bFail.setEnabled(false);
	        bFail.setOnClickListener(new OnClickListener(){
	        	@Override
	    		public void onClick(View v)
	        	{
	        		Intent i = new Intent();
	        		i.putExtra("LOG", LOG + "FAIL|");
	        		i.setClass(suspend.this , getResults.class);
	        		startActivity(i);
	        		suspend.this.finish();
	        	}
	        });
	        
	        bNa = (Button)findViewById(R.id.btn_na);
	        bNa.setVisibility(View.INVISIBLE);
	        
	        LOG = this.getIntent().getStringExtra("LOG");
//	        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
       
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
    	Time tm = new Time();
    	tm.setToNow();
    	beforeSuspend = tm.minute*60 + tm.second;
    	tm1.append("\nGoto Suspend time: " + beforeSuspend);
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	Time tm = new Time();
    	tm.setToNow();
    	afterSuspend = tm.minute*60 + tm.second;
    	if (loaded)
    	{
    		tm1.append("\nResume Suspend time: " + afterSuspend);
    		afterSuspend = afterSuspend - beforeSuspend;
    		tm1.append("\nTotal Suspend time: " + afterSuspend);
    		if (afterSuspend >= 20)
    		{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + afterSuspend + "|");
        		i.setClass(suspend.this , getResults.class);
        		startActivity(i);
        		suspend.this.finish();
    		}
    		bPass.setEnabled(true);
    		bFail.setEnabled(true);
    	}
    	loaded = true;
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