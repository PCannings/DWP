package itp.team1.jobseeker.DataParsing;

import org.json.JSONException;


//file: Observer.java
//pattern role: Observer interface
public interface HttpObserver
{
	@SuppressWarnings("rawtypes")
  public void RequestSuccess( Class responseName);
  public void RequestError(JSONException e);
  public void RequestFailure(Throwable e);
}	