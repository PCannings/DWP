package itp.team1.jobseeker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity 
{

	private ImageView banner;
	private Button 	  searchButton;
	private Button 	  diaryButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Setup UI
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		// Get buttons
		banner 		 = (ImageView) findViewById(R.id.banner);
		searchButton = (Button) findViewById(R.id.search_button);
		diaryButton  = (Button) findViewById(R.id.diary_button);

		
		// Banner Button event
		banner.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(MainActivity.this, "Banner clicked", Toast.LENGTH_SHORT).show();
			}
		});
		
		// Search Button event
		searchButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(MainActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();

			}
		});
		
		// Diary Button event
		diaryButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(MainActivity.this, "Diary clicked", Toast.LENGTH_SHORT).show();
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