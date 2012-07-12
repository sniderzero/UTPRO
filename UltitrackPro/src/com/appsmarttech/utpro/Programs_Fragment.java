package com.appsmarttech.utpro;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Programs_Fragment extends SherlockFragment {
	
	//Declarations
		SQLiteDatabase db;
		ListView lvPrograms;
		Cursor cPrograms;
		
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vPrograms = inflater.inflate(R.layout.programs_fragment, container, false);
   	 	
   	 	//telling it that it has an actionbar
   	 	setHasOptionsMenu(true);
   	 	
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
	    public void bindView(View view, Context context, Cursor cursor) {  
	      TextView tvProgramName = (TextView)view.findViewById(R.id.tvProgramName);  
	      tvProgramName.setText(cursor.getString(cursor.getColumnIndex("trackName")));
	  
 
	      /*
	      if(cursor.isNull(7)){
	    	  ((ImageView)view.findViewById(R.id.imgCheck)).setVisibility(View.INVISIBLE);
	      }
	      else{
	    	  ((ImageView)view.findViewById(R.id.imgCheck)).setVisibility(View.VISIBLE);
	      } */
	      }  
	  
	    @Override  
	    public View newView(Context context, Cursor cPrograms, ViewGroup parent) {  
	      final View view = mInflater.inflate(R.layout.programs_row, parent, false);  
	      return view;  
	    }  
	  }  
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
