package com.appsmarttech.utpro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class Days_Fragment extends SherlockFragment{
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vDays = inflater.inflate(R.layout.daylist_fragment, container, false);
   	 	
   	 	return vDays;
    }
}
    
    
