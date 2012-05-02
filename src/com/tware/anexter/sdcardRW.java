package com.tware.anexter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class sdcardRW extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String sdpath = "/mnt/sdcard/";
	private String LOG;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdcardrw);
        this.setTitle("aNexter - SD卡写读删测试");
        
        Button rwTest = (Button)findViewById(R.id.btn_sd);
        rwTest.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		
        		switch (Build.VERSION.SDK_INT)
        		{
        			case  7: // android 2.1
        				sdpath = "/sdcard/"; 
        				break; 
        			case  8: // android 2.2
        				sdpath = "/mnt/sdcard/";
        				break;
        			case  9: // android 2.3
        			case 10: // android 2.3.3
        				sdpath = "/mnt/sdcard/external_sdcard/";
        				break; 
        			case 11:  // android 3.0
        			case 12:  // android 3.1
        			case 13:  // android 3.2
        				sdpath = "/mnt/sdcard2/";
        				break;
        			case 14:  // android 4.0
        			case 15:  // android 4.0.3
        				sdpath = "/mnt/sdcard/external_sdcard/";
        				break;
        			default:
        				sdpath = "/mnt/sdcard/";  // default SD Path
        				break;
        		}
        		
                Log.d("aNexter", "R/W @ \"" + sdpath + "\" @SDK: "+ Build.VERSION.SDK_INT);
                
                try {
                	if (Environment
                			.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                			&& fileExists(sdpath + "sd.flg")) { 
                		File f = new File(sdpath + "sdt.txt");
        	        
                		f.createNewFile();
                		FileOutputStream wf = new FileOutputStream(f);
                		wf.write("\nSD_Test_String_from_aNexter\nWritten By Tody \n(c) 2012 T-ware Inc.\n".getBytes()); // write string to file...
                		wf.flush();
                		wf.close();
                		f.delete();  // delete file
                        Log.i("aNexter", "SD R/W Pass");
                	}else{
        				Toast.makeText(getApplicationContext(),
            					"Has SD Card insert?",
            					Toast.LENGTH_SHORT)
            					.show();
                        Log.e("aNexter", "SD R/W Fail, no sd card...");
        				bFail.setEnabled(true);
        				return ;
                	}

                	} catch (FileNotFoundException e) {
        				Toast.makeText(getApplicationContext(),
            					"SD R/W Test Failed! - File not Found!",
            					Toast.LENGTH_SHORT)
            					.show();
        				Log.e("aNexter", "SD R/W Fail, File not Found...");
        				bFail.setEnabled(true);
                		return;
                	} catch (IOException e) {
        				Toast.makeText(getApplicationContext(),
            					"SD R/W Test Failed! - I/O Exception!",
            					Toast.LENGTH_SHORT)
            					.show();
        				Log.e("aNexter", "SD R/W Fail, I/O Error!");
        				bFail.setEnabled(true);
        				return;	
                	}                
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
//        		i.setClass(sdcardRW.this , mp3.class);  //2012-04-03: removed
        		i.setClass(sdcardRW.this, recording.class); //2012-04-03: change to soundrecord.java
        		startActivity(i);
                sdcardRW.this.finish();
        	}
        });
        
        bPass = (Button)findViewById(R.id.btn_pass);
        bPass.setEnabled(false);
        
        
        LOG = this.getIntent().getStringExtra("LOG");
//        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
        
        bFail = (Button)findViewById(R.id.btn_fail);
//        bFail.setEnabled(false);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(sdcardRW.this , getResults.class);
        		startActivity(i);
        		sdcardRW.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);
        
    }
    
    public boolean fileExists(String filePath)
    {
    	File f = new File(filePath);
    	return f.exists();
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}