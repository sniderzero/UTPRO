package com.appsmarttech.ut90;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;



public class TimeDetail_Fragment extends SherlockFragment{
	timeNavListener timeNavListener;
	setDateListenerTime setDateListenerTime;
	Menu mnuActionBar;
	OnClickListener bStartListener, bStopListener, bResetListener, bDateListener, bNextListener, bSkipListener, bPrevListener;;
	EditText etNotes;
	DBHelper_activity db;
	int iExerID, iYear, iMonth, iDay;
	Bundle bArgs;
	Intent inDays;
	private TextView tvTimer;
	private Button bStart, bReset, bStop, bDate, bPrev, bSkip, bNext;
	private Handler mHandler = new Handler();
	private long startTime;
	private long elapsedTime;
	private final int REFRESH_RATE = 100;
	private String hours,minutes,seconds,sDate, sNotes, sTime;
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
   	 	bPrev = (Button)vTime.findViewById(R.id.btnPrev);
   	 	bNext = (Button)vTime.findViewById(R.id.btnNext);
   	 	bSkip = (Button)vTime.findViewById(R.id.btnSkip);
        tvTimer = (TextView)vTime.findViewById(R.id.tvTimer);
        bStart = (Button)vTime.findViewById(R.id.bStart);
        bStop = (Button)vTime.findViewById(R.id.bStop);
        bReset = (Button)vTime.findViewById(R.id.bReset);
        etNotes = (EditText)vTime.findViewById(R.id.etNotes);
        bDate = (Button)vTime.findViewById(R.id.bDate);
        //clears focus from the notes box so the keyboard will work
        etNotes.clearFocus();
        
        //declaring db helper class
   	 	db = (new DBHelper_activity(getActivity()));
   	 	
   	 	//grabbing exercise ID and date passed from the activity
   	 	bArgs = getArguments();
   	 	iExerID = bArgs.getInt("kExerID", 1);
   	 	sDate = bArgs.getString("kDate");  //date
   	 	
   	 	//setting the date button to reflect the date
   	 	bDate.setText(sDate);
   	 	
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
        
        bDateListener = new OnClickListener() {
    			@Override
    			public void onClick(View vExercises) {
    				diaglogDatePick(vExercises);	
    			}
            };
            
            bNextListener = new OnClickListener() {


     			@Override
     			public void onClick(View vExercises) {
     				onSave();
    				timeNavListener.mNavigation(1);
     				
     			}
           	  
             };
             
             bPrevListener = new OnClickListener() {


     			@Override
     			public void onClick(View vExercises) {
     				timeNavListener.mNavigation(0);
     				
     			}
           	  
             };
             
             bSkipListener = new OnClickListener() {


     			@Override
     			public void onClick(View vExercises) {
     				timeNavListener.mNavigation(1);
     				
     			}
           	  
             };
             
        
        bStart.setOnClickListener(bStartListener);
        bStop.setOnClickListener(bStopListener);
        bReset.setOnClickListener(bResetListener);
        bDate.setOnClickListener(bDateListener);
        bNext.setOnClickListener(bNextListener);
        bSkip.setOnClickListener(bSkipListener);
        bPrev.setOnClickListener(bPrevListener);
   	 	
   	 	return vTime;

}
	
	//actions when the user hits save/next
		public void onSave(){
			sDate = (String) bDate.getText();
			sNotes = etNotes.getText().toString();
			sTime = tvTimer.getText().toString();
			db.saveStat(1, iExerID, 0, 0, 0, sTime, sDate, sNotes, 0);
		}
	
	//declaring fragment listener for moving between the fragments inside of the activity
	public interface timeNavListener{
	public void mNavigation(int iNav);

	}
	
	//declaring fragment listener for setting the date in the activity
	public interface setDateListenerTime{
	public void mSetDateTime(String Date);

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
        bStart.setVisibility(View.GONE);
        bReset.setVisibility(View.GONE);
        bStop.setVisibility(View.VISIBLE);
    }
    //actions when clicking the stop button
    public void stopClick (){
    	mHandler.removeCallbacks(startTimer);
    	stopped = true;
    	bStart.setVisibility(View.VISIBLE);
        bReset.setVisibility(View.VISIBLE);
        bStop.setVisibility(View.GONE);
    }
    //actions when clicking the reset button
    public void resetClick (){
    	stopped = false;
    	tvTimer.setText("00.00.00"); 	
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
	

	
    //dialog box for choosing exercise date
    public void diaglogDatePick(View v){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.datepicker_dialog);
        dialog.setTitle("Choose Exercise Date");
        dialog.setCancelable(true);
        //declare dialog buttons
        Button btnOK = (Button) dialog.findViewById(R.id.btnDateOK);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnDateCancel);
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        //setting picker date based on current button date
        sDate = bDate.getText().toString();
        String delims = "[-]";
        String[] datePieces = sDate.split(delims);
        
        datePicker.init(Integer.parseInt(datePieces[0]), Integer.parseInt(datePieces[1]) - 1, Integer.parseInt(datePieces[2]), null);

        btnOK.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
            iYear = datePicker.getYear();
            iMonth = datePicker.getMonth();
            iDay = datePicker.getDayOfMonth();
            String sYear = String.valueOf(iYear);
            String sMonth = String.valueOf(iMonth + 1);
            String sDay = String.valueOf(iDay);
            //adding 0 if the string is only length of 1
            if(sMonth.length() == 1){
            	sMonth = "0" + sMonth;
            }
            if(sDay.length() == 1){
            	sDay = "0" + sDay;
            }
            
            String sDate = sYear + "-" + sMonth + "-" + sDay;
            bDate.setText(sDate);
            //sets date in the activity so it can be used in the next fragment
            setDateListenerTime.mSetDateTime(sDate);
        	dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        	dialog.dismiss();
            }
        });

    	dialog.show();

    }



	//setting the actions for the actionbar icons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default:
			inDays = new Intent(getActivity(), Days_Activity.class);
			startActivity(inDays);
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
        
        try {
            setDateListenerTime = (setDateListenerTime) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement setDateListener");
        }
}

}