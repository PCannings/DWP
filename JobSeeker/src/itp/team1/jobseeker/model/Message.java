package itp.team1.jobseeker.model;

import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class Message {

	static public Message messageObject;
	
	static public  String  message;
	
	static public  LinkedList<String> messages;
	
	static public Message parseJSONObject(JSONObject j) throws JSONException
	{
		if ( j == null ) {
			return null;
		}

		messageObject = new Message();
		
		messageObject.message = j.optString("message");
		
		return messageObject;
	}
	
	static public Message parseJSONArray(JSONArray j) throws JSONException
	{
		if ( j == null ) {
			return null;
		}

		messageObject = new Message();
		
		LinkedList<String> d = new LinkedList<String>();
		for(int i =0; i <j.length(); i++){
			String p = j.optString(i);
			d.add(p);
		}
		
		messageObject = new Message();
		
		messageObject.messages = new LinkedList<String>();
		messageObject.messages.addAll(d);
		
		return messageObject;
	}
}
