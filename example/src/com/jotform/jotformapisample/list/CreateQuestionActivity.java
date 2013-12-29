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

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class CreateQuestionActivity extends Activity {

	private Context					mContext;
	private static final long		FORM_ID = 0L;
	private ProgressDialog			mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_createquestion);

		mContext = this;

		initUI();
	}

	private void initUI() {

		Button createQuestionButton = (Button) findViewById(R.id.button_createquestion);

		createQuestionButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				createQuestion();
			}

		});
	}

	private void createQuestion() {

		// check if FORM_ID is specified
		if ( FORM_ID == 0L ) {

			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

			builder.setTitle("JotformAPISample");
			builder.setCancelable(false);
			builder.setMessage("Please put Form's id in line 27, CreateQuestionActivity.Java");
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {

				}
			});

			AlertDialog alert = builder.create();
			alert.show();

			return;
		}

		// show loading dialog
		mProgressDialog = ProgressDialog.show(this, "", "Creating question...", true, false);		

		// create question using JotformAPI client
		SharedData sharedData = (SharedData) getApplicationContext();

		JotformAPIClient apiClient = sharedData.getJotformAPIClient();

		/* 
			please click the following link to reference fields
				http://api.jotform.com/docs/properties/index.php
		 */

		HashMap<String, String> question = new HashMap<String, String>();
		question.put("type", "control_textbox");
		question.put("text", "New Text");
		question.put("order", "1");
		question.put("name", "New Name of Question");

		apiClient.createFormQuestion(FORM_ID, question, new JsonHttpResponseHandler() {

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
						builder.setMessage("You created question successfully.");
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
