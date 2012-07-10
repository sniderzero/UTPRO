package com.appsmarttech.utpro;

import java.io.IOException;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import android.os.Bundle;

public class main_activity extends SherlockFragmentActivity {
	
	//Declarations
	DBHelper_activity db;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("UltiTrack Pro");
        db = new DBHelper_activity(this);
        try {
			db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }


    
}
