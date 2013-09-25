package itp.team1.jobseeker.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


import itp.team1.jobseeker.Constants;
import itp.team1.jobseeker.R;
import itp.team1.jobseeker.mainscreens.DiaryFragment;
import itp.team1.jobseeker.mainscreens.MainSlidingActivity;
import itp.team1.jobseeker.mainscreens.SearchFragment;

public class MenuFragment extends Fragment {

	ListView lv;

	protected static CustomMenuItem data[] = new CustomMenuItem[]
			{
		new CustomMenuItem(R.drawable.icon_search_selector,  R.string.search),
		new CustomMenuItem(R.drawable.icon_diary_selector,  R.string.diary),
		new CustomMenuItem(R.drawable.icon_saved_selector,  R.string.saved_jobs),
		new CustomMenuItem(R.drawable.icon_help_selector,  R.string.help)
			};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.menu_list, null);
		lv =  (ListView) v.findViewById(R.id.menu_list_layout);
		lv.setItemsCanFocus(false);
		CustomMenuAdapter adapter = new CustomMenuAdapter(this.getActivity(), R.layout.menu_list_item, data);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener (){
			@Override
			public void onItemClick (AdapterView<?> parent, View view, int position, long id){
				lv.setItemChecked(position, true);

				Fragment newContent = null;

				// TODO 					ResponsiveUIActivity.session.getAuthority()
				switch (position) {
				case 0:
					newContent = new SearchFragment();
					break;
				case 1:
					newContent = new DiaryFragment();
					break;
				case 2:
					newContent = new Fragment();//SavedJobsFragment();
					break;
				case 3:
					newContent = new Fragment(); // HelpFragment();
					break;
				default: 
					newContent = new Fragment();
					break;
				}
				if (newContent != null) {
					switchFragment(newContent);
				}
			}
		});

		return v;
	}

	@Override
	public void onResume () {
		super.onResume();
		Log.v("MenuFragment", "OnResume");
		lv.setItemChecked(Constants.MENU_POSITION, true);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainSlidingActivity) {
			MainSlidingActivity ra = (MainSlidingActivity) getActivity();
			ra.switchContent(fragment);
		}
	}
}
