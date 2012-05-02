package com.tware.anexter;


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
import android.widget.Toast;

public class uHDMI extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	private MediaPlayer mediaplay = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uhdmi);
        this.setTitle("aNexter - U 盘与HDMI测试");
        
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
        
        
        
        Button iFile = (Button)findViewById(R.id.btn_ifileUdisk);
        iFile.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
    			try{
    				Intent i = new Intent();
   					i.setClassName("com.oem.iFileManager", "com.oem.iFileManager.iFileManager");
    		    	startActivity(i);
    		    	bPass.setEnabled(true);
    		    	bFail.setEnabled(true);
    			}catch(ActivityNotFoundException e)
    			{
    				try{
    					Intent i = new Intent();
    					i.setClassName("com.fb.FileBrower", "com.fb.FileBrower.FileBrower");
    					startActivity(i);
        		    	bFail.setEnabled(true);
        		    	bPass.setEnabled(true);
    				}catch(ActivityNotFoundException e1){
    					Toast.makeText(getApplicationContext(),
    							"Open Filemanager Failed!",
    							Toast.LENGTH_SHORT)
    							.show();
    					return ;
    				}
    			}
        	}
        });
        
        Button hdmiswitch = (Button)findViewById(R.id.btn_hdmisw);
        hdmiswitch.setOnClickListener(new OnClickListener(){
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
        bFail.setEnabled(false);
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
//        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
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
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}