package com.appsmarttech.utpro;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class Days_Fragment extends SherlockFragment{
	
	//Declarations
	DBHelper_activity dba;
	SQLiteDatabase db;
	ListView lvDays;
	Cursor cDays, cPrograms;
	SharedPreferences spPreferences;
	Editor ePreferences;
	String sActiveProgram, sDayName;
	Integer iActiveProgram, iProgramID, iImageVisible;
	ActionMode mActionMode;
	ActionBar actionBar;
	OnItemClickListener lvDaysListener;
	OnItemLongClickListener lvDaysLongListener;
	Boolean bActionPresent;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vDays = inflater.inflate(R.layout.days_fragment, container, false);
   	 	
        //open preferences
        spPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //declare preference editor
        ePreferences = spPreferences.edit();
        
        bActionPresent = false;
        
        //grabbing the active program from preferences
        sActiveProgram = spPreferences.getString("kActiveProgram", "0");
        
        //converting it to Integer
        iActiveProgram = Integer.valueOf(sActiveProgram);
   	 	
   	 	//assigning listview to listview widget
   	 	lvDays = (ListView)vDays.findViewById(R.id.lvDays);
   	 	
   	 	//telling it that it has an actionbar
   	 	setHasOptionsMenu(true);
   	 	
        //opening database
        db = (new DBHelper_activity(getActivity())).getWritableDatabase();
        
        //builing a cursor to get the program names 
        cPrograms = db.rawQuery("SELECT _id, programName FROM ProgramKey WHERE _id = " + iActiveProgram, null);
        cPrograms.moveToFirst();
        
        //setting activity title based on selected program
        getActivity().setTitle(cPrograms.getString(1));
        
        //closing program name cursor
        cPrograms.close();
        
        //building cursor - need to make this an object - JM needs to help
        cDays = db.rawQuery("SELECT DayOrder._id, DayOrder.programID, DayOrder.dayID, DayOrder.dayCompleted, " +
        		"DayKey.dayName, DayOrder.dayNumber FROM DayOrder JOIN DayKey ON " +
        		"DayOrder.dayID=DayKey._id WHERE programID = " + iActiveProgram, null);

    	//building the onclick listener for the lvPrograms Listview
        lvDaysListener = new OnItemClickListener() {

    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position,
    				long id) {

    		}
      	  
        };
        //building the long onclick listener for the lvPrograms listview
        
        lvDaysLongListener = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				//grabbing program name to update the action bar with
				//sProgramName = cPrograms.getString(1);
				//launching the contextual action bar
				mActionMode = getSherlockActivity().startActionMode(new mActionModeCallback());
				return true;
			}
        	
        };
        //setting click listener, long click listener, and adapter to the listview
        //lvDays.setOnItemClickListener(lvDaysListener);
        lvDays.setOnItemLongClickListener(lvDaysLongListener);
        lvDays.setAdapter(new adapter(getActivity(),cDays));
        
        //db.close();
   	 	return vDays;
    }
    
    
  //creating adapter for the list view
  	public class adapter extends CursorAdapter{  
  	    private Cursor mCursor;  
  	    private Context mContext;  
  	    LayoutInflater mInflater;  
  	    
  	  
  	    public adapter(Context context, Cursor cursor) {  
  	      super(context, cursor, true);  
  	      mInflater = LayoutInflater.from(context);  
  	      mContext = context;  
  	    }  
  	  
  	    @Override  
  	    public void bindView(View view, Context context, final Cursor cursor) {  
  	    	//declaring textviews and setting values from the cursor
  	      TextView tvDayName = (TextView)view.findViewById(R.id.tvDayName);
  	      tvDayName.setText(cursor.getString(cursor.getColumnIndex("DayKey.dayName")));
  	      
  	      TextView tvDayNumber = (TextView)view.findViewById(R.id.tvDayNumber);
  	      tvDayNumber.setText(cursor.getString(5));
  	      
  	      //declaring image views and assigning widgets
  	      ImageView ivCheck = (ImageView)view.findViewById(R.id.ivCheck);
  	      ImageView ivX = (ImageView)view.findViewById(R.id.ivX);
  	      
  	      //determining imageview visibility based on dayCompleted flag
  	      switch(cursor.getInt(3)){
  	      case 1:

  	    	  ivCheck.setVisibility(View.VISIBLE);
	    	  ivX.setVisibility(View.GONE);
	    	  break;
  	      case 2:
  	    	  ivCheck.setVisibility(View.GONE);
	    	  ivX.setVisibility(View.VISIBLE);
	    	  break;
	      default:
	    	  ivCheck.setVisibility(View.GONE);
	    	  ivX.setVisibility(View.GONE);
	    	  break;
  	      }
  	      
  	  
  	      }   
  	  
  	    @Override  
  	    public View newView(Context context, Cursor cDays, ViewGroup parent) {  
  	      final View view = mInflater.inflate(R.layout.days_row, parent, false);  
  	      return view;  
  	    }  
  	  }  
  	
	   //creating the actionbar
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
			inflater.inflate(R.menu.days_ab, menu);
			
			super.onCreateOptionsMenu(menu, inflater);
			
		}
		//creating the contextual action bar
		public final class mActionModeCallback implements ActionMode.Callback {

			// Called when the action mode is created; startActionMode() was called
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// Inflate a menu resource providing context menu items
				MenuInflater inflater = mode.getMenuInflater();
				// Assumes that you have "contexual.xml" menu resources
				inflater.inflate(R.menu.days_cab, menu);
				bActionPresent = true;
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
				case R.id.miComplete:

					db.execSQL("UPDATE DayOrder SET dayCompleted= 1 WHERE _id="+cDays.getInt(0));
					
					
					lvDays.invalidateViews();
					
					mode.finish(); // Action picked, so close the CAB
					return true;
				case R.id.miSkip:
					db.execSQL("UPDATE DayOrder SET dayCompleted= 2 WHERE _id="+cDays.getInt(0));
					
					
					lvDays.invalidateViews();
					
					mode.finish(); // Action picked, so close the CAB
					return true;
				default:
					return false;
				}
			}

			// Called when the user exits the action mode
			public void onDestroyActionMode(ActionMode mode) {
				bActionPresent = false;
				mActionMode = null;
			}

		}    
}
