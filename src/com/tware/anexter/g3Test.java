package com.tware.anexter;


import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class g3Test extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	private ConnectivityManager con;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g3);
        this.setTitle("aNexter - 3G Test");

		con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);

        timer.schedule(task, 1000, 1000);
        
        bPass = (Button)findViewById(R.id.btn_pass);
        bPass.setEnabled(false);
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
	        	timer.cancel();
        		timer = null;
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "PASS|");
        		i.setClass(g3Test.this , getResults.class);
        		startActivity(i);
        		g3Test.this.finish();
        	}
        });

        Button g3 = (Button)findViewById(R.id.btn_g3);
        g3.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
    			try{
    				Intent i = new Intent();
    				i.setClassName("com.android.phone", "com.android.phone.Settings");
    				startActivity(i);
    				bPass.setEnabled(true);
    				bFail.setEnabled(true);
    			}catch(ActivityNotFoundException e)
    			{
    				Toast.makeText(getApplicationContext(),
        					"3G Settings Failed!",
        					Toast.LENGTH_SHORT)
        					.show();
        			return ;
    			}
        	}
        });
        
        Button oBaidu = (Button)findViewById(R.id.btn_website);
        oBaidu.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{
        		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://wap.baidu.com")));
        	}
        });
        
        
        
        
        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setEnabled(false);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
	        	timer.cancel();
        		timer = null;
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(g3Test.this , getResults.class);
        		startActivity(i);
        		g3Test.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "N/A|");
        		i.setClass(g3Test.this , getResults.class);
        		startActivity(i);
        		g3Test.this.finish();
        	}
        });
        LOG = this.getIntent().getStringExtra("LOG");
        Toast.makeText(getApplicationContext(), LOG, Toast.LENGTH_SHORT).show();        
    }
    
	private Handler uHandler = new Handler();
	private Timer timer = new Timer();
	private TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			uHandler.post(new Runnable() {
				@Override
				public void run() {
					if(con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected())
					{
			        	timer.cancel();
		        		timer = null;
		        		Intent i = new Intent();
		        		i.putExtra("LOG", LOG + "PASS|");
		        		i.setClass(g3Test.this , getResults.class);
		        		startActivity(i);
		        		g3Test.this.finish();
			        }
				}
			});
		}

	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
    
}