package com.appsmarttech.utpro;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.appsmarttech.utpro.RepDetail_Fragment.updateEListener;

public class ExerDetail_Activity extends SherlockFragmentActivity implements ActionBar.TabListener, updateEListener {
    
	List<Exercise> Exercises;
	int iDayID, e, iExerID;
	DBHelper_activity db;
	Exercise eFirstExercise;
	String sDate;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exerdetail_activity);
        //declaring items about the action bar
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	getSupportActionBar().setDisplayUseLogoEnabled(true);
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
    	
    	//initializine e and sDate
    	updateE(-1, "balls", -1);
    	
    

    	
    }
    
    

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if(tab.getPosition() == 0){
			RepDetail_Fragment fRepDetail = new RepDetail_Fragment();
			Bundle bArgs = new Bundle();
			bArgs.putInt("kE", e);
			bArgs.putString("kDate", sDate);
			fRepDetail.setArguments(bArgs);
			ft.replace(android.R.id.content, fRepDetail);
		}
		if(tab.getPosition() == 1){
			History_Fragment fHistory = new History_Fragment();
			Bundle bArgs = new Bundle();
			bArgs.putInt("kExerID", iExerID);
			fHistory.setArguments(bArgs);
			ft.replace(android.R.id.content, fHistory);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void updateE(int e, String sDate, int iExerID) {
		this.e = e;
		this.sDate = sDate;
		this.iExerID = iExerID;
		
	}



}