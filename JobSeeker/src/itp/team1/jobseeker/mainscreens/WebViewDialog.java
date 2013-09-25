package itp.team1.jobseeker.mainscreens;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.session.Delegate;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewDialog extends Dialog  {

	public Activity c;
	private OnCompleteListener onCompleteListener;
	private ImageView close;
	private WebView mWebView;
	private FrameLayout layout;
	private Delegate delegate;
	private String URL = "";
	private View actionHeader;
	private ImageView logo;
	private TextView mTitle;
	String source;
	private ProgressBar progress;
	private long timeStarted = 0;
	private boolean loaded = false;
	private static final long TIME_PERIOD = 120000; // 2 mins in milliseconds

	public WebViewDialog(Activity a, OnCompleteListener listener, String url) {
		super(a, R.style.WebPopUp);
		// TODO Auto-generated constructor stub
		this.c = a;
		setCancelable(false);
		onCompleteListener= listener;
		delegate = (Delegate) a.getApplicationContext();
		URL = url;
	}

	public WebViewDialog(Activity a, OnCompleteListener listener, String url, String source) {
		super(a, R.style.WebPopUp);
		// TODO Auto-generated constructor stub
		this.c = a;
		setCancelable(false);
		onCompleteListener= listener;
		delegate = (Delegate) a.getApplicationContext();
		URL = url;
		this.source = source;
	}

	public WebViewDialog(Activity a, String url) {
		super(a, R.style.WebPopUp);
		// TODO Auto-generated constructor stub
		this.c = a;
		setCancelable(false);
		delegate = (Delegate) a.getApplicationContext();
		URL = url;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		getWindow().setGravity(Gravity.CENTER);

		setContentView(R.layout.dialog_webview);

		actionHeader = findViewById(R.id.top_navigation);
		logo = (ImageView)actionHeader.findViewById(R.id.logo_btn);
		logo.setImageResource(R.drawable.cross);
		logo.setOnClickListener(new cancelListener());
		mTitle = (TextView)actionHeader.findViewById(R.id.title);
		mTitle.setText("Loading Website Source: " + source);

		progress = (ProgressBar)findViewById(R.id.page_progress);

		mWebView = (WebView)  findViewById(R.id.customwebview);
		mWebView.getSettings().setPluginState(PluginState.ON);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(true);

		mWebView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress){
				if(newProgress!=100) {
					progress.setProgress(newProgress);
				}
			}
		});

		mWebView.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				//on some devices the url is read in differently, so added this or here
				Log.v("URL", url);
				/*if( (url.startsWith(CALLBACK_URL)) ){
					Uri uri = Uri.parse(url);
					String oauthVerifier = uri.getQueryParameter("oauth_verifier");
					new SetUserTask(oauthVerifier).execute();
				}
				else{
					view.loadUrl(url);
				} */

				view.loadUrl(url);

				return true;
			}

			@Override
			public void onPageStarted (WebView view, String url, Bitmap favicon){
				Log.v("Page Started", url);
				progress.setProgress(0);
			}

			@Override
			public void onPageFinished (WebView view, String url){
				Log.v("Page Finished", url);
				progress.setProgress(100);
				if(!loaded) {
					loaded = true;
					timeStarted = System.currentTimeMillis();
				}
			}
		});

		mWebView.loadUrl(URL);
	}

	class cancelListener implements View.OnClickListener {
		public void onClick(View v) {
			if(System.currentTimeMillis()> timeStarted + TIME_PERIOD)
				sendResultToListener(true);
			else
				sendResultToListener(false);
			WebViewDialog.this.dismiss();
		}
	}

	private void sendResultToListener(boolean result) {
		if (onCompleteListener != null ) {
			onCompleteListener.onComplete(result);
		}
	}

	public void setOnCompleteListener(OnCompleteListener listener) {
		onCompleteListener = listener;
	}

	public OnCompleteListener getOnCompleteListener() {
		return onCompleteListener;
	}

	public interface OnCompleteListener {
		/**
		 * Called when the dialog completes.
		 *
		 * @param values on success, contains the values returned by the dialog
		 * @param error  on an error, contains an exception describing the error
		 */
		void onComplete(boolean isLoggedIn);
	}
}
