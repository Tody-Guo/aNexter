package com.tware.anexter;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class uDisk extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	private MediaPlayer mediaplay = null;
	private List<String> uList = new ArrayList<String>();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udisk);
        this.setTitle("aNexter - U 盘测试");
        
        bPass = (Button)findViewById(R.id.btn_pass);
        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(uDisk.this , uHDMI.class);
        		startActivity(i);
        		uDisk.this.finish();
        	}
        });
        
        Button iFile = (Button)findViewById(R.id.btn_Udisk);
        iFile.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
        		uList.clear();
        		if (!getUDiskDirs())
        		{
					Toast.makeText(getApplicationContext(), "找不到U盘装置!", Toast.LENGTH_LONG).show();							
					bPass.setEnabled(false);
					return ;
        		}
        		for (int i=0; i< uList.size(); i++)
        		{
            		try {
            			File frw = new File(uList.get(i).toString()+"/uRW.txt");
            			Log.e("U Disk", "RW @ " + uList.get(i).toString());
						frw.createNewFile();
						FileOutputStream wf = new FileOutputStream(frw);
						wf.write("\r\nU Disk Test String from aNexter\r\n(c) Tody 2012, T-ware Inc.\r\n".getBytes());
						wf.flush();
						wf.close();
						frw.delete();
						Log.d("U Disk", "U Disk Read/Write Ok");
						
		        		Intent i1 = new Intent();
		        		i1.putExtra("LOG", LOG + "PASS|");
		        		i1.setClass(uDisk.this , uHDMI.class);
		        		startActivity(i1);
		        		uDisk.this.finish();
		        		
            		}catch (IOException e) {
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "U盘读写失败!", Toast.LENGTH_LONG).show();							
						bPass.setEnabled(false);
            		}
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
        		i.setClass(uDisk.this , getResults.class);
        		startActivity(i);
        		uDisk.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
//        bNa.setVisibility(View.INVISIBLE);
        bNa.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "N/A|");
        		i.setClass(uDisk.this , uHDMI.class);
        		startActivity(i);
        		uDisk.this.finish();
        	}
        });
        
        LOG = this.getIntent().getStringExtra("LOG");
    }
    
    
    public boolean getUDiskDirs()
    {
		File f = new File("/mnt/");
		if (f.exists())
		{
			File [] list = f.listFiles();
			for (int i=0; i< list.length; i++)
			{
				Log.e("U Disk", "Files: " + list[i].getName());
				if (list[i].isDirectory() && (	list[i].getName().toString().startsWith("sda") 
											 || list[i].getName().toString().startsWith("sdb")
											 /* Here add for Android 4.1 support */
											 || list[i].getName().toString().startsWith("usb")))
				{
					uList.add(list[i].getAbsolutePath().toString());
				}
			}
		}
		else
		{
			return false;
		}
		
		if (uList.size() <= 0)
		{
			return false;
		}else{
			return true;
		}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0, "Filemanager");        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
    		case 0:
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
    				}
    			}
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