package com.appsmarttech.ut90;

import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuItem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BandSet_Fragment extends SherlockFragment {
	
	//Declarations
		DBHelper_activity db;
		ListView lvBandSet;
		Cursor cPrograms;
		SharedPreferences spPreferences;
		Editor ePreferences;
		String sActiveBand, sProgramName;
		Integer iActiveBand, iBandSetID;
		ActionMode mActionMode;
		ActionBar actionBar;
		OnItemClickListener lvBandSetListener;
		OnItemLongClickListener lvProgramLongListener;
		Boolean bActionPresent;
		ListAdapter lvBandSetAdapter;
		BandSet bsSelected;
		
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vBandSet = inflater.inflate(R.layout.bandset_fragment, container, false);
   	 	
        //open preferences
        spPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //declare preference editor
        ePreferences = spPreferences.edit();
        
        //grabbing the active program from preferences
        sActiveBand = spPreferences.getString("kActiveBandSet", "0");
        
        //converting it to Integer
        iActiveBand = Integer.valueOf(sActiveBand);
        
        //changing action bar settings
        actionBar = getSherlockActivity().getSupportActionBar();
        
        //sets home button as enabled (the app icon in the left corner)
        setHasOptionsMenu(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //setting activity title
   	 	getActivity().setTitle("Select Bands");
   	 	
   	 	//assigning listview to listview widget
   	 	lvBandSet = (ListView)vBandSet.findViewById(R.id.lvBandSet);
   	 	
        //declearing database helper
        db = (new DBHelper_activity(getActivity()));

        //building list of programs
        List<BandSet> BandSets = db.getAllBandSets();

    	//building the onclick listener for the lvPrograms Listview
        lvBandSetListener = new OnItemClickListener() {

    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position,
    				long id) {

    			//grabbing the selected item from lvPrograms
    			bsSelected = (BandSet) (lvBandSet.getItemAtPosition(position));
    			View v = null;
				
    	        //going to list of days
    	        //startActivity(inDays);
    			diaglogBands(v);
    		}
      	  
        };

        //setting up adapter
        lvBandSetAdapter = new BandSetArrayAdapter(getActivity(),BandSets);
        	
        //setting click listener, long click listener, and adapter to the listview
        lvBandSet.setOnItemClickListener(lvBandSetListener);
        lvBandSet.setAdapter(lvBandSetAdapter);
        
        return vBandSet;
    }

	
	//creating custom listview adapter for the bandsets
	public class BandSetArrayAdapter extends ArrayAdapter<BandSet> {
		private final Context context;
		
	 
		public BandSetArrayAdapter(Context context, List<BandSet> values) {
			super(context, R.layout.bandset_row, values);
			this.context = context;
			
		}
	 
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View rowView = inflater.inflate(R.layout.bandset_row, parent, false);
			TextView tvBandSetName = (TextView)rowView.findViewById(R.id.tvBandSetName);
			TextView tvNumberOfBands = (TextView)rowView.findViewById(R.id.tvNumberOfBands);
			TextView tvFirstWeight = (TextView)rowView.findViewById(R.id.tvFirstWeight);
			TextView tvLastWeight = (TextView)rowView.findViewById(R.id.tvLastWeight);
	 
			//grab current bandset
			bsSelected = getItem(position);
			
			 //grab the band details
			 int[] aryBandInfo = db.getBandInfo(bsSelected.getSetID());
			 

			//setting text of bandset
			tvBandSetName.setText(bsSelected.getSetName());
			
			//setting text of bands
			tvNumberOfBands.setText(String.valueOf(aryBandInfo[0]));
			tvFirstWeight.setText(String.valueOf(aryBandInfo[1]));
			tvLastWeight.setText(String.valueOf(aryBandInfo[2]));
			
			//marking the active bandset 
			iBandSetID = bsSelected.getSetID();
			if(iBandSetID == iActiveBand){
				((ImageView)rowView.findViewById(R.id.ivActive)).setVisibility(View.VISIBLE);
			}
			else{
				((ImageView)rowView.findViewById(R.id.ivActive)).setVisibility(View.INVISIBLE);
			}

			return rowView;
		}
	}
	
	//dialog box for choosing exercise date
    public void diaglogBands(View v){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.bandset_dialog);
        dialog.setTitle("Bands in " + bsSelected.getSetName());
        dialog.setCancelable(true);
        //grabbing the list of bands
        List<Band> Bands = db.getAllSetBands(bsSelected.getSetID());
        //declaring the listview
        ListView lvBands = (ListView) dialog.findViewById(R.id.lvBands);
        //declare dialog buttons
        Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        
        btnOK.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        	//setting iActiveProgram to the selected item ID
			iActiveBand = bsSelected.getSetID();
	        //converting to a string
	        sActiveBand = String.valueOf(iActiveBand);
	        //storing in preferences
	        ePreferences.putString("kActiveBandSet", sActiveBand);
	        ePreferences.commit(); 
	        //refreshing the listview
	        lvBandSet.invalidateViews(); 
	        dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        	dialog.dismiss();
            }
        }); 
        
        
        
      //creating custom listview adapter for the bandsets
    	class BandArrayAdapter extends ArrayAdapter<Band> {
    		private final Context context;
    		
    	 
    		public BandArrayAdapter(Context context, List<Band> values) {
    			super(context, R.layout.bands_row, values);
    			this.context = context;
    			
    		}
    	 
    		@Override
    		public View getView(int position, View convertView, ViewGroup parent) {
    			LayoutInflater inflater = (LayoutInflater) context
    				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	 
    			View rowView = inflater.inflate(R.layout.bands_row, parent, false);
    			TextView tvBandWeight = (TextView)rowView.findViewById(R.id.tvWeight);
    			
    			
    			//grab current band
    			 Band bSelected = getItem(position);
    			 
    			 //setting band weight
    			 tvBandWeight.setText(String.valueOf(bSelected.getWeight()));

    			//setting the band colors
    			String mDrawableName = bSelected.getColor();
    			int resID = getResources().getIdentifier(mDrawableName , "drawable", getActivity().getPackageName());
    			Drawable myIcon = getResources().getDrawable(resID);
    			myIcon.setBounds(0,0,45,45);
    			tvBandWeight.setCompoundDrawables(myIcon, null, null, null);
    			

    			
    		    return rowView;
    		}
    	}
    	BandArrayAdapter lvBandAdapter = new BandArrayAdapter(getActivity(),Bands);
    	lvBands.setAdapter(lvBandAdapter); 
    	dialog.show();

    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			
		default:
			Intent inDashboard = new Intent(getActivity(), Preferences_Activity.class);
			startActivity(inDashboard);
			break;
		}

		return true;
	}

}
