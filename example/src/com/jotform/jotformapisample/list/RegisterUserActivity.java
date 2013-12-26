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
import android.widget.EditText;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class RegisterUserActivity extends Activity {
	
	private Context				mContext;
	private EditText			mUsernameEditText;
	private EditText			mPasswordEditText;
	private EditText			mEmailEditText;
	
	private ProgressDialog		mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		mContext = this;
		
		initUI();
	}
	
	private void initUI() {
		
		mUsernameEditText = (EditText) findViewById(R.id.edittext_username);
		
		mEmailEditText = (EditText) findViewById(R.id.edittext_email);
		
		mPasswordEditText = (EditText) findViewById(R.id.edittext_password);
		
		Button registerButton = (Button) findViewById(R.id.button_register);
		
		registerButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if ( mUsernameEditText.getText().length() == 0 ) {
					
					mUsernameEditText.setError("Please enter username.");
					
					return;
				}
				
				if ( mPasswordEditText.getText().length() == 0 ) {
					
					mPasswordEditText.setError("Please enter password");
					
					return;
				}
				
				if( mEmailEditText.getText().length() == 0 ) {
					
					mEmailEditText.setError("Please enter email");
					
					return;
				}
				
			}

		});
	}

	private void registerUser(String username, String email, String password) {
		
		// show loading dialog
		mProgressDialog = ProgressDialog.show(this, "", "Registering user...", true, false);		
		
		// register user using JotformAPI client
		SharedData sharedData = (SharedData) getApplicationContext();
		
		JotformAPIClient apiClient = sharedData.getJotformAPIClient();
		
		// create user info
		
		HashMap<String, String> userinfo = new HashMap<String, String>();
		userinfo.put("username", username);
		userinfo.put("password", password);
		userinfo.put("email", email);
		
		apiClient.registerUser(userinfo, new JsonHttpResponseHandler(){
			
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
						builder.setMessage("You registered new user successfully.");
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
			public void onFinish() {
				
			}
			
		});
	}
}
