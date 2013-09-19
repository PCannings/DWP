package itp.team1.jobseeker.Layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.*;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.*;
import android.view.animation.Animation.AnimationListener;
import android.widget.*;


import itp.team1.jobseeker.R;

/**
 * A generic, customizable Android ListView implementation that has 'Pull to Refresh' functionality.
 * <p/>
 * This ListView can be used in place of the normal Android android.widget.ListView class.
 * <p/>
 * Users of this class should implement OnRefreshListener and call setOnRefreshListener(..)
 * to get notified on refresh events. The using class should call onRefreshComplete() when
 * refreshing is finished.
 * <p/>
 * The using class can call setRefreshing() to set the state explicitly to refreshing. This
 * is useful when you want to show the spinner and 'Refreshing' text when the
 * refresh was not triggered by 'Pull to Refresh', for example on start.
 * <p/>
 * For more information, visit the project page:
 * https://github.com/erikwt/PullToRefresh-ListView
 *
 * @author Erik Wallentinsen <dev+ptr@erikw.eu>
 * @version 1.3.0
 */
public class NewPullToRefreshListView extends ListView{

	private static final float PULL_RESISTANCE                 = 1.7f;
	private static final int   BOUNCE_ANIMATION_DURATION       = 700;
	private static final int   BOUNCE_ANIMATION_DELAY          = 100;
	private static final float BOUNCE_OVERSHOOT_TENSION        = 1.4f;

	private GestureDetector mGestureDetector;

	private static enum State{
		PULL_TO_REFRESH,
		RELEASE_TO_REFRESH,
		REFRESHING
	}

	/**
	 * Interface to implement when you want to get notified of 'pull to refresh'
	 * events.
	 * Call setOnRefreshListener(..) to activate an OnRefreshListener.
	 */
	public interface OnRefreshListener{

		/**
		 * Method to be called when a refresh is requested
		 */
		public void onRefresh();
	}

	private static int measuredHeaderHeight;

	private boolean bounceBackHeader;
	private boolean lockScrollWhileRefreshing;

	private float                   previousY;
	private int                     headerPadding;
	private boolean                 hasResetHeader;
	private State                   state;
	private RelativeLayout			headerContainer;
	private FrameLayout             header;
	private Animation         		pushIn;
	private Animation         		pushOut;
	private ImageView               image;
	private ImageView               overlayImage;
	private OnItemClickListener     onItemClickListener;
	private OnItemLongClickListener onItemLongClickListener;
	private OnRefreshListener       onRefreshListener;

	private float mScrollStartY;
	private final int IDLE_DISTANCE = 5;

	public NewPullToRefreshListView(Context context){
		super(context);
		init(context);
	}

	public NewPullToRefreshListView(Context context, AttributeSet attrs){
		super(context, attrs);
		init(context);
	}

	public NewPullToRefreshListView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init(context);
	}

	@Override
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		this.onItemClickListener = onItemClickListener;
	}

	@Override
	public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
		this.onItemLongClickListener = onItemLongClickListener;
	}

	/**
	 * Activate an OnRefreshListener to get notified on 'pull to refresh'
	 * events.
	 *
	 * @param onRefreshListener The OnRefreshListener to get notified
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener){
		this.onRefreshListener = onRefreshListener;
	}

	/**
	 * @return If the list is in 'Refreshing' state
	 */
	public boolean isRefreshing(){
		return state == State.REFRESHING;
	}

	/**
	 * @return If the list is in 'Refreshing' state
	 */
	public boolean isIdle(){
		return state == State.PULL_TO_REFRESH;
	}

	/**
	 * Default is false. When lockScrollWhileRefreshing is set to true, the list
	 * cannot scroll when in 'refreshing' mode. It's 'locked' on refreshing.
	 *
	 * @param lockScrollWhileRefreshing
	 */
	public void setLockScrollWhileRefreshing(boolean lockScrollWhileRefreshing){
		this.lockScrollWhileRefreshing = lockScrollWhileRefreshing;
	}

	/**
	 * Explicitly set the state to refreshing. This
	 * is useful when you want to show the spinner and 'Refreshing' text when
	 * the refresh was not triggered by 'pull to refresh', for example on start.
	 */
	public void setRefreshing(){
		state = State.REFRESHING;
		scrollTo(0, 0);
		setHeaderPadding(0);
		startLoadingAnimation();
		onRefreshListener.onRefresh();
	}

	/**
	 * Set the state back to 'pull to refresh'. Call this method when refreshing
	 * the data is finished.
	 */
	public void onRefreshComplete(){
		state = State.PULL_TO_REFRESH;
		resetHeader();
	}
	
	private void init(Context context){
		setVerticalFadingEdgeEnabled(false);

		headerContainer = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.ptr_pending, null);
		header = (FrameLayout) headerContainer.findViewById(R.id.pull_to_refresh_header);
		overlayImage = (ImageView) header.findViewById(R.id.icon_overlay);
		image = (ImageView) header.findViewById(R.id.image);

		setLoadingAnimation(context);

		addHeaderView(headerContainer);
		setState(State.PULL_TO_REFRESH);

		ViewTreeObserver vto = header.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new PTROnGlobalLayoutListener());

		super.setOnItemClickListener(new PTROnItemClickListener());
		super.setOnItemLongClickListener(new PTROnItemLongClickListener());
	}

	private void setHeaderPadding(int padding){
		headerPadding = padding;

		MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) header.getLayoutParams();
		mlp.setMargins(0, Math.round(padding), 0, 0);
		header.setLayoutParams(mlp);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		if(lockScrollWhileRefreshing
				&& (state == State.REFRESHING || getAnimation() != null && !getAnimation().hasEnded())){
			return true;
		}

		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			if(getFirstVisiblePosition() == 0){
				previousY = event.getY();
			}
			else {
				previousY = -1;
			}

			// Remember where have we started
			mScrollStartY = event.getY();

			break;

		case MotionEvent.ACTION_UP:
			if(previousY != -1 && (state == State.RELEASE_TO_REFRESH || getFirstVisiblePosition() == 0)){
				switch(state){
				case RELEASE_TO_REFRESH:
					setState(State.REFRESHING);
					bounceBackHeader();
					break;

				case PULL_TO_REFRESH:
					resetHeader();
					break;
				}
			}
			break;

		case MotionEvent.ACTION_MOVE:
			if(previousY != -1 && getFirstVisiblePosition() == 0 && Math.abs(mScrollStartY-event.getY()) > IDLE_DISTANCE){
				float y = event.getY();
				float diff = y - previousY;
				if(diff > 0) diff /= PULL_RESISTANCE;
				previousY = y;

				int newHeaderPadding = Math.max(Math.round(headerPadding + diff), -header.getHeight());

				if(newHeaderPadding != headerPadding && state != State.REFRESHING){
					setHeaderPadding(newHeaderPadding);

					if(state == State.PULL_TO_REFRESH && headerPadding > 0){
						setState(State.RELEASE_TO_REFRESH);

						startLoadingAnimation();
					}else if(state == State.RELEASE_TO_REFRESH && headerPadding < 0){
						setState(State.PULL_TO_REFRESH);
						stopLoadingAnimation();
					}
				}
			}

			break;
		}

		return super.onTouchEvent(event);
	}

	private void bounceBackHeader(){
		int yTranslate = state == State.REFRESHING ?
				header.getHeight() - headerContainer.getHeight() :
					-headerContainer.getHeight() - headerContainer.getTop();

				TranslateAnimation bounceAnimation = new TranslateAnimation(
						TranslateAnimation.ABSOLUTE, 0,
						TranslateAnimation.ABSOLUTE, 0,
						TranslateAnimation.ABSOLUTE, 0,
						TranslateAnimation.ABSOLUTE, yTranslate);

				bounceAnimation.setDuration(BOUNCE_ANIMATION_DURATION);
				bounceAnimation.setFillEnabled(true);
				bounceAnimation.setFillAfter(false);
				bounceAnimation.setFillBefore(true);
				bounceAnimation.setInterpolator(new OvershootInterpolator(BOUNCE_OVERSHOOT_TENSION));
				bounceAnimation.setAnimationListener(new HeaderAnimationListener(yTranslate));

				startAnimation(bounceAnimation);
	}

	private void resetHeader(){
		if(getFirstVisiblePosition() > 0){
			setHeaderPadding(-header.getHeight());
			setState(State.PULL_TO_REFRESH);
			return;
		}

		if(getAnimation() != null && !getAnimation().hasEnded()){
			bounceBackHeader = true;
		}else{
			bounceBackHeader();
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void setState(State state){
		this.state = state;
		switch(state){
		case PULL_TO_REFRESH:
			stopLoadingAnimation();
			break;

		case RELEASE_TO_REFRESH:
			startLoadingAnimation();
			break;

		case REFRESHING:
			if(onRefreshListener == null){
				setState(State.PULL_TO_REFRESH);
			}else{
				onRefreshListener.onRefresh();
			}

			break;
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt){
		super.onScrollChanged(l, t, oldl, oldt);

		if(!hasResetHeader){
			if(measuredHeaderHeight > 0 && state != State.REFRESHING){
				setHeaderPadding(-measuredHeaderHeight);
			}

			hasResetHeader = true;
		}
	}

	private class HeaderAnimationListener implements AnimationListener{

		private int height, translation;
		private State stateAtAnimationStart;

		public HeaderAnimationListener(int translation){
			this.translation = translation;
		}

		@Override
		public void onAnimationStart(Animation animation){
			stateAtAnimationStart = state;

			android.view.ViewGroup.LayoutParams lp = getLayoutParams();
			height = lp.height;
			lp.height = getHeight() - translation;
			setLayoutParams(lp);
		}

		@Override
		public void onAnimationEnd(Animation animation){
			setHeaderPadding(stateAtAnimationStart == State.REFRESHING ? 0 : -measuredHeaderHeight - headerContainer.getTop());
			setSelection(0);

			android.view.ViewGroup.LayoutParams lp = getLayoutParams();
			lp.height = height;
			setLayoutParams(lp);

			if(bounceBackHeader){
				bounceBackHeader = false;

				postDelayed(new Runnable(){

					@Override
					public void run(){
						resetHeader();
					}
				}, BOUNCE_ANIMATION_DELAY);
			}else if(stateAtAnimationStart != State.REFRESHING){
				setState(State.PULL_TO_REFRESH);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation){}
	}

	private class PTROnGlobalLayoutListener implements OnGlobalLayoutListener{

		@Override
		public void onGlobalLayout(){
			int initialHeaderHeight = header.getHeight();

			if(initialHeaderHeight > 0){
				measuredHeaderHeight = initialHeaderHeight;

				if(measuredHeaderHeight > 0 && state != State.REFRESHING){
					setHeaderPadding(-measuredHeaderHeight);
					requestLayout();
				}
			}

			getViewTreeObserver().removeGlobalOnLayoutListener(this);
		}
	}

	private class PTROnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
			hasResetHeader = false;

			if(onItemClickListener != null && state == State.PULL_TO_REFRESH){
				// Passing up onItemClick. Correct position with the number of header views
				onItemClickListener.onItemClick(adapterView, view, position - getHeaderViewsCount(), id);
			}
		}
	}

	private class PTROnItemLongClickListener implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id){
			hasResetHeader = false;

			if(onItemLongClickListener != null && state == State.PULL_TO_REFRESH){
				// Passing up onItemLongClick. Correct position with the number of header views
				return onItemLongClickListener.onItemLongClick(adapterView, view, position - getHeaderViewsCount(), id);
			}

			return false;
		}
	}

	public void startLoadingAnimation() {
		overlayImage.startAnimation(pushIn);
		overlayImage.setVisibility(View.VISIBLE);
	}

	public void stopLoadingAnimation() {
		overlayImage.clearAnimation();
		overlayImage.setVisibility(View.GONE);
		//animationSlide.cancel();
		//animationSlide.reset();
	}

	public void setLoadingAnimation(Context context) {
		pushIn = AnimationUtils.loadAnimation(context,
				R.anim.slide_in_left);
		pushOut = AnimationUtils.loadAnimation(context,
				R.anim.slide_out_right);

		pushIn.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				overlayImage.startAnimation(pushOut);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
		});

		pushOut.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				overlayImage.startAnimation(pushIn);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
		});

	}


	
}
