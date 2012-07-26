package com.appsmarttech.utpro;

import java.util.Calendar;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RepDetail_Fragment extends SherlockFragment{
	Calendar cDate;
	Button bDate , bPlusRep, bMinusRep;
	DBHelper_activity db;
	int iDayID, iSize, e, iReps;
	List<Exercise> Exercises;
	Menu mnuActionBar;
	MenuItem miSaveNext;
	EditText etRep, etWeight, etNotes;
	OnClickListener bPlusListener, bMinusListener;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vExercises = inflater.inflate(R.layout.repdetail_fragment, container, false);
   	 	//assigning widgets
   	 	bDate = (Button)vExercises.findViewById(R.id.bDate);
   	 	bPlusRep = (Button)vExercises.findViewById(R.id.bPlusRep);
   	 	bMinusRep = (Button)vExercises.findViewById(R.id.bMinusRep);
   	 	etRep = (EditText)vExercises.findViewById(R.id.etRep);
   	 	etWeight = (EditText)vExercises.findViewById(R.id.etWeight);
   	 	etNotes = (EditText)vExercises.findViewById(R.id.etNotes);
   	 	
   	 	//setting current date to the date select button
   	 	bDate.setText(DateHelper.getDate());
   	 	//setting variable of e to 0 for exercise navigation
   	 	e=0;
   	 	//telling it that it has an actionbar
   	 	setHasOptionsMenu(true);
   	 	miSaveNext = (MenuItem) mnuActionBar.findItem(R.id.miSaveNext);
    	//declaring db helper class
   	 	db = (new DBHelper_activity(getActivity()));
    	//grabbing the DayID passed by the day list activity
    	iDayID = getActivity().getIntent().getIntExtra("DAY_ID", 0);
    	//creating a list of exercises based on the dayID
    	Exercises = db.getAllDayExercises(iDayID);
    	//getting the count of exercise array items
    	iSize = (Exercises.size() - 1);
        //setting title to first exercise name
        getActivity().setTitle(Exercises.get(e).getName());
        //building onclick listeners
        
        
        bPlusListener = new OnClickListener() {


			@Override
			public void onClick(View vExercises) {
				onPlusRep();
				
			}
      	  
        };
        
        bMinusListener = new OnClickListener() {


			@Override
			public void onClick(View vExercises) {
				onMinusRep();
				
			}
      	  
        };
		
        bPlusRep.setOnClickListener(bPlusListener);
        bMinusRep.setOnClickListener(bMinusListener);
	
   	 	return vExercises;
	}
	//actions when user hits save/next
	public void onNext(){

		if(e< iSize)
		{
		e=e+1;
		getActivity().setTitle(Exercises.get(e).getName());
			if(e==iSize)
			{
			miSaveNext.setTitle("Done");
			}
		}
		
	}
	//actions when user hits prev
	public void onPrev(){
		if(e>0)
		{
		e=e-1;
		getActivity().setTitle(Exercises.get(e).getName());
			//changing the button name back to Save/Next
			if(miSaveNext.getTitle() == "Done")
			{
			miSaveNext.setTitle("Save/Next");
			}
		}
		else
		{
			Toast.makeText(getActivity(), "You're at the beginning", Toast.LENGTH_SHORT)
			.show();
		}
	}
	//actions when user hits skip - same as onNext without the saves
	public void onSkip(){
		if(e< iSize)
		{
		e=e+1;
		getActivity().setTitle(Exercises.get(e).getName());
		}
		else
		{
			Toast.makeText(getActivity(), "You're at the end", Toast.LENGTH_SHORT)
			.show();
		}
	}
	
	//actions when the user hits done
	public void onDone(){
		Toast.makeText(getActivity(), "You're at the end", Toast.LENGTH_SHORT)
		.show();
	}
	
	//actions when user clicks the "+" button for reps
	public void onPlusRep(){
		int iLen = etRep.length();
		if(iLen <=0){//checking if the reps edit box is null
			iReps = 0; //if it is make iReps 0
		}
		else
		{
			iReps = Integer.parseInt(etRep.getText().toString());
					}
		//then add one to iReps and put that value in the edit box
		iReps = iReps +1;
		etRep.setText(String.valueOf(iReps));
	}
	
	public void onMinusRep(){
		int iLen = etRep.length();
		if(iLen <=0){//checking if the reps edit box is null
			
		}
		else
		{
			iReps = Integer.parseInt(etRep.getText().toString());
			if(iReps == 0){
				
			}
			else{
			iReps = iReps -1;
			etRep.setText(String.valueOf(iReps));
			}
		}
		//then add one to iReps and put that value in the edit box
		
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
			case R.id.miSaveNext:
				if(miSaveNext.getTitle() == "Done"){
					onDone();
				}
				else{
					onNext();
				}
				
				break;
			case R.id.miPrev:
				onPrev();
				break;
			case R.id.miSkip:
				onSkip();
				break;
			default:
				break;
			}

			return true;
		}
}