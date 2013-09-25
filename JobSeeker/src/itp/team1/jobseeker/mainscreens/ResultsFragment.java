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
import itp.team1.jobseeker.server.SearchSitesCall;
import itp.team1.jobseeker.server.SearchSocialCall;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	public boolean fetching = false;

	private JobAdapter adapter;

	RelativeLayout footerContainer;
	RelativeLayout footer;
	ProgressBar progress;
	TextView loading;
	boolean sitesOn = true;
	LinearLayout tabs;
	TextView sites;
	TextView social;

	private LayoutInflater mInflater;

	//public ResultsFragment(location, type, title, ....){

	//}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRetainInstance(true);
		//requestFeature(Window.FEATURE_NO_TITLE);
		activity = (MainSlidingActivity)getActivity();
		data = new  LinkedList<Job>();
		mInflater = inflater;

		View v = mInflater.inflate(R.layout.view_results, null);

		tabs = (LinearLayout) v.findViewById(R.id.tabs);
		sites = (TextView) v.findViewById(R.id.sites);
		sites.setSelected(true);
		social = (TextView) v.findViewById(R.id.social);

		sites.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!fetching) {
					sitesOn = true;
					//mListView.setSelection(0);
					sites.setSelected(true);
					social.setSelected(false);
					mListView.setRefreshing();
				}
			}
		});

		social.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!fetching) {
					sitesOn = false;
					//mListView.setSelection(0);
					social.setSelected(true);
					sites.setSelected(false);
					mListView.setRefreshing();
				}
			}
		});

		actionHeader = v.findViewById(R.id.top_navigation);
		logo = (ImageView)actionHeader.findViewById(R.id.logo_btn);
		logo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				activity.toggle();
			}
		});
		mTitle = (TextView)actionHeader.findViewById(R.id.title);

		mListView = (PullToRefreshListView)v.findViewById(R.id.layout_listview);

		setLoadingFooter();
		toggleLoadingFooter(false);

		adapter = new JobAdapter(activity, data);

		mListView.setAdapter(adapter);

		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				makeRefreshCall();
			}
		});

		mListView.setLockScrollWhileRefreshing(true);

		mListView.setOnScrollListener(new AbsListView.OnScrollListener(){

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				if(totalItemCount > 5)
					if (++firstVisibleItem + visibleItemCount > totalItemCount-3) {
						//load more content
						Log.v("DATA IN", String.valueOf(data.size()));
						if(appendMore && !finishedAppending) {
							Log.v("OnScroll", "Load more");
							makeAddCall();
							toggleLoadingFooter(true);
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
		footer = (RelativeLayout) footerContainer.findViewById(R.id.list_pending);
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

	public void toggleLoadingFooter(boolean open){

		if(open){
			MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) footer.getLayoutParams();
			mlp.setMargins(0, Math.round(0), 0, 0);
			footer.setLayoutParams(mlp);
			//footerContainer.setVisibility(View.VISIBLE);
		}
		else {
			MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) footer.getLayoutParams();
			mlp.setMargins(0, Math.round(-footer.getHeight()), 0, 0);
			footer.setLayoutParams(mlp);
			//footerContainer.setVisibility(View.GONE);
		}
	}

	public void makeRefreshCall(){
		fetching = true;
		data.clear();
		adapter.notifyDataSetChanged();
		if(sitesOn) {
			Log.v("Refresh Call", "Sites Made");
			appendMore = false;
			finishedAppending = false;
			Log.v("DATA IN", String.valueOf(data.size()));
			SearchSitesCall searchCall = new SearchSitesCall(activity);
			searchCall.setParamsSearch(0, CALL_STEP);
			searchCall.makeSearchCall();
			searchCall.addServerCallObserver(ResultsFragment.this);
		} else {
			Log.v("Refresh Call", "Social Made");
			appendMore = false;
			finishedAppending = false;
			Log.v("DATA IN", String.valueOf(data.size()));
			SearchSocialCall searchCall = new SearchSocialCall(activity);
			searchCall.setParamsSearch(0, CALL_STEP);
			searchCall.makeSearchCall();
			searchCall.addServerCallObserver(ResultsFragment.this);
		}
	}

	public void makeAddCall(){
		fetching = true;
		if(sitesOn) {
			Log.v("DATA IN", String.valueOf(data.size()));
			SearchSitesCall searchCall = new SearchSitesCall(activity);
			searchCall.setParamsSearch(data.size(), CALL_STEP);
			searchCall.makeSearchCall();
			searchCall.addServerCallObserver(ResultsFragment.this);
		} else {
			Log.v("DATA IN", String.valueOf(data.size()));
			SearchSocialCall searchCall = new SearchSocialCall(activity);
			searchCall.setParamsSearch(data.size(), CALL_STEP);
			searchCall.makeSearchCall();
			searchCall.addServerCallObserver(ResultsFragment.this);
		}
	}

	@Override
	public void RequestSuccess(int callID, String callUrl) {
		Log.v("CALL ID", String.valueOf(callID) + " " + callUrl);
		Log.v("Search", "SUCCESS");
		data.addAll(Job.jobs);
		Log.v("DATA IN", String.valueOf(data.size()));
		if(Job.jobs.size() < CALL_STEP-1) {
			appendMore = false;
			finishedAppending = true;
		} else {
			appendMore = true;
		}
		toggleLoadingFooter(false);
		//adapter.notifyDataSetChanged();
		mListView.onRefreshComplete();
		fetching = false;
	}

	@Override
	public void RequestFailed(int callID, String callUrl,
			String ServerResponse, Throwable exception) {

		Log.v("Search ", "FAILED");
		if(Job.jobs!=null) {
			data.addAll(Job.jobs);
			//adapter.notifyDataSetChanged();
		}
		toggleLoadingFooter(false);
		mListView.onRefreshComplete();
		if(String.valueOf(exception.getClass()).contains("java.net.SocketTimeoutException")){
			Toast.makeText(activity, "Ooops, something went wrong. Please try again :(",  Toast.LENGTH_LONG).show();
		} else {
			appendMore = false;
			finishedAppending = true;
		}
		fetching = false;

		if(!Utils.isNetworkAvailable(activity)) {
			Toast.makeText(activity, "No connection available :(", Toast.LENGTH_LONG).show();
			return;
		}
	}
}

