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
}