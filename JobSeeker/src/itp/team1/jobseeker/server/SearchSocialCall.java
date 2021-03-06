package itp.team1.jobseeker.server;

import itp.team1.jobseeker.Constants;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.DataParseingException;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.Base.DataParser;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall.PlainServerResponseHandler;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall.ServerCallObserver;
import itp.team1.jobseeker.model.Job;
import itp.team1.jobseeker.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class SearchSocialCall implements DataParser {
	
	Context mContext;
	String [] JSONKeys = {"jobs"};

	private static AsyncHttpClient client = new AsyncHttpClient();
	static StringBuilder uriBuilder;
	static PlainServerResponseHandler mResponseHandler;
	static RequestParams params;

	public SearchSocialCall(Context context) {
		uriBuilder = new StringBuilder(Constants.BASE_URL+ "search/social");
		client.addHeader("Accept", "application/json");
		client.addHeader("Content-type", "application/json");
		mResponseHandler = new PlainServerResponseHandler(context, this, uriBuilder.toString());
		mContext = context;
		params = new RequestParams();
	}

	public void makeSearchCall(){
		client.get(mContext, uriBuilder.toString(), params , mResponseHandler);
	}
	

	protected void extractSingleObject(JSONObject object) throws JSONException {
		// TODO Auto-generated method stub
		Job.parseJSONObject(object);
	}

	protected void extractedJSONArray(JSONArray object) throws JSONException {
		// TODO Auto-generated method stub
		Job.parseJSONArray(object);
	}


	protected void errorNoJSONStructureFound(String serverString) {
		// TODO Auto-generated method stub

	}

	protected JSONObject baseServerParseing(String JSONServerResponse) throws JSONException {
		if (JSONServerResponse == null)
			return null;
		Log.v("JSON response", JSONServerResponse);
		JSONObject outer = new JSONObject(JSONServerResponse); // the outer object
		if(outer.has("message"))
		{
			Message.parseJSONObject(outer);
			return outer;
		} else if (outer.has("messages")){
			Message.parseJSONObject(outer);
			return outer;
		} else if(outer.has("job") || outer.has("jobs")) return outer;

		JSONException t = new JSONException("error parsing initial json object");
		throw t;
	}

	public void addServerCallObserver(ServerCallObserver observer){
		mResponseHandler.addObserver(observer);
	}
	
	@Override
	public void parseServerData(String serverString) throws DataParseingException, JSONException {
		JSONObject objAfterServerJunkRemoved = baseServerParseing(serverString);
		if (objAfterServerJunkRemoved == null)
			return;
		JSONObject tempObject = objAfterServerJunkRemoved;
		
		for (String jsonKey : JSONKeys){
			tempObject = objAfterServerJunkRemoved;
			JSONArray array = tempObject.optJSONArray(jsonKey);
			if(array!= null ){
				extractedJSONArray(array);
			}else {
				JSONObject object = tempObject.optJSONObject(jsonKey) ;
				if(tempObject.optJSONObject(jsonKey) != null){
					extractSingleObject(object);
				}else{
					errorNoJSONStructureFound(serverString);
					throw new DataParseingException("Error: no JSON Structure Found");
				}
			}
		}
	}
	
	public RequestParams setParamsSearch(int offset, int limit){
		params = new RequestParams();
		params.put("location", String.valueOf("dundee"));
		params.put("offset", String.valueOf(offset));
		params.put("limit", String.valueOf(limit));
		return params;
	}
	
	public RequestParams setAllParamsSearch(String search, String location, String type, String hours, String employer, String radius, int offset, int limit) {
		params = new RequestParams();
		params.put("keyword", search);
		params.put("location", location);
		params.put("type", type);
		params.put("hours", hours);
		params.put("employer", employer);
		params.put("radius", radius);
		params.put("offset", String.valueOf(offset));
		params.put("limit", String.valueOf(limit));
		return params;
	}
	
}
