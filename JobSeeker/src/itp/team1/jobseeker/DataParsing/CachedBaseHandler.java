package itp.team1.jobseeker.DataParsing;

import android.content.Context;

public class CachedBaseHandler extends BaseHandler{

	Context mContext;
	String  mURL;
		
	public CachedBaseHandler(String URL, Context context) {
		// TODO Auto-generated constructor stub
		mURL     = URL;
		mContext = context;
		
	}
	

}
