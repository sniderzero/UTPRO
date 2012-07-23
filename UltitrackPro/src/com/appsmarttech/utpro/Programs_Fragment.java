package com.appsmarttech.utpro;

import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Programs_Fragment extends SherlockFragment {
	
	//Declarations
		DBHelper_activity db;
		ListView lvPrograms;
		Cursor cPrograms;
		SharedPreferences spPreferences;
		Editor ePreferences;
		String sActiveProgram, sProgramName;
		Integer iActiveProgram, iProgramID;
		ActionMode mActionMode;
		ActionBar actionBar;
		OnItemClickListener lvProgramListener;
		OnItemLongClickListener lvProgramLongListener;
		Boolean bActionPresent;
		Intent inDays;
		ListAdapter lvProgramsAdapter;
		Program pSelected;
		
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vPrograms = inflater.inflate(R.layout.programs_fragment, container, false);
   	 	
        //open preferences
        spPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //declare preference editor
        ePreferences = spPreferences.edit();
        
        //declaring intents
        inDays = new Intent(getActivity(), Days_Activity.class);
        

        //grabbing the active program from preferences
        sActiveProgram = spPreferences.getString("kActiveProgram", "0");
        
        //converting it to Integer
        iActiveProgram = Integer.valueOf(sActiveProgram);
        
        //changing action bar settings
        actionBar = getSherlockActivity().getSupportActionBar();
        
        //sets home button as enabled (the app icon in the left corner)
        //actionBar.setHomeButtonEnabled(true);
        //boolean for determining if the cab is up - not necessary for UT90 or UT92
        bActionPresent = false;
        
   	 	//telling it that it has an actionbar
   	 	setHasOptionsMenu(true);
   	 	
   	 	
   	 	//setting activity title
   	 	getActivity().setTitle("Select Program");
   	 	
   	 	//assigning listview to listview widget
   	 	lvPrograms = (ListView)vPrograms.findViewById(R.id.lvPrograms);
   	 	
        //opening database
        db = (new DBHelper_activity(getActivity()));
        
        //building cursor
        //cPrograms = db.rawQuery("SELECT _id, programName, isEditable, timesCompleted FROM ProgramKey", null);
        //building list of programs
        List<Program> Programs = db.getAllPrograms();

    	//building the onclick listener for the lvPrograms Listview
        lvProgramListener = new OnItemClickListener() {

    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position,
    				long id) {

    			//grabbing the selected item from lvPrograms
    			pSelected = (Program) (lvPrograms.getItemAtPosition(position));
    			//setting iActiveProgram to the selected item ID
    			iActiveProgram = pSelected.getID();
    	        //converting to a string
    	        sActiveProgram = String.valueOf(iActiveProgram);
    	        //storing in preferences
    	        ePreferences.putString("kActiveProgram", sActiveProgram);
    	        ePreferences.commit(); 
    	        //refreshing the listview
    	        lvPrograms.invalidateViews();
    			
    		}
      	  
        };

        //setting up adapter
        lvProgramsAdapter = new ProgramArrayAdapter(getActivity(),Programs);
        	
        //setting click listener, long click listener, and adapter to the listview
        lvPrograms.setOnItemClickListener(lvProgramListener);
        lvPrograms.setAdapter(lvProgramsAdapter);
        
        return vPrograms;
    }

	
	//creating custom listview adapter for programs
	public class ProgramArrayAdapter extends ArrayAdapter<Program> {
		private final Context context;
		
	 
		public ProgramArrayAdapter(Context context, List<Program> values) {
			super(context, R.layout.programs_row, values);
			this.context = context;
			
		}
	 
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View rowView = inflater.inflate(R.layout.programs_row, parent, false);
			TextView tvProgramName = (TextView)rowView.findViewById(R.id.tvProgramName);
	 
			//grab current program
			pSelected = getItem(position);
			
			//setting text of program name
			tvProgramName.setText(pSelected.getName());
			
			//retrieving the program ID from the object
			iProgramID = pSelected.getID();
			if(iProgramID == iActiveProgram){
				((TextView)rowView.findViewById(R.id.tvActive)).setVisibility(View.VISIBLE);
			}
			else{
				((TextView)rowView.findViewById(R.id.tvActive)).setVisibility(View.INVISIBLE);
			}

			return rowView;
		}
	}
	
	
	   //creating the actionbar
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
			inflater.inflate(R.menu.programs_ab, menu);
			
			super.onCreateOptionsMenu(menu, inflater);
			
		}
		//setting the actions for the actionbar icons
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.miSettings:
				//launch settings preference
				Toast.makeText(getActivity(), "You called settings", Toast.LENGTH_SHORT)
						.show();
				break;

			case R.id.miGetFit:
				//launch days activity
				startActivity(inDays);
				break;
				
			default:
				Toast.makeText(getActivity(), "You pressed some other shit", Toast.LENGTH_SHORT)
				.show();
				break;
			}

			return true;
		}
}
