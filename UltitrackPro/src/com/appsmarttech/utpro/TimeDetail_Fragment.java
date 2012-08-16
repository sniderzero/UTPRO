package com.appsmarttech.utpro;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;



public class TimeDetail_Fragment extends SherlockFragment{
	timeNavListener timeNavListener;
	Menu mnuActionBar;
	OnClickListener bStartListener, bStopListener, bResetListener;
	EditText etNotes;
	DBHelper_activity db;
	int iExerID;
	Bundle bArgs;
	private TextView tvTimer;
	private Button bStart, bReset, bStop, bSave, bPause;
	private Handler mHandler = new Handler();
	private long startTime;
	private long elapsedTime;
	private final int REFRESH_RATE = 100;
	private String hours,minutes,seconds,content, sDate, sNotes, sTime;
	private long secs,mins,hrs;
	private boolean stopped = false;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vTime = inflater.inflate(R.layout.timedetail_fragment, container, false);
   	 	
   	 	//has action bar
        setHasOptionsMenu(true);
        
        //declaring the timer and buttons
        tvTimer = (TextView)vTime.findViewById(R.id.tvTimer);
        bStart = (Button)vTime.findViewById(R.id.bStart);
        bStop = (Button)vTime.findViewById(R.id.bStop);
        bPause = (Button)vTime.findViewById(R.id.bPause);
        bReset = (Button)vTime.findViewById(R.id.bReset);
        etNotes = (EditText)vTime.findViewById(R.id.etNotes);
        //clears focus from the notes box so the keyboard will work
        etNotes.clearFocus();
        
        //declaring db helper class
   	 	db = (new DBHelper_activity(getActivity()));
   	 	
   	 	//grabbing exercise ID
   	 	bArgs = getArguments();
   	 	iExerID = bArgs.getInt("kExerID", 1);
   	 	
   	 	//declaring the listeners
        bStartListener = new OnClickListener() {
			@Override
			public void onClick(View vExercises) {
				startClick();
			}
        };

        bStopListener = new OnClickListener() {
			@Override
			public void onClick(View vExercises) {
				stopClick();
			}
        };

        bResetListener = new OnClickListener() {
			@Override
			public void onClick(View vExercises) {
				resetClick();
			}
        };
        
        bStart.setOnClickListener(bStartListener);
        bStop.setOnClickListener(bStopListener);
        bReset.setOnClickListener(bResetListener);
        bPause.setOnClickListener(bStopListener);
   	 	
   	 	return vTime;

}
	
	//actions when the user hits save/next
		public void onSave(){
			sDate = DateHelper.getDate();
			sNotes = etNotes.getText().toString();
			sTime = tvTimer.getText().toString();
			db.saveStat(1, iExerID, 0, 0, 0, sTime, sDate, sNotes);
		}
	
	//declaring fragment listener for moving between the fragments inside of the activity
	public interface timeNavListener{
	public void mNavigation(int iNav);

	}
	
	 //actions when clicking the start button
    public void startClick (){
    	if(stopped){
    		startTime = System.currentTimeMillis() - elapsedTime; 
    	}
    	else{
    		startTime = System.currentTimeMillis();
    	}
    	mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }
    //actions when clicking the stop button
    public void stopClick (){
    	mHandler.removeCallbacks(startTimer);
    	stopped = true;
    }
    //actions when clicking the reset button
    public void resetClick (){
    	stopped = false;
    	tvTimer.setText("00.00.00"); 	
    }
    //actions when clicking the save button
    public void saveAction (){

    }
    
    public void saveClick (){
    	content = tvTimer.getText().toString();
    	if(content.equals("00.00.00")){
    		Toast.makeText(getActivity(), "Exercise Not Saved, the timer is at 00:00:00", 50).show();
    	}
    	else{
    		saveAction();
    	}
    	
    }
    
    private Runnable startTimer = new Runnable() {
	 	   public void run() {
	 		   elapsedTime = System.currentTimeMillis() - startTime;
	 		   updateTimer(elapsedTime);
	 	       mHandler.postDelayed(this,REFRESH_RATE);
	 	   }
	 	};
	 	//logic for building the timer
	private void updateTimer (float time){
		secs = (long)(time/1000);
		mins = (long)((time/1000)/60);
		hrs = (long)(((time/1000)/60)/60);
		
		/* Convert the seconds to String 
		 * and format to ensure it has
		 * a leading zero when required
		 */
		secs = secs % 60;
		seconds=String.valueOf(secs);
    	if(secs == 0){
    		seconds = "00";
    	}
    	if(secs <10 && secs > 0){
    		seconds = "0"+seconds;
    	}
    	
		/* Convert the minutes to String and format the String */
    	
    	mins = mins % 60;
		minutes=String.valueOf(mins);
    	if(mins == 0){
    		minutes = "00";
    	}
    	if(mins <10 && mins > 0){
    		minutes = "0"+minutes;
    	}
		
    	/* Convert the hours to String and format the String */
    	
    	hours=String.valueOf(hrs);
    	if(hrs == 0){
    		hours = "00";
    	}
    	if(hrs <10 && hrs > 0){
    		hours = "0"+hours;
    	}
    	    	
		/* Setting the timer text to the elapsed time */
		tvTimer.setText(hours + "." + minutes + "." + seconds);
	}

	
	//creating the actionbar
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		inflater.inflate(R.menu.exer_ab, menu);
		mnuActionBar = menu;
		super.onCreateOptionsMenu(menu, inflater);

	}


	//setting the actions for the actionbar icons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.miSaveRep:
			onSave();
			timeNavListener.mNavigation(1);
			break;
		case R.id.miPrevRep:
			timeNavListener.mNavigation(0);
			break;
		case R.id.miSkip:
			timeNavListener.mNavigation(1);
			break;
		default:
			break;
		}

		return true;
	}

	//attaching to the listener in the activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            timeNavListener = (timeNavListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement timeNavListener");
        }
}

}