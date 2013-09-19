package itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.Base;

import org.json.JSONException;

import itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.DataParseingException;


abstract public interface DataParser{
	/**
	 * Called from ServerResponseHandler when call has been successfull.
	 * @param serverStringstring returned from the server 
	 * @return whether an error has occurred 
	 * @throws JSONException 
	 */
	public void parseServerData(String serverString) throws DataParseingException, JSONException;
}
