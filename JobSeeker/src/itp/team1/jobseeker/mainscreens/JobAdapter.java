package itp.team1.jobseeker.mainscreens;

import itp.team1.jobseeker.model.Job;
import itp.team1.jobseeker.R;
import itp.team1.jobseeker.Utils;

import java.util.LinkedList;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class JobAdapter  extends BaseAdapter implements ListAdapter{

	Activity con; 
	int layoutResourceId;    
	LinkedList<Job> items;

	public JobAdapter(Activity context, LinkedList<Job> items) {
		this.layoutResourceId = R.layout.job_list_item;
		this.con = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = ((Activity)con).getLayoutInflater().inflate(R.layout.job_list_item, null);
			convertView.setBackgroundColor(con.getResources().getColor(R.color.white));

			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.description = (TextView) convertView.findViewById(R.id.description);
			holder.source = (TextView) convertView.findViewById(R.id.source);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.offers = (ImageView)  convertView.findViewById(R.id.offer_icon);
			holder.food = (ImageView)  convertView.findViewById(R.id.food_icon);
			holder.wifi = (ImageView)  convertView.findViewById(R.id.wifi_icon);
			holder.outletImage = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Job item = getItem(position);

		// Set some view properties
		holder.title.setText(item.title);
		holder.description.setText(item.description);
		holder.source.setText(item.source);

		String text = Utils.parseTime(item.time);
		holder.time.setText(text);

		return convertView;
	}


	static class ViewHolder {
		public TextView title;
		public TextView description;
		public TextView time;
		public TextView source;
		public ImageView image;
		public ImageView offers;
		public ImageView food;
		public ImageView wifi;
		public ImageView outletImage;
	}

	@Override
	public int getCount() {
		return items.size();
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