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
}
