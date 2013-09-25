package itp.team1.jobseeker.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import itp.team1.jobseeker.Constants;
import itp.team1.jobseeker.R;
import itp.team1.jobseeker.Layouts.SelectableListItem;

public class CustomMenuAdapter  extends ArrayAdapter<CustomMenuItem>{

    Context context; 
    int layoutResourceId;    
    CustomMenuItem data[] = null;
    
    public CustomMenuAdapter(Context context, int layoutResourceId, CustomMenuItem[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MenuHolder holder = null;
        
        CustomMenuItem item = data[position];
       
		// Re-use the view if possible
		// --
		if (row == null) {
			row = LayoutInflater.from(getContext()).inflate(R.layout.menu_list_item, null);
			holder = new MenuHolder(row);
			row.setTag(holder);
		} else {
			holder = (MenuHolder) row.getTag();
		}

		// Set some view properties
		holder.txtTitle.setText(item.title);
	    holder.imgIcon.setImageResource(item.icon);

		// Restore the checked state properly
		final ListView lv = (ListView) parent;
		//holder.layout.setChecked(lv.isItemChecked(position), true);

		holder.layout.setSelected(lv.isItemChecked(position));
		
		if(position == Constants.MENU_POSITION) {
			//holder.layout.setChecked(true, true);
			lv.setItemChecked(position, true);
			holder.layout.setSelected(true);
		}
        
        return row;
    }
    
    static class MenuHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        //SelectableListItem layout;
        RelativeLayout layout;
        
        public MenuHolder(View root) {
        	txtTitle = (TextView) root.findViewById(R.id.item_text);
        	imgIcon = (ImageView)  root.findViewById(R.id.item_image);
			layout = (RelativeLayout) root.findViewById(R.id.menu_item_layout);
			//layout.setClickable(true);
		}
    }
}