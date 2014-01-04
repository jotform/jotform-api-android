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
import android.widget.TextView;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GetFormPropertiesActivity extends Activity {

	private static final long				FORM_ID = 0L;

	private Context							mContext;	
	private ProgressDialog					mProgressDialog;
	private TextView						mFormPropertyTextView;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_getformproperty);

		mContext = this;

		initUI();

		getFormProperty();		
	}

	private void initUI() {

		mFormPropertyTextView = (TextView) findViewById(R.id.textview_formproperty);
	}

	private void getFormProperty() {

		// check if FORM_ID is specified
		if ( FORM_ID == 0L ) {

			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

			builder.setTitle("JotformAPISample");
			builder.setCancelable(false);
			builder.setMessage("Please put Form's id in line 25, GetFormPropertiesActivity.java");
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {

				}
			});

			AlertDialog alert = builder.create();
			alert.show();

			return;
		}

		mProgressDialog = ProgressDialog.show(this, "", "Loading form properties...", true, false);

		SharedData sharedData = (SharedData) getApplicationContext();

		JotformAPIClient apiClient = sharedData.getJotformAPIClient();

		apiClient.getFormProperties(FORM_ID, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject data) {

				int responseCode;

				try {

					// check if result is success or fail
					responseCode = data.getInt("responseCode");

					if ( responseCode == 200 || responseCode == 206 ) {

						mFormPropertyTextView.setText(data.getString("content"));

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
