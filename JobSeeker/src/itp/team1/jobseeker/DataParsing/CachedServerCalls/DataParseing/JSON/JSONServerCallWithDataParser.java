package itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.DataParseingException;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.Base.DataParser;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall.ServerCall;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall.ServerCallMethod;

abstract public class JSONServerCallWithDataParser extends ServerCall implements DataParser {

	/**
	 * the server key used to encode the object
	 */
	String [] JSONKeys;
	
	/**
	 * @param jSONKey the key of the object/array you are trying to extract
	 */
	public JSONServerCallWithDataParser(Context context, String mApiName, ServerCallMethod mMethod, String jSONKey) {	
		super(context, mApiName, mMethod, null);
		setDataParser(this);
		JSONKeys = new String[1];
		JSONKeys[0] = jSONKey;
	};
	
	/**
	 * @param jSONKey the array of keys of the objects/arrays you are trying to extract
	 */
	public JSONServerCallWithDataParser(Context context, String mApiName, ServerCallMethod mMethod, String [] jSONKeys) {	
		super(context, mApiName, mMethod, null);
		setDataParser(this);
		JSONKeys = jSONKeys;
	};

	/**
	 * 
	 * here is were you perform any base server parsing for your JSON object I.E
	 * if you have any json data sent with every post that needs monitored here 
	 * is a good place to do it
	 * @return
	 */
	abstract protected JSONObject baseServerParseing(String JSONServerResponse) throws JSONException;

	/**
	 * @param object the JSON extracted from JSON returned from baseServerParseing 
	 * 
	 */
	abstract protected void extractSingleObject(JSONObject object) throws JSONException;

	/**
	 * @param object the JSON extracted from JSON returned from baseServerParseing 
	 * 
	 */
	abstract protected void extractedJSONArray(JSONArray object) throws JSONException;
	
	/**
	 * @param object the JSON extracted from JSON returned from baseServerParseing 
	 * 
	 */
	abstract protected void errorNoJSONStructureFound(String serverString);

	@Override
	public void parseServerData(String serverString) throws DataParseingException, JSONException {
		JSONObject objAfterServerJunkRemoved = baseServerParseing(serverString);
		if (objAfterServerJunkRemoved == null)
			return;
		for (String jsonKey : JSONKeys){
			
			JSONArray array = objAfterServerJunkRemoved.optJSONArray(jsonKey);
			if(array!= null ){
				extractedJSONArray(array);
			}else {
				JSONObject object = objAfterServerJunkRemoved.optJSONObject(jsonKey) ;
				if(objAfterServerJunkRemoved.optJSONObject(jsonKey) != null){
					extractSingleObject(object);
				}else{
					errorNoJSONStructureFound(serverString);
					throw new DataParseingException("Error: no JSON Structure Found");
				}
			}
		}
		
	}

}
