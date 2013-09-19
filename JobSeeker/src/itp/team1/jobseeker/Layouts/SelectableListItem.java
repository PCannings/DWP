package itp.team1.jobseeker.Layouts;

import itp.team1.jobseeker.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import itp.team1.jobseeker.Layouts.CheckableRelativeLayout;

public class SelectableListItem extends CheckableRelativeLayout {
	
	protected boolean isChecked;
	protected int checkSignId;
	protected int textId;
	protected OnCheckedChangeListener onCheckedChangeListener;

	public static interface OnCheckedChangeListener {
		public void onCheckedChanged(SelectableListItem layout, boolean isChecked);
	}

	public SelectableListItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SelectableListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SelectableListItem(Context context, int checkableId) {
		super(context, checkableId);
		checkSignId = checkableId;
	}
	
	public SelectableListItem(Context context, int checkableId, int layoutResource) {
		super(context, checkableId);
		checkSignId = checkableId;
		//this = (View)this.LayoutInflater.inflate(layoutResource);
	}

	public void setChecked(boolean isChecked, boolean menu) {
		this.isChecked = isChecked;
		if(isChecked){
			if(menu) {
				this.setBackgroundColor(getResources().getColor(R.color.menu_background_selected));
				(this.findViewById(checkSignId)).setVisibility(View.VISIBLE);
				((ImageView) this.findViewById(checkSignId)).setColorFilter(getResources().getColor(R.color.item_selected_text));
				((TextView) this.findViewById(textId)).setTextColor(getResources().getColor(R.color.item_selected_text));
			}
			else {
				this.setBackgroundColor(getResources().getColor(R.color.item_background_selected));
				(this.findViewById(checkSignId)).setVisibility(View.VISIBLE);
			}
			
		}
		else {
			if(menu) {
				this.setBackgroundColor(getResources().getColor(R.color.menu_background));
				(this.findViewById(checkSignId)).setVisibility(View.VISIBLE);
				((ImageView) this.findViewById(checkSignId)).setColorFilter(getResources().getColor(R.color.item_text));
				((TextView) this.findViewById(textId)).setTextColor(getResources().getColor(R.color.item_text));
			}
			else {
				this.setBackgroundColor(getResources().getColor(R.color.item_background));
				(this.findViewById(checkSignId)).setVisibility(View.INVISIBLE);
			}
		}
		
		if (onCheckedChangeListener != null) {
			onCheckedChangeListener.onCheckedChanged(this, isChecked);
		}
	}
	
	@Override
	public void toggle() {
		this.isChecked = !this.isChecked;
		setChecked(isChecked);
	}
	
	public void setCheckSignResource(int check){
		checkSignId = check;
	}
	
	public void setTextResource(int check){
		textId = check;
	}
	
	public void setSignVisibility(int visibility){
		(this.findViewById(checkSignId)).setVisibility(visibility);
	}
	
	public void setSignColor(int color){
		(this.findViewById(checkSignId)).setVisibility(color);
	}
	
	public View getCheckSign(){
		return (this.findViewById(checkSignId));
	}
}
