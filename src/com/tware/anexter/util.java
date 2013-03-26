package com.tware.anexter;

import java.io.File;

public class util
{
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
}