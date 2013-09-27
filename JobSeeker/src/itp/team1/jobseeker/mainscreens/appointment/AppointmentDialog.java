package itp.team1.jobseeker.mainscreens.appointment;

import itp.team1.jobseeker.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class AppointmentDialog extends Dialog implements OnClickListener {
	
	Activity activity;
	
	protected AppointmentDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}
	
	public AppointmentDialog(Context context) {
		super(context, R.style.WebPopUp);
		// TODO Auto-generated constructor stub
		setCancelable(true);
		activity = (Activity) context;
	}

	Button setFortNightlyAppointment, reset;
	TextView dateNotice;

	DateStore dateStore;
	TimeStore timeStore;

	String time;
	String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		getWindow().setGravity(Gravity.CENTER);
		setContentView(R.layout.activity_main);

		setFortNightlyAppointment = (Button) findViewById(R.id.bSigningAppointmentSet);

		setFortNightlyAppointment.setOnClickListener(this);

		reset = (Button) findViewById(R.id.bReset);

		reset.setOnClickListener(this);

		dateNotice = (TextView) findViewById(R.id.textView1);
		
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);

		dateNotice.setTextColor(Color.WHITE);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSigningAppointmentSet:
			// start the other activity
			Intent myIntent = new Intent(activity, Time.class);

			activity.startActivity(myIntent);
			break;

		case R.id.bReset:
			
			break;
		}
	}
	
}
