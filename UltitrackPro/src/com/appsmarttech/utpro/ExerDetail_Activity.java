package com.appsmarttech.utpro;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ExerDetail_Activity extends SherlockFragmentActivity implements ActionBar.TabListener{
    
	List<Exercise> Exercises;
	int iDayID, e, iExerID, iSize;
	DBHelper_activity db;
	Exercise eFirstExercise;
	String sDate;
	FragmentTransaction ft, ftOne;
	Tab tab;
	Bundle bArgs;
	Menu mnuActionBar;
	MenuItem miSaveNext;
	
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
    	
   	 	//declaring db helper class
   	 	db = (new DBHelper_activity(this));
    	
   	 	//grabbing the DayID passed by the day list activity
    	iDayID = getIntent().getIntExtra("DAY_ID", 0);
    	
    	//initializing e, used to track where we are in the array
    	e = 0;
    	
    	//creating a list of exercises based on the dayID
    	Exercises = db.getAllDayExercises(iDayID);
    	
    	//getting the count of exercise array items
    	iSize = (Exercises.size() - 1);
        
    	//setting title to first exercise name
        setTitle(Exercises.get(e).getName());
        
        //setting the exercise ID
        iExerID = Exercises.get(e).getExerID();

        updateE(2);
    }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction unused) {
		if(tab.getPosition() == 0){
		mRepFragment(2);
		}
		if(tab.getPosition() == 1){
			mHistoryFragment();
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}


	//method for launching the rep fragment
	public void mRepFragment(int iNav){
		FragmentManager fragMgr=getSupportFragmentManager();
		FragmentTransaction ft = fragMgr.beginTransaction();
		RepDetail_Fragment fRepDetail = new RepDetail_Fragment();
		fRepDetail.setArguments(mSetBundle());
		switch (iNav){
		case 0:
			ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
			break;
		case 1:
			ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		}
		ft.replace(android.R.id.content, fRepDetail);
		ft.commit();
		
	}
	//method for launching the history fragment
	public void mHistoryFragment(){
		FragmentManager fragMgr=getSupportFragmentManager();
		FragmentTransaction ft = fragMgr.beginTransaction();
		History_Fragment fHistory = new History_Fragment();
		fHistory.setArguments(mSetBundle());
		ft.replace(android.R.id.content, fHistory);
		ft.commit();
	}
	
	public Bundle mSetBundle(){
		bArgs = new Bundle();
		bArgs.putInt("kExerID", iExerID);
		bArgs.putString("kDate", sDate);
		
		return bArgs;
	}
	//navigation for the Rep Detail screen
	public void updateE(int iNav) {
		switch(iNav){
		case 0:
			onPrev();
			mRepFragment(0);
			break;
		case 1:
			onNext();
			mRepFragment(1);
			break;
		case 2:
			mRepFragment(2);
			break;
		}
		iExerID = Exercises.get(e).getExerID();
		setTitle(Exercises.get(e).getName());
	}
	
	//actions when user hits save/next
	public void onNext(){
			if(e<iSize)
			{
			e=e+1;
			}
			if(e==iSize)
			{
			miSaveNext.setTitle("Done");
			}
	}
	//actions when user hits prev
	public void onPrev(){
		if(e>0)
		{
		e=e-1;
			//changing the button name back to Save/Next
			if(miSaveNext.getTitle() == "Done");
			{
			miSaveNext.setTitle("Save/Next");
			}
		}
		else
		{
			Toast.makeText(this, "You're at the beginning", Toast.LENGTH_SHORT)
			.show();
		}
	}
	
	//actions when the user hits done
	public void onDone(){
		Toast.makeText(this, "You're at the end", Toast.LENGTH_SHORT)
		.show();
	}
	
	 //creating the actionbar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.exer_ab, menu);
	    this.mnuActionBar = menu;
	    return true;
	}
	
	//setting the actions for the actionbar icons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		miSaveNext = (MenuItem) mnuActionBar.findItem(R.id.miSaveNext);
		switch (item.getItemId()) {
		case R.id.miSaveNext:
		if(miSaveNext.getTitle() == "Done"){
			onDone();
			}
			else{ 
				RepDetail_Fragment fragment = 
						(RepDetail_Fragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
				fragment.onSave();
				
			updateE(1);
			}
			break;
		case R.id.miPrev:
		updateE(0);
			break;
		case R.id.miSkip:
		updateE(1);
			break;
		default:
			break;
		}

		return true;
	}




}