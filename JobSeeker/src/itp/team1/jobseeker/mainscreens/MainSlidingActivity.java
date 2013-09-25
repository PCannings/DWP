package itp.team1.jobseeker.mainscreens;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.menu.MenuFragment;
import itp.team1.jobseeker.slidingmenu.SlidingMenu;
import itp.team1.jobseeker.slidingmenu.SlidingMenu.OnOpenedListener;
import itp.team1.jobseeker.slidingmenu.app.SlidingFragmentActivityNA;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Window;

public class MainSlidingActivity extends SlidingFragmentActivityNA  { 

	private Fragment mContent;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.v("MainActivity", "OnCreate");
	
		// set the Above View
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		}
	
		if (mContent == null)
			mContent = new ResultsFragment();

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent, getFragmentTag(mContent))
		.commit();

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new MenuFragment())
		.commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setSlidingEnabled(true);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);

		sm.setOnOpenedListener(new OnOpenedListener() {
			public void onOpened() {
				getSlidingMenu().invalidate();
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
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//Use in case saving instance of fragment
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.v("MainActivity", "OnActivityResult");
		Log.v("MainActivity", "code " + requestCode);
		Log.v("MainActivity", "result code " + resultCode);
	}

	public void switchContent(final Fragment fragment) {

		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent, getFragmentTag(mContent))
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}

	public void switchContentDelayed(final Fragment fragment) {

		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent, getFragmentTag(mContent))
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 1500);

	}	

	@Override
	public void onBackPressed() {
		FragmentManager ft = getSupportFragmentManager();

		if (ft.getBackStackEntryCount() == 0){
			ft.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			finish();
			//android.os.Process.killProcess(android.os.Process.myPid());
		}
		else {
			ft.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			//finish();
			//android.os.Process.killProcess(android.os.Process.myPid());
		}

	}

	public String getFragmentTag(Fragment f) {
		String tag = f.toString();
		tag = tag.substring(0, tag.lastIndexOf("{"));
		return tag;
	}

}