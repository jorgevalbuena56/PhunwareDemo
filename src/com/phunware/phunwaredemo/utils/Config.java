package com.phunware.phunwaredemo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;


public class Config {
	
	private Properties mProperties;
	private Context mContext;
	
	public Config(Context context) {
		this.mContext=context;

		if(mProperties == null){
			initConfig();
		}
	}

	public void initConfig() {
		
		try {
			
			AssetManager assetManager = mContext.getAssets();
			InputStream rawResource = assetManager.open("config.properties");
			mProperties = new Properties();
			mProperties.load(rawResource);
			
		} catch (Resources.NotFoundException e) {
			Log.e("Properties Loading", "Did not find raw resource: " + e);
		} catch (IOException e) {
			Log.e("Properties Loading", "Failed to open microlog property file");
		}
	
	}
   

	public String get(String key) {
		return (String)mProperties.get(key);
	}

}
