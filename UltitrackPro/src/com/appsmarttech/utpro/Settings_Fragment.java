package com.appsmarttech.utpro;

import java.io.IOException;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Settings_Fragment extends SherlockFragment {
	
	//Declarations
	DBHelper_activity db;
	Button bProgramList;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   	 	View vMain = inflater.inflate(R.layout.settings_fragment, container, false);
   	 	
   	 	//telling it that it has an actionbar
   	 	setHasOptionsMenu(true);
   	 	
   	 	//copying the shipped database if it's not already there
        db = new DBHelper_activity(getActivity());
        try {
			db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //declare buttons
        bProgramList = (Button) vMain.findViewById(R.id.button2);
        
        //set onclick listeners for buttons  (likely to be images later)
        bProgramList.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				mProgramSelect(v);
				
			}
        	
        });
        return vMain;
    }
    
    //creating the options menu - actionbar
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		inflater.inflate(R.menu.main_actionbar, menu);
		
		super.onCreateOptionsMenu(menu, inflater);
		
	}
	//setting the actions for the actionbar icons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.miCancel:
			Toast.makeText(getActivity(), "You Pressed Cancel - DICK", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.miMeasurements:
			Toast.makeText(getActivity(), "You want to weight your fat ass", Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			Toast.makeText(getActivity(), "You pressed some other shit", Toast.LENGTH_SHORT)
			.show();
			break;
		}

		return true;
	}

    //method activated when user selects the program select button
    public void mProgramSelect(View v){
    	Intent in = new Intent(getActivity(), Programs_Activity.class);
    	startActivity(in);
    	
    }
}
