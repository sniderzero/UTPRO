package com.appsmarttech.utpro;

import java.util.Calendar;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.Dialog;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        actionBar.setHomeButtonEnabled(true);

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
				/* //setting iActiveProgram to the selected item ID
    			iActiveBand = bsSelected.getSetID();
    	        //converting to a string
    	        sActiveBand = String.valueOf(iActiveBand);
    	        //storing in preferences
    	        ePreferences.putString("kActiveBandSet", sActiveBand);
    	        ePreferences.commit(); 
    	        //refreshing the listview
    	        lvBandSet.invalidateViews(); */
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
	 
			//grab current bandset
			bsSelected = getItem(position);
			
			//setting text of bandset
			tvBandSetName.setText(bsSelected.getSetName());
			
			//retrieving the program ID from the object
			iBandSetID = bsSelected.getID();
			if(iBandSetID == iActiveBand){
				((TextView)rowView.findViewById(R.id.tvActive)).setVisibility(View.VISIBLE);
			}
			else{
				((TextView)rowView.findViewById(R.id.tvActive)).setVisibility(View.INVISIBLE);
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
        /*Button btnOK = (Button) dialog.findViewById(R.id.btnDateOK);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnDateCancel);
        
        btnOK.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
           
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        	dialog.dismiss();
            }
        }); */
        
        
        
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
    			TextView tvBandColor = (TextView)rowView.findViewById(R.id.tvColor);
    			TextView tvBandWeight = (TextView)rowView.findViewById(R.id.tvWeight);
    			ImageView ivColor = (ImageView)rowView.findViewById(R.id.ivColor);
    	 
    			//grab current band
    			 Band bSelected = getItem(position);
    			
    			//setting text of bands
    			tvBandColor.setText(bSelected.getColor());
    			tvBandWeight.setText(String.valueOf(bSelected.getWeight()));
    			ivColor.setImageResource(R.drawable.pink);
    			
    			if(tvBandColor.getText()=="nothing"){
    				tvBandColor.setVisibility(View.GONE);
    				tvBandWeight.setVisibility(View.GONE);
    			}
    			
    		    return rowView;
    		}
    	}
    	BandArrayAdapter lvBandAdapter = new BandArrayAdapter(getActivity(),Bands);
    	lvBands.setAdapter(lvBandAdapter); 
    	dialog.show();

    }

}
