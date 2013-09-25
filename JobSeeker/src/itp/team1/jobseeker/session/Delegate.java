package itp.team1.jobseeker.session;

import android.app.Application;

public class Delegate extends Application
{

	private SessionManager manager;
	
	public void onCreate ()
	{
		super.onCreate();
		manager = new SessionManager(this.getApplicationContext());
	}

	public SessionManager getSession(){
		return manager;
	}

}