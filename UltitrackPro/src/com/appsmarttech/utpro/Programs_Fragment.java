package com.appsmarttech.utpro;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Programs_Fragment extends SherlockFragment {
	
	//Declarations
		SQLiteDatabase db;
		ListView lvPrograms;
		Cursor cPrograms;
		SharedPreferences spPreferences;
		Editor ePreferences;
		String sActiveProgram;
		Integer iActiveProgram, iProgramID;
		ActionMode mActionMode;
		ActionBar actionBar;
		
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vPrograms = inflater.inflate(R.layout.programs_fragment, container, false);
   	 	
        //open preferences
        spPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //declare preference editor
        ePreferences = spPreferences.edit();

        //grabbing the active program from preferences
        sActiveProgram = spPreferences.getString("kActiveProgram", "0");
        
        //converting it to Integer
        iActiveProgram = Integer.valueOf(sActiveProgram);
        
        //changing action bar settings
        actionBar = getSherlockActivity().getSupportActionBar();
        
        actionBar.setHomeButtonEnabled(true);
        
        
/*
        //converting back to a string
        strRuns = String.valueOf(intRuns);
        //storing in preferences
        edit.putString("runnumber", strRuns);
        edit.commit(); 
        */
   	 	
   	 	//telling it that it has an actionbar
   	 	setHasOptionsMenu(true);
   	 	
   	 	
   	 	//setting activity title
   	 	getActivity().setTitle("Select Program");
   	 	
   	 	//assigning listview to listview widget
   	 	lvPrograms = (ListView)vPrograms.findViewById(R.id.lvPrograms);
   	 	
        //opening database
        db = (new DBHelper_activity(getActivity())).getReadableDatabase();
        
        //building cursor
        cPrograms = db.rawQuery("SELECT _id, trackName FROM ProgramKey", null);
        
        //setting adapter to the listview
        lvPrograms.setAdapter(new adapter(getActivity(),cPrograms));
        
        
        return vPrograms;
    }
    //creating adapter for the list view
	public class adapter extends CursorAdapter{  
	    private Cursor mCursor;  
	    private Context mContext;  
	    LayoutInflater mInflater;  
	    
	  
	    public adapter(Context context, Cursor cPrograms) {  
	      super(context, cPrograms, true);  
	      mInflater = LayoutInflater.from(context);  
	      mContext = context;  
	    }  
	  
	    @Override  
	    public void bindView(View view, Context context, final Cursor cursor) {  
	      TextView tvProgramName = (TextView)view.findViewById(R.id.tvProgramName);
	      CheckBox cbProgram = (CheckBox)view.findViewById(R.id.cbProgram);
	      tvProgramName.setText(cursor.getString(cursor.getColumnIndex("trackName")));
	  
	      //retrieving the program ID from the cursor
	      iProgramID = cursor.getInt(cursor.getColumnIndex("_id"));
	      //if programID is equal to the active program
	      if(iProgramID == iActiveProgram){
	    	  //then show the active textview
	    	  ((TextView)view.findViewById(R.id.tvActive)).setVisibility(View.VISIBLE);
	      }
	      else{
	    	  //if not hide the active textview
	    	  ((TextView)view.findViewById(R.id.tvActive)).setVisibility(View.INVISIBLE);
	      } 
	      //building an onclick listener for tvProgramName
	      OnClickListener tvProgramListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Toast.makeText(getActivity(), cursor.getString(cursor.getColumnIndex("trackName")), Toast.LENGTH_SHORT)
				.show();
				
		        //setting active program for testing purposes
		        //ePreferences.putString("kActiveProgram", cursor.getInt(cursor.getColumnIndex("_id")));
		        //ePreferences.commit();
				
			}
	    	  
	      };
	      //assigning the onclicklistener to the tvProgramName text view
	      tvProgramName.setOnClickListener(tvProgramListener);
	      
	      //building an onclick listener for cbProgram
	      OnCheckedChangeListener cbProgramListener = new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				mActionMode = getSherlockActivity().startActionMode(new mActionModeCallback());
				
				Toast.makeText(getActivity(), "check status changed" , Toast.LENGTH_SHORT)
				.show();
				
			}
	    	  
	      };
	      
	      cbProgram.setOnCheckedChangeListener(cbProgramListener);
	      
	      
	      
	      }  
	  
	    @Override  
	    public View newView(Context context, Cursor cPrograms, ViewGroup parent) {  
	      final View view = mInflater.inflate(R.layout.programs_row, parent, false);  
	      return view;  
	    }  
	  }  
	
		//building contextual actionbar stuff
	
	private final class mActionModeCallback implements ActionMode.Callback {

		// Called when the action mode is created; startActionMode() was called
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			// Assumes that you have "contexual.xml" menu resources
			inflater.inflate(R.menu.programs_contextual_actionbar, menu);
			mode.setTitle("1 Giant ball sack");
			return true;
		}

		// Called each time the action mode is shown. Always called after
		// onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.miEdit:
				Toast.makeText(getActivity(), "Selected menu",
						Toast.LENGTH_LONG).show();
				mode.finish(); // Action picked, so close the CAB
				return true;
			default:
				return false;
			}
		}

		// Called when the user exits the action mode
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
		}
	};
	
	
	   //creating the options menu - actionbar
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
			inflater.inflate(R.menu.customize_actionbar, menu);
			
			super.onCreateOptionsMenu(menu, inflater);
			
		}
		//setting the actions for the actionbar icons
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.miCancel:
				Toast.makeText(getActivity(), "Cancel Bitch", Toast.LENGTH_SHORT)
						.show();
				break;
			case R.id.miAddCustom:
				Toast.makeText(getActivity(), "Have it your way", Toast.LENGTH_SHORT)
						.show();
				break;

			default:
				Toast.makeText(getActivity(), "You pressed some other shit", Toast.LENGTH_SHORT)
				.show();
				break;
			}

			return true;
		}
}
