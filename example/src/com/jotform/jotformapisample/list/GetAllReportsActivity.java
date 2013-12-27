package com.jotform.jotformapisample.list;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TwoLineListItem;

import com.jotform.api.JotformAPIClient;
import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GetAllReportsActivity extends ListActivity {

	private Context					mContext;
	private ArrayList<JSONObject> 	mReportArrayList;
	private ArrayAdapter 			mReportListAdapter;
	private ProgressDialog			mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_samplelist);

		initData();
		initUI();
		
		getAllReports();
	}

	private void initData() {

		mReportArrayList = new ArrayList<JSONObject>();
	}

	@SuppressWarnings("unchecked")
	private void initUI() {

		mReportListAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2, mReportArrayList) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent){

				TwoLineListItem row;

				if( convertView == null ) {

					LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					row = (TwoLineListItem)inflater.inflate(android.R.layout.simple_list_item_2, null);

				} else {

					row = (TwoLineListItem)convertView;
				}

				JSONObject data = mReportArrayList.get(position);

				try {
					row.getText1().setTextColor(Color.BLACK);
					row.getText2().setTextColor(Color.DKGRAY);

					row.getText1().setText(data.getString("title"));
					row.getText2().setText(data.getString("id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return row;
			}
		};

		setListAdapter(mReportListAdapter);
	}

	private void updateFormsList() {

		mReportListAdapter.notifyDataSetChanged();
	}

	private void getAllReports() {

		mProgressDialog = ProgressDialog.show(this, "", "Loading reports...", true, false);

		SharedData sharedData = (SharedData) getApplicationContext();

		JotformAPIClient apiClient = sharedData.getJotformAPIClient();

		apiClient.getReports(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject formsResponse){

				if ( formsResponse != null ) {

					try {

						int responseCode = formsResponse.getInt("responseCode");

						if ( responseCode == 200 || responseCode == 206 ) {

							JSONArray forms = formsResponse.getJSONArray("content");

							for ( int i = 0; i < forms.length(); i ++ ) {

								JSONObject form = forms.getJSONObject(i);

								mReportArrayList.add(form);
							}

							updateFormsList();
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

		});


	}
}
