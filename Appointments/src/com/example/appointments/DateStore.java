package com.example.appointments;

import java.io.Serializable;
import java.util.ArrayList;

public class DateStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> list;
	
	private String listAsString;
	
	private int year, month, day;
	
	public int getYear(){
		return year;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getDay(){
		return day;
	}
    
    public DateStore(int year, int month, int day){
	    list = new ArrayList<String>();
	    listAsString = "";
	    this.year = year;
	    this.month = month;
	    this.day = day;
    }
    
    public ArrayList<String> getList(){
    	return list;
    }
    
    public void setList(ArrayList<String> list){
    	this.list = list;
    }
    
    public void addDateToList(String date){
    	list.add(date);
    }
    
    public void removeDateFromList(int index){
    	list.remove(index);
    }
    
    public String getListAsString()
    {
    
    	for(int i = 0; i < list.size(); i++){
    		listAsString += list.get(i);
    	}
    	return listAsString;
    	
    }
    
	public String getDateAsString(){
		
		return "Year: " + Integer.toString(year)+ "Month: " + Integer.toString(month+1) + "Day: " + Integer.toString(day);
		
	}
	
}
