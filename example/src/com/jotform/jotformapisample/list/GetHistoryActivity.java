package com.jotform.jotformapisample.list;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;

public class GetHistoryActivity extends ListActivity {
	
	private ArrayList<JSONObject>		mHistoryArrayList;
	private ArrayAdapter				mHisotryListAdapter;
	private Spinner						mActionSpinner;
	private Spinner						mDateSpinner;
	private Spinner						mSortBySpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_gethistory);
		
		initData();
		initUI();
	}
	
	private void initData() {
		mHistoryArrayList = new ArrayList<JSONObject>();
	}
	
	private void initUI() {
		
		mActionSpinner = (Spinner) findViewById(R.id.spinner_action);
		
		mDateSpinner = (Spinner) findViewById(R.id.spinner_date);
		
		mSortBySpinner = (Spinner) findViewById(R.id.spinner_sortby);
		
	}
	
	private void loadHistory() {
		
		SharedData sharedData = (SharedData) getApplicationContext(); 
		
		JotformAPIClient apiClient = sharedData.getJotformAPIClient();
	
		
//		apiClient.getHistory(action, date, sortBy, startDate, endDate, responseHandler)
	}
}
