package com.appsmarttech.utpro;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Splash_Activity extends Activity {
	//declarations
	SharedPreferences SharedPreferences;
	Editor Editor;
	boolean bFirstLaunch;
	int iTotalLaunch;
	
	private long splashDelay = 1000; //2 seconds

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        //initializing preferences
        SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //initializing editor
        Editor = SharedPreferences.edit();
        //grabbing first launch state
        bFirstLaunch = SharedPreferences.getBoolean("kFirstLaunch", false);
        //grabbing number of launches
        iTotalLaunch = Integer.valueOf(SharedPreferences.getString("kNagScreen", "0"));
        //adding one to the total launch count
        iTotalLaunch = iTotalLaunch +1;
        //writing the total launch count back to the preferences
        Editor.putString("kNagScreen", String.valueOf(iTotalLaunch));
        //committing edit
        Editor.commit();
        
        
        
        TimerTask task = new TimerTask()
        {

			@Override
			public void run() {
				finish();
				//goWhere(); taken out for testing
				goSettings();
			}
        	
        };
        
        Timer timer = new Timer();
        timer.schedule(task, splashDelay);
    }
    
    public void goWhere(){//decides where to go
    	if(bFirstLaunch == true)  //goes to day list if this is not the first launch
    	{
    		goDayList();  
    	}
    	else
    	{
    		//set first launch key to true - next launch will go to the day list
    		Editor.putBoolean("kFirstLaunch", true);
    		Editor.commit();
    		goSettings();  //goes to settings if it is the first launch
    	}
    }
    
    public void goSettings(){  //launches settings screen
    	Intent inSettings = new Intent(this,Settings_Activity.class);
    	startActivity(inSettings);
    }
    
    public void goDayList(){ //launches day list
    	Intent inDayList = new Intent(this,Days_Activity.class);
    	startActivity(inDayList);
    	
    }
}