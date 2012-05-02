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

public class compass extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass);
        this.setTitle("aNexter - 指南针测试");
        
        bPass = (Button)findViewById(R.id.btn_pass);
        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(compass.this , gyroscope.class);
        		startActivity(i);
        		compass.this.finish();
        	}
        });
        
        Button compass1 = (Button)findViewById(R.id.btn_compass);
        compass1.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
    			try{
    				Intent i = new Intent();
    					i.setClassName("com.apksoftware.compass",
    									"com.apksoftware.compass.Compass");
        		    	startActivity(i);
        		    	bPass.setEnabled(true);
        		    	bFail.setEnabled(true);
    			}catch(ActivityNotFoundException e)
    			{
    				Toast.makeText(getApplicationContext(),
        					"Open Compass Failed!",
        					Toast.LENGTH_SHORT)
        					.show();
    				return ;
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
        		i.setClass(compass.this , getResults.class);
        		startActivity(i);
        		compass.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "N/A|");
        		i.setClass(compass.this , gyroscope.class);
        		startActivity(i);
        		compass.this.finish();
        	}
        });
        
        
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