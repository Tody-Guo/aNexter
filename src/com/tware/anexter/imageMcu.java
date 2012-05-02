package com.tware.anexter;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toast;

public class imageMcu extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	
//	private	String sdk = android.os.Build.VERSION.SDK;
	private String release = android.os.Build.DISPLAY;
	private String mMCU_version; 
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagemcu);
        this.setTitle("aNexter - Image & MCU 版本检查");
        
        bPass = (Button)findViewById(R.id.btn_pass);       
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + mMCU_version + "|" + release + "|");
        		i.setClass(imageMcu.this , compass.class);
        		startActivity(i);
        		imageMcu.this.finish();
        	}
        });
        
	    try
	    {
	      FileReader fr = new FileReader("/proc/shmcu_version");
	      BufferedReader br = new BufferedReader(fr);
	      mMCU_version = br.readLine();
	      br.close();
	    }
	    catch (FileNotFoundException e)
	    {
	    	mMCU_version = "No MCU File";
	    } catch (IOException e) {
	    	mMCU_version = "Read MCU Err";
		}
        
        TextView imagemcu = (TextView)findViewById(R.id.view_image);
        
        imagemcu.setTextSize(20);
        imagemcu.setText("Model number: " + Build.MODEL
    			+ "\n\nAndroid: " + Build.VERSION.RELEASE
    			+ "\n\nMCU Version: " + mMCU_version
    			+ "\n\nRelease : " + release
    			+ "\n\nImage Version: " + System.getProperty("os.version", "Unknown OS"));
     
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|FAIL|");
        		i.setClass(imageMcu.this , getResults.class);
        		startActivity(i);
        		imageMcu.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);
        
        LOG = this.getIntent().getStringExtra("LOG");
//        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
        
        
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