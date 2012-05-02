package com.tware.anexter;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ethernet extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	private Button openWeb;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ethernet);
        this.setTitle("aNexter - USB TO LAN测试");
        
        Button setting = (Button)findViewById(R.id.button1);
        setting.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
    			try{
    				Intent i = new Intent();
    				i.setClassName("com.android.settings", "com.android.settings.EthernetSettings");
    				startActivity(i);
    				openWeb.setEnabled(true);
    			}catch(ActivityNotFoundException e)
    			{
    				Toast.makeText(getApplicationContext(),
        					"Open Ethernet failed",
        					Toast.LENGTH_SHORT)
        					.show();
        			return ;
    			}        		
        	}
        });
        
        openWeb = (Button)findViewById(R.id.button2);
        openWeb.setEnabled(false);
        openWeb.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.1.1")));
        		bPass.setEnabled(true);
        		bFail.setEnabled(true);
        	}
        });

        bPass = (Button)findViewById(R.id.btn_pass);
        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(ethernet.this , sdcardRW.class);
        		startActivity(i);
        		ethernet.this.finish();
        	}
        });
        
        LOG = this.getIntent().getStringExtra("LOG");
        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();
        
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setEnabled(false);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(ethernet.this , getResults.class);
        		startActivity(i);
        		ethernet.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "N/A|");
        		i.setClass(ethernet.this , sdcardRW.class);
        		startActivity(i);
        		ethernet.this.finish();
        	}
        });        
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