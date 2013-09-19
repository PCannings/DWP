package itp.team1.jobseeker.mainscreens;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.R.id;
import itp.team1.jobseeker.R.layout;
import itp.team1.jobseeker.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ChooserActivity extends Activity 
{

	private Button 	  simpleButton;
	private Button 	  slidingButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Setup UI
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooser_activity);
		
		// Get UI elements
		simpleButton = (Button) findViewById(R.id.simple_button);
		slidingButton  = (Button) findViewById(R.id.sliding_button);

		
		
		// Search Button event
		simpleButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// Go to "job search" page
				startActivity(new Intent(ChooserActivity.this, MainActivity.class));
				ChooserActivity.this.finish();
			}
		});
		
		// Diary Button event
		slidingButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(ChooserActivity.this, MainSlidingActivity.class));
				ChooserActivity.this.finish();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}
