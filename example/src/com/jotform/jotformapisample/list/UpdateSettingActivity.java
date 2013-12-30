package com.jotform.jotformapisample.list;

import org.json.JSONArray;
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
import android.widget.EditText;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UpdateSettingActivity extends Activity {
	
	private Context				mContext;
	private EditText			mNameEditText;
	private EditText			mEmailEditText;
	private EditText			mWebsiteEditText;
	private EditText			mTimezoneEditText;
	private EditText			mCompanyEditText;
	private EditText			mSecurityQuestionEditText;
	private EditText			mSecurityAnswerEditText;
	private EditText			mIndustryEditText;
	private ProgressDialog		mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_updatesetting);

		mContext = this;
		
		initUI();
		
		loadUserSetting();
	}
	
	private void initUI() {
		
		Button updateButton = (Button) findViewById(R.id.button_updatesettings);
		
		updateButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		mNameEditText = (EditText) findViewById(R.id.edittext_name);
		
		mEmailEditText = (EditText) findViewById(R.id.edittext_email);
		
		mWebsiteEditText = (EditText) findViewById(R.id.edittext_website);
		
		mTimezoneEditText = (EditText) findViewById(R.id.edittext_timezone);
		
		mCompanyEditText = (EditText) findViewById(R.id.edittext_company);
		
		mSecurityQuestionEditText = (EditText) findViewById(R.id.edittext_securityquestion);
		
		mSecurityAnswerEditText = (EditText) findViewById(R.id.edittext_securityanswer);
		
		mIndustryEditText = (EditText) findViewById(R.id.edittext_industry);
		
	}
	
	private void loadUserSetting() {
		
		// show loading dialog
		mProgressDialog = ProgressDialog.show(this, "", "Loading settings...", true, false);
		
		// create form using JotformAPI client
		SharedData sharedData = (SharedData) getApplicationContext();

		JotformAPIClient apiClient = sharedData.getJotformAPIClient();
		
		apiClient.getSettings(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONObject data) {
				
				int responseCode;

				try {
					
//					{"limit-left":1000,"content":{"time_zone":null,"username":"bestfriend21","updated_at":"2013-12-29 21:28:01","webhooks":"[\"https:\\\/\\\/android.googleapis.com\\\/gcm\\\/send\",\"ssl:\\\/\\\/gateway.sandbox.push.apple.com\"]","status":"ACTIVE","website":null,"email":"best_friend21@hotmail.com","avatarUrl":"http:\/\/www.gravatar.com\/avatar\/498ec06c433c84e4058d311b8a8418c2?s=50&d=identicon","name":null,"usage":"http:\/\/api.jotform.com\/user\/usage","created_at":"2013-06-25 12:31:52","account_type":"http:\/\/api.jotform.com\/system\/plan\/FREE"},"message":"success","responseCode":200}

					// check if result is success or fail
					responseCode = data.getInt("responseCode");

					if ( responseCode == 200 || responseCode == 206 ) {
					
						JSONObject content = data.getJSONObject("content");
						
						mNameEditText.setText(content.getString("name"));
						mEmailEditText.setText(content.getString("email"));
						mWebsiteEditText.setText(content.getString("website"));
						mTimezoneEditText.setText(content.getString("time_zone"));
//						mCompanyEditText.setText(data.getString("company"));
//						mSecurityQuestionEditText.setText(data.getString("securityquestion"));
						
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// dismiss loading dialog
				mProgressDialog.dismiss();				
			}
			
			@Override
			public void onFailure(Throwable arg0, JSONObject data) {
				
				int responseCode;

				try {

					// check what the error is
					responseCode = data.getInt("responseCode");

					if ( responseCode == 401 ) {
						
						// show alert dialog
						String errMsg = data.getString("message") + "\n" + "Please check if your API Key's permission is 'Read Access' or 'Full Access'. You can create form with API key for 'Full Access'.";
						
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
			public void onFailure(Throwable e, JSONArray errorResponse) {

				mProgressDialog.dismiss();
			}

			@Override
			public void onFailure(Throwable e, String errorResponse) {
				mProgressDialog.dismiss();
			}
			
			@Override
			public void onFinish() {
				
			}
			
		});
	}
	
	private void updateUserSetting() {
		
	}
}
