package itp.team1.jobseeker.mainscreens;

import java.util.LinkedList;

import itp.team1.jobseeker.Constants;
import itp.team1.jobseeker.R;
import itp.team1.jobseeker.Utils;
import itp.team1.jobseeker.DataParsing.CachedServerCalls.ServerCall.ServerCallObserver;
import itp.team1.jobseeker.Layouts.PullToRefreshListView;
import itp.team1.jobseeker.Layouts.PullToRefreshListView.OnRefreshListener;
import itp.team1.jobseeker.menu.CustomMenuAdapter;
import itp.team1.jobseeker.menu.CustomMenuItem;
import itp.team1.jobseeker.model.Job;
import itp.team1.jobseeker.server.SearchAllCall;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsFragment extends Fragment implements ServerCallObserver{

	protected static LinkedList<Job> data;

	private static final int CALL_STEP = 25;

	private PullToRefreshListView mListView;
	private MainSlidingActivity activity;
	private TextView mTitle;
	private View actionHeader;
	private ImageView logo;

	public boolean finishedAppending = false;
	public boolean appendMore = false;

	private JobAdapter adapter;

	RelativeLayout footerContainer;
	ProgressBar progress;
	TextView loading;

	private LayoutInflater mInflater;
	
	//public ResultsFragment(location, type, title, ....){
		
	//}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRetainInstance(true);

		activity = (MainSlidingActivity)getActivity();
		data = new  LinkedList<Job>();
		mInflater = inflater;

		FrameLayout v = (FrameLayout) mInflater.inflate(R.layout.view_results, null);

		mListView = (PullToRefreshListView)v.findViewById(R.id.layout_listview);

		actionHeader = v.findViewById(R.id.top_navigation);
		logo = (ImageView)actionHeader.findViewById(R.id.logo_btn);
		logo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				activity.toggle();
			}
		});
		mTitle = (TextView)actionHeader.findViewById(R.id.title);

		setLoadingFooter();
		//toggleLoadingFooter(false);

		adapter = new JobAdapter(activity, data);

		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				makeRefreshCall();
			}
		});

		mListView.setAdapter(adapter);

		mListView.setLockScrollWhileRefreshing(true);

		mListView.setOnScrollListener(new AbsListView.OnScrollListener(){

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				if(totalItemCount > 5)
					if (++firstVisibleItem + visibleItemCount > totalItemCount-3) {
						//load more content
						if(appendMore && !finishedAppending) {
							Log.v("OnScroll", "Load more");
							makeAddCall();
							//toggleLoadingFooter(true);
							appendMore = false;
						}
					}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

		});

		mListView.setRefreshing();
		setRetainInstance(true);

		return v;
	}

	public void setLoadingFooter(){
		footerContainer = (RelativeLayout) mInflater.inflate(R.layout.pending, null);
		progress = (ProgressBar) footerContainer.findViewById(R.id.progress);
		loading = (TextView) footerContainer.findViewById(R.id.pending_text);
		mListView.addFooterView(footerContainer);
	}

	@Override
	public void onResume(){
		super.onResume();
		if(adapter!=null)
			adapter.notifyDataSetChanged();

	}
	/*
	public void toggleLoadingFooter(boolean open){

		if(open){
			MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) footerContainer.getLayoutParams();
			mlp.setMargins(0, Math.round(0), 0, 0);
			footerContainer.setLayoutParams(mlp);
		}
		else {
			MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) footerContainer.getLayoutParams();
			mlp.setMargins(0, Math.round(-footerContainer.getHeight()), 0, 0);
			footerContainer.setLayoutParams(mlp);
		}
	}
	*/
	public void makeRefreshCall(){
		data.clear();
		Log.v("Refresh Call", "Made");
		SearchAllCall searchCall = new SearchAllCall(activity);
		//searchCall.setParamsAllVideos(0, CALL_STEP);
		searchCall.makeSearchCall();
		searchCall.addServerCallObserver(ResultsFragment.this);
	}

	public void makeAddCall(){
		SearchAllCall searchCall = new SearchAllCall(activity);
		//searchCall.setParamsAllVideos(data.size(), CALL_STEP);
		searchCall.makeSearchCall();
		searchCall.addServerCallObserver(ResultsFragment.this);
	}

	@Override
	public void RequestSuccess(int callID, String callUrl) {
		Log.v("CALL ID", String.valueOf(callID) + " " + callUrl);
		Log.v("Search", "SUCCESS");
		data.addAll(Job.jobs);
		//mListView.setSelection(0);
		/*
			if(Job.jobs.size() < CALL_STEP) {
				appendMore = false;
				finishedAppending = true;
			} else {
				appendMore = true;
			}*/
		appendMore = false;
		finishedAppending = true;
		//toggleLoadingFooter(false);
		adapter.notifyDataSetChanged();
		mListView.onRefreshComplete();
	}

	@Override
	public void RequestFailed(int callID, String callUrl,
			String ServerResponse, Throwable exception) {

		Log.v("Search ", "FAILED");
		if(Job.jobs!=null) {
			data.addAll(Job.jobs);
			adapter.notifyDataSetChanged();
		}
		//toggleLoadingFooter(false);
		mListView.onRefreshComplete();
		appendMore = false;
		finishedAppending = true;

		if(!Utils.isNetworkAvailable(activity)) {
			Toast.makeText(activity, "No connection available :(", Toast.LENGTH_LONG).show();
			return;
		}
	}
}

