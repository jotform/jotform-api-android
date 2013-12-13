package com.jotform.jotformapisample.list;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GetAllSubmissionsActivity extends ListActivity {

	private static final int		SUBMISSION_LIMIT_COUNT = 50;

	private ProgressDialog			mProgressDialog;
	private ArrayList<JSONObject>	mSubmissionArrayList;
	private ArrayAdapter<?>			mSubmissionListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_samplelist);
		
		initData();
		initUI();
		
		getAllSubmissions();
	}
	
	private void initData() {
		
		mSubmissionArrayList = new ArrayList<JSONObject>();
	}
	
	@SuppressWarnings("unchecked")
	private void initUI() {

		mSubmissionListAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2, mSubmissionArrayList){
			
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent){
	        	
	            TwoLineListItem row;
	            
	            if( convertView == null ) {
	            	
	                LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                row = (TwoLineListItem)inflater.inflate(android.R.layout.simple_list_item_2, null);
	                
	            } else {
	            	
	                row = (TwoLineListItem)convertView;
	            }
	            
	            JSONObject data = mSubmissionArrayList.get(position);
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
	
	private void updateFormsList() {
		
		mSubmissionListAdapter.notifyDataSetChanged();
	}
	
	private void getAllSubmissions() {
		
		mProgressDialog = ProgressDialog.show(this, "", "Loading submissions...", true, false);
		
		SharedData sharedData = (SharedData) getApplicationContext();
 		
		JotformAPIClient apiClient = sharedData.getJotformAPIClient();
		
		JSONObject filter = new JSONObject();
		
		try {
			
			filter.put("created_at:gt", "2013-01-01 00:00:00");
			
			apiClient.getSubmissions(SUBMISSION_LIMIT_COUNT, "created_at", filter, new JsonHttpResponseHandler() {
				
				@Override
				public void onSuccess(JSONObject submissionsResponse){
					
					if ( submissionsResponse != null ) {
						
						try {
							
							int responseCode = submissionsResponse.getInt("responseCode");
							
							if ( responseCode == 200 || responseCode == 206 ) {
								
								JSONArray forms = submissionsResponse.getJSONArray("content");

								for ( int i = 0; i < forms.length(); i ++ ) {
									
									JSONObject submission = forms.getJSONObject(i);
									
									mSubmissionArrayList.add(submission);
								}
								
								updateFormsList();
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					mProgressDialog.dismiss();
				}
				
				@Override
				public void onFailure(Throwable e, JSONArray errorResponse) {

					mProgressDialog.dismiss();
				}
				
			});
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
	}
}
