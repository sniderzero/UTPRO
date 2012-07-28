package com.appsmarttech.utpro;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash_Activity extends Activity {
	private long splashDelay = 2000; //5 seconds

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        
        TimerTask task = new TimerTask()
        {

			@Override
			public void run() {
				finish();
				Intent mainIntent = new Intent().setClass(Splash_Activity.this, main_activity.class);
				startActivity(mainIntent);
			}
        	
        };
        
        Timer timer = new Timer();
        timer.schedule(task, splashDelay);
    }
}