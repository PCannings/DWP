package itp.team1.jobseeker.DataParsing.CachedServerCalls.DataParseing.Base;

import itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerMemoryCache;

public class JSONDataObject {
	static ServerMemoryCache<JSONDataObject> data;
		
	/**
	 * saves the object in a memory safe structure
	 * 
	 * @param object the object to be saved
	 */
	static public void addObjectToMemoryCache(JSONDataObject object){
		data.put(object);
	}

}
