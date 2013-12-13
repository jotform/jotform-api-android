package com.jotform.jotformapisample.list;

import java.util.HashMap;
import java.util.Map;

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

public class CreateFormActivity extends Activity {

	private Context					mContext;
	private ProgressDialog			mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_createform);

		mContext = this;

		initUI();
	}

	private void initUI() {

		Button createFormButton = (Button) findViewById(R.id.button_createform);

		createFormButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				createForm();
			}

		});
	}

	private void createForm() {

		// show loading dialog
		mProgressDialog = ProgressDialog.show(this, "", "Creating forms...", true, false);		

		// create form using JotformAPI client
		SharedData sharedData = (SharedData) getApplicationContext();

		JotformAPIClient apiClient = sharedData.getJotformAPIClient();

		Map<String, String> properties = new HashMap<String, String>();
		properties.put("title", "Test Form");

		Map<String, Map<String, String>> questions = new HashMap<String, Map<String, String>>();

		//create textbox field		
		Map<String, String> questionsItem = new HashMap<String, String>();
		questionsItem.put("type", "control_textbox");
		questionsItem.put("text", "Name");
		questionsItem.put("order", "1");
		questionsItem.put("name", "textboxName");


		questions.put("1", questionsItem);

		//create an textarea field
		questionsItem = new HashMap<String, String>();
		questionsItem.put("type", "control_textarea");
		questionsItem.put("text", "Message");
		questionsItem.put("order", "2");
		questionsItem.put("name", "textboxMessage");

		questions.put("2", questionsItem);

		Map form = new HashMap();
		form.put("properties", properties);
		form.put("questions", questions);

		apiClient.createForm(form, new JsonHttpResponseHandler(){

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
						builder.setMessage("You created form successfully.");
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

		});
	}
}
