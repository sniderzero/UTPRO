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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;

public class Days_Fragment extends SherlockFragment{
	
	//Declarations
	SQLiteDatabase db;
	ListView lvDays;
	Cursor cDays;
	SharedPreferences spPreferences;
	Editor ePreferences;
	String sActiveProgram, sDayName;
	Integer iActiveProgram, iProgramID;
	ActionMode mActionMode;
	ActionBar actionBar;
	OnItemClickListener lvDaysListener;
	OnItemLongClickListener lvDaysLongListener;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vDays = inflater.inflate(R.layout.daylist_fragment, container, false);
   	 	
        //open preferences
        spPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //declare preference editor
        ePreferences = spPreferences.edit();
        
        //setting active program for testing purposes
        //ePreferences.putString("kActiveProgram", "1");
        //ePreferences.commit();

        //grabbing the active program from preferences
        sActiveProgram = spPreferences.getString("kActiveProgram", "0");
        
        //converting it to Integer
        iActiveProgram = Integer.valueOf(sActiveProgram);
   	 	
      //setting activity title
   	 	getActivity().setTitle("Select Program");
   	 	
   	 	//assigning listview to listview widget
   	 	lvDays = (ListView)vDays.findViewById(R.id.lvDays);
   	 	
        //opening database
        db = (new DBHelper_activity(getActivity())).getWritableDatabase();
        
        //building cursor - need to make this an object - JM needs to help
        cDays = db.rawQuery("SELECT DayKey._id, DayOrder.programID, DayOrder.dayID, DayOrder.dayCompleted, " +
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
				//mActionMode = getSherlockActivity().startActionMode(new mActionModeCallback());
				return true;
			}
        	
        };
        //setting click listener, long click listener, and adapter to the listview
        //lvDays.setOnItemClickListener(lvDaysListener);
        //lvDays.setOnItemLongClickListener(lvDaysLongListener);
        lvDays.setAdapter(new adapter(getActivity(),cDays));
        
        db.close();
   	 	return vDays;
    }
    
    
  //creating adapter for the list view
  	public class adapter extends CursorAdapter{  
  	    private Cursor mCursor;  
  	    private Context mContext;  
  	    LayoutInflater mInflater;  
  	    
  	  
  	    public adapter(Context context, Cursor cDays) {  
  	      super(context, cDays, true);  
  	      mInflater = LayoutInflater.from(context);  
  	      mContext = context;  
  	    }  
  	  
  	    @Override  
  	    public void bindView(View view, Context context, final Cursor cursor) {  
  	      TextView tvDayName = (TextView)view.findViewById(R.id.tvDayName);
  	      tvDayName.setText(cursor.getString(cursor.getColumnIndex("DayKey.dayName")));
  	  
  	      }   
  	  
  	    @Override  
  	    public View newView(Context context, Cursor cDays, ViewGroup parent) {  
  	      final View view = mInflater.inflate(R.layout.daylist_row, parent, false);  
  	      return view;  
  	    }  
  	  }  
}
    
    
