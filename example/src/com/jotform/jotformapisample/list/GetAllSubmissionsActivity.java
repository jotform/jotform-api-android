package com.jotform.jotformapisample.list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GetAllSubmissionsActivity extends Activity {

	private static final int		SUBMISSION_LIMIT_COUNT = 50;

	private ProgressDialog			mProgressDialog;
	private EditText				mOffsetEditText;
	private EditText				mLimitEditText;
	private Spinner					mOrderbySpinner;
	private EditText				mFilterEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_getsubmissions);

		initUI();
	}
	
	@SuppressWarnings("unchecked")
	private void initUI() {
		
		mOffsetEditText = (EditText) findViewById(R.id.edittext_offset);
		mLimitEditText = (EditText) findViewById(R.id.edittext_limit);
		mOrderbySpinner = (Spinner) findViewById(R.id.spinner_orderby);
		mFilterEditText = (EditText) findViewById(R.id.edittext_filter);
		
		Button getSubmissionButton = (Button) findViewById(R.id.button_getsubmissions);
		getSubmissionButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getAllSubmissions();
			}
			
		});
		
		mOffsetEditText.setText("0");
		mLimitEditText.setText("100");
		mFilterEditText.setText("2013-01-01 00:00:00");
	}
	
	private void getAllSubmissions() {
		
		mProgressDialog = ProgressDialog.show(this, "", "Loading submissions...", true, false);
		
		final SharedData sharedData = (SharedData) getApplicationContext();
 		
		JotformAPIClient apiClient = sharedData.getJotformAPIClient();
		
		JSONObject filter = null;
		
		try {
			
			Integer offset = null;
			
			if ( mOffsetEditText.getText().length() > 0 )
				offset = Integer.parseInt(mOffsetEditText.getText().toString());
			
			Integer limitCount = null;
			
			if ( mLimitEditText.getText().length() > 0 )
				limitCount = Integer.parseInt(mLimitEditText.getText().toString());
			else
				limitCount = SUBMISSION_LIMIT_COUNT;
			
			String orderBy = mOrderbySpinner.getSelectedItem().toString();

			if ( mFilterEditText.getText().length() > 0 ) {
				
				filter = new JSONObject();
				filter.put("created_at:gt", mFilterEditText.getText().toString());
				
			}
			
			apiClient.getSubmissions(offset, limitCount, orderBy, filter, new JsonHttpResponseHandler() {
				
				@Override
				public void onSuccess(JSONObject submissionsResponse){
					
					if ( submissionsResponse != null ) {
						
						try {
							
							int responseCode = submissionsResponse.getInt("responseCode");
							
							if ( responseCode == 200 || responseCode == 206 ) {
								
								JSONArray submissions = submissionsResponse.getJSONArray("content");

								sharedData.setSubmissionArrayList(submissions);
								
								startSubmissionListActivity();
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
	
	private void startSubmissionListActivity() {
		
		Intent intent = new Intent(this, SubmissionListActivity.class);
		startActivity(intent);
		
	}
}
