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

public class camera extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        this.setTitle("aNexter - 前置后置摄像头测试");
        
        bPass = (Button)findViewById(R.id.btn_pass);       
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(camera.this , cpuRam.class);
        		startActivity(i);
        		camera.this.finish();
        	}
        });
        
        Button cam = (Button)findViewById(R.id.btn_camera);
        cam.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
        		Toast.makeText(getApplicationContext(),
        				"正在打开摄像头，请稍后……",
        				Toast.LENGTH_SHORT)
        				.show();
    			try{
    				Intent i = new Intent();
   					i.setAction("android.media.action.VIDEO_CAMERA");
    		    	startActivity(i);
    			}catch(ActivityNotFoundException e)
    			{
    				Toast.makeText(getApplicationContext(),
        					"Open Camera Failed!",
        					Toast.LENGTH_SHORT)
        					.show();
    				return ;
    			}    			
        	}
        });
        
		Toast.makeText(getApplicationContext(),
				"正在打开摄像头，请稍后……",
				Toast.LENGTH_SHORT)
				.show();
        try{
			Intent i = new Intent();
			i.setAction("android.media.action.VIDEO_CAMERA");
	    	startActivity(i);
		}catch(ActivityNotFoundException e)
		{
			Toast.makeText(getApplicationContext(),
					"Open Camera Failed!",
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
        		i.setClass(camera.this , getResults.class);
        		startActivity(i);
        		camera.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);
        
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