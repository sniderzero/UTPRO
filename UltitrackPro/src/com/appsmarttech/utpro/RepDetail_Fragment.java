package com.appsmarttech.utpro;

import java.util.Calendar;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RepDetail_Fragment extends SherlockFragment{
	Calendar cDate;
	Button bDate , bPlusRep, bMinusRep, bPlusWeight, bMinusWeight;
	DBHelper_activity db;
	int iDayID, iSize, e, ae, iReps, iWeight, iExerID;
	List<Stat> Stats;
	
	EditText etRep, etWeight, etNotes;
	OnClickListener bPlusListener, bMinusListener, bPlusWeightListener, bMinusWeightListener;
	updateEListener updateEListener;
	String sDate, saDate;
	Bundle bArgs;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vExercises = inflater.inflate(R.layout.repdetail_fragment, container, false);
   	 	//assigning widgets
   	 	bDate = (Button)vExercises.findViewById(R.id.bDate);
   	 	bPlusRep = (Button)vExercises.findViewById(R.id.bPlusRep);
   	 	bMinusRep = (Button)vExercises.findViewById(R.id.bMinusRep);
   	 	bPlusWeight = (Button)vExercises.findViewById(R.id.bPlusWeight);
   	 	bMinusWeight = (Button)vExercises.findViewById(R.id.bMinusWeight);
   	 	etRep = (EditText)vExercises.findViewById(R.id.etRep);
   	 	etWeight = (EditText)vExercises.findViewById(R.id.etWeight);
   	 	etNotes = (EditText)vExercises.findViewById(R.id.etNotes);
   	 	//grabbing arguments from the activity
   	 	bArgs = getArguments();
   	 	saDate = bArgs.getString("kDate");  //date
   	 	iExerID = bArgs.getInt("kExerID", 1);
   	 	
   	 	//setting current date to the date select button
   	 	//bDate.setText(DateHelper.getDate());
   	 	bDate.setText(DateHelper.getDate());

   	 	
    	
   	 	//declaring db helper class
   	 	db = (new DBHelper_activity(getActivity()));

    	
        
        //grabbing last stats
        getLastStat();
        
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
        
        bPlusWeightListener = new OnClickListener() {


 			@Override
 			public void onClick(View vExercises) {
 				onPlusWeight();
 				
 			}
       	  
         };
         
         bMinusWeightListener = new OnClickListener() {


 			@Override
 			public void onClick(View vExercises) {
 				onMinusWeight();
 				
 			}
       	  
         };
		
        bPlusRep.setOnClickListener(bPlusListener);
        bMinusRep.setOnClickListener(bMinusListener);
        bPlusWeight.setOnClickListener(bPlusWeightListener);
        bMinusWeight.setOnClickListener(bMinusWeightListener);
   	 	return vExercises;
	}
	
	//declaring fragment listener for updating e in the activity
	public interface updateEListener{
	public void updateE(int iNav);

	}
	
	//actions when user hits save/next
	public void onNext(){

		if(e< iSize)
		{
		e=e+1;
			if(e==iSize)
			{
			
			}
		}
		
	}
	//actions when user hits prev
	public void onPrev(){
		if(e>0)
		{
		e=e-1;
			//changing the button name back to Save/Next
			
			{
			
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
		
			if(e==iSize)
			{
			
			}
		}
		else
		{
			onDone();
		}
	} 
	
	//actions when the user hits done
	public void onDone(){
		Toast.makeText(getActivity(), "You're at the end", Toast.LENGTH_SHORT)
		.show();
	}
	
	//actions when the user hits save/next
	public void onSave(){
		//int tExerID = Exercises.get(e).getExerID();
		int tWeight = Integer.parseInt(etWeight.getText().toString());
		int tRep = Integer.parseInt(etRep.getText().toString());
		sDate = bDate.getText().toString();
		String tNotes = etNotes.getText().toString();
		db.saveStat(1, iExerID, tWeight, tRep, 0, 0, sDate, tNotes);
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
	}
		//actions when user clicks the "+" button for reps
		public void onPlusWeight(){
			int iLen = etWeight.length();
			if(iLen <=0){//checking if the reps edit box is null
				iWeight = 0; //if it is make iReps 0
			}
			else
			{
				iWeight = Integer.parseInt(etWeight.getText().toString());
						}
			//then add one to iReps and put that value in the edit box
			iWeight = iWeight +1;
			etWeight.setText(String.valueOf(iWeight));
		}
		
		public void onMinusWeight(){
			int iLen = etWeight.length();
			if(iLen <=0){//checking if the reps edit box is null
				
			}
			else
			{
				iWeight = Integer.parseInt(etWeight.getText().toString());
				if(iWeight == 0){
					
				}
				else{
				iWeight = iWeight -1;
				etWeight.setText(String.valueOf(iWeight));
				}
			}
	}
		
		//method for getting the last exercise stats
		public void getLastStat(){
			//getting the stats for the current exercise
			Stats = db.getExerciseStats(iExerID);
			int s = Stats.size() -1 ;
			if (s >= 0){
			//setting the appropriate fields to the last user stat
			etRep.setText(String.valueOf(Stats.get(0).getReps()));
			etWeight.setText(String.valueOf(Stats.get(0).getWeight()));
			etNotes.setText(Stats.get(0).getNotes());
			}
		}
		
		
	
	  		
		//attaching to the listener in the activity
	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	            updateEListener = (updateEListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement updateEListener");
	        }
	}
}