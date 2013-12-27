package com.jotform.jotformapisample;


import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {

	private EditText		mUsernameEditText;
	private EditText		mPasswordEditText;
	private Button			mGetApiKeyButton;
	private ProgressDialog	mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		initUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initUI() {

		mUsernameEditText = (EditText) findViewById(R.id.edittext_username);
		mPasswordEditText = (EditText) findViewById(R.id.edittext_password);

		mGetApiKeyButton = (Button) findViewById(R.id.button_getappkey);

		mGetApiKeyButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if ( mUsernameEditText.length() == 0 ) {

					mUsernameEditText.setError("");
					mUsernameEditText.requestFocus();

					return;
				}

				if ( mPasswordEditText.length() == 0 ) {

					mPasswordEditText.setError("");
					mPasswordEditText.requestFocus();

					return;
				}

				String username = mUsernameEditText.getText().toString();
				String password = mPasswordEditText.getText().toString();


				getAppKey(username, password);
			}

		});

		selectOptionDialog();
	}

	private void selectOptionDialog() {

		final SharedData sharedData = (SharedData) getApplicationContext();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setCancelable(false);
		builder.setTitle("JotformAPISample");
		builder.setMessage("Do you have your Jotform account?");
		builder.setPositiveButton("Yes, i have", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {

			}

		});
		builder.setNegativeButton("No, i have an API key", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {

				if ( SharedData.API_KEY.equals("") ) {
					showApiKeyError();

					return;
				}

				sharedData.setApiKey(SharedData.API_KEY);
				sharedData.initApiClient();
				startSampleListActivity();
			}

		});

		AlertDialog alert = builder.create();
		alert.show();

	}

	private void showApiKeyError() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setCancelable(false);
		builder.setTitle("JotformAPISample");
		builder.setMessage("Please put your API key in SharedData.java 9 line.");
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {
				System.exit(0);
			}

		});

		builder.show();
	}

	private void startSampleListActivity() {

		Intent intent = new Intent(this, SampleListActivity.class);

		startActivity(intent);

	}

	private void getAppKey(String username, String password) {

		mProgressDialog = ProgressDialog.show(this, "", "Getting App Key...", true, false);

		final SharedData sharedData = (SharedData) getApplicationContext();

		JotformAPIClient apiClient = new JotformAPIClient();
		apiClient.setTimeOut(SharedData.TIMEOUT);

		HashMap<String, String> userInfo = new HashMap<String, String>();
		userInfo.put("username", username);
		userInfo.put("password", password);
		userInfo.put("appName", "JotformAPISample");
		userInfo.put("access", "full");

		apiClient.login(userInfo, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject loginResponse){

				if ( loginResponse != null ) {

					try {

						int responseCode = loginResponse.getInt("responseCode");

						if ( responseCode == 200 || responseCode == 206 ) {

							JSONObject content = loginResponse.getJSONObject("content");

							sharedData.setApiKey(content.getString("appKey"));
							sharedData.initApiClient();

							startSampleListActivity();
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

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {

				mProgressDialog.dismiss();

				// show alert dialog
				String errMsg;

				try {

					errMsg = errorResponse.getString("errorDetails");

					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

					builder.setTitle("JotformAPISample");
					builder.setCancelable(false);
					builder.setMessage(errMsg);
					builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {

						}
					});

					AlertDialog alert = builder.create();
					alert.show();

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void onFailure(Throwable e, String response) {

			}

			@Override
			public void onFinish() {

				mProgressDialog.dismiss();
			}

		});
	}
}
