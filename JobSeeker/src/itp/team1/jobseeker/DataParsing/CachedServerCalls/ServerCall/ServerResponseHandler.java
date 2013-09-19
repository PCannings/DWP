package itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerDataCache;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.DataParseingException;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.Base.DataParser;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ServerResponseHandler extends AsyncHttpResponseHandler {
	/**
	 * List of all object that are notified upon completion of serverCall
	 */
	ArrayList<WeakReference<ServerCallObserver>> mObservers = new ArrayList<WeakReference<ServerCallObserver>>();
	/**
	 * The java object used to parse the server response
	 */
	DataParser mParser;
	/**
	 *  The URL that is being called 
	 */
	String        mURL;
	/**
	 *  a has of the URL used to uniquely identify this call
	 */
	int          mCallID;
	/**
	 * stores the responses from each URL in a data base to be returned when there is no internet connection
	 */
	ServerDataCache mServerDataCache;
	/**
	 * @param parser the DataParserObject that will create objects for this server call
	 * @param apiName The name of the API that this Responder is attached to
	 */
	public ServerResponseHandler(Context context, DataParser parser, String URL) {
		super();
		this.mParser = parser;
		this.mURL = URL;
		this.mCallID = mURL.hashCode();
		mServerDataCache = ServerDataCache.getInstance(context);
	}
	
	/* (non-Javadoc)
	 * @see com.loopj.android.http.AsyncHttpResponseHandler#onStart()
	 */
	@Override
	public void onStart(){
		
		
	}

	/* (non-Javadoc)
	 * @see com.loopj.android.http.AsyncHttpResponseHandler#onSuccess(java.lang.String)
	 */
	@Override
	public void onSuccess(String content) {
		super.onSuccess(content);
		try {
			mParser.parseServerData(content);
			mServerDataCache.addServerResponse(mURL, content);
			notifyObserversSuccess();
		} catch (DataParseingException e) {
			notifyObserversFailure(content, e);
		} catch (JSONException e) {
			notifyObserversFailure(content, e);
		}
	}
	/* (non-Javadoc)
	 * @see com.loopj.android.http.AsyncHttpResponseHandler#onFinish()
	 */
	@Override
	public void onFinish() {
		super.onFinish();
	}
	/* (non-Javadoc)
	 * @see com.loopj.android.http.AsyncHttpResponseHandler#onFailure(java.lang.Throwable, java.lang.String)
	 */
	@Override
	public void onFailure(Throwable error, String content) {
		super.onFailure(error, content);
		//try and get it from the cache
		if(mServerDataCache != null) {
			String response = mServerDataCache.getCachedJsonFor(mURL);
			if(response == null) {
				System.out.println("NULL RESPONSE");
				notifyObserversFailure(content,error);
			}
			else if(response.length()>10){
				System.out.println(response);
				this.onSuccess(response);
			}else{
				error.printStackTrace();
				notifyObserversFailure(content,error);
			}
		}
	};
	/**
	
	/**
	 *  * Add an observer to respond to this call
	 * @param obsrNewObserver the observer to add
	 * @return the id for the call
	 */
	public int addObserver(ServerCallObserver obsrNewObserver) 
	{
		WeakReference <ServerCallObserver> temp =  new WeakReference<ServerCallObserver>(obsrNewObserver);
		mObservers.add(temp);
		return mCallID;
	}
	/**
	 * Clear add object that are listening to this server call
	 */
	public void removeAllObservers()
	{
		mObservers.clear();
	}
	/**
	 * When called RequestSuccess(mApiName) is called for all ServerCallObserver added with addObserver(ServerCallObserver obsrNewObserver) 
	 */
	private void notifyObserversSuccess() {
		Iterator<WeakReference<ServerCallObserver>> elements = mObservers.iterator();
		while (elements.hasNext()) {
			ServerCallObserver h = elements.next().get();
			if(h!=null){
				h.RequestSuccess(mCallID, mURL);
				elements.remove();
			}
			else{
				elements.remove();
			}
		}

	}
	/**
	 * @param content what has been returned from the server (if anything, can be null)
	 * @param exception The exception that called the failure (if any, can be null)
	 */
	private void notifyObserversFailure(String content, Throwable exception) {
		System.out.println("FAILURE OBSERVER");
		Iterator<WeakReference<ServerCallObserver>> elements = mObservers.iterator();
		while (elements.hasNext()) {
			ServerCallObserver h = elements.next().get();
			if(h!=null){
				h.RequestFailed(mCallID, mURL, content, exception);
				elements.remove();
				exception.printStackTrace();
			}
			else{
				elements.remove();
				exception.printStackTrace();
			}
		}
	}
	
	public int getCallID() {
		return mCallID;
	}
}
