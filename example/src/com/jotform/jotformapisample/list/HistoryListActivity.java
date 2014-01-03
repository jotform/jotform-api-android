package com.jotform.jotformapisample.list;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TwoLineListItem;

import com.jotform.jotformapisample.R;
import com.jotform.jotformapisample.model.SharedData;

public class HistoryListActivity extends ListActivity {

	private ArrayAdapter 			mHistoryListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_samplelist);

		initUI();		
		updateHistoryList();
	}

	@SuppressWarnings("unchecked")
	private void initUI() {
		
		SharedData sharedData = (SharedData) getApplicationContext();
		
		final ArrayList<JSONObject> historyList = sharedData.getHistoryArrayList();

		mHistoryListAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2, historyList) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent){

				TwoLineListItem row;

				if( convertView == null ) {

					LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					row = (TwoLineListItem)inflater.inflate(android.R.layout.simple_list_item_2, null);

				} else {

					row = (TwoLineListItem)convertView;
				}

				JSONObject data = historyList.get(position);

				try {
					
					row.getText1().setTextColor(Color.BLACK);
					row.getText2().setTextColor(Color.DKGRAY);

					row.getText1().setText("Username : " + data.getString("username"));
					row.getText2().setText("Type : " + data.getString("type") + "\n" + "TimeStamp : " + data.getString("timestamp"));
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return row;
			}
		};

		setListAdapter(mHistoryListAdapter);
	}

	private void updateHistoryList() {
		mHistoryListAdapter.notifyDataSetChanged();
	}
}
