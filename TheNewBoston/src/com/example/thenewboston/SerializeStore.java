package com.example.thenewboston;

import java.io.Serializable;

public class SerializeStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int day;
	int month;
	int year;
	
	public SerializeStore(int day, int month, int year){
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public String getString(){
		return Integer.toString(day) + Integer.toString(month) +Integer.toString(year) ;
	}
	
}
