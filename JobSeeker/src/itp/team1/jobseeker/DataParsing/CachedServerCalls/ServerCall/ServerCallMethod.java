package itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall;

public enum ServerCallMethod {
	POST,PUT,GET,DELETE;

	public static ServerCallMethod[] getListEntries(){
		ServerCallMethod[] a = new ServerCallMethod[4];
		a[0] = POST;
		a[1] = PUT;
		a[2] = GET;
		a[3] = DELETE;
		return a;
	}
}
