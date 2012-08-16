package com.tware.anexter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class uHDMI extends Activity{
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	private MediaPlayer mediaplay = null;
	private VideoView videoP;
	private Button btnSW;
	private Button hdmiswitch;
	
	private List<String> playList = new ArrayList<String>();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uhdmi);
        this.setTitle("aNexter - HDMI测试");
        
        bPass = (Button)findViewById(R.id.btn_pass);
        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(uHDMI.this , docking.class);
        		startActivity(i);
        		uHDMI.this.finish();
        	}
        });
        
        videoP = (VideoView)findViewById(R.id.videoView_HDMI);
        videoP.setVisibility(View.INVISIBLE);
        
        hdmiswitch = (Button)findViewById(R.id.btn_hdmisw);
        hdmiswitch.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{    			
				playList.clear();
        		if (!findVideo("/sdcard"))
        		{
        			Toast.makeText(getApplicationContext(), "未找到视频文件", Toast.LENGTH_LONG).show();
        			return ;
        		}
            	Random  rand = new Random(System.currentTimeMillis());

        		videoP.setVisibility(View.VISIBLE);
        		videoP.setVideoPath(playList.get(rand.nextInt(playList.size())));
        		videoP.setMediaController(new MediaController(uHDMI.this));
        		videoP.requestFocus();
        		videoP.start();
        		bPass.setEnabled(true);
        		btnSW.setVisibility(View.INVISIBLE);
        	}
        });
        
        
        btnSW = (Button)findViewById(R.id.btn_hdmiSW);
        btnSW.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
        		try{
        			Intent i = new Intent();
        			i.setClassName("com.amlogic.HdmiSwitch", "com.amlogic.HdmiSwitch.HdmiSwitch");
        			startActivity(i);
        		}catch(ActivityNotFoundException e)
        		{
        			Toast.makeText(getApplicationContext(),
    					"Open HdmiSwitch Failed!",
    					Toast.LENGTH_SHORT)
    					.show();
				return ;
        		}
        	}
        });
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(uHDMI.this , getResults.class);
        		startActivity(i);
        		uHDMI.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);
/*
        bNa.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "N/A|");
        		i.setClass(uHDMI.this , docking.class);
        		startActivity(i);
        		uHDMI.this.finish();
        	}
        });
*/        
        LOG = this.getIntent().getStringExtra("LOG");
    }
    
    
    public boolean findVideo(String sdPath1)
    {
    	File f = new File(sdPath1);
    	if (!f.exists()) return false;
    	    	
    	try{
    		File[] fl = f.listFiles();
    		if (fl.length == 0 )
    		{
    			return false;
    		}
    	
    		for (int i = 0; i< fl.length; i++)
    		{
    			if (fl[i].isDirectory())   findVideo(fl[i].getAbsolutePath());
    			if (	fl[i].toString().toLowerCase().endsWith(".mp4") 
    				||	fl[i].toString().toLowerCase().endsWith(".3gp"))
    			{
    			playList.add(fl[i].getAbsoluteFile().toString());
    			}
    		}
    	}
    	catch (Exception e)
    	{
    		return false;
    	}
    	
    	if (!playList.isEmpty())
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0, "Music");        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
    		case 0:
    			mediaplay=MediaPlayer.create(this, R.raw.ilu);
				mediaplay.setLooping(true);
				mediaplay.start();
				bPass.setEnabled(true);
				bFail.setEnabled(true);
    			return true;
    	}
        return true;
    }
    
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (mediaplay != null) mediaplay.release();
	}	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			videoP.stopPlayback();
			videoP.setVisibility(View.INVISIBLE);
			
    		try{
    			Intent i = new Intent();
    			i.setClassName("com.amlogic.HdmiSwitch", "com.amlogic.HdmiSwitch.HdmiSwitch");
    			startActivity(i);
    		}catch(ActivityNotFoundException e)
    		{
    			Toast.makeText(getApplicationContext(),
					"Open HdmiSwitch Failed!",
					Toast.LENGTH_SHORT)
					.show();
			return false;
    		}
			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}