package com.appsmarttech.utpro;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RepDetail_Fragment extends SherlockFragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
   	 	View vExercises = inflater.inflate(R.layout.repdetail_fragment, container, false);
   	 	return vExercises;
	}
}