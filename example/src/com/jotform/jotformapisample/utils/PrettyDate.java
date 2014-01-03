package com.jotform.jotformapisample.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class for human-readable, pretty date formatting
 * @author Lea Verou
 */
public class PrettyDate
{
	private static final SimpleDateFormat DATE_FORMATTER =
			new SimpleDateFormat("MM/dd/yyyy");
	
	private Date date;

	public PrettyDate() {
		this(new Date());
	}

	public PrettyDate(Date date) {
		this.date = date;
	}

	public String toString() {
		long	current = (new Date()).getTime(),
			timestamp = date.getTime(),
			diff = (current - timestamp)/1000;
		int	amount = 0;
		String	what = "";

		/**
		 * Second counts
		 * 3600: hour
		 * 86400: day
		 * 604800: week
		 * 2592000: month
		 * 31536000: year
		 */

		if(diff > 31536000) {
			amount = (int)(diff/31536000);
			what = "y";
		}
		else if(diff > 31536000) {
			amount = (int)(diff/31536000);
			what = "M";
		}
		else if(diff > 604800) {
			amount = (int)(diff/604800);
			what = "w";
		}
		else if(diff > 86400) {
			amount = (int)(diff/86400);
			what = "d";
		}
		else if(diff > 3600) {
			amount = (int)(diff/3600);
			what = "h";
		}
		else if(diff > 60) {
			amount = (int)(diff/60);
			what = "m";
		}
		else {
			amount = (int)diff;
			what = "s";
			if(amount < 6) {
				return "Just now";
			}
		}

		return amount + what;
	}
	
	public static String getDateString(Date date) {	
		return DATE_FORMATTER.format(date);
	}
	
	public static String getDateFromTimeStamp(long timestamp) {
		
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		Date date = cal.getTime();
		
		return DATE_FORMATTER.format(date);		
	}
}
