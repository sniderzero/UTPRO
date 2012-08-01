package com.appsmarttech.utpro;

import java.io.IOException;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.net.Uri;
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
		inflater.inflate(R.menu.settings_ab, menu);
		
		super.onCreateOptionsMenu(menu, inflater);
		
	}
	//setting the actions for the actionbar icons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.miEmail:
			sendEmail();
			break;
		case R.id.miShare:
			shareApp();
			break;
		case R.id.miWeb:
			launchWebPage();
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
    
    public void sendEmail(){
    	Intent intEmail = new Intent(android.content.Intent.ACTION_SEND);
    	intEmail.setType("message/rfc822");
    	intEmail.putExtra(android.content.Intent.EXTRA_EMAIL, "support@appsmarttech.com");
    	intEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, "UltiTrack 90 - Support");
    	startActivity(intEmail);
    }
    
    public void shareApp(){
    	Intent intShare = new Intent(android.content.Intent.ACTION_SEND);
    	intShare.setType("text/plain");
    	intShare.putExtra(android.content.Intent.EXTRA_TEXT, "Check out UltiTrack 90, it's the shit!  http://www.ultitrack.net");
    	startActivity(Intent.createChooser(intShare, "Share UltiTrack 90 :"));
    }
    
    public void launchWebPage(){
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
    	startActivity(browserIntent);
    }
}
