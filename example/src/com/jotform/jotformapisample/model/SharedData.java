package com.jotform.jotformapisample.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.jotform.api.JotformAPIClient;

public class SharedData extends Application {
	
	public static final String API_KEY = "";
	public static final int TIMEOUT = 30000; //miliseconds
	
	private CommonInfo				mCommonInfo;
	private JotformAPIClient		mApiClient;
	private ArrayList<JSONObject>	mHistoryList;
	private ArrayList<JSONObject>	mSubmissionList;
	
	public SharedData() {
		mCommonInfo = new CommonInfo();
		mHistoryList = new ArrayList<JSONObject>();
		mSubmissionList = new ArrayList<JSONObject>();
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
	
	public void setHistoryArrayList( JSONArray historyList ) {
		
		mHistoryList.clear();

		for ( int i = 0; i < historyList.length(); i ++ ) {

			JSONObject history;
			
			try {
				
				history = historyList.getJSONObject(i);
				mHistoryList.add(history);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
	
	public ArrayList<JSONObject> getHistoryArrayList() {
		
		return mHistoryList;
	}
	
	public void setSubmissionArrayList( JSONArray submissionList ) {
		
		mSubmissionList.clear();

		for ( int i = 0; i < submissionList.length(); i ++ ) {

			JSONObject history;
			
			try {
				
				history = submissionList.getJSONObject(i);
				mSubmissionList.add(history);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public ArrayList<JSONObject> getSubmissionArrayList() {
		
		return mSubmissionList;
	}
}
