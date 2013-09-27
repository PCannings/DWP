package itp.team1.jobseeker.mainscreens;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.datetime.DateTimePicker;
import itp.team1.jobseeker.session.Delegate;
import itp.team1.jobseeker.session.database.ActivityItem;

import java.util.Calendar;
import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DiaryFragment extends Fragment {

	private TableLayout mSummary;
	private TableLayout mActivity;
	private MainSlidingActivity activity;
	private TextView mTitle;
	private View actionHeader;
	private ImageView logo;

	LinearLayout inner;

	private LayoutInflater mInflater;
	private TextView searchNumber;
	private TextView clickNumber;
	private TextView applicationNumber;
	private TextView viewedNumber;
	private Button appointment;
	private Button note;

	private Delegate delegate;

	private LinkedList<ActivityItem> data;
	
	FrameLayout v;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		activity = (MainSlidingActivity)getActivity();
		mInflater = inflater;
		data = new LinkedList<ActivityItem>();
		data = MainSlidingActivity.databases.getAllActivities();
		delegate = ((Delegate)activity.getApplicationContext());
		v = (FrameLayout) mInflater.inflate(R.layout.view_diary, null);

		inner = (LinearLayout) v.findViewById(R.id.inner_layout);

		actionHeader = v.findViewById(R.id.top_navigation);
		logo = (ImageView)actionHeader.findViewById(R.id.logo_btn);
		logo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				activity.toggle();
			}
		});
		mTitle = (TextView)actionHeader.findViewById(R.id.title);

		mSummary = (TableLayout)inner.findViewById(R.id.summary);

		searchNumber = (TextView)mSummary.findViewById(R.id.search_number);
		searchNumber.setText(String.valueOf(delegate.getSession().getSearches()));
		clickNumber = (TextView)mSummary.findViewById(R.id.clicked_number);
		clickNumber.setText(String.valueOf(delegate.getSession().getClicked()));
		applicationNumber = (TextView)mSummary.findViewById(R.id.applied_number);
		applicationNumber.setText(String.valueOf(delegate.getSession().getApplied()));
		viewedNumber = (TextView)mSummary.findViewById(R.id.viewed_number);
		viewedNumber.setText(String.valueOf(delegate.getSession().getViewed()));

		appointment = (Button)mSummary.findViewById(R.id.appointment);
		appointment.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				showDateTimeDialog();
				//AppointmentDialog d = new AppointmentDialog(activity);
				//d.show();
			}
		});

		mActivity = (TableLayout) inner.findViewById(R.id.activity);

		for(int i = 0; i< data.size(); i++) {
			TableRow row = (TableRow)inflater.inflate(R.layout.table_row, null);
			TextView date = (TextView) row.findViewById(R.id.date);
			TextView details = (TextView) row.findViewById(R.id.details);
			TextView viewed = (TextView) row.findViewById(R.id.viewed_text);
			TextView applied = (TextView) row.findViewById(R.id.applied_text);
			TextView caution = (TextView) row.findViewById(R.id.caution_text);
			date.setText(data.get(i).getDate());
			details.setText(data.get(i).getJobDetails() + "\n Notes: " + data.get(i).getJobNotes());
			if(data.get(i).getViewed())
				viewed.setText("Y");
			if(data.get(i).getApplied())
				applied.setText("Y");
			if(data.get(i).getCaution())
				caution.setText("Y");
			mActivity.addView(row);
		}
		
		note = (Button)mActivity.findViewById(R.id.note);
		note.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//activity.toggle();
				showNoteDialog();
			}
		});

		setRetainInstance(true);

		return v;
	}
	
	private void showDateTimeDialog() {
		// Create the dialog
		final Dialog mDateTimeDialog = new Dialog(activity);
		// Inflate the root layout
		final RelativeLayout mDateTimeDialogView = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.date_time_dialog, null);
		// Grab widget instance
		final itp.team1.jobseeker.datetime.DateTimePicker mDateTimePicker = (itp.team1.jobseeker.datetime.DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
		// Check is system is set to use 24h time (this doesn't seem to work as expected though)
		final String timeS = android.provider.Settings.System.getString(activity.getContentResolver(), android.provider.Settings.System.TIME_12_24);
		final boolean is24h = !(timeS == null || timeS.equals("12"));
		
		// Update demo TextViews when the "OK" button is clicked 
		((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				mDateTimePicker.clearFocus();
				// TODO Auto-generated method stub
				((TextView) v.findViewById(R.id.Date)).setText(mDateTimePicker.get(Calendar.YEAR) + "/" + (mDateTimePicker.get(Calendar.MONTH)+1) + "/"
						+ mDateTimePicker.get(Calendar.DAY_OF_MONTH));
				if (mDateTimePicker.is24HourView()) {
					((TextView) v.findViewById(R.id.Time)).setText(mDateTimePicker.get(Calendar.HOUR_OF_DAY) + ":" + mDateTimePicker.get(Calendar.MINUTE));
				} else {
					((TextView) v.findViewById(R.id.Time)).setText(mDateTimePicker.get(Calendar.HOUR) + ":" + mDateTimePicker.get(Calendar.MINUTE) + " "
							+ (mDateTimePicker.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM"));
				}
				((TextView) v.findViewById(R.id.appointment)).setText("Appointment Set");
				//startCalendarEvent(mDateTimePicker.get(Calendar.YEAR), (mDateTimePicker.get(Calendar.MONTH)+1), mDateTimePicker.get(Calendar.DAY_OF_MONTH), mDateTimePicker.get(Calendar.HOUR_OF_DAY),
				//		mDateTimePicker.get(Calendar.MINUTE));
				mDateTimeDialog.dismiss();
			}
		});

		// Cancel the dialog when the "Cancel" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDateTimeDialog.cancel();
			}
		});

		// Reset Date and Time pickers when the "Reset" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDateTimePicker.reset();
			}
		});
		
		// Setup TimePicker
		mDateTimePicker.setIs24HourView(is24h);
		// No title on the dialog window
		mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set the dialog content view
		mDateTimeDialog.setContentView(mDateTimeDialogView);
		// Display the dialog
		mDateTimeDialog.show();
	}
	
	
	private void showNoteDialog() {
		// Create the dialog
		final Dialog mNoteDialog = new Dialog(activity);
		// Inflate the root layout
		final RelativeLayout mNoteDialogView = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.note_dialog, null);
		// Grab widget instance
		final EditText note = (EditText)mNoteDialogView.findViewById(R.id.note_text);

		// Update demo TextViews when the "OK" button is clicked 
		((Button) mNoteDialogView.findViewById(R.id.add_btn)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(note.getText().toString().isEmpty()) {
					Toast.makeText(activity, "Please, add note text :)", Toast.LENGTH_LONG).show();
					mNoteDialog.dismiss();
				}
				else {
					mNoteDialog.dismiss();
				}
			}
		});

		// Cancel the dialog when the "Cancel" button is clicked
		((Button) mNoteDialogView.findViewById(R.id.cancel_btn)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mNoteDialog.cancel();
			}
		});
	
		mNoteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set the dialog content view
		mNoteDialog.setContentView(mNoteDialogView);
		// Display the dialog
		mNoteDialog.show();
	}
	
	public void startCalendarEvent(int year, int month, int day, int hour,
			int minute) {

		long startMillis = 0;

		Calendar beginTime = Calendar.getInstance();
		beginTime.set(year, month, day, hour, minute);
		startMillis = beginTime.getTimeInMillis();

		long endMillis = 0;

		Calendar endTime = Calendar.getInstance();
		endTime.set(year, month, day, hour, (minute + 30));
		endMillis = endTime.getTimeInMillis();

		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setType("vnd.android.cursor.item/event");
		intent.putExtra(Events.TITLE, "Advisor Meeting");
		intent.putExtra(Events.EVENT_LOCATION,
				"Department of Work and Pensions");
		intent.putExtra(Events.DESCRIPTION, "Meet advisor and show diary.");

		intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis);
		intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis);

		intent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PUBLIC);
		intent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);

		activity.startActivity(intent);

	}

}

