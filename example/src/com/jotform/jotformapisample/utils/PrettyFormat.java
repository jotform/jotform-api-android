package com.jotform.jotformapisample.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.util.Base64;

public class PrettyFormat {

	public static boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	public static String getUniqueDeviceId(Context context) {
		
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

	    final String tmDevice, tmSerial, androidId;
	    tmDevice = "" + tm.getDeviceId();
	    tmSerial = "" + tm.getSimSerialNumber();
	    androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	    String deviceId = deviceUuid.toString();
	    
	    return deviceId;
	}
	
	public static ArrayList<String> getEmailListFromAnswerCursor(Cursor cursor) {

		ArrayList<String> emailList = new ArrayList<String>();
		
		String answersString = cursor.getString(cursor.getColumnIndex("answers"));

		byte[] data = Base64.decode(answersString, Base64.DEFAULT);

		try {
			answersString = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JSONObject answers;
		
		try {
			answers = new JSONObject(answersString);

			Iterator<String> answer_it = answers.keys();
			List<String> answer_list = new ArrayList<String>();

			while ( answer_it.hasNext() )
				answer_list.add(answer_it.next());


			Collections.sort(answer_list, new Comparator<String>() {

				@Override
				public int compare(String lhs, String rhs) {

					Integer numA = Integer.parseInt(lhs);
					Integer numB = Integer.parseInt(rhs);

					return numA.compareTo(numB);
				}

			});

			String type = "";

			for ( int i = 0; i < answer_list.size(); i ++ ) {

				JSONObject answer = answers.getJSONObject(answer_list.get(i));

				type = answer.getString("type");

				if (type.equals("control_text") 
						|| type.equals("control_textarea")
						|| type.equals("control_textbox")) {
					
					String answerStr = answer.getString("text");

					if ( PrettyFormat.isEmailValid(answerStr) ) {
						emailList.add(answerStr);
					}
					
					if ( answer.has("answer") && answer.get("answer") instanceof String ) {
						answerStr = answer.getString("answer");
						
						if ( PrettyFormat.isEmailValid(answerStr) ) {
							emailList.add(answerStr);
						}
					}
					
				} else if ( type.equals("control_email") ) {
					
					String email = answer.getString("answer");
					
					if ( PrettyFormat.isEmailValid(email) ) {
						emailList.add(email);
					}
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return emailList;
	}
}
