package com.appsmarttech.utpro;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class History_Fragment extends SherlockFragment{
	//declarations
	List<Stat> Stats;
	DBHelper_activity db;
	Stat sSelected;
	ListAdapter lvHistoryAdapter;
	int e;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vHistory = inflater.inflate(R.layout.history_fragment, container, false);
   	 	//initializing db
   	 	db = (new DBHelper_activity(getActivity()));
   	 	//initialize listview widget
   	 	ListView lvHistory = (ListView)vHistory.findViewById(R.id.lvHistory);
   	 	//grab exerID passed from the activity
   	 	e = getArguments().getInt("kExerID");
   	 	//grabbing list of stats from exercise passed by activity 
   	 	Stats = db.getExerciseStats(e); //using 1 for testing purposes
   	 	//setting up adapter
        lvHistoryAdapter = new HistoryArrayAdapter(getActivity(),Stats);

   	 	//inflating header to use it
   	 	LayoutInflater iHeader = (LayoutInflater) getActivity()
  				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   	 	View header = iHeader.inflate(R.layout.history_rowheader, lvHistory, false);
   	 	lvHistory.addHeaderView(header);
   	 	//setting adapter to lvHistory
   	 	lvHistory.setAdapter(lvHistoryAdapter);
   	 	
   	 	return vHistory;
   	 	
	}
	
	 //creating custom listview adapter for days
  	public class HistoryArrayAdapter extends ArrayAdapter<Stat> {
  		private final Context context;
  		
  	 
  		public HistoryArrayAdapter(Context context, List<Stat> values) {
  			super(context, R.layout.history_row, values);
  			this.context = context;
  			
  		}
  	 
  		@Override
  		public View getView(int position, View convertView, ViewGroup parent) {
  			LayoutInflater inflater = (LayoutInflater) context
  				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  	 
  			View rowView = inflater.inflate(R.layout.history_row, parent, false);
  			//declaring textview widgets in the row
  			TextView tvReps = (TextView)rowView.findViewById(R.id.tvReps);
  			TextView tvWeight = (TextView)rowView.findViewById(R.id.tvWeight);
  			TextView tvTime = (TextView)rowView.findViewById(R.id.tvTime);
  			TextView tvDate = (TextView)rowView.findViewById(R.id.tvDate);
  			TextView tvBand = (TextView)rowView.findViewById(R.id.tvBand);
  			TextView tvNotes = (TextView)rowView.findViewById(R.id.tvNotes);
  	 	 
  	 
  			//grab current stat
  			sSelected = getItem(position);
  			
  			//setting text of day name and day number
  			tvReps.setText(String.valueOf(sSelected.getReps()));
  			tvWeight.setText(String.valueOf(sSelected.getWeight()));
  			tvTime.setText(String.valueOf(sSelected.getTime()));
  			tvDate.setText(sSelected.getDate());
  			tvBand.setText(String.valueOf(sSelected.getBandID()));
  			tvNotes.setText(sSelected.getNotes());
  			
    	      
  			return rowView;
  		}
  	} 
}
