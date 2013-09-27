package itp.team1.jobseeker.mainscreens.appointment;

import java.io.Serializable;

public class TimeStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int hour, minute;
	
	public int getHour(){
		return hour;
	}
	
	public int getMinute(){
		return minute;
	}
	
	public TimeStore (int hour, int minute){
		this.hour = hour;
		this.minute = minute;
	}
	
	public String getTimeAsString(){
		return "Hours: " + Integer.toString(hour)+ "Minutes: " + Integer.toString(minute);
	}
	
}
