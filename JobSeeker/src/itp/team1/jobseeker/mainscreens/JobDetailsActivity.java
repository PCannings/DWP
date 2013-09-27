package itp.team1.jobseeker.mainscreens;

import java.util.Date;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.Utils;
import itp.team1.jobseeker.mainscreens.WebViewDialog.OnCompleteListener;
import itp.team1.jobseeker.model.Job;
import itp.team1.jobseeker.session.Delegate;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class JobDetailsActivity extends Activity { 
	
	ImageView provider;
	
	TextView title;
	TextView location;
	TextView description;
	TextView salary;
	TextView opening;
	TextView closing;
	TextView employer;
	TextView posted;
	
	TextView link;
	
	Job item;
	Activity activity;
	
	OnCompleteListener listener = new OnCompleteListener(){

		@Override
		public void onComplete(boolean isLoggedIn) {
			// TODO Auto-generated method stub
			
			//dialog stuff here
			if(isLoggedIn){
				//TODO
				delegate.getSession().incrementApplied();
			} else {
				
			}
		}
		
	};

	private View actionHeader;

	private ImageView logo;

	private TextView mTitle;
	
	private Delegate delegate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view_job_details);
		
		activity = this;
		delegate = (Delegate)activity.getApplicationContext();
		
		actionHeader = findViewById(R.id.top_navigation);
		logo = (ImageView)actionHeader.findViewById(R.id.logo_btn);
		logo.setImageResource(R.drawable.back_arrow);
		logo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		mTitle = (TextView)actionHeader.findViewById(R.id.title);
		
		Bundle b = this.getIntent().getExtras();
		if(b !=null) {
			int position = this.getIntent().getIntExtra("Position", 0);
			item = ResultsFragment.data.get(position);
		}
		
		provider = (ImageView) findViewById(R.id.image);
		
		title = (TextView) findViewById(R.id.title);
		
		location = (TextView) findViewById(R.id.location);
		
		description = (TextView) findViewById(R.id.description);
		
		salary = (TextView) findViewById(R.id.salary);
		
		opening = (TextView) findViewById(R.id.opening);
		
		closing = (TextView) findViewById(R.id.closing);
		
		employer = (TextView) findViewById(R.id.employer);
		
		posted = (TextView) findViewById(R.id.time);
		
		if(item.source.contains("facebook")) {
			provider.setImageResource(R.drawable.logo_facebook);
		} else if(item.source.contains("twitter")){
			provider.setImageResource(R.drawable.logo_twitter);
		} else if(item.source.contains("indeed")){
			provider.setImageResource(R.drawable.logo_indeed);
		} else if(item.source.contains("guardian")){
			provider.setImageResource(R.drawable.logo_guardian);
		} else if(item.source.contains("s1")){
			provider.setImageResource(R.drawable.logo_sone);
		} 
		// Set some view properties
		if(!item.title.equals("")) {
			title.setVisibility(View.VISIBLE);
			title.setText(item.title);
		}
		else title.setVisibility(View.GONE);
		
		if(!item.location.equals("")) {
			location.setVisibility(View.VISIBLE);
			location.setText("Location: " + item.location);
		}
		else location.setVisibility(View.GONE);
		
		if(!item.description.equals("")) {
			description.setVisibility(View.VISIBLE);
			description.setText(item.description);
		}
		else description.setVisibility(View.GONE);
		
		if(!item.salary.equals("")) {
			salary.setVisibility(View.VISIBLE);
			salary.setText("Salary: " + item.salary);
		}
		else salary.setVisibility(View.GONE);
		
		if(!item.openingDate.equals("")) {
			opening.setVisibility(View.VISIBLE);
			opening.setText("Opening Date: " + item.openingDate);
		}
		else opening.setVisibility(View.GONE);
		
		if(!item.closingDate.equals("")) {
			closing.setVisibility(View.VISIBLE);
			closing.setText("Closing Date: " + item.closingDate);
		}
		else closing.setVisibility(View.GONE);
		
		if(!item.employer.equals("")) {
			employer.setVisibility(View.VISIBLE);
			employer.setText("Employer: " + item.employer);
		}
		else employer.setVisibility(View.GONE);

		String text = Utils.parseTime(item.time);
		posted.setText(text);
		
		link = (TextView) findViewById(R.id.external_link);
		link.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				delegate.getSession().incrementClicked();
				WebViewDialog dialog = new WebViewDialog(activity, listener, item.externalLink, item.source);
				dialog.show();
			}
			
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.v("MainActivity", "OnResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v("MainActivity", "OnPause");
	}

	@Override
	protected void onDestroy() {
		Log.v("MainActivity", "onDestroy");

		super.onDestroy();
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}

}