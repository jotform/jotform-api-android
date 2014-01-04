package com.jotform.jotformapisample.list;

import java.util.HashMap;

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
import android.widget.Spinner;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class CreateFormPropertiesActivity extends Activity {
	
	private static final long FORM_ID = 0L;
	
	private Spinner				mActiveRedirectSpinner;
	private EditText			mThankUrlEditText;
	private EditText			mFormWidthEditText;
	private EditText			mLabelWidthEditText;
	private Spinner				mStylesSpinner;
	private ProgressDialog		mProgressDialog;
	private Context				mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_createformproperty);
		
		mContext = this;
		
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
		
		mActiveRedirectSpinner = (Spinner) findViewById(R.id.spinner_activeredirect);
		
		mThankUrlEditText = (EditText) findViewById(R.id.edittext_thankurl);
		mFormWidthEditText = (EditText) findViewById(R.id.edittext_formWidth);
		mLabelWidthEditText = (EditText) findViewById(R.id.edittext_labelWidth);
		
		mStylesSpinner = (Spinner) findViewById(R.id.spinner_styles);
	}
	
	private void createFormProperties() {
	
		mProgressDialog = ProgressDialog.show(this, "", "Creating form properties...", true, false);
		
		SharedData sharedData = (SharedData) getApplicationContext();
		
		JotformAPIClient apiClient = sharedData.getJotformAPIClient();
		
		HashMap<String, String> formProperties = new HashMap<String, String>();
		
		if ( mThankUrlEditText.getText().length() > 0 )
			formProperties.put("thankurl", mThankUrlEditText.getText().toString());
		
		if ( mFormWidthEditText.getText().length() > 0 )
			formProperties.put("formWidth", mFormWidthEditText.getText().toString());
		
		if ( mLabelWidthEditText.getText().length() > 0 )
			formProperties.put("labelWidth", mLabelWidthEditText.getText().toString());
		
		formProperties.put("activeRedirect", mActiveRedirectSpinner.getSelectedItem().toString());
		formProperties.put("styles", mStylesSpinner.getSelectedItem().toString());
		
		apiClient.setFormProperties(FORM_ID, formProperties, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONObject data) {

				int responseCode;

				try {

					// check if result is success or fail
					responseCode = data.getInt("responseCode");

					if ( responseCode == 200 || responseCode == 206 ) {
						
						// show alert dialog for success
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

						builder.setTitle("JotformAPISample");
						builder.setCancelable(false);
						builder.setMessage("You added form properties successfully.");
						builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {

							}
						});

						AlertDialog alert = builder.create();
						alert.show();
						
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// dismiss loading dialog
				mProgressDialog.dismiss();
			}

			@Override
			public void onFailure(Throwable e, JSONObject data) {

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
			public void onFailure(Throwable e, String response) {

				mProgressDialog.dismiss();
			}
			
		});
	}
	
}
