package itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.Base.DataParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class ServerCall {
	/**
	 * Context
	 */
	protected Context mContext;
	/**
	 * the username all server calls Require
	 * */
	protected static String SERVER_ACCESSUSER = null;
	/**
	 * the access token all server calls Require
	 */
	protected static String SERVER_ACCESSTOKEN = null;
	/**
	 * the access token all server calls Require
	 */
	protected static String SERVER_USERAGENT = null;
	/**
	 * the base URL Used by all server calls
	 */
	protected static String BASEURL = null;
	/**
	 * the part of the URL for this API call After the url
	 */
	protected String mApiName = null;
	/**
	 * the Method that this server call uses can be either POST,PUT,GET,DELETE	
	 */
	ServerCallMethod mMethod;
	/**
	 * A single response Handler that will parse every response from the server
	 */
	protected ServerResponseHandler mResponseHandler;	
	/**
	 * The AsyncHttpClient that actually makes the calls
	 */
	protected AsyncHttpClient client = new AsyncHttpClient();
	/**
	 * @param mApiName   the part of the URL for this API call After the url
	 * @param mMethod    the Method that this server call uses can be either POST,PUT,GET,DELETE	
	 * @param dataParser A data Parser object customized for this call
	 */
	public ServerCall(Context context, String mApiName, ServerCallMethod mMethod, DataParser dataParser) {
		super();
		this.mApiName = mApiName;
		this.mMethod  = mMethod;
		this.mContext = context;
		if(dataParser != null)
			mResponseHandler = new ServerResponseHandler(context,dataParser, mApiName);
		client.setBasicAuth(SERVER_ACCESSUSER, SERVER_ACCESSTOKEN);
		client.addHeader("Accept", "application/json");
		client.addHeader("Content-type", "application/json");
		client.setUserAgent(SERVER_USERAGENT);
	}
	
	public void SetClientCredentials(){
		client.setBasicAuth(SERVER_ACCESSUSER, SERVER_ACCESSTOKEN);
	}

	/**
	 * 
	 * only to be used by JSONDATAPARSER DON'T KNOW IF THIS IS A GOOD IDEA!
	 * 
	 * @param dataParser the data parser you want to use
	 */
	
	public void setDataParser(DataParser dataParser){
		mResponseHandler = new ServerResponseHandler(mContext,dataParser, mApiName);
	}
	
	/**
	 * @param observer adds an observer that will ne notified when the call completes
	 */
	public int addCallObserver(ServerCallObserver observer){
		return mResponseHandler.addObserver(observer);
	}
	
	/**
	 * all the server trying to get the information we need for this call
	 * 
	 * @param mParameters  The parameters for this call.
	 */
	public void makeCall(RequestParams mParameters){
		makeCall(null, mParameters);
	}
	
	/**
	 * @param mCallID
	 * @return true if the mCallID is the same as the current call;
	 */
	public boolean isResponseFromCall(int callID){
		return callID == mResponseHandler.getCallID();
	}
	/**
	 * 
	 * Call the server trying to get the information we need for this call
	 * 
	 * @param mAPIURLParams occasionally the server will require that you append some parameters on to the end of the URL this is 
	 * a String that will be appended on to the end of the url. So the url will be mBaseURL + mApiName + "/" + mAPIURLParams if null nothing happens
	 * @param mParameters The parameters for this call.
	 */
	public void makeCall(String mAPIURLParams, RequestParams mParameters){
		if (!BASEURL.endsWith("/")){
			BASEURL = BASEURL + "/";
		}
		final StringBuilder uriBuilder = new StringBuilder(BASEURL + mApiName);
		if(mAPIURLParams != null){
			uriBuilder.append( "/" + mAPIURLParams);
		}
		
		switch (mMethod) {
		case PUT:
			client.put(mContext, uriBuilder.toString(), mParameters, mResponseHandler);
			break;
		case POST:
			client.post(uriBuilder.toString(), mParameters, mResponseHandler);
			break;
		case GET:
			client.get(uriBuilder.toString(), mParameters, mResponseHandler);
			break;
		case DELETE:

			break;
		}
	}
	
	/**
	 * 
	 * Call the server trying to get the information we need for this call
	 * 
	 * @param parameters The byte entity parameters for this call.
	 * @param content The content type for this call.
	 */
	public void makeJsonCall(String content){
		if (!BASEURL.endsWith("/")){
			BASEURL = BASEURL + "/";
		}
		final StringBuilder uriBuilder = new StringBuilder(BASEURL + mApiName);
		
		switch (mMethod) {
		case PUT:
			client.put(mContext, uriBuilder.toString(), buildJsonByteEntity(content), "application/json", mResponseHandler);
			break;
		case POST:
			client.post(mContext, uriBuilder.toString(), buildJsonByteEntity(content), "application/json", mResponseHandler);
			break;
		case GET:
			client.get(uriBuilder.toString(), mResponseHandler);
			break;
		case DELETE:

			break;
		}
	}
	
	/**
	 * set the username used for security on the server.
	 * @param AccessUser the username used for security on the server
	 */
	public static void setServerAccessUser(String AccessUser) {
		SERVER_ACCESSUSER = AccessUser;
	}
	/**
	 * set the server access token used for security on the server.
	 * @param AccessToken he server access token used for security on the server.
	 */
	public static void setServerAccessToken(String AccessToken) {
		SERVER_ACCESSTOKEN = AccessToken;
	}
	/**
	 * set the server access token used for security on the server.
	 * @param AccessToken he server access token used for security on the server.
	 */
	public static void setServerUserAgent(String UserAgent) {
		SERVER_USERAGENT = UserAgent;
	}
	/**
	 * set the URL address of the server.
	 * @param mBaseURL the URL address of the server.
	 */
	public static void setBaseURL(String mBaseURL) {
		BASEURL = mBaseURL;
	}
	
	 /**
	  * Utility function to convert Request params to JSON string entity
	 * @return JSON StrinEntity
	 */
	public StringEntity buildJSONStringEntity(RequestParams params){
		StringEntity stringParams = null;
		try {
			stringParams = new StringEntity(params.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stringParams.setContentType("application/json");
		return stringParams;
	}
	
	/**
	  * Utility function to convert String JSON to Byte Array entity
	 * @return JSON StrinEntity
	 */
	public ByteArrayEntity buildJsonByteEntity(String json){
		ByteArrayEntity entity = null;
		try {
			entity = new ByteArrayEntity(json.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return entity;
	}
}
