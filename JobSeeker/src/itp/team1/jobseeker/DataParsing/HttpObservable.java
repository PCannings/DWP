package itp.team1.jobseeker.DataParsing;

import org.json.JSONException;


public interface HttpObservable
{
	//add add/remove an observer
  public void addObserver(HttpObserver obsrNewObserver);
 
  public void notifyObserversSuccess();
  public void notifyObserversFailure(Throwable e);
  public void notifyObserversError(JSONException e);
}