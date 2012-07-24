package com.appsmarttech.utpro;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ExerDetail_Activity extends SherlockFragmentActivity implements ActionBar.TabListener {
    
	List<Exercise> Exercises;
	int iDayID;
	DBHelper_activity db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exerdetail_activity);
        //declaring items about the action bar
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	getSupportActionBar().setDisplayUseLogoEnabled(false);
    	getSupportActionBar().setDisplayShowTitleEnabled(true);
    	getSupportActionBar().setDisplayShowHomeEnabled(true);
    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    	//creating the action bar tab Workout
    	ActionBar.Tab tWorkout = getSupportActionBar().newTab();
    	tWorkout.setText("Exercise");
    	//creating the action bar tab History
    	ActionBar.Tab tHistory = getSupportActionBar().newTab();
    	tHistory.setText("History");

    	//setting the tab listeners - what to do when the tab is clicked
    	tWorkout.setTabListener(this);
    	tHistory.setTabListener(this);

    	//adding the tabs to the action bar
    	getSupportActionBar().addTab(tWorkout);
    	getSupportActionBar().addTab(tHistory);
    	
    	//declaring db helper class
   	 	db = (new DBHelper_activity(this));
    	//grabbing the DayID passed by the day list activity
    	iDayID = this.getIntent().getIntExtra("DAY_ID", 0);
    	//creating a list of exercises based on the dayID
    	Exercises = db.getAllDayExercises(iDayID);
    	//for testing purposes pushing them all to the log
        for (Exercise cn : Exercises) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName();
                // Writing Contacts to log
        Log.d("Exercises: ", log);
        }

    	
    }
    

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		toastText("tab " + String.valueOf(tab.getText()) + " clicked");
		if(tab.getPosition() == 0){
			RepDetail_Fragment fRepDetail = new RepDetail_Fragment();
			ft.replace(android.R.id.content, fRepDetail);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	private void toastText(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

}