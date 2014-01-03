package com.jotform.jotformapisample.list;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.jotform.jotformapisample.utils.DateTimePicker;
import com.jotform.jotformapisample.utils.DateTimePicker.ICustomDateTimeListener;
import com.jotform.jotformapisample.utils.PrettyDate;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GetHistoryActivity extends Activity implements ICustomDateTimeListener {
	
	private Context						mContext;
	
	private Spinner						mActionSpinner;
	private Spinner						mDateSpinner;
	private Spinner						mSortBySpinner;
	
	private Button						mStartDateButton;
	private Button						mEndDateButton;
	
	private int							mSelectedType; // 0 : startdate, 1 : enddate
	private Date						mStartDate;
	private Date						mEndDate;
	
	private ProgressDialog				mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_gethistory);
		
		initUI();
	}
	
	private void initUI() {
		
		mActionSpinner = (Spinner) findViewById(R.id.spinner_action);
		
		mDateSpinner = (Spinner) findViewById(R.id.spinner_date);
		
		mSortBySpinner = (Spinner) findViewById(R.id.spinner_sortby);
		
		
		mStartDateButton = (Button) findViewById(R.id.button_startdate);
		
		mStartDateButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				mSelectedType = 0;
				
				showTimePicker();
			}
			
		});
		
		mEndDateButton = (Button) findViewById(R.id.button_enddate);
		mEndDateButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mSelectedType = 1;
				
				showTimePicker();
			}
			
		});
		
		Button getHistoryButton = (Button) findViewById(R.id.button_gethistory);
		
		getHistoryButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadHistory();
			}
			
		});
	}
	
	private void startHistoryListActivity() {
		
		Intent intent = new Intent(this, HistoryListActivity.class);
		
		startActivity(intent);
	}
	
	private void showTimePicker() {
		
		DateTimePicker dateTimePicker = new DateTimePicker(GetHistoryActivity.this, this);
		dateTimePicker.set24HourFormat(true);
		dateTimePicker.showDialog();
		
	}
	
	private void loadHistory() {
		
		mProgressDialog = ProgressDialog.show(this, "", "Loading history...", true, false);
		
		final SharedData sharedData = (SharedData) getApplicationContext(); 
		
		JotformAPIClient apiClient = sharedData.getJotformAPIClient();
	
		String action = mActionSpinner.getSelectedItem().toString();
		
		String date = mDateSpinner.getSelectedItem().toString();
		
		String sortBy = mSortBySpinner.getSelectedItem().toString();
		
		String startDate = null;
		
		if ( mStartDate != null )
			startDate = PrettyDate.getDateString(mStartDate);
		
		String endDate = null;
		
		if ( mEndDate != null )
			endDate = PrettyDate.getDateString(mEndDate);
			
		apiClient.getHistory(action, date, sortBy, startDate, endDate, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONObject data) {
				
				int responseCode;

				try {
					
					// check if result is success or fail
					responseCode = data.getInt("responseCode");

					if ( responseCode == 200 || responseCode == 206 ) {
					
						JSONArray historyArray = data.getJSONArray("content");

						sharedData.setHistoryArrayList(historyArray);					
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// dismiss loading dialog
				mProgressDialog.dismiss();
				
				startHistoryListActivity();				
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

//	{"limit-left":1000,"content":[{"timestamp":1388370398,"type":"userLogin","username":"bestfriend21","ip":"199.27.128.165"},{"timestamp":1388218350,"formStatus":"ENABLED","username":"bestfriend21","formID":31963320282854,"type":"formUpdate","ip":"199.27.128.157","formTitle":"Title Me","creator":"bestfriend21"},{"timestamp":1388201861,"type":"userLogin","username":"bestfriend21","ip":"199.27.128.157"}],"message":"success","responseCode":200}
	
	@Override
	public void onSet(Calendar calendarSelected, Date dateSelected, int year,
			String monthFullName, String monthShortName, int monthNumber,
			int date, String weekDayFullName, String weekDayShortName,
			int hour24, int hour12, int min, int sec, String AM_PM) {
		// TODO Auto-generated method stub
		
		if ( mSelectedType == 0 ) {
			
			mStartDate = dateSelected;
			
			mStartDateButton.setText(PrettyDate.getDateString(mStartDate));
			
		} else if ( mSelectedType == 1 ) {
			
			mEndDate = dateSelected;

			mEndDateButton.setText(PrettyDate.getDateString(mEndDate));
		}
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		
	}
}
