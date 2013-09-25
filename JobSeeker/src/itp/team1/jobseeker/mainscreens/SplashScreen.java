package itp.team1.jobseeker.mainscreens;

import itp.team1.jobseeker.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class SplashScreen extends Activity{
	
	 // Set the display time, in milliseconds (or extract it out as a configurable parameter)
   protected int SPLASH_DISPLAY_LENGTH = 1800;
   LinearLayout layout;
   public static DisplayMetrics metrics;
   private boolean finished = false;
       
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		layout = (LinearLayout)findViewById(R.id.layout);
		metrics = new DisplayMetrics();
		
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		layout.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if (!finished){
					// TODO Auto-generated method stub
		        	// Create an Intent that will start the main activity.
		        	Intent mainIntent = new Intent(SplashScreen.this, MainSlidingActivity.class);
		        	SplashScreen.this.startActivity(mainIntent);
		        	//Finish the splash activity so it can't be returned to.
		        	finish();
		        	finished = true;
				}
	        	
			}
			
		});			
	}	
	
	@Override
	protected void onResume() {
		 super.onResume();
	     new Handler().postDelayed(new Runnable()
	     {
	        public void run()
	        {
		   		 if (!finished)
				 {
		        	// Create an Intent that will start the main activity.
		        	Intent mainIntent = new Intent(SplashScreen.this, MainSlidingActivity.class);
			        SplashScreen.this.startActivity(mainIntent);
		        	//Finish the splash activity so it can't be returned to.
		        	finish();
		        	finished = true;
				 }
	        }
	     }, SPLASH_DISPLAY_LENGTH);
	}
	
	
}
