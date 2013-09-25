package itp.team1.jobseeker.mainscreens;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.Utils;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall.ServerCallObserver;
import itp.team1.jobseeker.Layouts.PullToRefreshListView;
import itp.team1.jobseeker.Layouts.PullToRefreshListView.OnRefreshListener;
import itp.team1.jobseeker.model.Job;
import itp.team1.jobseeker.server.SearchSitesCall;
import itp.team1.jobseeker.server.SearchSocialCall;
import itp.team1.jobseeker.session.Delegate;

import java.util.LinkedList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		activity = (MainSlidingActivity)getActivity();
		mInflater = inflater;

		delegate = ((Delegate)activity.getApplicationContext());
		FrameLayout v = (FrameLayout) mInflater.inflate(R.layout.view_diary, null);

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
				//activity.toggle();
			}
		});
		
		mActivity = (TableLayout) inner.findViewById(R.id.activity);
		
		note = (Button)mActivity.findViewById(R.id.note);
		note.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//activity.toggle();
			}
		});
		
		setRetainInstance(true);

		return v;
	}

}

