package com.appsmarttech.utpro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class TimeDetail_Fragment extends SherlockFragment{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vTime = inflater.inflate(R.layout.timedetail_fragment, container, false);
   	 	
   	 	return vTime;

}
}