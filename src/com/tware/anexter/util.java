package com.tware.anexter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class util
{
	private static final String TAG = "Util";
	private static String RKPATH = "/dev/rknand_sys_storage";
	
	public static boolean fileExists(String path)
	{
		File f = new File(path);
		if(f.exists()/* && f.isFile()*/)
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean isRk()
	{
		return fileExists(RKPATH);
	}
	
    /*
     * set the eMMC Size for each range
     * */
	public static float setEmmcSize(float size)
	{
	    if (size >= 30 && size <=60)
	    	return (float) 32;
	    else if (size >= 16 && size <=18)
	    	return (float) 16;
	    else if(size >= 7 && size <= 10)
	    	return (float)  8;
	    else if (size >=4 && size<=5)
	    	return (float)  4;
	    else if (size>=1 && size <=3)
	    	return (float)  2;
	    else
	    	return 0;
	}
	
	public static String getRealSDPath(){
		List<String> sdPathList = new ArrayList<String>();
		/*
		 * Init for Shuttle Image sd path...
		 * */
		sdPathList.add("/mnt/external_sd/");
		sdPathList.add("/mnt/sdcard/external_sd/");
		sdPathList.add("/mnt/sdcard/external_sdcard/");
		sdPathList.add("/mnt/sdcard2/");
		sdPathList.add("/mnt/sdcard/external_storage/sdcard1/");
		
		int size = sdPathList.size();
		for (int i=0; i<size; i++)
		{
			Log.d(TAG, "path -> "+sdPathList.get(i));
			if(fileExists(sdPathList.get(i)+"sd.flg"))
				return sdPathList.get(i);
		}
		return "NOT_FOUND";
	}	
	
}