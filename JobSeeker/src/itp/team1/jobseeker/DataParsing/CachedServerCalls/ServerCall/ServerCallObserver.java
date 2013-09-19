package itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall;


public interface ServerCallObserver
{
	public void RequestSuccess(int callID, String callUrl);
	public void RequestFailed(int  callID, String callUrl, String ServerResponse, Throwable exception);
}	