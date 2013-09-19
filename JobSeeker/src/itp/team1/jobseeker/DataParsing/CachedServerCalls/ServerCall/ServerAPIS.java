package itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall;


public enum ServerAPIS 
{
	Default("default", ServerCallMethod.GET);
	
	final String mApiName;
	final ServerCallMethod mMethod;
	
	ServerAPIS(String apiName, ServerCallMethod method)
	{
		this.mApiName = apiName;
		this.mMethod  = method;
	}
	
	public String apiName()
	{
		return mApiName;
	}
	
	public ServerCallMethod getMethod()
	{
		return mMethod;
	}
}