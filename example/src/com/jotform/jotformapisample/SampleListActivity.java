package com.jotform.jotformapisample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jotform.jotformapisample.list.CreateFormActivity;
import com.jotform.jotformapisample.list.CreateQuestionActivity;
import com.jotform.jotformapisample.list.CreateReportActivity;
import com.jotform.jotformapisample.list.CreateSubmissionActivity;
import com.jotform.jotformapisample.list.GetAllFormsActivity;
import com.jotform.jotformapisample.list.GetAllReportsActivity;
import com.jotform.jotformapisample.list.GetAllSubmissionsActivity;
import com.jotform.jotformapisample.list.RegisterUserActivity;

public class SampleListActivity extends ListActivity {

	String[] listItems = {"Get all forms", "Get all submissions", "Get all reports", "Create form", "Create submission", "Create report", "Register user", "Create question"};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_samplelist);

		setListAdapter(new ArrayAdapter(this,  android.R.layout.simple_list_item_1, listItems));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		startSampleActivity(position);
	}

	private void startSampleActivity(int index) {

		if ( index == 0 ) {

			Intent intent = new Intent(this, GetAllFormsActivity.class);
			startActivity(intent);

		} else if ( index == 1 ) {

			Intent intent = new Intent(this, GetAllSubmissionsActivity.class);
			startActivity(intent);
			
		} else if ( index == 2 ) {
			
			Intent intent = new Intent(this, GetAllReportsActivity.class);
			startActivity(intent);
			
		} else if ( index == 3 ) {

			Intent intent = new Intent(this, CreateFormActivity.class);
			startActivity(intent);
			
		} else if ( index == 4 ) {
			
			Intent intent = new Intent(this, CreateSubmissionActivity.class);
			startActivity(intent);
			
		} else if ( index == 5 ) {
			
			Intent intent = new Intent(this, CreateReportActivity.class);
			startActivity(intent);
			
		} else if ( index == 6 ) {
			
			Intent intent = new Intent(this, RegisterUserActivity.class);
			startActivity(intent);
			
		} else if ( index == 7 ) {
			
			Intent intent = new Intent(this, CreateQuestionActivity.class);
			startActivity(intent);
		}
	}
}
