package com.jotform.jotformapisample.list;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class CreateFormPropertiesActivity extends Activity {
	
	private static final long FORM_ID = 0L;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_createformproperty);
		
		initData();
		initUI();
	}
	
	private void initData() {
		
	}
	
	private void initUI() {
		
		Button createFormPropertiesButton = (Button) findViewById(R.id.button_createformproperty);
		
		createFormPropertiesButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				createFormProperties();
			}
			
		});
	}
	
	private void createFormProperties() {
	
		SharedData sharedData = (SharedData) getApplicationContext();
		
		JotformAPIClient apiClient = sharedData.getJotformAPIClient();
		
		HashMap<String, String> formProperties = new HashMap<String, String>();
		
		
		apiClient.setFormProperties(FORM_ID, formProperties, new JsonHttpResponseHandler(){
			
			
			
			
		});
	}
	
}
