package com.jotform.jotformapisample.list;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class CreateSubmissionActivity extends Activity {

	private static final long FORM_ID = 33444839040453L;
	
	private ProgressDialog			mProgressDialog;	
	private Context					mContext;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_createsubmission);

		mContext = this;

		initData();
		initUI();
	}


	private void initData() {

	}

	private void initUI() {

		Button createSubmissionButton = (Button) findViewById(R.id.button_createsubmission);

		createSubmissionButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				createSubmission();
			}

		});
	}

	@SuppressWarnings("unused")
	private void createSubmission() {
		
		if ( FORM_ID == 0 ) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

			builder.setTitle("JotformAPISample");
			builder.setCancelable(false);
			builder.setMessage("Please put Form's id in line 23.");
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {

				}
			});

			AlertDialog alert = builder.create();
			alert.show();
			
			return;
		}
		
		mProgressDialog = ProgressDialog.show(this, "", "Creating submission...", true, false);		

		SharedData sharedData = (SharedData) getApplicationContext();
		
		JotformAPIClient apiClient = sharedData.getJotformAPIClient();

		HashMap<String, String> submission = new HashMap<String, String>();

		submission.put("1", "XXX");
		submission.put("2", "This is a test for creating submission.");

		apiClient.createFormSubmissions(FORM_ID, submission, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject data) {
				
				int responseCode;

				try {

					responseCode = data.getInt("responseCode");

					if ( responseCode == 200 || responseCode == 206 ) {

						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

						builder.setTitle("JotformAPISample");
						builder.setCancelable(false);
						builder.setMessage("You created submission successfully.");
						builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {

							}
						});

						AlertDialog alert = builder.create();
						alert.show();

					}

					mProgressDialog.dismiss();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0, JSONObject data) {
				
				int responseCode;

				try {

					responseCode = data.getInt("responseCode");

					if ( responseCode == 401 ) {
						
						String errMsg = data.getString("message") + "\n" + "Please check if your API Key's permission is 'Read Access' or 'Full Access'. You can create submission with API key for 'Full Access'.";
						
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

						builder.setTitle("JotformAPISample");
						builder.setCancelable(false);
						builder.setMessage(errMsg);
						builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {

							}
						});

						AlertDialog alert = builder.create();
						alert.show();
					}
					
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				
				mProgressDialog.dismiss();
			}

			@Override
			public void onFinish() {
				
			}

		});
	}
}
