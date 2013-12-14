package com.jotform.jotformapisample.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.jotform.api.JotformAPIClient;

public class SharedData extends Application {
	
	public static final String API_KEY = "";
	
	private CommonInfo				mCommonInfo;
	private JotformAPIClient		mApiClient;
	
	public SharedData() {
		mCommonInfo = new CommonInfo();
	}
	
	public void setApiKey( String apiKey ) {
		mCommonInfo.setApiKey(apiKey);
	}
	
	public void initApiClient() {
		
		String apiKey = mCommonInfo.getApiKey();
		
		mApiClient = new JotformAPIClient(apiKey);
	}
	
	public CommonInfo getCommonInfo() {
		
		return mCommonInfo;
	}
	
	public JotformAPIClient getJotformAPIClient() {
		
		return mApiClient;
	}
	
	public boolean getCreatedFormFlag(Context context, String apiKey) {
		
		SharedPreferences prefs = context.getSharedPreferences(
				"com.jotform.jotformapisample", Context.MODE_PRIVATE);

		boolean isCreated = prefs.getBoolean(apiKey, false);
		
		return isCreated;
	}
	
	public void setCreatedFormFlag(Context context, String apiKey) {
		
		SharedPreferences prefs = this.getSharedPreferences("com.jotform.jotformapisample", Context.MODE_PRIVATE);
		
		prefs.edit().putBoolean(apiKey, true).commit();
	}
}
