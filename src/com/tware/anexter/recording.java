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

public class recording extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording);
        this.setTitle("aNexter - 录音测试");
        
        bPass = (Button)findViewById(R.id.btn_pass);       
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
//        		i.setClass(recording.this , lcdrgb.class); //2012-04-03: removed
           		i.setClass(recording.this , dateTime.class); // 2012-04-03: change lcdrgb to brightness
           	    startActivity(i);
        		recording.this.finish();
        	}
        });
        
        Button iFile = (Button)findViewById(R.id.btn_record);
        iFile.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
    			try{
    				Intent i = new Intent();
    				i.setClassName("com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder");
    				startActivity(i);
    			}catch(ActivityNotFoundException e)
    			{
    				Toast.makeText(getApplicationContext(),
        					"SoundRecorder not found!",
        					Toast.LENGTH_SHORT)
        					.show();
        			return ;
    			}   			
        	}
        });
        
			try{
				Intent i = new Intent();
				i.setClassName("com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder");
				startActivity(i);
			}catch(ActivityNotFoundException e)
			{
				Toast.makeText(getApplicationContext(),
    					"SoundRecorder not found!",
    					Toast.LENGTH_SHORT)
    					.show();
    			return ;
			} 
			
	        bFail = (Button)findViewById(R.id.btn_fail);
	        bFail.setOnClickListener(new OnClickListener(){
	        	@Override
	    		public void onClick(View v)
	        	{
	        		Intent i = new Intent();
	        		i.putExtra("LOG", LOG + "FAIL|");
	        		i.setClass(recording.this , getResults.class);
	        		startActivity(i);
	        		recording.this.finish();
	        	}
	        });
	        
	        bNa = (Button)findViewById(R.id.btn_na);
	        bNa.setVisibility(View.INVISIBLE);
	        
	        LOG = this.getIntent().getStringExtra("LOG");
//	        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();

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