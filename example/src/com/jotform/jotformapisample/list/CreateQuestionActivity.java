package com.jotform.jotformapisample.list;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.jotform.jotformapisample.R;

public class CreateQuestionActivity extends Activity {
	
	private Context		mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_createquestion);

		mContext = this;

		initUI();
	}
	
	private void initUI() {
		
		Button createQuestionButton = (Button) findViewById(R.id.button_createquestion);
	}
}
