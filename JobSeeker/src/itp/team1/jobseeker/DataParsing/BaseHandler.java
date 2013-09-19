package itp.team1.jobseeker.DataParsing;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class BaseHandler extends AsyncHttpResponseHandler implements HttpObservable {
	ArrayList<WeakReference<HttpObserver>> observers = new ArrayList<WeakReference<HttpObserver>>();
	
	static int activeRequests=0;
	
	@Override
	public void onStart() {
		activeRequests++;
	}

	public JSONObject BaseParseJson(String json) throws JSONException{
		JSONObject outer = new JSONObject(json); // the outer object
		JSONObject ja =null;
		if (outer != null) {
			// Get the inner object and parse out the data
			if(outer.has("results"))
			{
				ja = outer.getJSONObject("results");
				if(ja.getInt("code")!=0)
				{
					JSONException t = new JSONException(ja.getString("message"));
					notifyObserversError(t);
				}
			}
			else if(outer.has("responseResults")){
				ja = outer.getJSONObject("responseResults");
				if(ja.has("results"))
					ja = ja.getJSONObject("results");
			}
			//look for errors
			if(ja.has("code")){
				int ErrorCode = ja.getInt("code");
				if(ErrorCode!=0){
					//String message =  ja.getString("message");
					//TODO error handling
				}
			}
			//return the important json for the supsequesnt handlers to use
			if(ja.has("values"))
				ja = ja.getJSONObject("values");
		}
		return ja;
	}

	public void addObserver(HttpObserver obsrNewObserver) 
	{
		WeakReference <HttpObserver> temp =  new WeakReference<HttpObserver>(obsrNewObserver);
		observers.add(temp);
			
	}

	public void notifyObserversSuccess() {
		Iterator<WeakReference<HttpObserver>> elements = observers.iterator();
		while (elements.hasNext()) {
			HttpObserver h = elements.next().get();
			if(h!=null){
				h.RequestSuccess(getClass());
				elements.remove();
			}
			else{
				elements.remove();
			}
		}
		activeRequests--;
	}



	public void notifyObserversFailure(Throwable e) {
		Iterator<WeakReference<HttpObserver>> elements = observers.iterator();
		while (elements.hasNext()) {
			HttpObserver h = elements.next().get();
			if(h!=null){
				h.RequestFailure(e);
				elements.remove();
			}
			else{
				elements.remove();
			}
		}
		activeRequests--;

	}

	public void notifyObserversError(JSONException e) {
		Iterator<WeakReference<HttpObserver>> elements = observers.iterator();
		while (elements.hasNext()) {
			HttpObserver h = elements.next().get();
			if(h!=null){
				h.RequestError(e);
				elements.remove();
			}
			else{
				elements.remove();
			}
		}
		activeRequests--;
	}
}