package com.scripted.roi;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

public class ROIExeTime {
	public static  Timestamp startTime;
	public static Timestamp endTime;
	private static final Logger log = Logger.getLogger(ROIAPIrequest.class);
	public static void startTime() {

		Date date = new java.util.Date();
		startTime = new Timestamp(date.getTime());
		
	}

	public static String endTime() throws ClientProtocolException, IOException {
		String totalHours = null;
		try {
		Date date = new java.util.Date();
		endTime = new Timestamp(date.getTime());
		// get time difference in seconds
		long milliseconds = endTime.getTime() - startTime.getTime();
		double seconds = milliseconds / 1000;
		// calculate hours minutes and seconds
		DecimalFormat df = new DecimalFormat("0.0000");
		double hours = seconds / 3600;
		double minutes = (seconds % 3600) / 60;
		seconds = (seconds % 3600) % 60;
		df = new DecimalFormat("0.0000");
		 totalHours = df.format(hours);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error occurred while calling ROI API "+"Exception"+e);
		}
		return totalHours;
		

	}

}
