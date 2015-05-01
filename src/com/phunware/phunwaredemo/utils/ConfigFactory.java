package com.phunware.phunwaredemo.utils;

import android.content.Context;

public class ConfigFactory {

	private static Config mConfig;
	
	public static Config getInstance(Context context){
		
		if(mConfig!= null){
			return mConfig;
		}else{
			mConfig = new Config(context);
			return mConfig;
		}
		
	}	
}