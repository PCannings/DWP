package com.example.thenewboston;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity {

	MediaPlayer ourSong;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		//setup sound
		//soundpool = small clips
		//media player, longer sound bites.
		ourSong = MediaPlayer.create(Splash.this, R.raw.splashsound);
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean music = getPrefs.getBoolean("checkbox", false);
		if(music == true){
		ourSong.start();
		}
		//setup a thread
		Thread timer = new Thread(){
			//setup run method within the thread
			public void run(){
				try{
					//sleep for 5 seconds
					sleep(1000);
				}catch (InterruptedException e){
					e.printStackTrace();
				}finally{
					//start the other activity
					Intent openMainActivity = new Intent("com.example.thenewboston.MENU");
					startActivity(openMainActivity);
				}
				
			}
		};
		timer.start();
		//minimum thread framework end.
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSong.release();
		finish();
	}
	
	

}
