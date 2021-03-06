package itp.team1.jobseeker.mainscreens;

import itp.team1.jobseeker.Layouts.PullToRefreshListView;
import itp.team1.jobseeker.model.Job;
import itp.team1.jobseeker.session.Delegate;
import itp.team1.jobseeker.R;
import itp.team1.jobseeker.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JobAdapter  extends BaseAdapter {

	Activity con; 
	int layoutResourceId;    
	LinkedList<Job> items;
	Delegate delegate;
	ViewHolder holder;

	public JobAdapter(Activity context, LinkedList<Job> items) {
		this.layoutResourceId = R.layout.job_list_item;
		this.con = context;
		this.items = items;
		delegate = (Delegate) con.getApplicationContext();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = null;

		if (convertView == null) {
			convertView = ((Activity)con).getLayoutInflater().inflate(R.layout.job_list_item, null);
			convertView.setBackgroundColor(con.getResources().getColor(R.color.white));

			holder = new ViewHolder();
			holder.layout = (RelativeLayout) convertView.findViewById(R.id.item_layout);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.description = (TextView) convertView.findViewById(R.id.description);
			holder.salary = (TextView) convertView.findViewById(R.id.salary);
			holder.closing = (TextView) convertView.findViewById(R.id.closing);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.viewed = (ImageView)  convertView.findViewById(R.id.viewed);
			holder.applied = (ImageView)  convertView.findViewById(R.id.applied);
			holder.notes = (ImageView)  convertView.findViewById(R.id.notes);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.more = (ImageView) convertView.findViewById(R.id.more_info);
			holder.check = (ImageView) convertView.findViewById(R.id.check_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Job item = getItem(position);

		if(item.source.contains("facebook")) {
			holder.image.setImageResource(R.drawable.logo_facebook);
		} else if(item.source.contains("twitter")){
			holder.image.setImageResource(R.drawable.logo_twitter);
		} else if(item.source.contains("indeed")){
			holder.image.setImageResource(R.drawable.logo_indeed);
		} else if(item.source.contains("guardian")){
			holder.image.setImageResource(R.drawable.logo_guardian);
		} else if(item.source.contains("s1")){
			holder.image.setImageResource(R.drawable.logo_sone);
		} 
		// Set some view properties
		if(!item.title.equals("")) {
			holder.title.setVisibility(View.VISIBLE);
			holder.title.setText(item.title);
		}
		else holder.title.setVisibility(View.GONE);

		if(!item.description.equals("")) {
			holder.description.setVisibility(View.VISIBLE);
			holder.description.setText(item.description);
		}
		else holder.description.setVisibility(View.GONE);

		if(!item.salary.equals("")) {
			holder.salary.setVisibility(View.VISIBLE);
			holder.salary.setText("Salary: " + item.salary);
		}
		else holder.salary.setVisibility(View.GONE);

		if(!item.closingDate.equals("")) {
			holder.closing.setVisibility(View.VISIBLE);
			holder.closing.setText("Closing Date: " + item.closingDate);
		}
		else holder.closing.setVisibility(View.GONE);

		String text = Utils.parseTime(item.time);
		holder.time.setText(text);

		holder.more.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				delegate.getSession().incrementViewed();
				if(item.title.equals(""))
					MainSlidingActivity.databases.addActivity(item.id, Utils.getFormattedDate(), 1, 0, 0, "", item.description.substring(0, 140)+ "...", 0);
				else 
					MainSlidingActivity.databases.addActivity(item.id, Utils.getFormattedDate(), 1, 0, 0, "", item.title, 0);
				Intent i = new Intent(con, JobDetailsActivity.class);
				i.putExtra("Position", position);
				con.startActivity(i);
				con.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			}

		});
		
		if(MainSlidingActivity.databases.getJobViewedActivity(item.id, false, false, false).size()!=0){
			holder.viewed.setColorFilter(con.getResources().getColor(R.color.medium_blue));
		} else {
			holder.viewed.setColorFilter(con.getResources().getColor(R.color.greyed_out));
		}

		if(ResultsFragment.toSave.contains(item)) {
			holder.check.setVisibility(View.VISIBLE);
		} else {
			holder.check.setVisibility(View.GONE);
		}

		/*
		holder.layout.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				
				holder.check.setVisibility(View.VISIBLE);
				ResultsFragment.toSave.add(item);
				ResultsFragment.mSave.setVisibility(View.VISIBLE);
				return false;
			}

		});
		*/

		return convertView;
	}


	static class ViewHolder {
		public TextView title;
		public TextView description;
		public TextView time;
		public TextView salary;
		public TextView closing;
		public ImageView image;
		public ImageView viewed;
		public ImageView applied;
		public ImageView notes;
		public ImageView more;
		public RelativeLayout layout;
		public ImageView check;
	}

	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public Job getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addBottom(Job OutletItem) {
		items.addLast(OutletItem);
	}

	public void addTop(Job OutletItem) {
		items.addFirst(OutletItem);
	}
}