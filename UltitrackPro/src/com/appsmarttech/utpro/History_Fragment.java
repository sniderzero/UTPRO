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
	int e, iExerType;
	TextView tvReps, tvWeight, tvTime, tvBand, tvNotes;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vHistory = inflater.inflate(R.layout.history_fragment, container, false);
   	 	//initializing db
   	 	db = (new DBHelper_activity(getActivity()));
   	 	//initialize widgets
   	 	ListView lvHistory = (ListView)vHistory.findViewById(R.id.lvHistory);
   	 	tvTime = (TextView)vHistory.findViewById(R.id.tvTime);
   	 	tvReps = (TextView)vHistory.findViewById(R.id.tvRep);
   	 	tvWeight = (TextView)vHistory.findViewById(R.id.tvWeight);
   	 	tvBand = (TextView)vHistory.findViewById(R.id.tvBand);
   	 	tvNotes = (TextView)vHistory.findViewById(R.id.tvNotes);
   	 	//grab exerID and exerType passed from the activity
   	 	e = getArguments().getInt("kExerID");
   	 	iExerType = getArguments().getInt("kExerType");
   	 	//grabbing list of stats from exercise passed by activity 
   	 	Stats = db.getExerciseStats(e); 
   	 	//setting up adapter
        lvHistoryAdapter = new HistoryArrayAdapter(getActivity(),Stats);

   	 	//setting adapter to lvHistory
   	 	lvHistory.setAdapter(lvHistoryAdapter);
   	 	
   	 	//hiding certain fields based on the exercise type
   	 	switch(iExerType){
   	 	case 1:
   	 		tvTime.setVisibility(View.GONE);
   	 		break;
   	 		
   	 	case 2:
   	 		tvReps.setVisibility(View.GONE);
   	 		tvWeight.setVisibility(View.GONE);
   	 		tvBand.setVisibility(View.GONE);
   	 	}
   	 	
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
  			
  			//hiding them based on the exercise type
  			switch(iExerType){
  			case 1:
  				tvTime.setVisibility(View.GONE);
  				break;
  			case 2:
  				tvReps.setVisibility(View.GONE);
  				tvWeight.setVisibility(View.GONE);
  				tvBand.setVisibility(View.GONE);
  				break;
  			}
  			
    	      
  			return rowView;
  		}
  	} 
}
