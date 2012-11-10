package com.tware.anexter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class inputchar extends Activity {
	private Button bPass;
	private Button bFail;
	private Button bNa;
	private String LOG;
	private EditText editBox;
	private InputMethodManager imm;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputchar);
        this.setTitle("aNexter - Touchpanel Input char Test");
        
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        
        bPass = (Button)findViewById(R.id.btn_pass);       
        bPass.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v)
        	{
        		if (editBox.getText().length() > 3 &&
        			isDuplicateChar(editBox.getText().toString()))
        		{
        			Intent i = new Intent();
        			i.putExtra("LOG", LOG + "PASS|");
        			i.setClass(inputchar.this , wifiTest.class);
        			startActivity(i);
        			inputchar.this.finish();
        		}else{
        			Toast.makeText(getApplicationContext(),
        					"Input large than 4 chars and can not be duplicated",
        					Toast.LENGTH_SHORT).show();
        		}
        	}
        });
        
        editBox = (EditText)findViewById(R.id.edit_input);
        editBox.setHint("Here input");

        bFail = (Button)findViewById(R.id.btn_fail);
        bFail.setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v)
        	{
        		Intent i = new Intent();
        		i.putExtra("LOG", LOG + "FAIL|");
        		i.setClass(inputchar.this , getResults.class);
        		startActivity(i);
        		inputchar.this.finish();
        	}
        });
        
        bNa = (Button)findViewById(R.id.btn_na);
        bNa.setVisibility(View.INVISIBLE);
        
        LOG = this.getIntent().getStringExtra("LOG");   
		
    }
    
    public boolean isDuplicateChar(String src)
    {
    	String tmp = src;    	
    	
    	if (tmp == null) return false;
    	
    	for (int i=0; i<src.length(); i++)
    	{
    		if ( src.indexOf(tmp.substring(i, i+1)) + 1 == src.lastIndexOf(tmp.substring(i, i+1)))
    			return false;
    	}
    	
    	return true;
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
		imm.hideSoftInputFromWindow(editBox.getWindowToken(), 0);
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}