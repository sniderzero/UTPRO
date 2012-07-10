package com.appsmarttech.utpro;

import java.io.IOException;

import com.actionbarsherlock.app.SherlockFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Main_Fragment extends SherlockFragment {
	
	//Declarations
	DBHelper_activity db;
	Button bProgramList;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   	 	View vMain = inflater.inflate(R.layout.fragment_main, container, false);
   	 	//setting title of activity
   	 	getActivity().setTitle("UltiTrack Pro");
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
    
    public void mProgramSelect(View v){
    	Intent in = new Intent(getActivity(), Programs_Activity.class);
    	startActivity(in);
    	
    }
}
