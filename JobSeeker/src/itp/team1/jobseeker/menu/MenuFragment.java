package itp.team1.jobseeker.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import itp.team1.jobseeker.Constants;
import itp.team1.jobseeker.R;
import itp.team1.jobseeker.mainscreens.DiaryFragment;
import itp.team1.jobseeker.mainscreens.MainSlidingActivity;
import itp.team1.jobseeker.mainscreens.SearchFragment;

public class MenuFragment extends ListFragment {
	
	protected static CustomMenuItem data[] = new CustomMenuItem[]
			{
					new CustomMenuItem(R.drawable.ic_launcher, R.string.account),
					new CustomMenuItem(R.drawable.ic_launcher,  R.string.search),
					new CustomMenuItem(R.drawable.ic_launcher,  R.string.diary),
					new CustomMenuItem(R.drawable.ic_launcher,  R.string.settings)
			};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ListView v =  (ListView) inflater.inflate(R.layout.menu_list, null);
		v.setItemsCanFocus(false);
		CustomMenuAdapter adapter = new CustomMenuAdapter(this.getActivity(), R.layout.menu_list_item, data);
		setListAdapter(adapter);
		
		return v;
	}
	
	@Override
	public void onResume () {
		super.onResume();
		Log.v("MenuFragment", "OnResume");
		ListView v =  this.getListView();
		v.setItemChecked(Constants.MENU_POSITION, true);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		super.onListItemClick(lv, v, position, id);
        lv.setItemChecked(position, true);
		
		Fragment newContent = null;

		// TODO 					ResponsiveUIActivity.session.getAuthority()
			switch (position) {
			case 0:
				newContent = new Fragment();
				break;
			case 1:
				newContent = new SearchFragment();
				break;
			case 2:
				newContent = new DiaryFragment();
				break;
			case 3:
				newContent = new Fragment();
				break;
			default: 
				newContent = new Fragment();
				break;
			}
			if (newContent != null) {
				switchFragment(newContent);
			}
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
