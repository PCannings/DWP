package itp.team1.jobseeker.mainscreens.savedjobs;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.Utils;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall.ServerCallObserver;
import itp.team1.jobseeker.Layouts.PullToRefreshListView;
import itp.team1.jobseeker.Layouts.PullToRefreshListView.OnRefreshListener;
import itp.team1.jobseeker.mainscreens.JobAdapter;
import itp.team1.jobseeker.mainscreens.MainSlidingActivity;
import itp.team1.jobseeker.mainscreens.ResultsFragment;
import itp.team1.jobseeker.model.Job;
import itp.team1.jobseeker.server.SearchSitesCall;
import itp.team1.jobseeker.server.SearchSocialCall;
import itp.team1.jobseeker.session.database.JobItem;

import java.util.LinkedList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class SavedJobsFragment extends Fragment{

	protected static LinkedList<JobItem> data;
	protected static LinkedList<JobItem> toDelete;

	private ListView mListView;
	private MainSlidingActivity activity;
	private TextView mTitle;
	private static TextView mDelete;
	private View actionHeader;
	private ImageView logo;

	private JobItemAdapter adapter;
	private ProgressBar progress;

	private LayoutInflater mInflater;

	private RelativeLayout resultsFooterContainer;

	private RelativeLayout resultsFooter;

	private TextView resultsText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRetainInstance(true);
		//requestFeature(Window.FEATURE_NO_TITLE);
		activity = (MainSlidingActivity)getActivity();
		data = new  LinkedList<JobItem>();
		toDelete = new  LinkedList<JobItem>();
		mInflater = inflater;

		View v = mInflater.inflate(R.layout.view_saved_jobs, null);
		progress = (ProgressBar)v.findViewById(R.id.progress_bar);

		progress.setVisibility(View.VISIBLE);

		data = MainSlidingActivity.databases.getAllJobs();

		actionHeader = v.findViewById(R.id.top_navigation);
		logo = (ImageView)actionHeader.findViewById(R.id.logo_btn);
		logo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				activity.toggle();
			}
		});
		mTitle = (TextView)actionHeader.findViewById(R.id.title);
		mDelete = (TextView)actionHeader.findViewById(R.id.save_delete);
		mDelete.setText("Delete");
		mDelete.setVisibility(View.INVISIBLE);
		
		mDelete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for(int i = 0; i< toDelete.size(); i++) {
					data.remove(toDelete.get(i));
					MainSlidingActivity.databases.deleteJob(toDelete.get(i));
				}
				toDelete.clear();
				adapter.notifyDataSetChanged();
				mListView.invalidate();
				mDelete.setVisibility(View.GONE);
			}
			
		});

		mListView = (ListView)v.findViewById(R.id.layout_listview);

		setNoResultsFooter();
		
		progress.setVisibility(View.GONE);

		adapter = new JobItemAdapter(activity, data);

		if(data.size()==0)toggleNoResultsFooter(true);

		mListView.setAdapter(adapter);
		
		mListView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if(!SavedJobsFragment.toDelete.contains(data.get(position)))
					SavedJobsFragment.toDelete.add(data.get(position));
				SavedJobsFragment.mDelete.setVisibility(View.VISIBLE);
				adapter.notifyDataSetChanged();
				mListView.invalidate();
				return true;
			}
			
		});

		setRetainInstance(true);

		return v;
	}

	public void setNoResultsFooter(){
		resultsFooterContainer = (RelativeLayout) mInflater.inflate(R.layout.no_results, null);
		resultsFooter = (RelativeLayout) resultsFooterContainer.findViewById(R.id.no_results_layout);
		resultsText = (TextView) resultsFooterContainer.findViewById(R.id.no_results_text);
		resultsText.setText("No Saved Jobs found");
		mListView.addFooterView(resultsFooterContainer);
		toggleNoResultsFooter(false);
	}

	public void toggleNoResultsFooter(boolean open){

		if(open){
			MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) resultsFooter.getLayoutParams();
			mlp.setMargins(0, Math.round(0), 0, 0);
			resultsFooter.setLayoutParams(mlp);
			resultsFooter.setVisibility(View.VISIBLE);
		}
		else {
			MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) resultsFooter.getLayoutParams();
			mlp.setMargins(0, Math.round(-resultsFooter.getHeight()), 0, 0);
			resultsFooter.setLayoutParams(mlp);
			resultsFooter.setVisibility(View.GONE);
		}
	}

	@Override
	public void onResume(){
		super.onResume();
		if(adapter!=null)
			adapter.notifyDataSetChanged();

	}
}

