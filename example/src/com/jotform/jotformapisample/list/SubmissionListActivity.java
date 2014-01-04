package com.jotform.jotformapisample.list;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TwoLineListItem;

public class SubmissionListActivity extends ListActivity {

	private ArrayAdapter<?>			mSubmissionListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_samplelist);

		initUI();		
	}
	
	@SuppressWarnings("unchecked")
	private void initUI() {
		
		SharedData sharedData = (SharedData) getApplicationContext();
		
		final ArrayList<JSONObject> submissionList = sharedData.getSubmissionArrayList();

		mSubmissionListAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2, submissionList){
			
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent){
	        	
	            TwoLineListItem row;
	            
	            if( convertView == null ) {
	            	
	                LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                row = (TwoLineListItem)inflater.inflate(android.R.layout.simple_list_item_2, null);
	                
	            } else {
	            	
	                row = (TwoLineListItem)convertView;
	            }
	            
	            JSONObject data = submissionList.get(position);
	            try {
	            	row.getText1().setTextColor(Color.BLACK);
	            	row.getText2().setTextColor(Color.DKGRAY);
	            	
					row.getText1().setText(data.getString("created_at"));
		            row.getText2().setText(data.getString("answers"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 
	            return row;
	        }
	    };
		
		setListAdapter(mSubmissionListAdapter);
	}
}
