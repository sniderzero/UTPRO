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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
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
		String sActiveProgram, sProgramName;
		Integer iActiveProgram, iProgramID;
		ActionMode mActionMode;
		ActionBar actionBar;
		OnItemClickListener lvProgramListener;
		OnItemLongClickListener lvProgramLongListener;
		
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vPrograms = inflater.inflate(R.layout.programs_fragment, container, false);
   	 	
        //open preferences
        spPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //declare preference editor
        ePreferences = spPreferences.edit();
        
        //setting active program for testing purposes
        ePreferences.putString("kActiveProgram", "1");
        ePreferences.commit();

        //grabbing the active program from preferences
        sActiveProgram = spPreferences.getString("kActiveProgram", "0");
        
        //converting it to Integer
        iActiveProgram = Integer.valueOf(sActiveProgram);
        
        //changing action bar settings
        actionBar = getSherlockActivity().getSupportActionBar();
        
        actionBar.setHomeButtonEnabled(true);
        
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

    	//building the onclick listener for the lvPrograms Listview
        lvProgramListener = new OnItemClickListener() {

    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position,
    				long id) {
    			//setting iActiveProgram to the selected item
    			iActiveProgram = cPrograms.getInt(cPrograms.getColumnIndex("_id"));
    	        //converting to a string
    	        sActiveProgram = String.valueOf(iActiveProgram);
    	        //storing in preferences
    	        ePreferences.putString("kActiveProgram", sActiveProgram);
    	        ePreferences.commit(); 
    	        //refreshing the listview
    	        lvPrograms.invalidateViews();
    		}
      	  
        };
        //building the long onclick listener for the lvPrograms listview
        
        lvProgramLongListener = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				//grabbing program name to update the action bar with
				sProgramName = cPrograms.getString(1);
				//launching the contextual action bar
				mActionMode = getSherlockActivity().startActionMode(new mActionModeCallback());
				return true;
			}
        	
        };
        //setting click listener, long click listener, and adapter to the listview
        lvPrograms.setOnItemClickListener(lvProgramListener);
        lvPrograms.setOnItemLongClickListener(lvProgramLongListener);
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
			mode.setTitle(sProgramName);
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
	
	

	
	   //creating the actionbar
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

			case R.id.miAccept:
				Toast.makeText(getActivity(), String.valueOf(iActiveProgram), Toast.LENGTH_SHORT)
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
